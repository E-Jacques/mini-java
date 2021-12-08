package intermediate.ir;

/** Visiteur de la forme intermédiaire par défaut: Unmanaged exception. */
public abstract class IRvisitorDefault implements IRvisitor {
  /** Visite par défaut.
   * @param q Le nœud IR à visiter
   * @throws main.CompilerException si visit(q) non surchargé */
  public void defaultVisit(final IRquadruple q) {
    throw new main.CompilerException("IRvisitor Quadrulple unmanaged :" + q);
  }

  public void visit(final QLabelMeth n) { defaultVisit(n); }
  public void visit(final QLabel n) { defaultVisit(n); }
  public void visit(final QJump n) { defaultVisit(n); }
  public void visit(final QJumpCond n) { defaultVisit(n); }
  public void visit(final QReturn n) { defaultVisit(n); }
  public void visit(final QParam n) { defaultVisit(n); }
  public void visit(final QCall n) { defaultVisit(n); }
  public void visit(final QCallStatic n) { defaultVisit(n); }
  public void visit(final QNew n) { defaultVisit(n); }
  public void visit(final QCopy n) { defaultVisit(n); }
  public void visit(final QAssign n) { defaultVisit(n); }
  public void visit(final QAssignUnary n) { defaultVisit(n); }
  public void visit(final QNewArray n) { defaultVisit(n); }
  public void visit(final QAssignArrayFrom n) { defaultVisit(n); }
  public void visit(final QAssignArrayTo n) { defaultVisit(n); }
  public void visit(final QLength n) { defaultVisit(n); }
}
