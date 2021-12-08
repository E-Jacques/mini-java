package syntax.ast;

/** Expression : classe abstraite pour Expr*. */
public abstract class Expr extends AstNode {
  /** Constructeur hérité.
   * @param fils La liste des nœuds fils */
  Expr(final AstNode... fils) { super(fils); }
}
