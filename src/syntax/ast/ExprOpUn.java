package syntax.ast;

/** Opérateur Unaire.
 * <ul><li>{@link #op}
 * <li>{@link #expr} </ul> */
public class ExprOpUn extends Expr {
  /** L'opérateur. */
  public final main.EnumOper op;
  /** L'opérande. */
  public final Expr expr;

  /** Constructeur.
   * @param op L'opérateur
   * @param expr L'Opérande */
  public ExprOpUn(final main.EnumOper op, final Expr expr) {
    super(expr);
    this.op = op;
    this.expr = expr;
  }

  @Override
  public String toString() { return super.toString() + " " + op.name(); }

  public void accept(final AstVisitor v) { v.visit(this); }
}
