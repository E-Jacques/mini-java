package syntax.ast;

/** Déclaration de variables.
 * <ul><li>{@link #typeId}
 * <li>{@link #varId} </ul> */
public class Var extends AstNode {
  /** Le type. */
  public final Type typeId;
  /** Le nom de variable. */
  public final Ident varId;

  /** Constructeur.
   * @param typeId Le type
   * @param varId Le nom de variable */
  public Var(final Type typeId, final Ident varId) {
    super(typeId, varId);
    this.typeId = typeId;
    this.varId = varId;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
