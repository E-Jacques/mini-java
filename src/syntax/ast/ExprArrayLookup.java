package syntax.ast;

/** Accès à l'élément d'un tableau.
 * <ul><li>{@link #array}
 * <li>{@link #index} </ul> */
public class ExprArrayLookup extends Expr {
  /** Le Tableau. */
  public final Expr array;
  /** L'indice. */
  public final Expr index;

  /** Constructeur.
   * @param array Le tableau
   * @param index L'indice */
  public ExprArrayLookup(final Expr array, final Expr index) {
    super(array, index);
    this.array = array;
    this.index = index;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
