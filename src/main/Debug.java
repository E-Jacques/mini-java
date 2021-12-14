package main;

import java.io.PrintWriter;

/** Running Options and helper for main(). */
public final class Debug {
  /** Argument par défaut du main. Fichier à compiler. */
  public static final String FILE = "input.txt";
  /** Impression des Tokens lus par le parser. */
  public static final boolean TOKEN = false;
  /** Trace d’exécution de l'automate LR. */
  public static final boolean PARSE = false;
  /** Impression de l'AST. */
  public static final boolean TREE = false;
  /** Ajout des "Locations" dans l'impression de l'AST. */
  public static final boolean TREELOCATION = false;
  /** PrettyPrint minijava par visite de l'AST. */
  public static final boolean PRETTY = false;
  /** Impression de la table des symboles. */
  public static final boolean SYMTAB = false;
  /** Impression des Variables non utilisées. */
  public static final boolean UNUSED = false;
  /** Impression de la forme intermédiaire. */
  public static final boolean INTERMED = false;
  /** Dump de l'allocation Mémoire. */
  public static final boolean ALLOCATOR = true;
  /** Exécution avec Mars du résultat de la compilation. */
  public static final boolean RUNMARS = true;

  public static final boolean TESTINDENT = false;
  public static final boolean TESTSCOPE = false;
  public static final boolean TESTFUSION = false;

  /** Flots d'impressions. */
  public static final PrintWriter PW = new PrintWriter(System.out, true);
  /** Flots d'impressions en erreur. */
  public static final PrintWriter PWERR = new PrintWriter(System.err, true);

  /** No constructeur.
   * @throws IllegalStateException Ce constructeur est interdit */
  private Debug() { throw new IllegalStateException("Utility class"); }

  /** Remplacement de "System.out.println()".
   * @param o L'objet à imprimer */
  public static void log(final Object o) { PW.println(o.toString()); }
  /** Remplacement de "System.err.println()".
   * @param o L'objet à imprimer */
  public static void logErr(final Object o) { PWERR.println(o.toString()); }

  /** Fin provisoire de compilation pour cause de travaux.
   * @throws CompilerException Fin de comilation */
  public static void toBeContinued() {
    throw new CompilerException("To Be Continued");
  }

}
