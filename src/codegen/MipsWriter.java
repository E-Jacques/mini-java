package codegen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.CompilerException;

/** Classe utilitaire d'impression MIPS, "Etend" PrintWriter. */
public class MipsWriter {
  /** La virgule MIPS.*/
  private static final String SEP = ", ";

  /** Le writer pour l'impression. */
  private PrintWriter pw;

  /** Constructeur.
   * @param outfile Le nom du fichier en sortie
   * @throws CompilerException Fin de compilation sur problème Entrée/Sortie */
  public MipsWriter(final String outfile) {
    try {
      this.pw = new PrintWriter(new FileWriter(outfile));
    } catch (IOException e) {
      throw new CompilerException(e.getMessage());
    }
  }

  /** Fermeture propre du writer. */
  public void close() { pw.close(); }

  /** Print a full Line.
   * @param s La ligne MIPS */
  public void println(final String s) { if (s != null) pw.println(s); }

  /** Print an indented MIPS instruction.
   * @param s La Ligne MIPS */
  public void inst(final String s) { if (s != null) pw.println("    " + s); }

  /** Print an indented MIPS comment.
   * @param s Le commentaire */
  public void com(final String s) { if (s != null) pw.println("  ## " + s); }

  /** Print a MIPS label.
   * @param s Le label MIPS */
  public void label(final String s) { if (s != null) pw.println(s + ":"); }

  // Des instructions prédéfinies
  // Jumps
  /** Jump : to name .
   * @param name Le nom de label pour le saut */
  public void jump(final String name) {
    inst("j    " + name);
  }

  /** Jump Conditionnel : if Not (r0) jump to name.
   * @param r0 Le registre de condition
   * @param name Le label de saut */
  public void jumpIfNot(final Reg r0, final String name) {
    inst("beq  " + r0 + SEP + "$zero" + SEP + name);
  }

  /** Jump : to function. $ra= ...
   * @param name Le label de fonction */
  public void jumpIn(final String name) {
    inst("jal  " + name);
  }

  /** Jump : function return. */
  public void jumpOut() {
    inst("jr $ra");
  }

  // Load/Store/move registers
  /** Load r0 with immediate.
   * @param r0 Le registre à charger
   * @param immediate La constante entière*/
  public void load(final Reg r0, final int immediate) {
    inst("li   " + r0 + SEP + immediate);
  }

  /** Load r0 from address r1 + offset .
   * @param r0 Le registre à charger
   * @param r1 Le registre d'adresse
   * @param offset L'offset de l'adresse */
  public void loadOffset(final Reg r0, final int offset, final Reg r1) {
    inst("lw   " + r0 + SEP + offset + "(" + r1 + ")");
  }

  /** Store r0 to address r1 + offset .
   * @param r0 Le registre à sauver
   * @param r1 Le registre d'adresse
   * @param offset L'offset de l'adresse */
  public void storeOffset(final Reg r0, final int offset, final Reg r1) {
    inst("sw   " + r0 + SEP + offset + "(" + r1 + ")");
  }

  /** Move/Cppy : {@code r0 = r1 } .
   * @param r0 Le registre à charger
   * @param r1 Le registre à sauver */
  public void move(final Reg r0, final Reg r1) {
    inst("move " + r0 + SEP + r1); // pseudo MIPS
    // inst("addiu "+ r0 +SEP + r1 + SEP +"0"); // real MIPS
  }

  // Opérations
  /** Oper : {@code r0 = r0 + r1 }.
   * @param r0 Le registre oérande et resultat
   * @param r1 Le deuxième registre opérande */
  public void plus(final Reg r0, final Reg r1) {
    inst("add  " + r0 + SEP + r0 + SEP + r1);
  }

  /** Oper : {@code r0 = r0 + immediate }.
   * @param r0 Le registre opérande et résultat
   * @param immediate La constante entière */
  public void plus(final Reg r0, final int immediate) {
    if (immediate == 0) return;  // + 0 est imediat !!
    inst("addi " + r0 + SEP + r0 + SEP + immediate);
  }

  /** Oper : {@code r0 = r0 - r1 }.
   * @param r0 Le registre opérande et résultat
   * @param r1 Le deuxième registre opérande */
  public void moins(final Reg r0, final Reg r1) {
    inst("sub  " + r0 + SEP + r0 + SEP + r1);
  }

  /** Oper : {@code r0 = r0 * r1 }.
  * @param r0 Le registre opérande et résultat
  * @param r1 Le deuxième registre opérande */
  public void fois(final Reg r0, final Reg r1) {
    inst("mult " + r0 + SEP + r1);
    inst("mflo " + r0);
  }

  /** Oper : {@code r0 = 4 * r0 }.
   * @param r0 Le registre opérande et résultat */
  public void fois4(final Reg r0) {
    inst("sll  " + r0 + SEP + r0 + SEP + "2");
  }

  /** Oper : {@code r0 = r0 && r1 }.
  * @param r0 Le registre opérande et résultat
  * @param r1 Le deuxième registre opérande */
  public void et(final Reg r0, final Reg r1) {
    inst("and  " + r0 + SEP + r0 + SEP + r1);
  }

  /** Oper : {@code r0 = (r0 < r1) }.
    * @param r0 Le registre opérande et résultat
    * @param r1 Le deuxième registre opérande */
  public void inferieur(final Reg r0, final Reg r1) {
    inst("slt  " + r0 + SEP + r0 + SEP + r1);
  }

  /** Oper : {@code r0 = ! r0 }.
    * @param r0 Le registre opérande et résultat */
  public void not(final Reg r0) {
    inst("seq  " + r0 + SEP + "$zero" + SEP + r0);
  }

  /** Magic inRange : {@code r0 = ( 0 <= r1 < r2 ) }.
   * @param r0 Le registre résultat
   * @param r1 Le registre opérande 1
   * @param r2 Le registre opérande 2 */
  public void inRange(final Reg r0, final Reg r1, final Reg r2) {
    inst("sltu " + r0 + SEP + r1 + SEP + r2);
  }
}
