package main;

/** Enumeration des opérateurs Binaires et Unaires, et mapping toString(). */
public enum EnumOper {
  /** Opérateur binaire Addition. */
  PLUS("+"),
  /** Opérateur binaire Soustraction. */
  MINUS("-"),
  /** Opérateur binaire Multiplication. */
  TIMES("*"),
  /** Opérateur binaire Et logique. */
  AND("&&"),
  /** Opérateur binaire Inférieur. */
  LESS("<"),
  /** Opérateur unaire Négation. */
  NOT("!"),
  /** Opérateur indéfini (inutile). */
  UNDEF("undef");

  /** Nom de l'opérateur pour debug. */
  private final String name;

  /** Constructeur privé pour nommage des opérateurs.
   * @param name La notation usuelle de l'opérateur */
  EnumOper(final String name) { this.name = name; }

  @Override
  public String toString() { return name; }
}
