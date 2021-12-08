package syntax.ast;

/** Appel de méthode d'objet.
 * <ul><li>{@link #receiver}
 * <li>{@link #methodId}
 * <li>{@link #args} </ul>*/
public class ExprCall extends Expr {
  /** Le receveur. */
  public final Expr receiver;
  /** Le nom de méthode. */
  public final Ident methodId;
  /** La liste des arguments. */
  public final AstList<Expr> args;

  /** Constructeur.
   * @param receiver Le receveur
   * @param methodId Le nom de méthode
   * @param args La liste des arguments */
  public ExprCall(final Expr receiver, final Ident methodId,
      final AstList<Expr> args) {
    super(receiver, methodId, args);
    this.receiver = receiver;
    this.methodId = methodId;
    this.args = args;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
