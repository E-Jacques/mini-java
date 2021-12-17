package semantic;

import main.CompilerException;
import main.Debug;
import syntax.ast.AstNode;

/** Analyse Sémantique. */
public class Semantic {
  /** Des erreurs sémantiques.
   * Continuation sur error jusqu’à la fin de l'analyse sémantique */
  private boolean error;

  /** La structure de données en sortie de l'analyse sémantique. */
  private final SemanticTree semanticTree;

  /** Constructeur.
   * @param axiom La racine de l'AST
   * @throws CompilerException Semantic aborted */
  public Semantic(final AstNode axiom) {
    this.semanticTree = new SemanticTree(axiom);
    this.error = false;
    analyse();
    if (error) throw new CompilerException("Semantic Error(s)");
  }

  /** Le résultat de l'analyse sémantique.
   * @return La structure de données en sortie de l'analyse sémantique. */
  public SemanticTree getResult() { return semanticTree; }

  /** Analyses sémantiques. */
  private void analyse() {
    //Quelques tests de visiteur
    //...

    // Construction de la table de symbole (passe 1)
    // Contrôle la duplication de définition ("already defined")
    final BuildSymTab bst = new BuildSymTab(semanticTree);
    error = bst.getError() || error;
    if (Debug.SYMTAB) {
      Debug.log("= Table des Symboles (passe1)");
      Debug.log(semanticTree.rootScope.toPrint());
    }

    // Construction de la hiérarchie des classes Java
    //  - Contrôle consistance de l'héritage (loop, "Object",..)
    //  - Intégration de l'héritage dans la table des symboles (passe 2)
    final CheckInheritance cch = new CheckInheritance(semanticTree);
    error = cch.getError() || error;
    if (Debug.SYMTAB) {
      Debug.log("= Table des Symboles (passe2)");
      Debug.log(semanticTree.rootScope.toPrint());
      Debug.log("= Liste des variables");
      Debug.log(semanticTree.rootScope.getAllVariables());
    }

    new UndefinedAndUnused(semanticTree);

    // Controle de Type et calcul de l'attribut nodeType
    final TypeChecking tc = new TypeChecking(semanticTree);
    error = tc.getError() || error;

    // Contrôle des identificateurs non définis
    // Contrôle des déclarations de variables unused dans la même phase
    // NB : le contrôle de type est requis avant
    //...
  }
}
