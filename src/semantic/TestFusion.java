package semantic;

import syntax.ast.*;

public class TestFusion extends AstVisitorDefault {
    public String outputString;
    public String currentMethod;

    public TestFusion(SemanticTree semTree) {
        this.outputString = "Test Fusion : ";
        this.currentMethod = "";
        semTree.axiom.accept(this);
    }

    @Override
    public void visit(Klass n) {
        this.outputString += n.klassId.name + "{";
        for (AstNode v : n.vars) {
            v.accept(this);
        }
        for (AstNode m : n.methods) {
            m.accept(this);
        }
        this.outputString += "}";
    }

    @Override
    public void visit(Var n) {
        this.outputString += ((Var) n).varId.name;

        if (this.currentMethod == "") {
            this.outputString += "(field),";
            return;
        }

        this.outputString += "(local),";
    }

    @Override
    public void visit(Method n) {
        this.currentMethod = n.methodId.name;
        this.outputString += n.methodId.name + "{";

        for (AstNode fa : n.fargs) {
            fa.accept(this);
        }

        for (AstNode v : n.vars) {
            v.accept(this);
        }

        this.outputString += "}";
        this.currentMethod = "";
    }

    @Override
    public void visit(Formal n) {
        this.outputString += ((Formal) n).varId.name + "(formal),";
    }

    @Override
    public String toString() {
        return this.outputString;
    }
}
