package intermediate.ir;

/** <b>QReturn :</b> <br> Return arg1 . */
public class QReturn extends IRquadruple {
  /** Constructeur : Retour de méthode.
   * @param arg1  La valeur de retour
   */
  public QReturn(final IRvariable arg1) {
    super(null, arg1, null, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return "return " + arg1.getName();
  }
}
