package semantic;

import syntax.ast.*;

public class TestScope extends AstVisitorDefault {
    public String outputString;
    public String currentMethod;

    public TestScope(SemanticTree semTree) {
        this.outputString = "Test Scope : ";
        this.currentMethod = "";
        semTree.axiom.accept(this);
    }

    @Override
    public void visit(Klass n)
    {
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
    public void visit(Method n) {
        this.currentMethod = n.methodId.name;
        this.outputString += n.methodId.name + "{}";

        for (AstNode fa: n.fargs) {
            fa.accept(this);
        }

        for (AstNode v: n.vars) {
            v.accept(this);
        }

        this.currentMethod = "";
    }

    @Override
    public String toString() {
        return this.outputString;
    }
}
