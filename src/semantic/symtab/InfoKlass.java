package semantic.symtab;

import java.util.Collection;

/** Déclaration de classe pour la table de symboles.
 * <br>Insertion d'une classe :
 * <ul><li> Ajout de la classe : scope.add(new InfoKlass);
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> mise a jour : InfoKlass.setScope(scope); </ul>
 * Le scope de la classe est fils du scope de la déclaration de classe */
public class InfoKlass implements Info {
  /** Le nom de la classe. */
  private final String name;

  /** Le nom de la classe Mère. */
  private final String parent;

  /** Link to Klass attributes : fields, methods. */
  private Scope scope;

  /** Constructeur.
   * @param name Le nom de la classe
   * @param parentName Le nom de la classe mère */
  public InfoKlass(final String name, final String parentName) {
    this.name = name;
    this.parent = parentName;
    this.scope = null;
  }

  // getters/setters/helpers
  /** Gets the name.
   * @return Le nom de la classe */
  public String getName() { return name; }
  /** Gets the parent.
   * @return Le nom de la classe parente*/
  public String getParent() { return parent; }
  /** Gets the scope.
   * @return Le scope des attributs de la classe */
  public Scope getScope() { return this.scope; }
  /** Sets the scope.
   * @param scope Le scope des attributs de la classe */
  public void setScope(final Scope scope) { this.scope = scope; }
  /** Gets the methods.
   * @return Les méthodes de la classe */
  public Collection<InfoMethod> getMethods() { return scope.getMethods(); }
  /** Gets the fields.
   * @return Les champs de la classe */
  public Collection<InfoVar> getFields() { return scope.getVariables(); }

  @Override
  public String toString() {
    return "class " + name + " extends " + parent;
  }
}

