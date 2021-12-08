package intermediate.ir;

/** <b>QParam : </b> <br> Param arg1 . */
public class QParam extends IRquadruple {
  /** Constructeur : Un paramètre d'appel.
   * @param arg1  Le paramètre d'appel
   */
  public QParam(final IRvariable arg1) {
    super(null, arg1, null, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return "param " + arg1.getName();
  }
}
