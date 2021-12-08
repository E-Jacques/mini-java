package codegen;

import intermediate.IR;
import main.Debug;

/** Génération du code assembleur. */
public class CodeGen {
  /** Le nom du fichier de sortie. ~= "infile.mios"*/
  private String outfile;

  /** Constructeur.
   * @param ir La forme intermédiaire
   * @param infile Le nom de fichier source */
  public CodeGen(final IR ir, final String infile) {
    // outfile = "basneame(infile).mips"
    final int dot = infile.lastIndexOf('.');
    this.outfile = (dot == -1) ? infile : infile.substring(0, dot);
    this.outfile = this.outfile + ".mips";

    // open a "MIPSWriter"
    final MipsWriter mipsWriter = new MipsWriter(outfile);

    Debug.log("= Allocation Mémoire");
    final Allocator allocator = new Allocator(ir);

    Debug.log("= Traduction IR to MIPS -> " + outfile);
    new ToMips(ir, allocator, mipsWriter);

    Debug.log("= Édition de lien : " + outfile + " -> " + outfile);
    new LinkRuntime(mipsWriter);

    // close Writer
    mipsWriter.close();
  }

  /** Get the result.
   * @return Le nom du fichier MIPS généré */
  public String getResult() { return outfile; }

}
