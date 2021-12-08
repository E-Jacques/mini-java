package syntax.ast;

/** Instruction if then else.
 * <ul><li>{@link #test}
 * <li>{@link #ifTrue}
 * <li>{@link #ifFalse} </ul> */
public class StmtIf extends Stmt {
  /** Le test. */
  public final Expr test;
  /** Le cas Vrai. */
  public final Stmt ifTrue;
  /** Le cas Faux. */
  public final Stmt ifFalse;

  /** Constructeur.
   * @param test Le test
   * @param ifTrue Le cas Vrai
   * @param ifFalse Le cas Faux */
  public StmtIf(final Expr test, final Stmt ifTrue, final Stmt ifFalse) {
    super(test, ifTrue, ifFalse);
    this.test = test;
    this.ifTrue = ifTrue;
    this.ifFalse = ifFalse;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
