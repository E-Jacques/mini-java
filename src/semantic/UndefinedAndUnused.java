package semantic;

import java.util.ArrayList;
import java.util.List;

import main.CompilerException;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;
import syntax.ast.*;

public class UndefinedAndUnused extends AstVisitorDefault {
    private SemanticTree semTree;

    private List<String> klassVisitedVars;
    private List<String> methodVisitedVars;
    private List<String> currentKlassVars;

    public UndefinedAndUnused(final SemanticTree semTree) {
        this.semTree = semTree;
        this.semTree.axiom.accept(this);

        this.klassVisitedVars = new ArrayList<String>();
        this.methodVisitedVars = new ArrayList<String>();
        this.currentKlassVars = new ArrayList<String>();
    }

    @Override
    public void visit(final Klass n) {
        this.klassVisitedVars = new ArrayList<String>();
        this.currentKlassVars = new ArrayList<String>();

        for (AstNode v : n.vars) {
            this.currentKlassVars.add(((Var) v).varId.name);
        }
        n.vars.accept(this);
        n.methods.accept(this);

        for (AstNode v : n.vars) {
            String varName = ((Var) v).varId.name;
            if (!klassVisitedVars.contains(varName)) {
                System.out.println("[WARNING] " + varName + " is never used in " + n.klassId.name);
            }
        }
    }

    @Override
    public void visit(final Method n) {
        this.methodVisitedVars = new ArrayList<String>();

        n.vars.accept(this);
        n.fargs.accept(this);
        n.stmts.accept(this);
        n.returnExp.accept(this);


        for (AstNode v : n.vars) {
            String varName = ((Var) v).varId.name;
            if (!methodVisitedVars.contains(varName)) {
                System.out.println("[WARNING] " + varName + " is never used in " + n.methodId.name);
            }

        }

        for (AstNode v : n.fargs) {
            String varName = ((Formal) v).varId.name;
            if (!methodVisitedVars.contains(varName)) {
                System.out.println("[WARNING] " + varName + " is never used in " + n.methodId.name);
            }

        }
    }

    @Override
    public void visit(final ExprIdent n) {
        Scope sc = this.semTree.scopeAttr.get(n);

        if (sc.lookupVariable(n.varId.name) == null) {
            throw new CompilerException(n.varId.name + " is not defined");
        }

        if (this.currentKlassVars.contains(n.varId.name) && !this.klassVisitedVars.contains(n.varId.name)) {
            this.klassVisitedVars.add(n.varId.name);
        } else {
            this.methodVisitedVars.add(n.varId.name);
        }
    }
}
