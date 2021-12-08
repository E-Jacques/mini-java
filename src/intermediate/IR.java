package intermediate;

import java.util.ArrayList;
import java.util.List;

import intermediate.ir.*;
import semantic.SemanticTree;
import semantic.symtab.Scope;

/** Représentation Intermédiaire.
 * <ul><li>Programe : sequence d'instructions (IRQuadruple)
 * <li>Table des symboles de l'AST
 * <li>Table des variables IR : labels, constantes, temporaires</ul> */
public class IR {
  /** Programe intermédiaire = sequence d'instructions (IRQuadruple). */
  public final List<IRquadruple> program;

  /** Racine de la table des symboles de l'AST. */
  public final Scope rootScope;

  // "Table des symboles" IR : Variables Label, constante, temporaire
  /** Liste des Variables Tempos. */
  public final List<IRtempo> tempos;
  /** Liste des Constantes. */
  public final List<IRconst> consts;
  /** Liste des Labels. */
  public final List<IRlabel> labels;

  /** Constructeur.
   * @param semanticTree L'arbre sémantique */
  public IR(final SemanticTree semanticTree) {
    this.program = new ArrayList<>();
    this.rootScope = semanticTree.rootScope;
    this.tempos = new ArrayList<>();
    this.labels = new ArrayList<>();
    this.consts = new ArrayList<>();
  }

  /** Création Variable Temporaire IR.
   * Nom de la variable auto-généré
   * @param scope Le nom de la méthode courante
   * @return La variable IR */
  public IRtempo newTemp(final String scope) {
    final IRtempo v = new IRtempo(scope);
    tempos.add(v);
    return v;
  }

  /** Création Constante IR.
   * Nom de la variable = value.toString()
   * @param value La constante entière
   * @return La variable IR */
  public IRconst newConst(final int value) {
    final IRconst v = new IRconst(value);
    consts.add(v);
    return v;
  }

  /** Création Label Temporaire.
   * Nom de label auto-généré
   * @return La variable IR */
  public IRlabel newLabel() {
    final IRlabel v = new IRlabel();
    labels.add(v);
    return v;
  }

  /** Création Label de Méthode .
   * @param name Le nom de la méthode
   * @return La variable IR */
  public IRlabel newLabel(final String name) {
    final IRlabel v = new IRlabel(name);
    labels.add(v);
    return v;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (IRquadruple q : program) {
      if (!(q instanceof QLabel || q instanceof QLabelMeth)) {
        sb.append('\t');
      }
      sb.append(q).append(System.lineSeparator());
    }
    sb.append("= IR Tempos : ").append(tempos).append(System.lineSeparator());
    sb.append("= IR Labels : ").append(labels).append(System.lineSeparator());
    sb.append("= IR Consts : ").append(consts);
    return sb.toString();
  }
}
