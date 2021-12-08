package semantic.symtab;

import java.util.Collection;

/**  Interface standard d'une table de Symbole.
 * @param <T> Le type de "Nom" (String)
 * @param <R> Le type d' "Information" */
public interface Table<T, R extends Info> {
  /** Recherche dans la table.
   * @param name Le nom
   * @return l'information trouvée */
  R lookup(T name);

  /** Ajout dans la table.
   * @param name Le nom
   * @param info L'information ajoutée
   * @return L'information précédente (déjà existante)*/
  R insert(T name, R info);

  /** La liste des symboles de la table.
   * @return les Informations */
  Collection<R> getInfos();
}
// Table avec Scope :
//    lookupLocal() : may be not usefull if insert return "already exists"
//    enterScope() -> currentScope = new Scope (currentScope)
//    exitScope() -> currentScope = curentScope.getParent()
//                   ou currentScope = savedCurrentScope
