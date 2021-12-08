package syntax.ast;

/** Déclaration de la classe main().
 * <ul><li>{@link #klassId}
 * <li>{@link #argId}
 * <li>{@link #stmt} </ul> */
public class KlassMain extends AstNode {
  /** Le nom de classe. */
  public final Ident klassId;
  /** Le nom de l'argument (unused). */
  public final Ident argId;
  /** L'instruction unique de main(). */
  public final Stmt stmt;

  /** Constructeur.
   * @param klassId Le nom de classe
   * @param argId Le nom de l'argument (unused)
   * @param stmt L'instruction unique de main() */
  public KlassMain(final Ident klassId, final Ident argId, final Stmt stmt) {
    super(klassId, argId, stmt);
    this.klassId = klassId;
    this.argId = argId;
    this.stmt = stmt;
  }

  public void accept(final AstVisitor v) { v.visit(this); }
}
