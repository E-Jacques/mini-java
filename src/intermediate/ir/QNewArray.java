package intermediate.ir;

/** <b>QNewArray : </b> <br>
 * result = new arg1 [Size=arg2]. */
public class QNewArray extends IRquadruple {
  /** Constructeur Instanciation de tableau.
   * @param arg1  type de tableau (unused)
   * @param arg2  taille tableau
   * @param result adresse du tableau crée
   */
  public QNewArray(final IRvariable arg1, final IRvariable arg2,
      final IRvariable result) {
    super(null, arg1, arg2, result);
    
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := new " + arg1.getName()
      + "<" + arg2.getName() + ">";
  }
}
