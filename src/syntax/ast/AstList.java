package syntax.ast;

/** AstNode avec fils homogènes.
 * <br>Construction itérative avec la méthode add(R)
 * @param <R> Le type de fils */
public class AstList<R extends AstNode> extends AstNode {
  /** Constructeur vararg. Not usefull.
   * @param fils La liste des fils */
  public AstList(final AstNode... fils) { super(fils); }

  /** Construction itérative.
   * @param node nœud ajouté en fin de liste */
  public void add(final R node) { this.addFils(node); }

  public void accept(final AstVisitor v) { v.visit(this); }
}

