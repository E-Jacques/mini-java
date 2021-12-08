package codegen.access;

import codegen.Reg;

/** Accès Mips dans registre. */
public class AccessReg implements Access {
  /** Le registre contenant la variable. */
  private final Reg register;

  /** Constructeur.
   * @param register Le registre */
  public AccessReg(final Reg register) { this.register = register; }

  @Override
  public String store(final Reg reg) {
    if (reg.equals(this.register)) return null;
    else return "move " + this.register + ", " + reg;
  }

  @Override
  public String load(final Reg reg) {
    if (reg.equals(this.register)) return null;
    else return "move " + reg + ", " + this.register;
  }

  @Override
  public String loadSaved(final Reg reg) {
    final String load = "lw   " + reg;
    switch (this.register) {
      case A0:
        return load + ", 0($sp)";
      case A1:
        return load + ", 4($sp)";
      case A2:
        return load + ", 8($sp)";
      case A3:
        return load + ", 12($sp)";
      default:
        return load(reg);
    }
  }

  @Override
  public Reg getRegister() { return this.register; }

  @Override
  public String toString() { return this.register.toString(); }
}
