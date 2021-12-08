package intermediate.ir;

/** <b>QAssign :</b> <br> result = arg1 op arg2 . */
public class QAssign extends IRquadruple {
  /** Constructeur Opérateur binaire.
   * @param op L'opérateur
   * @param arg1 L'opérande gauche
   * @param arg2 L'opérande droite
   * @param result Le résultat */
  public QAssign(final main.EnumOper op, final IRvariable arg1,
      final IRvariable arg2, final IRvariable result) {
    super(op, arg1, arg2, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := "
        + arg1.getName()
        + " " + op + " "
        + arg2.getName();
  }
}
