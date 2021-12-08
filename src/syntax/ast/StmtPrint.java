package syntax.ast;

/** Impression d'une valeur entière.
 * <ul><li>{@link #expr} </ul>*/
public class StmtPrint extends Stmt {
  /** La valeur. */
  public final Expr expr;

  /** Constructeur.
   * @param expr La valeur */
  public StmtPrint(final Expr expr) { super(expr); this.expr = expr; }

  public void accept(final AstVisitor v) { v.visit(this); }
}
