package syntax.ast;

/** Paramètre formel de méthode.
 * <ul><li>{@link #typeId}
 * <li>{@link #varId} </ul> */
public class Formal extends AstNode {
  /** Le nom de Type. */
  public final Type typeId;
  /** Le nom de Variable. */
  public final Ident varId;

  /** Constructeur.
   * @param typeId Le nom de Type
   * @param varId Le nom de Variable */
  public Formal(final Type typeId, final Ident varId) {
    super(typeId, varId);
    this.typeId = typeId;
    this.varId = varId;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
