package syntax.ast;

/** Programme Minijava.
 * <ul><li>{@link #klassMain}
 * <li>{@link #klassList} </ul>*/
public class Axiom extends AstNode {
  /** La classe conventionnelle main(). */
  public final KlassMain klassMain;
  /** La liste des autres classes. */
  public final AstList<Klass> klassList;

  /** Constructeur.
   * @param klassMain La classe conventionnelle main()
   * @param klassList La liste des autres classes */
  public Axiom(final KlassMain klassMain, final AstList<Klass> klassList) {
    super(klassMain, klassList);
    this.klassMain = klassMain;
    this.klassList = klassList;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
