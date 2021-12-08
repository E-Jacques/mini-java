package intermediate.ir;

/** Variable IR : Label. */
public class IRlabel implements IRvariable {
  /** La numérotation des labels.*/
  private static int index;

  /** Le nom du label. */
  private final String name;

  /** Constructeur avec nom fixé.
   * @param name Le Nom du label (en pratique nom de méthode pour QCall) */
  public IRlabel(final String name) {
    this.name = name;
  }

  /** Constructeur avec nom auto-généré. */
  public IRlabel() { this.name = "L_" + index++; }

  /** Gets the name. */
  public String getName() { return name; }

  /** Gets the type. */
  public String getType() { return "IRLabel"; }

  @Override
  public String toString() { return name; }
}
