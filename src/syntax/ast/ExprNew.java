package syntax.ast;

/** Instanciation d'objet.
 * <ul><li>{@link #klassId} </ul>*/
public class ExprNew extends Expr {
  /** Le nom de la classe. */
  public final Ident klassId;

  /** Constructeur.
   * @param klassId Le nom de la classe */
  public ExprNew(final Ident klassId) {
    super(klassId);
    this.klassId = klassId;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
