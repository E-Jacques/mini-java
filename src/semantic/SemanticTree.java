package semantic;

import semantic.symtab.*;
import syntax.ast.AstNode;

/** <b>Arbre Sémantique</b> == AST + Attributs de Nœuds + Table de Symboles.
 * <ul><li><b>Scope (hérité)</b> : portée courante dans la table de symboles
 * <li><b>Type (synthétisé)</b> : type de données pour les expressions
 * </ul> */
public class SemanticTree {
  /** Racine de l'AST.
   * <br> assert(axiom instanceOf Axiom) */
  public final AstNode axiom;

  /** Racine de la table des symboles.
   * <br>Utile comme entrée dans la table des classes
   * <br>assert( rootScope=axiom.getScope() */
  public final Scope rootScope;

  /** Attribut sémantique Scope. Entrée de la table des symboles d'un nœud */
  public final SemanticAttribut<Scope> scopeAttr;

  /** Attribut sémantique Type. Type de données pour nœuds Pexpr* */
  public final SemanticAttribut<String> typeAttr;

  /** Constructeur.
   * @param axiom La racine de l'AST */
  public SemanticTree(final AstNode axiom) {
    this.axiom = axiom;
    this.rootScope = new Scope(null, "Root");
    this.scopeAttr = new SemanticAttribut<>();
    this.typeAttr = new SemanticAttribut<>();
  }

}
