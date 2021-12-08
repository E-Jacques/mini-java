package syntax.ast;

/** Opérateur Binaire.
 * <ul><li>{@link #expr1}
 * <li>{@link #op}
 * <li>{@link #expr2} </ul> */
public class ExprOpBin extends Expr {
  /** L'opérande gauche. */
  public final Expr expr1;
  /** L'opérateur. */
  public final main.EnumOper op;
  /** L'opérande droite. */
  public final Expr expr2;

  /** Constructeur.
   * @param expr1 L'opérande gauche1
   * @param op L'opérateur
   * @param expr2 L'opérande droite */
  public ExprOpBin(final Expr expr1, final main.EnumOper op, final Expr expr2) {
    super(expr1, expr2);
    this.expr1 = expr1;
    this.op = op;
    this.expr2 = expr2;
  }

  @Override
  public String toString() { return super.toString() + " " + op.name(); }

  public void accept(final AstVisitor v) { v.visit(this); }
}
