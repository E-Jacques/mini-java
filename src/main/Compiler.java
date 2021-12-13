package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Main du Compilateur Minijava. */
public class Compiler {

  /** Constructeur.
   * @param infile Le fichier Minijava en entrée */
  Compiler(final String infile) {
    try {
      Debug.log("=== Analyse Lexicale et Syntaxique ===");
      final syntax.ast.AstNode axiom = new syntax.Syntax(infile).getResult();

      Debug.log("=== Analyse Sémantique ===");
      final semantic.SemanticTree st = new semantic.Semantic(axiom).getResult();

      Debug.log("=== Génération de la Représentation Intermédiaire ===");
      final intermediate.IR ir = new intermediate.Intermediate(st).getResult();
      // Debug.toBeContinued();
      
      Debug.log("=== Génération de Code ===");
      final String outfile = new codegen.CodeGen(ir, infile).getResult();
      
      if (Debug.RUNMARS) { // may be not here
        Debug.log("== Exécution Mars de " + outfile + " ===");
        execCmd("java", "-jar", "lib/mars.jar", "nc", outfile);
      }
    } catch (CompilerException | IOException e) {
      Debug.logErr("Compilation aborted : " + e.getMessage());
    }
  }

  /** La Méthode main().
   * @param args Une liste de fichiers Minijava, si vide fichier=Debug.FILE */
  public static void main(final String[] args) {
    if (args.length == 0) new Compiler(Debug.FILE);
    for (String file : args) new Compiler(file);
  }

  /** Exécution d'une commande dans un processus externe.
   * @param cmd La ligne de commande
   * @throws IOException Exception d'entrée/sortie */
  private void execCmd(final String... cmd) throws IOException {
    BufferedReader std;
    String s;
    final Process p = Runtime.getRuntime().exec(cmd);
    std = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((s = std.readLine()) != null) Debug.log(s);
    std.close();
    std = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    while ((s = std.readLine()) != null) Debug.logErr(s);
    std.close();
  }
}
