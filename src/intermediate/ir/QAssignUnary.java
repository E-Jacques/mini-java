package intermediate.ir;

/** <b>QAssignUnary :</b> <br>
 * result = op arg1 . */
public class QAssignUnary extends IRquadruple {
  /** Constructeur Opérateur unaire.
   * @param op L'opérateur
   * @param arg1 L'opérande
   * @param result Le résultat */
  public QAssignUnary(final main.EnumOper op, final IRvariable arg1,
      final IRvariable result) {
    super(op, arg1, null, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := " + op + " " + arg1.getName();
  }
}
