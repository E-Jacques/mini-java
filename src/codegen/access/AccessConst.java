package codegen.access;

import codegen.Reg;

/** Accès Mips Immediate (Constante). */
public class AccessConst implements Access {
  /** The immediate. */
  private final int immediate;

  /** Constructeur.
   * @param immediate La valeur constante */
  public AccessConst(final int immediate) { this.immediate = immediate; }

  @Override
  public String store(final Reg reg) {
    throw new main.CompilerException("codegen : store in immediate !?!?");
  }

  @Override
  public String load(final Reg reg) {
    return "li   " + reg + ", " + immediate;
  }

  @Override
  public String loadSaved(final Reg reg) { return load(reg); }

  @Override
  public Reg getRegister() { return null; }

  @Override
  public String toString() { return "" + immediate; }
}
