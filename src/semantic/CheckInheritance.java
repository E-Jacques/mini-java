package semantic;

import java.util.HashSet;
import java.util.Set;

import main.Debug;
import semantic.symtab.InfoKlass;
import semantic.symtab.Scope;

/** Validation de la hiérarchie des classes (parent connu,
 *  sans boucle, "Object" comme racine). <br>
 * Reconstruction de l'arbre des portées pour intégration transparente
 *  de l'héritage de classe dans la table des symboles */
public class CheckInheritance {
  /** L'arbre des portées. Entrée/Sortie de la classe */
  private final Scope rootScope;

  /** La classe Object. Requise comme racine de l'héritage Java */
  private final InfoKlass objKlass;

  /** Des erreurs d'héritage. Boucle, Undef, not Objecr. */
  private boolean error;

  /** Constructeur.
   * @param semanticTree L'arbre sémantique */
  public CheckInheritance(final SemanticTree semanticTree) {
    this.error = false;
    this.rootScope = semanticTree.rootScope;
    this.objKlass = rootScope.lookupKlass("Object");
    checkAndBuild();
    if (error) Debug.logErr("CheckInheritance : Héritage Java invalide");
  }

  /** Sortie en erreur.
   * @return true si des erreurs sémantiques dans l'héritage java */
  public boolean getError() { return error; }

  /** Recherche de la classe parent.
   * @param kl La déclaration d'une classe
   * @return La déclaration de la classe parent */
  private InfoKlass parent(final InfoKlass kl) {
    return rootScope.lookupKlass(kl.getParent());
  }

  /** Gestion erreur avec continuation.
   * @param condition Le Test à valider
   * @param message Le message d'erreur
   * @return La valeur du test
   */
  private boolean assume(final boolean condition, final String message) {
    if (!condition) { Debug.logErr(message); error = true; }
    return condition;
  }

  /** Construction de l'arbre des classes à la racine de l'arbre des portées. */
  private void checkAndBuild() {
    // Classe "Object" valide ?
    if (!checkObject()) return;
    for (InfoKlass kl : rootScope.getKlasses()) {
      // reconstruit toutes les classes avec branche d'ancêtres valides
      if (checkAncestors(kl)) {
        for (InfoKlass k = kl; k != objKlass; k = parent(k)) {
          k.getScope().mute(rootScope, parent(k).getScope());
        }
      }
    }
  }

  /** Vérifier que la classe Object est la racine de l'arbre des protées.
   * @return true si la classe Object est valide */
  private boolean checkObject() {
    return assume(objKlass != null, "Missing Object Class")
        && assume(objKlass.getParent() == null,
        "Object Class extends !! " + objKlass);
  }

  /** Test validité de l'héritage Java.
   * branche sans boucles, classes connues, et "Object" comme racine
   * @param kl La déclaration de classe
   * @return true si les ancêtres de la classe forment une branche valide */
  private boolean checkAncestors(final InfoKlass kl) {
    final Set<InfoKlass> ancestors = new HashSet<>();
    boolean ok = true;
    for (InfoKlass k = kl; ok && k != objKlass; k = parent(k)) {
      ok = assume(ancestors.add(k), "Loop in ancestors from class " + kl)
          && assume(parent(k) != null, "Unknown ancestor for " + k);
    }
    return ok;
  }
}
