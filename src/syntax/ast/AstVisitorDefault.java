package syntax.ast;

/** Visiteur générique de l'AST avec parcours en profondeur. */
public abstract class AstVisitorDefault implements AstVisitor {
  /** Parcours récursif en profondeur.
   * @param node Le nœud à visiter */
  public void defaultVisit(final AstNode node) {
    for (AstNode f : node) f.accept(this);
  }

  // Liste homogène, extends ASTNode
  public <T extends AstNode> void visit(final AstList<T> n) { defaultVisit(n); }

  // Productions de base, extends ASTNode
  public void visit(final Axiom n) { defaultVisit(n); }
  public void visit(final Klass n) { defaultVisit(n); }
  public void visit(final KlassMain n) { defaultVisit(n); }
  public void visit(final Method n) { defaultVisit(n); }
  public void visit(final Formal n) { defaultVisit(n); }
  public void visit(final Ident n) { defaultVisit(n); }
  public void visit(final Type n) { defaultVisit(n); }
  public void visit(final Var n) { defaultVisit(n); }

  // Expressions , extends Expr
  public void visit(final ExprArrayLength n) { defaultVisit(n); }
  public void visit(final ExprArrayLookup n) { defaultVisit(n); }
  public void visit(final ExprArrayNew n) { defaultVisit(n); }
  public void visit(final ExprCall n) { defaultVisit(n); }
  public void visit(final ExprIdent n) { defaultVisit(n); }
  public void visit(final ExprLiteralBool n) { defaultVisit(n); }
  public void visit(final ExprLiteralInt n) { defaultVisit(n); }
  public void visit(final ExprNew n) { defaultVisit(n); }
  public void visit(final ExprOpBin n) { defaultVisit(n); }
  public void visit(final ExprOpUn n) { defaultVisit(n); }

  // Instructions, extends Stmt
  public void visit(final StmtArrayAssign n) { defaultVisit(n); }
  public void visit(final StmtAssign n) { defaultVisit(n); }
  public void visit(final StmtBlock n) { defaultVisit(n); }
  public void visit(final StmtIf n) { defaultVisit(n); }
  public void visit(final StmtPrint n) { defaultVisit(n); }
  public void visit(final StmtWhile n) { defaultVisit(n); }
}
