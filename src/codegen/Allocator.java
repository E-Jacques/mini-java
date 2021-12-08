package codegen;

import java.util.HashMap;
import java.util.Map;

import codegen.access.*;
import intermediate.IR;
import intermediate.ir.IRconst;
import intermediate.ir.IRtempo;
import intermediate.ir.IRvariable;
import main.CompilerException;
import main.Debug;
import semantic.symtab.*;

/** Allocation mémoire : Frame, registre, ... . */
public class Allocator {
  /** La taille mémoire d'une variable. Tous les types utilisent 32 bits !! */
  private static final int SIZEOF = 4;

  /** Le Nombre d'arguments passés dans les registres. */
  private static final int NBARGS = 4;

  /** Les 4 registres pour les arguments de fonction. */
  private static final Reg[] AREGS = {Reg.A0, Reg.A1, Reg.A2, Reg.A3};

  /** Le Nombre de registres sauvegardés dans la frame (partie fixe). */
  private static final int NBREGS = 9; /* ra + s0...s7 */

  /** La forme intermédiaire en entrée. */
  private final intermediate.IR ir;

  /** La taille allouée pour les variables globales. */
  private int globalSize;

  /** La taille des instances de classes. */
  private final Map<String, Integer> classSize;

  /** La taille des frames de méthodes. */
  private final Map<String, Integer> frameSize;

  /** L'allocarion. Associe à chaque variable de l'IR,
   * le code MIPS pour Load/Store */
  private final Map<IRvariable, Access> access;

  /** Constructeur.
   * @param ir La forme intermédiaire */
  public Allocator(final IR ir) {
    this.globalSize = 0;
    this.classSize = new HashMap<>();
    this.frameSize = new HashMap<>();
    this.access = new HashMap<>();
    this.ir = ir;
    klassAlloc();
    methodAlloc();
    intermedAlloc();
    if (Debug.ALLOCATOR) {
      Debug.log(" globalSize (main) " + globalSize);
      Debug.log(" classSize " + classSize);
      Debug.log(" frameSize " + frameSize);
      Debug.log(" Access " + access);
    }
  }

  /** Taille de la mémoire pour les variables globales.
   * @return La taille des variables globales */
  public Integer globalSize() { return globalSize; }

  /** Taille mémoire d'une instance de la classe.
   * @param klassName Le nom de la classe
   * @return La taille d'une instance de classe */
  public Integer classSize(final String klassName) {
    return classSize.get(klassName);
  }

  /** Taille de la frame d'une méthode.
   * @param methodName Le nom de la méthode
   * @return La taille de la frame de la méthode */
  public Integer frameSize(final String methodName) {
    return frameSize.get(methodName);
  }

  /** Access d'une variable IR.
   * @param v La variable de la forme intermédiaire
   * @return L'acces MIPS à la variable */
  public Access access(final IRvariable v) { return access.get(v); }

  /** Calcul de la taille totale d'une instance de classe.
   * A extends B extends ... Object (extends null)
   *  new A == [champs Object] ... [champs B][champs A]
   * @param kl La définition de la classe
   * @return la taille totale d'une instance de la classe
   */
  private Integer klassSize(final InfoKlass kl) {
    if (kl == null) return 0;
    else {
      final InfoKlass parent = ir.rootScope.lookupKlass(kl.getParent());
      return SIZEOF * kl.getFields().size() + klassSize(parent);
    }
  }

  /** Allocation des champs des classes.
   * Calcul des offsets des champs par rapport à "this" == registre $a0
   * @throws CompilerException Classe invalide dans la table de symbole
   */
  private void klassAlloc() {
    classSize.put(null, 0);
    for (InfoKlass kl : ir.rootScope.getKlasses()) {
      if (kl == null) throw new CompilerException("Allocator : class==null");
      int off = klassSize(kl);
      classSize.put(kl.getName(), off);
      for (IRvariable v : kl.getFields()) {
        off -= SIZEOF;
        access.put(v, new AccessOff(Reg.A0, off));
      }
    }
  }

  /** Allocation des méthodes. */
  private void methodAlloc() {
    for (InfoKlass kl : ir.rootScope.getKlasses()) {
      for (InfoMethod m : kl.getMethods()) {
        methodAlloc(m);
      }
    }
  }

  /** Allocation de la trame (frame) d'une méthode.
   * Argn Argn-1 ... Arg4 | $ra $s0-$s7 locals IRlocals
   * @param m La définition de la méthode
   */
  private void methodAlloc(final InfoMethod m) {
    int frSize = 0;
    // fixed frame : save/restore $ra, $s0-$s7
    frSize += NBREGS * SIZEOF;
    // args : firsts in $ai, next in stack before FP
    int i = 0;
    for (IRvariable v : m.getArgs()) {
      if (i < NBARGS) access.put(v, new AccessReg(AREGS[i]));
      else access.put(v, new AccessOff(Reg.FP, SIZEOF * (i - NBARGS)));
      i++;
    }
    // local vars in Frame
    for (IRvariable v : m.getLocals()) {
      access.put(v, new AccessOff(Reg.FP, -SIZEOF - frSize));
      frSize += SIZEOF;
    }
    frameSize.put(m.getName(), frSize);
  }

  /** Allocation des variables IR : temporaire, constant=immediate. */
  private void intermedAlloc() {
    for (IRconst v : ir.consts) access.put(v, new AccessConst(v.getValue()));
    for (IRtempo v : ir.tempos) {
      final String methName = v.getScope();
      if ("main".equals(methName)) {
        access.put(v, new AccessOff(Reg.GP, globalSize));
        globalSize += SIZEOF;
      } else {
        int frSize = frameSize.get(methName);
        access.put(v, new AccessOff(Reg.FP, -SIZEOF - frSize));
        frSize += SIZEOF;
        frameSize.put(methName, frSize);
      }
    }
  }

}
