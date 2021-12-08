package intermediate.ir;

/** <b>QCall :</b> <br>
 * result = call arg1 [numParams=arg2] . */
public class QCall extends IRquadruple {
  /** Constructeur Appel de méthode.
   * (QCall est précédé de (arg2) QParams )
   * @param arg1  Le nom de méthode
   * @param arg2  Le nombre de paramètres
   * @param result La valeur de retour */
  public QCall(final IRlabel arg1, final IRconst arg2,
      final IRvariable result) {
    super(null, arg1, arg2, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := "
      + "call " + arg1.getName() + "<" + arg2.getName() + ">";
  }
}
