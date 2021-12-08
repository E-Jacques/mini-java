package intermediate.ir;

/** Interface Visiteur pour la forme intermetiaire. */
public interface IRvisitor {
  /** Visite d'un Label de méthode.
   * @param n Le nœud visité */
  void visit(QLabelMeth n);
  /** Visite d'un Label (non méthode).
   * @param n Le nœud visité */
  void visit(QLabel n);
  /** Visite d'un Saut.
   * @param n Le nœud visité */
  void visit(QJump n);
  /** Visite d'un Saut Conditionnel.
   * @param n Le nœud visité */
  void visit(QJumpCond n);
  /** Visite d'un Retour de méthode.
   * @param n Le nœud visité */
  void visit(QReturn n);
  /** Visite d'un Paramètre d'appel.
   * @param n Le nœud visité */
  void visit(QParam n);
  /** Visite d'un Appel de méthode.
   * @param n Le nœud visité */
  void visit(QCall n);
  /** Visite d'un Appel de méthode spéciale : "static void".
   * @param n Le nœud visité */
  void visit(QCallStatic n);
  /** Visite d'une Instanciation.
   * @param n Le nœud visité */
  void visit(QNew n);
  /** Visite d'un Move/Copy.
   * @param n Le nœud visité */
  void visit(QCopy n);
  /** Visite d'un Opérateur Binaire.
   * @param n Le nœud visité */
  void visit(QAssign n);
  /** Visite d'un Opérateur Unaire.
   * @param n Le nœud visité */
  void visit(QAssignUnary n);
  /** Visite d'une Instanciation de tableau.
   * @param n Le nœud visité */
  void visit(QNewArray n);
  /** Visite d'une Lecture dans un tableau.
   * @param n Le nœud visité */
  void visit(QAssignArrayFrom n);
  /** Visite d'une Affectation dans un tableau.
   * @param n Le nœud visité */
  void visit(QAssignArrayTo n);
  /** Visite d'un Longueur de tableau.
   * @param n Le nœud visité */
  void visit(QLength n);
}
