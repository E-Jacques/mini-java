package syntax.ast;

/** Instruction : classse abstraite pour Stmt*. */
public abstract class Stmt extends AstNode {
  /** Constructeur hérité.
   * @param fils La liste des nœuds fils */
  Stmt(final AstNode... fils) { super(fils); }
}
