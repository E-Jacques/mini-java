package intermediate.ir;

/** <b>QLabel : </b> <br> Label arg1 . */
public class QLabel extends IRquadruple {
  /** Constructeur Label.
   * @param arg1  Le nom du label
   */
  public QLabel(final IRlabel arg1) {
    super(null, arg1, null, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return arg1.getName() + ":";
  }
}
