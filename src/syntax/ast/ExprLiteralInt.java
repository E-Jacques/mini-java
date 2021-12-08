package syntax.ast;

/** Constante Entière.
 * <ul><li>{@link #value} </ul>*/
public class ExprLiteralInt extends Expr {
  /** La valeur entière. */
  public final Integer value;

  /** Constructeur.
   * @param value La valeur entière */
  public ExprLiteralInt(final Integer value) { this.value = value; }

  /** Constructeur altérnatif.
   * @param s La chaîne d'une constante entière Java */
  public ExprLiteralInt(final String s) { this.value = Integer.parseInt(s); }

  @Override
  public String toString() { return super.toString() + " " + value; }

  public void accept(final AstVisitor v) { v.visit(this); }
}
