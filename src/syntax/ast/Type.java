package syntax.ast;

/** Identificateur de type.
 * <ul><li>{@link #name} </ul> */
public class Type extends AstNode {
  /** Le nom de type. */
  public final String name;

  /** Constructeur.
   * @param name Le nom de type. */
  public Type(final String name) { this.name = name; }

  /** Constructeur alternatif.
   * @param t Le type primitif */
  public Type(final main.EnumType t) { this.name = t.toString(); }

  @Override
  public String toString() { return super.toString() + " " + name; }

  public void accept(final AstVisitor v) { v.visit(this); }
}
