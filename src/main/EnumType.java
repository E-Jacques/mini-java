package main;

/** Enumeration des types primitifs et mapping toString(). */
public enum EnumType {
  /** Type booléen. */
  BOOL("boolean"),
  /** Type entier. */
  INT("int"),
  /** Type tableau d'entiers. */
  INT_ARRAY("int[]"),
  /** Type tableau de booléens. */
  BOOL_ARRAY("boolean[]"),
  /** Type vide (inutile). */
  VOID("void"),
  /** Type inconnu (inutile). */
  UNDEF("undef");

  /** Nom de type (pour debug et typechecking!. */
  private final String name;

  /** Constructeur privé pour nommage des types.
   * @param name Le nom java du type */
  EnumType(final String name) { this.name = name; }

  @Override
  public String toString() { return name; }
}
