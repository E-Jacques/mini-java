package syntax.ast;

/** Interface Visiteur pour l'AST Minijava. */
public interface AstVisitor {
  /** Visite Liste de nœuds homogénes.
   * @param <T> Le type commun des fils du nœud
   * @param n Le nœud visité */
  <T extends AstNode> void visit(AstList<T> n);

  /** Visite Axiome.
   * @param n Le nœud visité */
  void visit(Axiom n);
  /** Visite Déclaration de classe.
   * @param n Le nœud visité */
  void visit(Klass n);
  /** Visite Déclaration de la classe "Main".
   * @param n Le nœud visité */
  void visit(KlassMain n);
  /** Visite Déclaration de Méthode.
   * @param n Le nœud visité */
  void visit(Method n);
  /** Visite Déclaration de paramètre fromel.
   * @param n Le nœud visité */
  void visit(Formal n);
  /** Visite Identificateur (classe, méthode, champs, variable).
   * @param n Le nœud visité */
  void visit(Ident n);
  /** Visite Identificateur de type.
   * @param n Le nœud visité */
  void visit(Type n);
  /** Visite Déclaration de variable/champs.
   * @param n Le nœud visité */
  void visit(Var n);

  // Expressions, extends Expr
  /** Visite Expression longueur de tableau.
   * @param n Le nœud visité */
  void visit(ExprArrayLength n);
  /** Visite Expression lecture dans un tableau.
   * @param n Le nœud visité */
  void visit(ExprArrayLookup n);
  /** Visite Expression instanciation de tableau.
   * @param n Le nœud visité */
  void visit(ExprArrayNew n);
  /** Visite Expression appel de fonction.
   * @param n Le nœud visité */
  void visit(ExprCall n);
  /** Visite Expression valeur de variable.
   * @param n Le nœud visité */
  void visit(ExprIdent n);
  /** Visite Expression constante booléenne.
   * @param n Le nœud visité */
  void visit(ExprLiteralBool n);
  /** Visite Expression constante entière.
   * @param n Le nœud visité */
  void visit(ExprLiteralInt n);
  /** Visite Expression instanciation d'objet.
   * @param n Le nœud visité */
  void visit(ExprNew n);
  /** Visite Expression opérateur binaire.
   * @param n Le nœud visité */
  void visit(ExprOpBin n);
  /** Visite Expression opérateur unaire.
   * @param n Le nœud visité */
  void visit(ExprOpUn n);

  // Instructions, extends Stmt
  /** Visite Instruction affectation dans un tableau.
   * @param n Le nœud visité */
  void visit(StmtArrayAssign n);
  /** Visite Instruction affectation.
   * @param n Le nœud visité */
  void visit(StmtAssign n);
  /** Visite Instruction bloc d'instructions.
   * @param n Le nœud visité */
  void visit(StmtBlock n);
  /** Visite Instruction if.
   * @param n Le nœud visité */
  void visit(StmtIf n);
  /** Visite Instruction impression valeur entière.
   * @param n Le nœud visité */
  void visit(StmtPrint n);
  /** Visite Instruction while.
   * @param n Le nœud visité */
  void visit(StmtWhile n);
}
