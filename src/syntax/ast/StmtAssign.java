package syntax.ast;

/** Affectation d'une variable.
 * <ul><li>{@link #varId}
 * <li>{@link #value} </ul> */
public class StmtAssign extends Stmt {
  /** Le nom de la variable. */
  public final Ident varId;
  /** La valeur. */
  public final Expr value;

  /** Constructeur.
   * @param varId Le nom de la variable
   * @param value La valeur */
  public StmtAssign(final Ident varId, final Expr value) {
    super(varId, value);
    this.varId = varId;
    this.value = value;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
