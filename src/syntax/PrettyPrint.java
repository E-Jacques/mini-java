package syntax;

import main.IndentWriter;
import syntax.ast.*;

/** PrettyPrint minijava par visite de l'AST. */
public final class PrettyPrint extends AstVisitorDefault {
  /** Le Writer pour l'impression avec indentation. */
  private IndentWriter out;

  /** Constructeur.
   * @param axiom L'arbre de syntaxe */
  public PrettyPrint(final AstNode axiom) {
    this.out = new IndentWriter();
    axiom.accept(this); // => visit((Axiom)axiom)
  }

  /** Impression du résultat prettyprint. */
  @Override
  public String toString() {
    return out.toString();
  }

  // helpers
  /** Debut de block "{". */
  private void openBlock() { out.println('{'); out.indent(); }

  /** Fin de block "{". */
  private void closeBlock() { out.outdent(); out.println('}'); }

  /** Espace. */
  private void space() { out.print(' '); }

  /////////////////// Visit ////////////////////
  /** Visite générique pour une liste séparée par des virgules.
   * @param n Le nœud courant */
  private void visitCommaList(final AstNode n) {
    boolean first = true;
    for (AstNode f : n) {
      if (first) first = false;
      else out.print(", ");
      f.accept(this);
    }
  }

  // Productions de base, extends ASTNode
  @Override
  public void visit(final Axiom n) {
    defaultVisit(n);
  }

  @Override
  public void visit(final Klass n) {
    out.println();
    out.print("class ");
    n.klassId.accept(this);
    out.print(" extends ");
    n.parentId.accept(this);
    space();
    openBlock();
    n.vars.accept(this);
    n.methods.accept(this);
    closeBlock();
  }

  @Override
  public void visit(final KlassMain n) {
    out.print("class ");
    n.klassId.accept(this);
    openBlock();
    out.print("public static void main(String [] ");
    n.argId.accept(this);
    out.print(')');
    space();
    openBlock();
    n.stmt.accept(this);
    closeBlock();
    closeBlock();
  }

  @Override
  public void visit(final Method n) {
    out.println();
    out.print("public ");
    n.returnType.accept(this);
    n.methodId.accept(this);
    out.print('(');
    visitCommaList(n.fargs);
    out.print(')');
    space();
    openBlock();
    n.vars.accept(this);
    n.stmts.accept(this);
    out.print("return ");
    n.returnExp.accept(this);
    out.println(';');
    closeBlock();
  }

  @Override
  public void visit(final Formal n) {
    n.typeId.accept(this);
    n.varId.accept(this);
  }

  @Override
  public void visit(final Ident n) {
    out.print(n.name);
  }

  @Override
  public void visit(final Type n) {
    out.print(n.name);
    space();
  }

  @Override
  public void visit(final Var n) {
    n.typeId.accept(this);
    n.varId.accept(this);
    out.println(';');
  }

  // Expressions , extends Expr
  @Override
  public void visit(final ExprArrayLength n) {
    n.array.accept(this);
    out.print(".length");
  }

  @Override
  public void visit(final ExprArrayLookup n) {
    n.array.accept(this);
    out.print('[');
    n.index.accept(this);
    out.print(']');
  }

  @Override
  public void visit(final ExprArrayNew n) {
    out.print("new int [");
    n.size.accept(this);
    out.print(']');
  }

  @Override
  public void visit(final ExprCall n) {
    n.receiver.accept(this);
    out.print('.');
    n.methodId.accept(this);
    out.print('(');
    visitCommaList(n.args);
    out.print(')');
  }

  @Override
  public void visit(final ExprLiteralBool n) {
    out.print(n.value);
  }

  @Override
  public void visit(final ExprLiteralInt n) {
    out.print(n.value);
  }

  @Override
  public void visit(final ExprNew n) {
    out.print("new ");
    n.klassId.accept(this);
    out.print("()");
  }

  @Override
  public void visit(final ExprOpBin n) {
    // some unneeded parenthesis !? not trival to supress,
    // parenthesis required if parent node is an prioritary Oper ?!?
    out.print('(');
    n.expr1.accept(this);
    space();
    out.print(n.op);
    space();
    n.expr2.accept(this);
    out.print(')');
  }

  @Override
  public void visit(final ExprOpUn n) {
    out.print(n.op);
    space();
    n.expr.accept(this);
  }

  // Instructions, extends Stmt
  @Override
  public void visit(final StmtArrayAssign n) {
    n.arrayId.accept(this);
    out.print('[');
    n.index.accept(this);
    out.print("] = ");
    n.value.accept(this);
    out.println(';');
  }

  @Override
  public void visit(final StmtAssign n) {
    n.varId.accept(this);
    out.print(" = ");
    n.value.accept(this);
    out.println(';');
  }

  @Override
  public void visit(final StmtBlock n) {
    switch (n.stmts.size() + n.vars.size()) {
      case 0:
        out.println("{}");
        break;
      case 1:
        n.vars.accept(this);
        n.stmts.accept(this);
        break;
      default:
        openBlock();
        n.vars.accept(this);
        n.stmts.accept(this);
        closeBlock();
    }
  }

  @Override
  public void visit(final StmtIf n) {
    out.print("if (");
    n.test.accept(this);
    out.print(") ");
    n.ifTrue.accept(this);
    out.print("else ");
    n.ifFalse.accept(this);
  }

  @Override
  public void visit(final StmtPrint n) {
    out.print("System.out.println(");
    n.expr.accept(this);
    out.println(");");
  }

  @Override
  public void visit(final StmtWhile n) {
    out.print("while (");
    n.test.accept(this);
    out.print(')');
    space();
    n.body.accept(this);
  }
}
