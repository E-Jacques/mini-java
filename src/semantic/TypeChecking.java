package semantic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.CompilerException;
import main.EnumOper;
import main.EnumType;
import semantic.symtab.*;
import syntax.ast.*;

/**
 * Contrôle de Type.
 * <ul>
 * <li>Calcule l'attribut synthétisé Type : requis pour les nœuds Expr*
 * <li>Vérifie les contraintes de Typage de minijava
 * </ul>
 */
public class TypeChecking extends AstVisitorDefault {
  // revoir une classe Type adéquat pour union primitif/class. String inadéquat
  /** Nom de type booleen. */
  private static final String BOOL = main.EnumType.BOOL.toString();
  private static final String BOOL_ARRAY = main.EnumType.BOOL_ARRAY.toString();
  /** Nom de type entier. */
  private static final String INT = main.EnumType.INT.toString();
  private static final String INT_ARRAY = main.EnumType.INT_ARRAY.toString();
  /** Nom de type indéfini. */
  private static final String VOID = main.EnumType.UNDEF.toString();

  /** La structure de données de l'analyse sémantique. */
  private final SemanticTree semanticTree;

  private InfoKlass currentKlass;

  /** Des erreurs de Type. */
  private boolean error;

  /**
   * Constructeur.
   * 
   * @param semanticTree the semantic tree
   */
  public TypeChecking(final SemanticTree semanticTree) {
    this.error = false;
    this.currentKlass = null;
    this.semanticTree = semanticTree;
    semanticTree.axiom.accept(this);
  }

  /**
   * Sortie en erreur.
   * 
   * @return Des erreurs sémantiques de typage
   */
  public boolean getError() {
    return error;
  }

  // //// Helpers
  /**
   * "getter" pour l'attribut Type.
   * 
   * @param n Le nœud de l'AST
   * @return le nom de type
   */
  private String getType(final AstNode n) {
    return semanticTree.typeAttr.get(n);
  }

  /**
   * "setter" pour l'attribut Type.
   * 
   * @param n    Le nœud de l'AST
   * @param type le nom de type
   */
  private void setType(final AstNode n, final String type) {
    semanticTree.typeAttr.set(n, type);
  }

  /**
   * "getter" pour l'attribut Scope.
   * 
   * @param n Le nœud de l'AST
   * @return La portée courante du nœud
   */
  private Scope getScope(final AstNode n) {
    return semanticTree.scopeAttr.get(n);
  }

  /**
   * Recherche d'une classe dans la table des symboles".
   * 
   * @param name Le nom de la classe
   * @return La définition de la classe ou <b>null</b> si indéfinie
   */
  private InfoKlass lookupKlass(final String name) {
    return semanticTree.rootScope.lookupKlass(name);
  }

  //// helpers
  /**
   * Rapport d'erreur.
   * 
   * @param where Le nœud de l'AST en faute
   * @param msg   Le message d'erreur
   */
  private void erreur(final AstNode where, final String msg) {
    main.Debug.logErr(where + " " + msg);
    error = true;
  }

  /**
   * Comparaison de type.
   * 
   * @param t1 Nom de type 1
   * @param t2 Nom de type 2
   * @return true si t2 est sous-type de t1
   */
  private boolean compareType(final String t1, final String t2) {
    if (t2 == null)
      return false;
    if (t2.equals(t1))
      return true;
    // sinon (t1 ancêtre de t2) ?
    final InfoKlass kl2 = lookupKlass(t2);
    if (kl2 != null)
      return compareType(t1, kl2.getParent());
    return false;
    // NB : Suppose héritage valide !!!
  }

  /**
   * Validation du transtypage implicite.
   * 
   * @param t1    Le type attendu
   * @param t2    Le type testé
   * @param msg   Le message d'erreur si t2 ne cast pas en t1
   * @param where Le nœud de l'AST en faute
   */
  private void checkType(final String t1, final String t2,
      final String msg, final AstNode where) {
    if (!compareType(t1, t2)) {
      erreur(where, "Wrong Type : " + t2 + "->" + t1 + ";  " + msg);
    }
  }

  /**
   * Validation du nom de type.
   * 
   * @param type  Le nom de type testé
   * @param where Le nœud de l'AST en faute
   */
  private void checkTypeName(final String type, final AstNode where) {
    if (type.equals(BOOL) || type.equals(INT))
      return;
    if (type.equals(BOOL_ARRAY) || type.equals(INT_ARRAY))
      return;
    if (type.equals(VOID))
      return;
    if (lookupKlass(type) != null)
      return;
    erreur(where, "Unknown Type : " + type);
  }

  /**
   * Recherche du type d'une variable dans la table des symboles.
   * 
   * @param n    Le nœud de l'AST (pour obtenir la portée courrante
   * @param name Le nom de la variable
   * @return Le nom de type (VOID pour type inconnu)
   */
  private String lookupVarType(final AstNode n, final String name) {
    final InfoVar v = getScope(n).lookupVariable(name);
    if (v == null)
      return VOID;
    else
      return v.getType();
  }

  /////////////////// Visit ////////////////////
  // Visites spécifiques : (non defaultvisit)
  // - Expr* : set Type
  // - Stmt* + Expr* (sauf exceptions) : Compatibilité des Types
  // - Type : Validité des noms de type dans Var, Method, Formal
  // - Method : returnType compatible avec Type(returnExpr)
  // NB : validité des déclarations de classe prérequis (checkInheritance)

  @Override
  public void visit(final ExprLiteralInt n) {
    setType(n, INT);
  }

  @Override
  public void visit(final ExprLiteralBool n) {
    setType(n, BOOL);
  }

  @Override
  public void visit(final ExprNew n) {
    defaultVisit(n);
    setType(n, n.klassId.name);
  }

  @Override
  public void visit(final ExprOpUn n) {
    defaultVisit(n);
    checkType(BOOL, getType(n.expr), "non bool for boolean operation", n);

    if (n.op == EnumOper.NOT) {
      setType(n, BOOL);
    } else {
      erreur(n, "Unkown operator");
    }
  }

  @Override
  public void visit(final ExprIdent n) throws CompilerException {
    defaultVisit(n);
    // if (n.varId.name == "this") {
    // setType(n, this.currentKlass.getName());
    // } else {
    String t = lookupVarType(n, n.varId.name);
    if (t == "undef")
      throw new CompilerException("'" + n.varId.name + "' is not defined");
    setType(n, t);
    // }
  }

  @Override
  public void visit(final Method n) {
    List<InfoVar> formalsList = new ArrayList<InfoVar>();
    formalsList.add(new InfoVar("this", this.currentKlass.getName()));
    for (AstNode f : n.fargs) {
      formalsList.add(new InfoVar(((Formal) f).varId.name, ((Formal) f).typeId.name));
    }

    defaultVisit(n);
    checkType(n.returnType.name, getType(n.returnExp), "Return type should match type in method declaration", n);
  }

  @Override
  public void visit(final Klass n) {
    this.currentKlass = new InfoKlass(n.klassId.name, n.parentId.name);
    this.currentKlass.setScope(getScope(n));
    defaultVisit(n);
  }

  @Override
  public void visit(final Formal n) {
    defaultVisit(n);
    setType(n, getType(n.typeId));
  }

  @Override
  public void visit(final ExprOpBin n) {
    defaultVisit(n);
    if (n.op == EnumOper.AND) {
      checkType(BOOL, getType(n.expr1), "non bool for and operation (expr1)", n);
      checkType(BOOL, getType(n.expr2), "non bool for and operation (expr2)", n);
      setType(n, BOOL);
    } else if (n.op == EnumOper.LESS) {
      checkType(INT, getType(n.expr1), "non int for and operation (expr1)", n);
      checkType(INT, getType(n.expr2), "non int for and operation (expr2)", n);
      setType(n, BOOL);
    } else {
      checkType(INT, getType(n.expr1), "non int for and operation (expr1)", n);
      checkType(INT, getType(n.expr2), "non int for and operation (expr2)", n);
      setType(n, INT);
    }
  }

  @Override
  public void visit(final ExprCall n) throws CompilerException {
    defaultVisit(n);
    InfoKlass ik = this.semanticTree.rootScope.lookupKlass(getType(n.receiver));
    InfoMethod m = ik.getScope().lookupMethod(n.methodId.name);
    if (m == null) {
      erreur(n, "Method '" + n.methodId.name + "' not found in ");
    }

    int idx = 1;
    for (AstNode f : n.args) {
      f.accept(this);
      if (idx > m.getArgs().length - 1) {
        erreur(n, "Too much args in " + m.getName() + " ( Expected " + m.getArgs().length + " got " + idx + ")");
      }
      checkTypeName(m.getArgs()[idx].getType(), n);
      checkType(m.getArgs()[idx].getType(), getType(f),
          "non " + m.getArgs()[idx].getType() + " args type for method '" + m.getName() + "' call", n);
      idx++;
    }

    if (idx < m.getArgs().length) {
      erreur(n, "Too few args in " + m.getName() + " ( Expected " + m.getArgs().length + " got " + idx + ")");
    }

    setType(n, m.getReturnType());
  }

  @Override
  public void visit(final Var n) {
    defaultVisit(n);
    setType(n, getType(n.typeId));
  }

  @Override
  public void visit(final ExprArrayLength n) {
    defaultVisit(n);
    setType(n, INT);
  }

  @Override
  public void visit(final ExprArrayLookup n) {
    defaultVisit(n);
    checkType(getType(n.index), INT, "Index have to be int", n);
    setType(n, getType(n.array) == INT_ARRAY ? INT : BOOL);
  }

  @Override
  public void visit(final ExprArrayNew n) {
    defaultVisit(n);
    setType(n, n.type.toString());
  }

  @Override
  public void visit(final StmtArrayAssign n) {
    defaultVisit(n);
    String arrayType = lookupVarType(n, n.arrayId.name) == INT_ARRAY ? INT : BOOL;
    checkType(getType(n.index), INT, "Index have to be int", n);
    checkType(getType(n.value), arrayType, "Index have to be int", n);
  }

  @Override
  public void visit(final Type n) {
    defaultVisit(n);
    checkTypeName(n.name, n);
    setType(n, n.name);
  }

  @Override
  public void visit(final StmtPrint n) {
    defaultVisit(n);
    checkType(INT, getType(n.expr), "non integer for printing", n);
  }

  @Override
  public void visit(final StmtAssign n) {
    defaultVisit(n);
    checkType(lookupVarType(n, n.varId.name), getType(n.value),
        "'" + n.varId.name + "' Variable type should be the same as expression type", n);
  }

  @Override
  public void visit(final StmtIf n) {
    defaultVisit(n);
    checkType(BOOL, getType(n.test), "Test type should be BOOL", n);
  }

  @Override
  public void visit(final StmtWhile n) {
    defaultVisit(n);
    checkType(BOOL, getType(n.test), "Test type should be BOOL", n);
  }
}
