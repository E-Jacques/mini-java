package intermediate.ir;

/** Classe abstraite mère des Instructions Intermédiaires. <br>
 * <b>IRQuadruple</b> = Code à 3 adresses :
 * <ul><li>{ OPER op, IRvar arg1, IRVar arg2, IRVar result }
 * <li>Interprétation commune : result = op ( arg1, arg2 )
 * </ul>*/
public abstract class IRquadruple {
  /** Opération. */
  public final main.EnumOper op;
  /** "Adresse 1". */
  public final IRvariable arg1;
  /** "Adresse 2". */
  public final IRvariable arg2;
  /** "Adresse 3". */
  public final IRvariable result;

  /** Constructeur.
   * @param op L'opération
   * @param arg1 L'opérande 1
   * @param arg2 L'opérande 2
   * @param result Le résultat */
  IRquadruple(final main.EnumOper op, final IRvariable arg1,
      final IRvariable arg2, final IRvariable result) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
  }

  /** Accept() pour le visiteur de la forme intermédiaire.
   * @param v Le visiteur */
  public abstract void accept(IRvisitor v);

  /** Debug. */
  public abstract String toString();
}
