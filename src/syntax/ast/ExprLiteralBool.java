package syntax.ast;

/** Constante Booléenne.
 * <ul><li>{@link #value} </ul>*/
public class ExprLiteralBool extends Expr {
  /** La valeur booléenne. */
  public final Boolean value;

  /** Constructeur.
   * @param value La valeur booléenne */
  public ExprLiteralBool(final Boolean value) {
    this.value = value;
  }

  /** Constructeur alternatif.
   * @param s La chaine "true" ou "false" */
  public ExprLiteralBool(final String s) {
    this.value = Boolean.parseBoolean(s);
  }

  @Override
  public String toString() { return super.toString() + " " + value; }

  public void accept(final AstVisitor v) { v.visit(this); }
}
