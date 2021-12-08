package intermediate.ir;

/** <b>QCallStatic :</b> <br>
 * call arg1 [numParams=arg2] .
 * Cas particulier des fonctions <code>static void</code> :
 main(), _system_out_println(), _system_exit() . */
public class QCallStatic extends IRquadruple {
  /** Constructeur Appel de méthode.
   * (QCallStatic est précédé de (arg2) QParams )
   * @param arg1  Le nom de méthode
   * @param arg2  Le nombre de paramètres */
  public QCallStatic(final IRlabel arg1, final IRconst arg2) {
    super(null, arg1, arg2, null);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return "static void "
      + "call " + arg1.getName() + "<" + arg2.getName() + ">";
  }
}
