package codegen;

import intermediate.IR;
import intermediate.ir.*;
import main.EnumOper.*;

/** La traduction de la forme intermédiaire vers MIPS. */
public class ToMips extends IRvisitorDefault {
  /** La taille mémoire d'un registre variable. 32 bits !! */
  protected static final int SIZEOF = 4;

  /** Le Nombre d'arguments passés dans les registres. */
  protected static final int NBARGS = 4;

  /** Les 4 registres pour les arguments de fonction. */
  protected static final Reg[] AREGS = { Reg.A0, Reg.A1, Reg.A2, Reg.A3 };

  /** Les registres "s" a sauvegarder. (not usefull) */
  protected static final Reg[] S_REG_USED_LIST = {};
  // {Reg.S0, Reg.S1, Reg.S2, Reg.S3, Reg.S4, Reg.S5, Reg.S6, Reg.S7};

  /** Les registres "t" a sauvegarder. (not usefull) */
  protected static final Reg[] T_REG_USED_LIST = {};
  // {Reg.T0,Reg.T1,Reg.T2,Reg.T3,Reg.T4,Reg.T5,Reg.T6,Reg.T7,Reg.T8,Reg.T9};

  /** L'allocateur. */
  protected final Allocator allocator;

  /** Le Writer Mips. */
  protected final MipsWriter mw;

  /**
   * Une table pour les paramètres.
   * Empile les QParams pour traitement par QCall ou QCallStatic
   */
  protected final java.util.ArrayList<IRvariable> params = new java.util.ArrayList<>();

  /**
   * Constructeur.
   * 
   * @param ir         La forme intermédiaire
   * @param allocator  L'allocateur
   * @param mipsWriter Le writer pour l'impression MIPS
   */
  public ToMips(final IR ir, final Allocator allocator,
      final MipsWriter mipsWriter) {
    this.allocator = allocator;
    this.mw = mipsWriter;
    mw.println(".text");

    for (IRquadruple q : ir.program) {
      mw.com(q.toString()); // put IR as comment
      q.accept(this);
    }
  }

  // Helpers : save/load dans les registre
  /**
   * Load une variable IR dans un registre.
   * 
   * @param reg Le registre à charger
   * @param v   La variable IR
   */
  void regLoad(final Reg reg, final IRvariable v) {
    mw.inst(allocator.access(v).load(reg));
  }

  /**
   * Load une variable IR dans un registre.
   * Utilise la sauvegarde des registres si la variable est dans a0,a1,a2,a3.
   * 
   * @param reg Le registre à charger
   * @param v   La variable IR
   */
  void regLoadSaved(final Reg reg, final IRvariable v) {
    mw.inst(allocator.access(v).loadSaved(reg));
  }

  /**
   * Store un registre dans une variable IR.
   * 
   * @param reg Le registre à sauver
   * @param v   La variable IR
   */
  void regStore(final Reg reg, final IRvariable v) {
    mw.inst(allocator.access(v).store(reg));
  }

  // helpers : save/restore dans la pile
  /**
   * Empile le contenu de N registres (sauvegarde).
   * 
   * @param regs Les registres à empiler
   */
  void push(final Reg... regs) {
    final int size = regs.length;
    mw.plus(Reg.SP, -SIZEOF * size);
    for (int i = 0; i < size; i++) {
      mw.storeOffset(regs[i], SIZEOF * (size - i - 1), Reg.SP);
    }
  }

  /**
   * Dépile le contenu de N registres (restauration).
   * 
   * @param regs Les registres à dépiler
   */
  void pop(final Reg... regs) {
    final int size = regs.length;
    for (int i = 0; i < size; i++) {
      mw.loadOffset(regs[i], SIZEOF * (size - i - 1), Reg.SP);
    }
    mw.plus(Reg.SP, SIZEOF * size);
  }

  /// helpers : Convention d'appel
  /** Démarrage de l'appelé. */
  void calleeIn() {
    int offset = -SIZEOF;
    mw.storeOffset(Reg.RA, offset, Reg.FP);
    for (Reg reg : S_REG_USED_LIST) {
      offset -= SIZEOF;
      mw.storeOffset(reg, offset, Reg.FP);
    }
  }

  /** Terminaison de l'appelé. */
  void calleeOut() {
    int offset = -SIZEOF;
    mw.loadOffset(Reg.RA, offset, Reg.FP);
    for (Reg reg : S_REG_USED_LIST) {
      offset -= SIZEOF;
      mw.loadOffset(reg, offset, Reg.FP);
    }
  }

  /** Sauvegarde de l'appellant. (avant l'appel) */
  void callerSave() {
    push(T_REG_USED_LIST);
    push(Reg.FP, Reg.A3, Reg.A2, Reg.A1, Reg.A0);
  }

  /** Restauration de l'appellant. (retour d'appel) */
  void callerRestore() {
    pop(Reg.FP, Reg.A3, Reg.A2, Reg.A1, Reg.A0);
    pop(T_REG_USED_LIST);
  }

  // ////////////// VISIT ///////////////
  /**
   * <b>QLabel : </b> <br>
   * Label arg1 .
   */
  @Override
  public void visit(final QLabel q) {
    mw.label(q.arg1.getName());
  }

  /**
   * <b>QParam : </b> <br>
   * Param arg1 .
   */
  @Override
  public void visit(final QParam q) {
    params.add(q.arg1);
  }

  /**
   * <b>QCallStatic :</b> <br>
   * static void call arg1 [numParams=arg2] .
   */
  @Override
  public void visit(final QCallStatic q) {
    final String function = q.arg1.getName();
    final int nbArg = Integer.parseInt(q.arg2.getName());

    if (nbArg != params.size()) {
      throw new main.CompilerException("ToMips : Params error");
    }
    if (nbArg > NBARGS) {
      throw new main.CompilerException("ToMips : too many args for " + function);
    }
    switch (function) {
      case "_system_exit":
      case "_system_out_println":
        push(Reg.A0);
        regLoad(Reg.A0, params.get(0));
        mw.jumpIn(function);
        pop(Reg.A0);
        break;
      case "main":
        throw new main.CompilerException("ToMpis : recurse main forbidden");
      default:
        throw new main.CompilerException("ToMips : wrong special " + function);
    }
    params.clear();
  }

  @Override
  public void visit(QAssign q) {
    Boolean arg2InReg = true;
    this.regLoad(Reg.V0, q.arg1);

    if (q.arg2 instanceof IRconst) {
      arg2InReg = false;
    }

    switch (q.op) {
      case PLUS:
        if (arg2InReg) {
          this.regLoad(Reg.V1, q.arg2);
          mw.plus(Reg.V0, Reg.V1);
        } else {
          mw.plus(Reg.V0, ((IRconst) q.arg2).getValue());
        }
        break;
      case TIMES:
        this.regLoad(Reg.V1, q.arg2);
        mw.fois(Reg.V0, Reg.V1);
        break;
      case MINUS:
        this.regLoad(Reg.V1, q.arg2);
        mw.moins(Reg.V0, Reg.V1);
        break;
      case AND:
        this.regLoad(Reg.V1, q.arg2);
        mw.et(Reg.V0, Reg.V1);
        break;
      case LESS:
        this.regLoad(Reg.V1, q.arg2);
        mw.inferieur(Reg.V0, Reg.V1);
        break;
      default:
        break;
    }

    this.regStore(Reg.V0, q.result);
  }

  @Override
  public void visit(QNew q) {
    push(Reg.A0);

    String klassName =((IRlabel) q.arg1).getName();
    Integer classSize = allocator.classSize(klassName);
    mw.load(Reg.A0, classSize);
    mw.jumpIn("_new_object");
    this.regStore(Reg.V0, q.result);
    pop(Reg.A0);
  }

  @Override
  public void visit(final QCall q) {
    String methodName = ((IRlabel) q.arg1).getName();
    int nbArg = ((IRconst) q.arg2).getValue() ;
    
    if (nbArg != params.size()) {
      throw new main.CompilerException("ToMips : Params error");
    }
    if (nbArg > NBARGS) {
      throw new main.CompilerException("ToMips : too many args for " + methodName);
    }
    
    this.callerSave();
    for (int i = 0; i < nbArg; i++) {
      this.regLoadSaved(AREGS[i], params.get(i));
    }
    
    mw.jumpIn(methodName);
    this.callerRestore();
    this.regStore(Reg.V0, q.result);

    params.clear();
  }


  @Override
  public void visit(final QReturn q) {
    this.regLoad(Reg.V0, q.arg1);
    this.calleeOut();
    mw.jumpOut();
  }

  @Override
  public void visit(final QLabelMeth q) {
    String methodName = ((IRlabel) q.arg1).getName();
    mw.label(methodName);
    this.calleeIn();
  }

  @Override
  public void visit (final QJumpCond q) {
    this.regLoad(Reg.V0, q.arg2);
    mw.jumpIfNot(Reg.V0, q.arg1.getName());
  }

  @Override
  public void visit(final QJump q) {
    mw.jump(q.arg1.getName());
  }

  @Override
  public void visit(final QAssignUnary q) {
    if (q.op == main.EnumOper.NOT) {
      this.regLoad(Reg.V0, q.arg1);
      mw.not(Reg.V0);
    } 

    this.regStore(Reg.V0, q.result);
  }

  @Override
  public void visit (final QCopy q) {
    this.regLoad(Reg.V0, q.arg1);
    this.regStore(Reg.V0, q.result);
  }

  @Override
  public void visit (final QAssignArrayFrom q) {
    push(Reg.T0, Reg.T1);

    this.regLoad(Reg.T0, q.arg1);
    this.regLoad(Reg.T1, q.arg2);

    mw.fois4(Reg.T1); // 4 * i
    mw.plus(Reg.T0, Reg.T1);

    mw.loadOffset(Reg.V0, 4, Reg.T0);
    this.regStore(Reg.V0, q.result);

    pop(Reg.T0, Reg.T1);
  }

  @Override
  public void visit (final QAssignArrayTo q) {
    push(Reg.T0, Reg.T1);

    this.regLoad(Reg.T0, q.result);
    this.regLoad(Reg.T1, q.arg2);

    mw.fois4(Reg.T1);
    mw.plus(Reg.T0, Reg.T1);

    this.regLoad(Reg.V0, q.arg1);
    mw.storeOffset(Reg.V0, 4, Reg.T0);

    pop(Reg.T0, Reg.T1);
  }

  @Override 
  public void visit (final QNewArray q) {
    push(Reg.A0);

    this.regLoad(Reg.A0, q.arg2);
    mw.fois4(Reg.A0);
    mw.plus(Reg.A0, 4);

    mw.jumpIn("_new_object");
    this.regStore(Reg.V0, q.result);
    this.regLoad(Reg.V0, q.arg2);

    pop(Reg.A0);
  }

  @Override
  public void visit (final QLength q) {
    push(Reg.T0);

    this.regLoad(Reg.T0, q.arg1);
    mw.loadOffset(Reg.V0, 4, Reg.T0);
    this.regStore(Reg.V0, q.result);

    pop(Reg.T0);
  }
}
