package syntax;

import main.CompilerException;
import main.Debug;
import semantic.*;
import syntax.parser;
import syntax.CupParse;
import syntax.ast.AstNode;
import syntax.ast.Axiom;

/** Analyse Syntaxique : encapsulation de la classe CupParse. */
public class Syntax {
  /** Sortie de L'analyse syntaxique. */
  private AstNode axiom;

  /**
   * Analyse syntaxique.
   * 
   * @param file Fichier en entrée, "stdin" pour entrée standard
   * @throws CompilerException AST invalide
   */
  public Syntax(final String file) {
    final Object ast = new CupParse(file, Debug.TOKEN,
        Debug.PARSE, Debug.PW).getAxiom();
    if (!(ast instanceof AstNode)) {
      throw new CompilerException("axiom is not an ASTNode");
    }
    this.axiom = (AstNode) ast;
    if (Debug.TREE) {
      Debug.log("= AST");
      Debug.log(axiom.toPrint());
    }
    if (!(axiom instanceof Axiom)) {
      throw new CompilerException("axiom is not an Axiom");
    }
    if (Debug.PRETTY) {
      Debug.log("= Pretty Print");
      Debug.log(new PrettyPrint(axiom));
    }
    if (Debug.TESTINDENT) {
      Debug.log("= Test indent");
      Debug.log(new TestIndent(new SemanticTree(axiom)).toString());
    }
    if (Debug.TESTSCOPE) {
      Debug.log("= Test scope");
      Debug.log(new TestScope(new SemanticTree(axiom)).toString());
    }
    if (Debug.TESTFUSION) {
      Debug.log("= Test fusion");
      Debug.log(new TestFusion(new SemanticTree(axiom)).toString());
    }
  }

  /**
   * Structure de données en sortie de l'analyse syntaxique.
   * 
   * @return L'axiome en sortie d'analyse. assert(Class==Axiom)
   */
  public AstNode getResult() {
    return axiom;
  }
}
