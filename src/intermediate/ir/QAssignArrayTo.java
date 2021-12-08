package intermediate.ir;

/** <b>QAssignArrayTo :</b> <br>
 * result[arg2] = arg1 . */
public class QAssignArrayTo extends IRquadruple {
  /** Constructeur Écriture dans un tableau.
   * @param arg1 La valeur à affecter
   * @param arg2 L'index dans le tableau
   * @param result L'adresse du tableau */
  public QAssignArrayTo(final IRvariable arg1,
      final IRvariable arg2, final IRvariable result) {
    super(null, arg1, arg2, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + "[" + arg2.getName() + "]"
        + " := " + arg1.getName();
  }
}
