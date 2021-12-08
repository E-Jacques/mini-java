package syntax.ast;

/** Utilisation de Variable.
 *  <ul><li>{@link #varId} </ul> */
public class ExprIdent extends Expr {
  /** Le nom de la variable. */
  public final Ident varId;

  /** Constructeur.
   * @param varId Le nom de la variable */
  public ExprIdent(final Ident varId) {
    super(varId);
    this.varId = varId;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
