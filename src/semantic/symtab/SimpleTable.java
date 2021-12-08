package semantic.symtab;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**  Table de symbole élémentaire en utilisant HashMap.
 * @param <R> le type d'information */
public class SimpleTable<R extends Info> implements Table<String, R> {
  /** La Table de symbole. (=HashMap)*/
  private Map<String, R> symbols;

  /** Constructeur par défaut.*/
  public SimpleTable() {
    this.symbols = new HashMap<>();
  }

  @Override
  public R lookup(final String name) {
    return symbols.get(name);
  }

  @Override
  public R insert(final String name, final R info) {
    return symbols.put(name, info);
  }

  @Override
  public Collection<R> getInfos() {
    return symbols.values();
  }

  @Override
  public String toString() {
    return "SimpleTable [" + symbols + "]";
  }
}
