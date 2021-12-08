// Test sémantique : Tableaux et contrôle de types
class Test409 {
  public static void main(String[] a) {
    System.out.println(new Operator().compute());
  }
}

class Operator {
  boolean b;
  int i;
  Operator op;
  int[] tab;

  public int compute() {
    int i2;
    int[] tab2;

    tab = new int[true]; // FAIL : type
    tab = new int[op.get()]; // FAIL : type
    tab = new int[op.compute()];
    tab = new int[10];
    tab = tab2;
    tab2 = new int[i];
    i = i2[10]; // FAIL : type
    i2[10] = 5; // FAIL : type
    op[10] = 5; // FAIL : type
    i = tab[true]; // FAIL : type
    tab = 5; // FAIL : type
    tab = i2; // FAIL : type
    tab[true] = 20; // FAIL : type
    tab[op] = 20; // FAIL : type
    tab[op.get()] = 20; // FAIL : Type
    tab[op.compute()] = 20; // OK !
    tab[i] = true; // FAIL : Type
    tab[i] = op; // FAIL : Type
    tab[1] = op.get(); // FAIL : Type
    tab[1] = op.compute();// OK
    b = !op; // FAIL : type
    b = !op.compute(); // FAIL : type
    i = i2.length; // FAIL : .length for non Array
    return 0;
  }

  public Operator get() {
    return op;
  }
}

