package intermediate.ir;

/** <b>QJumpCond : </b> <br> Jump arg1 IfNot arg2 . */
public class QJumpCond extends IRquadruple {
  /** Constructeur Saut conditionnel.
   * @param arg1  Le label du saut
   * @param arg2  Le test de non saut (jump if not arg2)
   */
  public QJumpCond(final IRlabel arg1, final IRvariable arg2) {
    super(null, arg1, arg2, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return "iffalse " + arg2.getName() + " goto " + arg1.getName();
  }
}
