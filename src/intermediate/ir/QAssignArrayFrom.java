package intermediate.ir;

/** <b>QAssignArrayFrom :</b> <br>
 * result = arg1[arg2] . */
public class QAssignArrayFrom extends IRquadruple {
  /** Constructeur Lecture dans un tableau.
   * @param arg1 L'adresse du tableau
   * @param arg2 L'index dans le tableau
   * @param result La valeur en retour */
  public QAssignArrayFrom(final IRvariable arg1, final IRvariable arg2,
      final IRvariable result) {
    super(null, arg1, arg2, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := " + arg1.getName()
      + "[" + arg2.getName() + "]";
  }
}
