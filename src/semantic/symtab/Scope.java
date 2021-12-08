package semantic.symtab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.IndentWriter;

/** Table des Symbole.
 * <ul><li>Implémentation plus large que nécessaire pour Java ou MiniJava
 * <li>Implémentation générale d'un arbre de portées avec lookup recursif
 * <li>3 espaces de nommage séparés : Classe, Méthode, Variable
 * <li>Utilisable pour intégrer l'héritage des classes
 * Java dans l'arbre de portée
 * <li>..... </ul> */
public class Scope {
  /** Le chainage Botom-Up des portées. */
  private Scope parent;

  /** La table des variables de la portée. */
  private final Table<String, InfoVar> variables;

  /** La table des méthodes de la portée. */
  private final Table<String, InfoMethod> methods;

  /** La table des classes de la portée. */
  private final Table<String, InfoKlass> klasses;

  // En pratique avec Minijava :
  //  - Classes uniquement dans le scope Racine
  //  - Méthodes uniquement dans les scopes de classe
  //  - Variables partout sauf dans le scope Racine

  /** Le Nom de portée. Uniquement pour debug */
  private final String scopeName;

  /** L'arbre Top-Down des portées. Uniquement pour debug */
  private final List<Scope> scopes;

  /** Constructeur avec nommage implicite.
   * @param parent Le scope parent */
  public Scope(final Scope parent) { // default naming
    this(parent, (parent == null) ? "Root"
        : parent.scopeName + "_" + parent.scopes.size());
  }

  /** Constructeur avec nommage explicite.
   * @param parent Le scope parent
   * @param name Le nom du scope */
  public Scope(final Scope parent, final String name) {
    this.scopeName = name;
    this.parent = parent;
    this.variables = new SimpleTable<>();
    this.methods = new SimpleTable<>();
    this.klasses = new SimpleTable<>();
    this.scopes = new ArrayList<>();
    if (parent != null) {
      parent.scopes.add(this);
    }
  }

  // Interface de la table de symbol
  /** Lookup variable.
   * @param name Le nom de variable
   * @return L'information dans la table de symbole */
  public InfoVar lookupVariable(final String name) {
    InfoVar v = null;
    for (Scope s = this; s != null && v == null; s = s.parent) {
      v = s.variables.lookup(name);
    }
    return v;
  }

  /** Insert variable.
   * @param v L'information ajoutée
   * @return L'information précédente si déjà existante */
  public InfoVar insertVariable(final InfoVar v) {
    return variables.insert(v.getName(), v);
  }

  /** Liste des variables de la portée courrante.
   * @return Les variables de la portée courante */
  public Collection<InfoVar> getVariables() { return variables.getInfos(); }

  /** Liste des variables de la portée courrante et des sous-portées .
   *  @return Les variables de la portée courante et de sous-portées */
  public Collection<InfoVar> getAllVariables() {
    final List<InfoVar> res = new ArrayList<>();
    res.addAll(variables.getInfos());
    for (Scope s : this.scopes) res.addAll(s.getAllVariables());
    return res;
  }

  /** Lookup méthode.
   * @param name Le nom de méthode
   * @return L'information dans la table de symbole */
  public InfoMethod lookupMethod(final String name) {
    InfoMethod m = null;
    for (Scope s = this; s != null && m == null; s = s.parent) {
      m = s.methods.lookup(name);
    }
    return m;
  }

  /** Insert méthode.
   * @param m L'information ajoutée
   * @return L'information précédente si déjà existante */
  public InfoMethod insertMethod(final InfoMethod m) {
    return methods.insert(m.getName(), m);
  }

  /** Liste des méthodes de la portée courante.
   * @return Les méthodes de la portée courante */
  public Collection<InfoMethod> getMethods() { return methods.getInfos(); }

  /** Lookup classe.
   * @param name Le nom de classe
   * @return L'information dans la table de symbole */
  public InfoKlass lookupKlass(final String name) {
    InfoKlass kl = null;
    for (Scope s = this; s != null && kl == null; s = s.parent) {
      kl = s.klasses.lookup(name);
    }
    return kl;
  }

  /** Insert classe.
   * @param kl L'information ajoutée
   * @return L'information précédente si déjà existante */
  public InfoKlass insertKlass(final InfoKlass kl) {
    return klasses.insert(kl.getName(), kl);
  }

  /** Liste des classes de la portée courante.
   * @return Les classes de la portée courante */
  public Collection<InfoKlass> getKlasses() { return klasses.getInfos(); }

  /** Reconstruction en Passe2 : intégration de la hiérarchie des classes dans
   * l'arbre des portées.
   * <ul><li>Seul les fils de la racine peuvent muter dans un nouvelle arbre.
   * <li>Not safe : loop not checked here, field parent not final
   * <li>retourne true, si modification effectuée </ul>
   * @param  oldParent L'ancien père, doit être la racine
   * @param newParent Le nouveau père
   * @return true si modification de l'arbre de portée */
  public boolean mute(final Scope oldParent, final Scope newParent) {
    // mute only a level 1 scope (parent is a rootScope)
    if (parent == null || parent.parent != null) return false;
    // don't create a new root
    if (newParent == null) return false;
    // mute only a known parent
    if (parent != oldParent) return false;
    // OK
    parent = newParent;
    parent.scopes.add(this);
    oldParent.scopes.remove(this);
    return true;
  }

  /** Impression Noeud. */
  @Override
  public String toString() {
    return "Scope " + scopeName;
  }

  /** Impression de l'arbre des portées bottom-up.
   * @return La representation textuelle de l'arbre des portées */
  public String toPrintUp() {
    return scopeName + " -> " + parent.toPrintUp();
  }

  /** Impression de l'arbre des portées top-down avec symboles.
   * @return La representation textuelle de l'arbre des portées */
  public String toPrint() {
    final IndentWriter out = new IndentWriter();
    print(out);
    return out.toString();
  }

  /** Recursion pour l'impression de l'arbre des portées.
   * @param out un writer pour le résultat d'impression */
  private void print(final IndentWriter out) {
    out.println("Scope " + scopeName);
    out.indent();
    for (Info i : getKlasses()) out.println(i);
    for (Info i : getMethods()) out.println(i);
    for (Info i : getVariables()) out.println(i);
    for (Scope s : scopes) s.print(out);
    out.outdent();
  }
}
