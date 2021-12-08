package intermediate.ir;

/** Variable IR : Variable Temporaire. */
public class IRtempo implements IRvariable {
  /** La numérotation des variables temporaires. */
  private static int index;

  /** Le nom unique des variables temporaires. */
  private final String name;

  /** Le nom de la méthode courante. Utilisé comme portée de la variable */
  private final String scope; // current method name

  /** Constructeur avec nom de variable auto-généré.
   * @param scope Nom de la méthode courante */
  public IRtempo(final String scope) {
    this.name = "t_" + index++;
    this.scope = scope;
  }

  /** Gets the name. */
  public String getName() { return name; }

  /** Gets the type. */
  public String getType() { return "IRTeno"; }

  /** Gets the scope.
   * @return scope La portée = Nom de la méthode courante */
  public String getScope() { return this.scope; }

  @Override
  public String toString() {
    return name;
  }
}
