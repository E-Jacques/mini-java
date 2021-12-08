package semantic.symtab;

/** Déclaration de Variable pour la table de symboles.
 * Suivant le scope une Variable est :
 * <ul><li>un champs de classe
 * <li>un argument de méthode
 * <li>une variable locale de méthode
 * <li>une variable locale de bloc
 * </ul>
 */
public class InfoVar implements Info, intermediate.ir.IRvariable {
  /** Le nom de la variable. */
  private final String name;

  /** Le type de la variable. */
  private final String type;

  /** Constructeur.
   * @param name Le nom de la variable
   * @param type Le type de la variable */
  public InfoVar(final String name, final String type) {
    this.name = name;
    this.type = type;
  }

  /** Gets the name. */
  public String getName() { return name; }
  /** Gets the type. */
  public String getType() { return type; }

  @Override
  public String toString() {
    return type + " " + name;
  }
}
