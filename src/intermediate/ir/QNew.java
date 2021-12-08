package intermediate.ir;

/** <b>QNew : </b> <br> result = new arg1 . */
public class QNew extends IRquadruple {
  /** Constructeur Instanciation d'objet.
   * @param arg1  La classe de l'objet (Nom)
   * @param result L'adresse de l'objet crée
   */
  public QNew(final IRvariable arg1, final IRvariable result) {
    super(null, arg1, null, result);
  }

  public void accept(final IRvisitor v) { v.visit(this); }

  @Override
  public String toString() {
    return result.getName() + " := new " + arg1.getName();
  }
}
