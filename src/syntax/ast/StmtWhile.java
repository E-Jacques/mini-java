package syntax.ast;

/** Boucle While.
 * <ul><li>{@link #test}
 * <li>{@link #body} </ul> */
public class StmtWhile extends Stmt {
  /** Le test. */
  public final Expr test;
  /** Le corps. */
  public final Stmt body;

  /** Constructeur.
   * @param test Le test
   * @param body Le corps */
  public StmtWhile(final Expr test, final Stmt body) {
    super(test, body);
    this.test = test;
    this.body = body;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
