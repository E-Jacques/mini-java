package intermediate;

import intermediate.ir.*;
import main.Debug;
import main.EnumOper;
import semantic.SemanticAttribut;
import semantic.SemanticTree;
import syntax.ast.*;

/** Génération de la forme intermédiaire (Code à 3 adresses). */
public class Intermediate extends AstVisitorDefault {
  /**
   * L'arbre décoré et la table de symbol.
   * Entrée de la génération intermédiaire
   */
  private final SemanticTree semanticTree;

  /** La représentation intermédiaire. Sortie de la génération intermédiaire */
  private final IR ir;

  /**
   * L'attribut synthétisé nodeVar.
   * Variable IR Temp pour résultat des expressions
   */
  private final SemanticAttribut<IRvariable> varAttr;

  /**
   * L'attribut synthétisé currentMethod.
   * Utilisé comme portée pour les variables IRtempo
   */
  private String currentMethod;

  /**
   * Constructeur.
   * 
   * @param semanticTree L'arbre sémantique
   */
  public Intermediate(final SemanticTree semanticTree) {
    this.semanticTree = semanticTree;
    this.ir = new IR(semanticTree);
    this.varAttr = new SemanticAttribut<>();
    this.currentMethod = null;
    semanticTree.axiom.accept(this); // => visit((Axiome)axiome)
    if (Debug.INTERMED)
      Debug.log(ir);
  }

  /*
   * "getter" de l'attribut Var.
   * 
   * @param n Le nœud de l'AST
   * 
   * @return La valeur de l'attribut Var
   */
  private IRvariable getVar(final AstNode n) {
    return varAttr.get(n);
  }

  /*
   * "setter" de l'attribut Var.
   * 
   * @param n Le nœud de l'AST
   * 
   * @param La valeur de l'attribut Var
   */
  private IRvariable setVar(final AstNode n, final IRvariable irv) {
    return varAttr.set(n, irv);
  }

  /**
   * Structure de données en sortie de la génération de code intermédiaire.
   * 
   * @return La forme intermédiaire générée
   */
  public IR getResult() {
    return ir;
  }

  //// Helpers
  /**
   * Ajout d'une instruction au programme IR.
   * 
   * @param irq L'instruction ajoutée en fin de programme
   */
  private void add(final IRquadruple irq) {
    ir.program.add(irq);
  }

  /**
   * Création d'un label.
   * 
   * @return Le label
   */
  private IRlabel newLabel() {
    return ir.newLabel();
  }

  /**
   * Création d'un label avec nom.
   * 
   * @param name Le nom de Label
   * @return Le label
   */
  private IRlabel newLabel(final String name) {
    return ir.newLabel(name);
  }

  /**
   * Création d'une constante.
   * 
   * @param value La valeur de la constante
   * @return La constante
   */
  private IRconst newConst(final int value) {
    return ir.newConst(value);
  }

  /**
   * Création d'une variable temporaire dans la portée de la méthode.
   * 
   * @return La variable temporaire
   */
  private IRtempo newTemp() {
    return ir.newTemp(currentMethod);
  }

  /**
   * Acces aux variables de l'AST dans la table des symboles.
   * 
   * @param name Le nom de la variable
   * @param n    Le nœud de l'AST (=> portée courrante)
   * @return La variable Intemediaire ou <b>null</b> si indéfinie
   */
  private IRvariable lookupVar(final String name, final AstNode n) {
    return semanticTree.scopeAttr.get(n).lookupVariable(name);
  }

  /////////////////// Visit ////////////////////
  @Override
  public void visit(final KlassMain n) {
    currentMethod = "main";
    add(new QLabel(newLabel(currentMethod)));
    defaultVisit(n);
    add(new QParam(newConst(0)));
    add(new QCallStatic(newLabel("_system_exit"), newConst(1)));
    currentMethod = null;
  }

  @Override
  public void visit(final ExprLiteralInt n) {
    setVar(n, newConst(n.value));
  }

  @Override
  public void visit(final ExprLiteralBool n) {
    setVar(n, newConst(n.value ? 1 : 0));
  }

  @Override
  public void visit(final ExprCall n) {
    defaultVisit(n);
    add(new QParam(getVar(n.receiver)));
    for (AstNode f : n.args) {
      add(new QParam(getVar(f)));
    }
    setVar(n, newTemp());
    add(new QCall(newLabel(n.methodId.name), newConst(n.args.size() + 1), getVar(n)));
  }

  @Override
  public void visit(final ExprIdent n) {
    setVar(n, lookupVar(n.varId.name, n));
  }

  @Override
  public void visit(final ExprOpBin n) {
    defaultVisit(n);

    IRvariable c1 = getVar(n.expr1);
    IRvariable c2 = getVar(n.expr2);

    if (c1 instanceof IRconst && c2 instanceof IRconst) {
      IRconst c1Const = (IRconst) c1;
      IRconst c2Const = (IRconst) c2;

      switch (n.op) {
        case AND:
          setVar(n, newConst((c1Const.getValue() * c2Const.getValue() > 0) ? 1 : 0));
          break;
        case TIMES:
          setVar(n, newConst(c1Const.getValue() * c2Const.getValue()));
          break;
        case LESS:
          setVar(n, newConst((c1Const.getValue() < c2Const.getValue()) ? 1 : 0));
          break;
        case MINUS:
          setVar(n, newConst(c1Const.getValue() - c2Const.getValue()));
          break;
        case PLUS:
          setVar(n, newConst(c1Const.getValue() + c2Const.getValue()));
          break;
        default:
          break;
      }
    } else {
      if (n.op == EnumOper.AND) {
        IRlabel label = newLabel();

        setVar(n, newTemp());
        add(new QCopy(getVar(n.expr1), getVar(n)));
        add(new QJumpCond(label, getVar(n.expr1)));

        add(new QCopy(getVar(n.expr2), getVar(n)));
        add(new QLabel(label));
        return;
      }
      
      setVar(n, newTemp());
      add(new QAssign(n.op, c1, c2, getVar(n)));
    }
  }

  @Override
  public void visit(final ExprOpUn n) {
    defaultVisit(n);

    if (getVar(n.expr) instanceof IRconst) {
      setVar(n, newConst(((IRconst) getVar(n.expr)).getValue() > 0 ? 0 : 1));
    } else {
      setVar(n, newTemp());
      add(new QAssignUnary(n.op, getVar(n.expr), getVar(n)));
    }
  }

  @Override
  public void visit(final ExprNew n) {
    defaultVisit(n);

    setVar(n, newTemp());
    add(new QNew(newLabel(n.klassId.name), getVar(n)));
  }

  @Override
  public void visit(final Method n) {
    currentMethod = n.methodId.name;
    add(new QLabel(newLabel(currentMethod)));
    defaultVisit(n);
    add(new QReturn(getVar(n.returnExp)));
    currentMethod = null;
  }

  @Override
  public void visit(final StmtAssign n) {
    defaultVisit(n);
    add(new QCopy(getVar(n.value), lookupVar(n.varId.name, n)));
  }

  @Override
  public void visit(final StmtPrint n) {
    defaultVisit(n);
    add(new QParam(getVar(n.expr)));
    add(new QCallStatic(newLabel("_system_out_println"), newConst(1)));
  }

  @Override
  public void visit(final StmtIf n) {
    IRlabel falseLabel = newLabel();
    IRlabel endLabel = newLabel();

    n.test.accept(this);
    add(new QJumpCond(falseLabel, getVar(n.test)));
    n.ifTrue.accept(this);
    add(new QJump(endLabel));
    add(new QLabel(falseLabel));
    n.ifFalse.accept(this);
    add(new QLabel(endLabel));
  }

  @Override
  public void visit(final StmtWhile n) {
    IRlabel loopLabel = newLabel();
    IRlabel endLabel = newLabel();
    add(new QLabel(loopLabel));
    n.test.accept(this);
    add(new QJumpCond(endLabel, getVar(n.test)));
    n.body.accept(this);
    add(new QJump(loopLabel));
    add(new QLabel(endLabel));
  }

  @Override
  public void visit(final StmtArrayAssign n) {
    defaultVisit(n);
    add(new QAssignArrayTo(getVar(n.value), getVar(n.index), lookupVar(n.arrayId.name, n)));
  }

  @Override
  public void visit(final ExprArrayLength n) {
    defaultVisit(n);
    setVar(n, newTemp());
    add(new QLength(getVar(n.array), getVar(n)));
  }

  @Override
  public void visit(final ExprArrayLookup n) {
    defaultVisit(n);
    setVar(n, newTemp());
    add(new QAssignArrayFrom(getVar(n.array), getVar(n.index), getVar(n)));
  }

  @Override
  public void visit(final ExprArrayNew n) {
    defaultVisit(n);
    setVar(n, newTemp());
    add(new QNewArray(newLabel(n.type.name()), getVar(n.size), getVar(n)));
  }
}
