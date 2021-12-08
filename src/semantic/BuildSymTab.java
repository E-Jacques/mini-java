package semantic;

import java.util.ArrayList;
import java.util.List;

import main.CompilerException;
import main.Debug;
import semantic.symtab.*;
import syntax.ast.*;

/** Construction de la Table de Symboles. */
public class BuildSymTab extends AstVisitorDefault {
  /** La structure de données de l'analyse sémantique. */
  private final SemanticTree semanticTree;

  /**
   * L'attribut hérité Scope.
   * Entrée dans la table des symboles pour chaque nœud
   */
  private Scope currentScope;

  /** L'attribut hérité Klass. Fournit le Type de la variable "this". */
  private InfoKlass currentKlass;

  /** Des erreurs de redéfinition dans la table de symbole. */
  private boolean error;

  /**
   * Constructeur.
   * 
   * @param semanticTree L'arbre sémantique
   */
  public BuildSymTab(final SemanticTree semanticTree) {
    this.error = false;
    this.semanticTree = semanticTree;
    this.currentScope = semanticTree.rootScope;
    this.currentKlass = null;
    addObjectKlass();
    semanticTree.axiom.accept(this);
  }

  /**
   * Sortie en erreur.
   * 
   * @return true si erreurs sémantiques de redéfinition des symboles
   */
  public boolean getError() {
    return error;
  }

  // helpers ...
  /**
   * "setter" de l'attribut "Scope".
   * 
   * @param n  Le nœud AST
   * @param sc le scope
   */
  private void setScope(final AstNode n, final Scope sc) {
    semanticTree.scopeAttr.set(n, sc);
  }

  /**
   * "getter" de l'attribut "Scope".
   * 
   * @param n Le nœud AST
   * @return le scope
   */
  private Scope getScope(final AstNode n) {
    return semanticTree.scopeAttr.get(n);
  }

  /**
   * Ajout d'une déclaration de classe et création nouvelle portée.
   * 
   * @param sc La portée courante
   * @param kl La définition de classe
   * @return La portée pour la nouvelle classe
   */
  private Scope newKlassScope(final Scope sc, final InfoKlass kl) {
    checkRedef(sc.insertKlass(kl));
    final Scope fils = new Scope(sc, kl.getName());
    kl.setScope(fils);
    return fils;
  }

  /**
   * Ajout d'une déclaration de Méthode et création de 2 nouvelles portées.
   * Inclus l'ajout des paramètres formels dans la portée intermédiaire
   * 
   * @param sc La portée courante
   * @param m  La définition de la méthode
   * @return La portée pour la nouvelle méthode
   */
  private Scope newMethodScope(final Scope sc, final InfoMethod m) {
    checkRedef(sc.insertMethod(m));
    final Scope fils = new Scope(sc, m.getName() + "_args");
    for (InfoVar v : m.getArgs())
      checkRedef(fils.insertVariable(v));
    final Scope pf = new Scope(fils, m.getName());
    m.setScope(pf);
    return pf;
  }

  /**
   * Gestion des redéfinitions dans une même portée.
   * NB : HashMap.add() non null => already exists
   * 
   * @param i La déclaration á tester
   */
  private void checkRedef(final Info i) {
    if (i != null) {
      Debug.logErr("BuildSymtab : Duplication d'identificateur " + i);
      error = true;
    }
  }

  /**
   * Création de la classe Object avec méthode equals().
   * La classe est nécessaite comme racine de la hierarchie des classes.
   * La méthode equals est pour le fun.
   */
  private void addObjectKlass() {
    Scope sc = currentScope; // ==rootScope
    final InfoKlass kl = new InfoKlass("Object", null);
    sc = newKlassScope(sc, kl);
    final InfoMethod m = new InfoMethod("boolean", "equals",
        new InfoVar("this", kl.getName()),
        new InfoVar("o", kl.getName()));
    sc = newMethodScope(sc, m);
  }

  ////////////// Visit ////////////////////////
  // visite par défaut avec gestion de l'attribut hérité currentScope
  @Override
  public void defaultVisit(final AstNode n) {
    setScope(n, currentScope);
    System.out.println(n);
    for (AstNode f : n)
      f.accept(this);
    currentScope = getScope(n);
  }

  @Override
  public void visit(final Klass n) {
    setScope(n, this.currentScope);
    n.klassId.accept(this);
    this.currentKlass = new InfoKlass(n.klassId.name, n.parentId.name);
    this.currentScope = newKlassScope(this.currentScope, this.currentKlass);

    for (AstNode v : n.vars) {
      v.accept(this);
    }

    for (AstNode m : n.methods) {
      m.accept(this);
    }

    currentKlass = null;
    this.currentScope = getScope(n);
  }

  @Override
  public void visit(final Var n) {
    setScope(n, currentScope);
    for (AstNode f: n) f.accept(this);
    currentScope.insertVariable(new InfoVar(n.varId.name, n.typeId.name));
    currentScope = getScope(n);
  }

  @Override
  public void visit(final Formal n) {
    setScope(n, currentScope);
    for (AstNode f: n) f.accept(this);
    currentScope.insertVariable(new InfoVar(n.varId.name, n.typeId.name));
    currentScope = getScope(n);
  }

  @Override
  public void visit(final Method n) {
    setScope(n, currentScope);
    List<InfoVar> formalsList = new ArrayList<InfoVar>();
    formalsList.add(new InfoVar("this", this.currentKlass.getName()));
    for (AstNode f : n.fargs) {
      formalsList.add(new InfoVar(((Formal) f).varId.name, ((Formal) f).typeId.name));
    }

    final InfoMethod mInfo = new InfoMethod(n.returnType.name, n.methodId.name, formalsList);
    currentScope = newMethodScope(this.currentScope, mInfo);

    for (AstNode v : n.vars) {
      v.accept(this);
    }

    for (AstNode stmt : n.stmts) {
      stmt.accept(this);
    }

    n.returnExp.accept(this);
  }

  // Visites Spécifiques : (non defaultVisit)
  // - new Scope : KlassMain. Klass, Method, StmtBlock
  // - Déclarations : KlassMain, Klass, Method, Var, (Formal in Method)

  /*
   * Not really usefull !!
   * 
   * @Override
   * public void visit(final KlassMain n) {
   * setScope(n, currentScope);
   * n.klassId.accept(this);
   * final InfoKlass kl = new InfoKlass(n.klassId.name, "Object");
   * currentKlass = kl;
   * currentScope = newKlassScope(currentScope, kl);
   * n.argId.accept(this);
   * final java.util.List<InfoVar> formals = new java.util.ArrayList<>();
   * formals.add(new InfoVar(n.argId.name, "String[]"));
   * final InfoMethod m = new InfoMethod("void", "main",formals);
   * currentScope = newMethodScope(currentScope, m);
   * n.stmt.accept(this);
   * currentKlass = null;
   * currentScope = getScope(n);
   * }
   */

}
