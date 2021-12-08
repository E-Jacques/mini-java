package syntax.ast;

/** Bloc d'instructions.
 * <ul><li>{@link #vars}
 * <li>{@link #stmts} </ul> */
public class StmtBlock extends Stmt {
  /** La liste des variables locales. */
  public final AstList<Var> vars;
  /** La liste des instructions. */
  public final AstList<Stmt> stmts;

  /** Constructeur.
   * @param vars La liste des variables locales
   * @param stmts La liste des instructions */
  public StmtBlock(final AstList<Var> vars, final AstList<Stmt> stmts) {
    super(vars, stmts);
    this.vars = vars;
    this.stmts = stmts;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
