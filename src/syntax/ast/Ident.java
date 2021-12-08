package syntax.ast;

/** Identificateur (classe, méthode, champs, variable).
 * <ul><li>{@link #name} </ul> */
public class Ident extends AstNode {
  /** Le Nom. */
  public final String name;

  /** Constructeur.
   * @param name Le Nom */
  public Ident(final String name) { this.name = name; }

  @Override
  public String toString() { return super.toString() + " " + name; }

  public void accept(final AstVisitor v) { v.visit(this); }
}
