package codegen;

/** Enumeration des 32 registres MIPS et mapping toString(). */
public enum Reg {
  /** Toujours à 0. */
  ZERO("$zero", 0),

  /** Global pointer : variables globales. */
  GP("$gp", 28),
  /** Stack pointer : La pile . */
  SP("$sp", 29),
  /** Frame pointer : variables locales. Sauvegardé par l'appelé . */
  FP("$fp", 30),
  /** Adresse de retour. Sauvegardé par l'appelé */
  RA("$ra", 31),

  /** Stockage des résultats de fonction. */
  V0("$v0", 2),
  /** Stockage des résultats de fonction. */
  V1("$v1", 3),

  /** Stockage des arguments de fonction. Sauvegardés par appelant, A0=this */
  A0("$a0", 4),
  /** Stockage des arguments de fonction. Sauvegardés par appelant */
  A1("$a1", 5),
  /** Stockage des arguments de fonction. Sauvegardés par appelant */
  A2("$a2", 6),
  /** Stockage des arguments de fonction. Sauvegardés par appelant */
  A3("$a3", 7),

  /** Registre temporaire. Sauvegardés par appelant */
  T0("$t0", 8),
  /** Registre temporaire. Sauvegardés par appelant */
  T1("$t1", 9),
  /** Registre temporaire. Sauvegardés par appelant */
  T2("$t2", 10),
  /** Registre temporaire. Sauvegardés par appelant */
  T3("$t3", 11),
  /** Registre temporaire. Sauvegardés par appelant */
  T4("$t4", 12),
  /** Registre temporaire. Sauvegardés par appelant */
  T5("$t5", 13),
  /** Registre temporaire. Sauvegardés par appelant */
  T6("$t6", 14),
  /** Registre temporaire. Sauvegardés par appelant */
  T7("$t7", 15),
  /** Registre temporaire. Sauvegardés par appelant */
  T8("$t8", 24),
  /** Registre temporaire. Sauvegardés par appelant */
  T9("$t9", 25),

  /** Registre temporaire. Sauvegardé par l'appelé */
  S0("$s0", 16),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S1("$s1", 17),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S2("$s2", 18),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S3("$s3", 19),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S4("$s4", 20),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S5("$s5", 21),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S6("$s6", 22),
  /** Registre temporaire. Sauvegardé par l'appelé */
  S7("$s7", 23),

  /** Registre réservés au système. */
  K0("$k0", 26),
  /** Registre réservés au système. */
  K1("$k1", 27),
  /** Registre réservés à l'assembleur. */
  AT("$at", 1);

  /** Nom du registre. */
  private final String name;

  /** Numero du registre. */
  private final int numero;

  /** Constructeur privé pour nommage des types.
   *  @param name Le nom MIPS
   *  @param numero Le numero MIPS */
  Reg(final String name, final int numero) {
    this.name = name;
    this.numero = numero;
  }

  @Override
  public String toString() {
    return name;
  }

  /** Version moins lisible.
   * @return Le nom MIPS avec numéro */
  public String toStringBis() {
    return "$" + numero;
  }
}
