package semantic.symtab;

import java.util.Collection;
import java.util.List;

/** Déclaration de Méthode pour la table de symboles.
 * <br>Insertion d'une Méthode :
 * <ul><li> Ajout de la Méthode : scope.add(new InfoMethod);
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> Ajout des arguments Formels: scope.add(new InfoVar); ...
 * <li> Entrée dans un nouveau scope : scope=new Scope(scope);
 * <li> mise a jour : InfoMethod.setScope(scope); </ul>
 * <br>Le scope de la Méthode est fils du scope des Arguments
 * qui est file du scope de déclaration de methode.
 * <br>NB : les arguments formels sont dupliqués comme une
 * liste ordonnée dans InfoMethod et
 * comme une collection non ordonnée dans le scope des arguments
 */
public class InfoMethod implements Info {
  /** Le type de retour. */
  private final String returnType;

  /** Le nom de méthode. */
  private final String name;

  /** La liste des arguments. NB : args[0] = "this" */
  private final InfoVar[] args;

  /** Link to Method attributes : formal args , local variables. */
  private Scope scope;

  /** Constructeur VarArg.
   * @param returnType Le type de retour
   * @param name Le nom de méthode
   * @param args La liste des arguments */
  public InfoMethod(final String returnType, final String name,
      final InfoVar... args) {
    this.returnType = returnType;
    this.name = name;
    this.args = args;
    this.scope = null;
  }

  /** Constructeur Liste.
   * @param returnType Le type de retour
   * @param name Le nom de méthode
   * @param args La liste des arguments */
  public InfoMethod(final String returnType, final String name,
      final List<InfoVar> args) {
    this.returnType = returnType;
    this.name = name;
    this.args = args.toArray(new InfoVar[0]);
    this.scope = null;
  }

  // getters/setters/helpers
  /** Gets the return type.
   * @return Le type de retour */
  public String getReturnType() { return returnType; }
  /** Gets the name.
   * @return Le nom de la méthode */
  public String getName() { return name; }
  /** Gets the args.
   * @return Les arguments de la méthode */
  public InfoVar[] getArgs() { return args; }
  /** Gets the scope.
   * @return La portée (scope) de la méthode */
  public Scope getScope() { return scope; }
  /** Sets the scope.
   * @param sc a portée (scope) de la méthode */
  public void setScope(final Scope sc) { this.scope = sc; }
  /** Gets the locals.
   * @return Les variables locales de la méthode */
  public Collection<InfoVar> getLocals() { return scope.getVariables(); }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(returnType).append(' ').append(name);
    sb.append('(');
    if (args.length != 0) sb.append(args[0]);
    for (int i = 1; i < args.length; i++) sb.append(", ").append(args[i]);
    sb.append(')');
    return sb.toString();
  }

  /** Gets the canonical name. (Unused)
   * @return Un nom canonique de méthode */
  public String getCanonicalName() {
    final StringBuilder sb = new StringBuilder();
    sb.append("__").append(name);
    for (InfoVar v : args) sb.append('_').append(v.getType());
    return sb.toString();
  }
}
