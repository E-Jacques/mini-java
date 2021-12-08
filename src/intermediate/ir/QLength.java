package intermediate.ir;

/** <b>QLength :</b> <br> result = length arg1 . */
public class QLength extends IRquadruple {
  /** Constructeur Longueur d'un tableau.
   * @param arg1  L'adresse du tableau
   * @param result La taille en retour
   */
  public QLength(final IRvariable arg1, final IRvariable result) {
    super(null, arg1, null, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := length " + arg1.getName();
  }
}
