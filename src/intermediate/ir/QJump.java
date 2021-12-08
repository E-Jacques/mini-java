package intermediate.ir;

/** <b>QJump :</b> <br> Jump arg1 . */
public class QJump extends IRquadruple {
  /** Constructeur Saut inconditionnel.
   * @param arg1  Le label du saut
   */
  public QJump(final IRlabel arg1) {
    super(null, arg1, null, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return "goto " + arg1.getName();
  }
}
