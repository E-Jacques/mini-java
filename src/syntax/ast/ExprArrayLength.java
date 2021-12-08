package syntax.ast;

/** Taille d'un tableau.
 * <ul><li>{@link #array} </ul> */
public class ExprArrayLength extends Expr {
  /** L'expression tableau. */
  public final Expr array;

  /** Constructeur.
   * @param array L'expression tableau */
  public ExprArrayLength(final Expr array) {
    super(array);
    this.array = array;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
