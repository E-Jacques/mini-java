package intermediate.ir;

/** Variable IR : Constante (litteral entier). */
public class IRconst implements IRvariable {
  /** La valeur de la constante. */
  private final Integer value;

  /** Constructeur.
   * @param value La valeur de la constante */
  public IRconst(final int value) {
    this.value = value;
  }

  /** Valeur entière de la constante.
   * @return La valeur Entière de la constante */
  public int getValue() { return value; }

  /** Gets the name.
   * @return La notation décimale de la constante */
  public String getName() { return Integer.toString(getValue()); }

  /** Gets the type.
   * @return Le type de la constante */
  public String getType() { return "IRConst"; }

  @Override
  public String toString() {
    return getType() + " " + getName();
  }

}
