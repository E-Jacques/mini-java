package codegen;

/** Concaténation d'un Runtime MIPS.
 * <ul><li> equals: boolean Object.equals(Object x)
 * <li> _new_object: "void *malloc(int size)"
 * <li> _system_out_println: void System.out.println(int n)
 * <li> _system_exit: void System.exit(int status) </ul> */
public class LinkRuntime {
  /** Le writer Mips. */
  private MipsWriter mw;

  /** Constructeur.
   * @param mipsWriter Un writer MIPS */
  public LinkRuntime(final MipsWriter mipsWriter) {
    this.mw = mipsWriter;
    mw.println("#### RUNTIME MIPS (LinkRuntime) ####");
    malloc();
    objEquals();
    sysPrint();
    sysExit();
  }

  /** La fonction d'allocation mémoire. */
  private void malloc() {
    mw.println("_new_object:");
    mw.println("  ## \"void *malloc(int size)\"");
    mw.println("  ## IN  $a0 = number of bytes");
    mw.println("  ## OUT $v0 = allocated address");
    mw.println("    li   $v0, 9");
    mw.println("    syscall # sbrk");
    mw.println("  # initialize with zeros");
    mw.println("    move $t0, $a0");
    mw.println("    move $t1, $v0");
    mw.println("__LoopIn:");
    mw.println("  # do until $t0=0");
    mw.println("    beq  $t0, $zero, __LoopOut");
    mw.println("    sb   $zero, 0($t1)");
    mw.println("    addi $t1, $t1,  1 ");
    mw.println("    addi $t0, $t0, -1 ");
    mw.println("    j    __LoopIn ");
    mw.println("   # done");
    mw.println("__LoopOut:");
    mw.println("    jr   $ra");
  }

  /** La méthode Object.equals(). */
  private void objEquals() {
    mw.println("equals:  ## boolean Object.equals(Object)");
    mw.println("    seq $v0, $a0, $a1");
    mw.println("    jr   $ra ");
  }

  /** La fonction d'impression d'une valeur entière. */
  private void sysPrint() {
    mw.println("_system_out_println:");
    mw.println("  ## void System.out.println(int x)");
    mw.println("  ## IN  $a0 = integer to print");
    mw.println("    li   $v0,  1");
    mw.println("    syscall # print int");
    mw.println("    li   $a0, 10 # char LineFeed");
    mw.println("    li   $v0, 11");
    mw.println("    syscall # print char");
    mw.println("    jr   $ra");
  }

  /** La fonction Exit avec exit status. */
  private void sysExit() {
    mw.println("_system_exit:");
    mw.println("  ## void System.exit(int status)");
    mw.println("  ## IN  $a0 = exit status");
    mw.println("    move $a1, $a0");
    mw.println("    la   $a0, MsgExit");
    mw.println("    li   $v0,  4");
    mw.println("    syscall # print string");
    mw.println("    move $a0, $a1");
    mw.println("    li   $v0,  1");
    mw.println("    syscall # print int");
    mw.println("    li   $v0,  17");
    mw.println("    syscall # exit with status");
    mw.println(".data");
    mw.println("MsgExit: .asciiz \"Exit status \"");
    mw.println(".text");
  }
}
