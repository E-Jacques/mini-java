package syntax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Scanner;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

/** Analyse Syntaxique.
 * <br>Encapsulation et adaptations des classes "Yylex" et "parser"
 * générées par Jflex et CUP
 * <br>Méthode {@link #main} pour analyse syntaxique autonome */
public class CupParse {
  /** Filename for standard input. */
  private static final String STDIN = "stdin";

  /** Sortie de CUP. */
  private Object axiom;

  /** Constructeur.
   * @param file Le fichier Minijava en entrée;
   *     "stdin" ou "-" pour l'entrée standard
   * @param debugToken true pour dump des tokens lues par le parseur
   * @param debugParse true pour trace détaillée d’exécution de l'automate LALR
   * @param pw java.io.PrinterWriter Un "Writer" pour les impressions/debug */
  public CupParse(final String file, final boolean debugToken,
      final boolean debugParse, final PrintWriter pw) {
    this.axiom = null;
    // redirect file as stdin
    if (STDIN.equals(file) || "-".equals(file)) {
      pw.println("Reading standard input : ");
    } else {
      try {
        System.setIn(new FileInputStream(file));
        pw.println("Reading file " + file);
      } catch (FileNotFoundException e) {
        pw.println("File " + file + " not found");
        return;
      }
    }
    // create lexer and parser
    final ComplexSymbolFactory csf = new ComplexSymbolFactory();
    final Reader reader = new InputStreamReader(System.in);
    final Scanner lexer = new Yylex(reader, csf);
    final ScannerBuffer scanBuf = new ScannerBuffer(lexer);
    final lr_parser parser = new parser(scanBuf, csf);
    // run parser and check return
    try {
      final Symbol result = debugParse ? parser.debug_parse() : parser.parse();
      if (result == null || result.sym != 0) {
        pw.println("Parsing ends with symbol: " + result);
      } else {
        this.axiom = result.value;
        pw.println("Parsing OK, Axiome = " + axiom);
      }
    }// CHECKSTYLE:OFF
    catch (RuntimeException e) {
      throw e;
    }
    catch (Exception e) {
      pw.println(e.getMessage());
    } // CHECKSTYLE:ON
    // catch pas propre: capture Exception de lr_parser.report_fatal_error().
    // TBD : surcharger report_falal_error pour avoir un CompilException ?
    finally {
      if (debugToken) {
        pw.println("= Token Debug");
        pw.println(scanBuf.getBuffered().toString());
      }
    }
  }

  /** Le Résultat de CUP.
   * @return L'axiome de la grammaire. (type défini dans la spécification CUP */
  public Object getAxiom() { return axiom; }

  /** Main() pour analyse syntaxique autonome.
   * @param args [-help] [-debug] [-trace] [ files ]
   */
  public static void main(final String[] args) {
    final PrintWriter pw = new PrintWriter(System.err, true);
    // parse args
    final String usage =
        " CupParse [-help] [-debug] [-trace] [ files ]\n"
            + "     -degug      dump tokens,\n"
            + "     -trace      trace LR Automaton\n"
            + "     files       stdin by default";
    boolean debug = false;
    boolean trace = false;
    final java.util.List<String> files = new java.util.ArrayList<>();
    for (String opt : args) {
      switch (opt) {
        case "-help": case "-h": pw.println(usage); System.exit(0); break;
        case "-debug": debug = true; break;
        case "-trace": trace = true; break;
        default: files.add(opt);
      }
    }
    if (files.isEmpty()) files.add(STDIN);
    // run
    for (String file : files) new CupParse(file, debug, trace, pw);
  }
}
