package syntax.ast;

/**
 * Déclaration de classe.
 * <ul>
 * <li>{@link #klassId}
 * <li>{@link #parentId}
 * <li>{@link #vars}
 * <li>{@link #methods}
 * </ul>
 */
public class Klass extends AstNode {
  /** Le nom de classe. */
  public final Ident klassId;
  /** La classe mère, défaut="Object". */
  public final Ident parentId;
  /** La liste des champs. */
  public final AstList<Var> vars;
  /** La liste des méthodes. */
  public final AstList<Method> methods;

  /**
   * Constructeur.
   * 
   * @param klassId  Le nom de classe
   * @param parentId La classe mère
   * @param vars     La liste des champs
   * @param methods  La liste des méthodes
   */
  public Klass(final Ident klassId, final Ident parentId,
      final AstList<Var> vars, final AstList<Method> methods) {
    super(klassId, parentId, vars, methods);
    this.klassId = klassId;
    if (parentId != null)
      this.parentId = parentId;
    else
      this.parentId = new Ident("Object");
    this.vars = vars;
    this.methods = methods;
  }

  public void accept(final AstVisitor v) {
    v.visit(this);
  }
}
