package syntax.ast;

/** Affectation d'un élément de tableau.
 * <ul><li>{@link #arrayId}
 * <li>{@link #index}
 * <li>{@link #value} </ul> */
public class StmtArrayAssign extends Stmt {
  /** Le nom de la variable tableau. */
  public final Ident arrayId;
  /** L'indice de l’élément. */
  public final Expr index;
  /** La valeur. */
  public final Expr value;

  /** Constructeur.
   * @param arrayId Le nom de la variable tableau
   * @param index L'indice de l’élément
   * @param value La valeur */
  public StmtArrayAssign(final Ident arrayId, final Expr index,
      final Expr value) {
    super(arrayId, index, value);
    this.arrayId = arrayId;
    this.index = index;
    this.value = value;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
