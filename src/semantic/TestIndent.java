package semantic;
import syntax.ast.*;

public class TestIndent extends AstVisitorDefault {

    public String outputString;
    public String currentMethod;

    public TestIndent(SemanticTree semTree) {
        this.outputString = "Test des identificateurs : ";
        this.currentMethod = "";
        semTree.axiom.accept(this);
    }

    @Override
    public void visit(Klass n)
    {
        this.outputString += n.klassId.name + " (Klass),";
        for (AstNode v : n.vars) {
            v.accept(this);
        }
        for (AstNode m : n.methods) {
            m.accept(this);
        }


    }

    @Override
    public void visit(Var n)
    {
        this.outputString += ((Var) n).varId.name;

        if (this.currentMethod == "")
        {
            this.outputString += " (field),";
            return;
        }

        this.outputString += " (local),";
    }

    @Override
    public void visit(Method n) {
        this.currentMethod = n.methodId.name;
        this.outputString += n.methodId.name + " (method),";

        for (AstNode fa: n.fargs) {
            fa.accept(this);
        }

        for (AstNode v: n.vars) {
            v.accept(this);
        }

        this.currentMethod = "";
    }

    @Override
    public void visit(Formal n)
    {
        this.outputString += ((Formal) n).varId.name + " (formal),";
    }

    @Override
    public String toString() {
        return this.outputString;
    }
}
