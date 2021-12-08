package intermediate.ir;

/** <b>QLabelMeth : </b> <br> Label arg1 . <br>
 * Label for method */
public class QLabelMeth extends IRquadruple {
  /** Constructeur Label pour méthode.
   * @param arg1  Le nom de la méthode
   */
  public QLabelMeth(final IRlabel arg1) { super(null, arg1, null, null); }

  public void accept(final IRvisitor v) { v.visit(this); }

  public String toString() { return arg1.getName() + ":"; }

}
