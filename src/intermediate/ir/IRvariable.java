package intermediate.ir;

/** Classe Commune pour la table des symboles
 * des variables de l'AST (InfoVar) et des variables
 * de la forme intermédiaire (IRConst, IRTemp, IRLabel). */
public interface IRvariable {
  /** Nom de la variable.
   * @return Le nom de la variable IR */
  String getName();

  /** Type de la variable.
   * @return le type de la variable IR */
  String getType();
}
