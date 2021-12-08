package main;

/** Runtime Exception de Compilation. */
public class CompilerException extends RuntimeException {
  /** Serialisation !!!!. */
  private static final long serialVersionUID = 42L;

  /** Exception pour le compilateur.
   * @param message Le message d'erreur */
  public CompilerException(final String message) {
    super(message);
  }
}
