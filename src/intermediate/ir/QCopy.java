package intermediate.ir;

/** <b>QCopy :</b> <br> result = arg1 . */
public class QCopy extends IRquadruple {
  /** Constructeur Copy/Move.
   * @param arg1 La source
   * @param result La destination */
  public QCopy(final IRvariable arg1, final IRvariable result) {
    super(null, arg1, null, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := " + arg1.getName();
  }
}
