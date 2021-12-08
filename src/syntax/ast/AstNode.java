package syntax.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;

/** Classe abstraite ancêtre pour AST.
 * <ul><li>Construction VarArgs
 * <li>Classe itérable : {@code for (AstNode fils : pere) {...} }
 * <li>Impression de l'arbre : {@link #toPrint()}
 * <li>Gestion des références dans le source :
 * {@link #addPosition(Location, Location)}
 * </ul> */
public abstract class AstNode implements Iterable<AstNode> {
  /** Un Nom pour le debugging. utilise le nom de la classe instanciante */
  private final String label;

  /** La liste des nœuds fils. */
  private final List<AstNode> fils;

  /** La localisation dans le fichier source: Debut. */
  private Location start;

  /** La localisation dans le fichier source: Fin. */
  private Location stop;

  /** Constructeur avec VarArgs.
     @param fils liste quelconque de fils */
  protected AstNode(final AstNode... fils) {
    this.label = getClass().getSimpleName();
    this.fils = new ArrayList<>();
    for (AstNode f : fils) this.fils.add(f);
  }

  /** Accept() pour le patron visiteur.
   * @param v Le visiteur */
  public abstract void accept(AstVisitor v);

  /** Construction itérative (uniquement pour AstList).
   * @param f Le nœud fils à ajouter*/
  protected void addFils(final AstNode f) {
    this.fils.add(f);
  }

  /** Itération sur les fils.
   *  <br> i.e. {@code AstNode p; for (AstNode f : p) {}} */
  public Iterator<AstNode> iterator() {
    return fils.iterator();
  }

  /** Taille.
   * @return Nombre de fils */
  public int size() { return this.fils.size(); }

  /** Impression Noeud. */
  public String toString() {
    return label + toLocationString();
  }

  /** Impression Arbre.
   * @return La représentation textuelle de L'AST */
  public String toPrint() {
    /* Choix AsciiArt vs Unicode !!! */
    final String[][] charsets = {
      /* ASCII */
      {"|-", "| ", "\\-", "  "},
      /* Unicode "\u251c\u2500","\u2502\u0020","\u2514\u2500","\u0020\u0020" */
      {"├─", "│ ", "└─", "  "},
      /* Unicode compact :  "\u251c", "\u2502", "\u2514", "\u0020"  */
      {"├", "│", "└", " "}
    };
    final String[] cs = charsets[1];

    final StringBuilder sb = new StringBuilder("");
    print("", sb, cs);
    return sb.toString();
  }

  /** Impression recursive de l'AST.
   * @param indent La chaîne d'indentation courrante
   * @param sb Le "StringBuilder" pour la sortie d'impression
   * @param charSet Le jeux de caractère pour "l'ASCII art" */
  private void print(final String indent, final StringBuilder sb,
      final String[] charSet) {
    sb.append(this).append(System.lineSeparator());
    for (Iterator<AstNode> it = this.iterator(); it.hasNext();) {
      final AstNode f = it.next();
      sb.append(indent);
      if (it.hasNext()) {
        sb.append(charSet[0]);
        f.print(indent + charSet[1], sb, charSet);
      } else {
        sb.append(charSet[2]);
        f.print(indent + charSet[3], sb, charSet);
      }
    }
  }

  /** Gestion optionnelle des positions des symboles dans le source.
   * <br>Utilisation dans CUP :
   * {@code x := A:a .. Z:z {: RESULT = new Node(A,..,Z);
   *    RESULT.addPosition(axleft,zxright); :} }
   * @param left La position de début du symbole
   * @param right La position de fin du symbole */
  public void addPosition(final Location left, final Location right) {
    this.start = left;
    this.stop = right;
  }

  /** Impression d'une position dans le fichier source.
   * @param l La "Location"
   * @return La chaîne imprimable */
  private String locStr(final Location l) {
    if (l == null) return "";
    return l.getLine() + "/" + l.getColumn() + "(" + l.getOffset() + ")";
  }

  /** Impression de la position du nœud AST dans le source.
   * @return La chaîne imprimable */
  private String toLocationString() {
    if (!main.Debug.TREELOCATION) return "";
    if (start == null && stop == null) return "";
    return "[" + locStr(start) + "-" + locStr(stop) + "]";
  }
}
