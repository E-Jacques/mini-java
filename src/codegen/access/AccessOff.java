package codegen.access;

import codegen.Reg;

/** Accès Mips par valeur de registre + offseet. */
public class AccessOff implements Access {
  /** Le registre contenant l'adresse de base. En pratique
   * <ul><li> "$fp" pour les variables locales dans la frame</li>
   * <li> "$a0" pour "this" et les champs de classe</li>
   * <li> "$gp" pour les variables locales</li>
   * <li> "$sp" pour les 4 premiers arguments d'une méthode</li></ul> */
  private final Reg register;

  /** L'offset d'adresse en Octet. */
  private final Integer offset;

  /** Constructeur. Variable à l'adresse "offset ( register )".
   * @param register  Adresse de base
   * @param offset    offset par rapport à l'adresse de base
   */
  public AccessOff(final Reg register, final Integer offset) {
    this.register = register;
    this.offset = offset;
  }

  @Override
  public String load(final Reg reg) {
    return "lw   " + reg + ", " + offset + '(' + this.register + ')';
  }

  @Override
  public String store(final Reg reg) {
    return "sw   " + reg + ", " + offset + '(' + this.register + ')';
  }

  @Override
  public String loadSaved(final Reg reg) {
    if (Reg.A0.equals(this.register)) {
      return "lw   " + reg + ", 0($sp)\n"
          + "\t lw   " + reg + ", " + offset + '(' + reg + ')';
    } else return load(reg);
  }

  @Override
  public Reg getRegister() { return null; }

  @Override
  public String toString() {
    return offset + '(' + this.register.toString() + ')';
  }
}
