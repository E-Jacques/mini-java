package main;

/** Classe utilitaire pour impression avec indentation
 * et utilisation de StringBuffer.
 * <br> <code>iw=new IdentWrite();
 * <br> iw.println(....); iw.print(...); iw.println();
 * <br> iw.indent(); iw.print(...); iw.println(...); iw.outdent();
 * <br> System.out.print(iw);
 * </code> */
public class IndentWriter {
  /** La tabulation. */
  private static final String TAB = "  ";

  /** Le StringBuilder pour impression. */
  private StringBuilder sb;

  /** L'indentation courante. */
  private int indent;

  /** La prochaine impression commence une ligne. */
  private boolean startOfLine;

  /** Constructeur. */
  public IndentWriter() {
    this.sb = new StringBuilder();
    this.indent = 0;
    this.startOfLine = true;
  }

  /** Le résultat de l'impression. */
  @Override
  public String toString() { return sb.toString(); }

  /** Augmenter l'indentation. */
  public void indent() { indent++; }

  /** Réduire l'indentation. */
  public void outdent() { indent--; }

  /** Imprimer un Object ( toString()).
   * @param o L'objet à imprimer */
  public void print(final Object o) { print(o.toString()); }

  /** Imprimer une String.
   * @param s La chaîne à imprimer */
  public void print(final String s) {
    if (startOfLine) for (int i = 0; i < indent; i++) { sb.append(TAB); }
    startOfLine = false;
    sb.append(s);
  }

  /** Imprimer un Object et NewLine.
    * @param o L'objet à imprimer */
  public void println(final Object o) { println(o.toString()); }

  /** Imprimer une String et NewLine.
   * @param s La chaîne à imprimer */
  public void println(final String s) { print(s); println(); }

  /** Imprimer NewLine. */
  public void println() {
    sb.append(System.lineSeparator());
    startOfLine = true;
  }
}
