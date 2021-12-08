package syntax.ast;

/** Instanciation d'un tableau.
 * <ul><li>{@link #size} </ul> */
public class ExprArrayNew extends Expr {
  /** La taille du tableau. */
  public final Expr size;

  /** Le type du tableau. INT_ARRAY par défaut mais extensions possibles */
  public final main.EnumType type;

  /** Constructeur. Uniquement tableau d'entier
   * @param size La taille du tableau
   */
  public ExprArrayNew(final Expr size) {
    super(size);
    this.size = size;
    this.type = main.EnumType.INT_ARRAY;
  }

  /** Constructeur. Extension pour différents types de tableau
   * @param size La taille du tableau
   * @param type Le type de tableau
   */
  public ExprArrayNew(final Expr size, final main.EnumType type) {
    super(size);
    this.size = size;
    this.type = type;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
