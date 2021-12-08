package semantic;

import java.util.HashMap;
import java.util.Map;

import syntax.ast.AstNode;

/**  Décoration de l'AST : Attributs Sémantiques. <br>
 * Utilise une structure {@code Map<ASTNode, R>} pour décorer
 * l'AST existant.
 * <br>Usefull to :
 * <ul><li>manage return value with (void) visitor (bottom-up Attributs)
 * <li>avoid paramaters in visitor (top-down Attributs)
 * <li>reuse Attributs between several visits (avoiding unconsistancy)
 * </ul>
 * @param <R> Le type de l'attribut */
public class SemanticAttribut<R> {
  /** L'attribut sémantique. */
  private final Map<AstNode, R> attribut;

  /** Constructeur par défault. */
  public SemanticAttribut() {
    this.attribut = new HashMap<>();
  }

  /** Valeur de l'attribut d'un nœud.
   * @param n Le nœud
   * @return La valeur ou "null" si absent */
  public R get(final AstNode n) {
    return attribut.get(n);
  }

  /** Affectation de la valeur d'un attribut à un nœud.
   * @param n Le nœud
   * @param attr La valeur de l'attribut
   * @return  "null" ou la valeur précédente */
  public R set(final AstNode n, final R attr) {
    return attribut.put(n, attr);
  }
}
