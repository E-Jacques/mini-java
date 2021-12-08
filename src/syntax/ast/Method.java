package syntax.ast;

/** Déclaration de méthode.
 * <ul><li>{@link #returnType}
 * <li>{@link #methodId}
 * <li>{@link #fargs}
 * <li>{@link #vars}
 * <li>{@link #stmts}
 * <li>{@link #returnExp} </ul> */
public class Method extends AstNode {
  /** Le type de retour. */
  public final Type returnType;
  /** Le nom de méthode. */
  public final Ident methodId;
  /** La liste des paramètres formels. */
  public final AstList<Formal> fargs;
  /** La liste des variables locales. */
  public final AstList<Var> vars;
  /** La liste des instructions. */
  public final AstList<Stmt> stmts;
  /** L'argument du return. */
  public final Expr returnExp;

  /** Constructeur.
   * @param returnType Le type de retour
   * @param methodId Le nom de méthode
   * @param fargs La liste des paramètres formels
   * @param vars La liste des variables locales
   * @param stmts La liste des instructions
   * @param returnExp L'argument du return */
  public Method(final Type returnType, final Ident methodId,
      final AstList<Formal> fargs, final AstList<Var> vars,
      final AstList<Stmt> stmts, final Expr returnExp) {
    super(returnType, methodId, fargs, vars, stmts, returnExp);
    this.returnType = returnType;
    this.methodId = methodId;
    this.fargs = fargs;
    this.vars = vars;
    this.stmts = stmts;
    this.returnExp = returnExp;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
