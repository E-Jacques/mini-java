// Opérateurs
class Test201 {
  public static void main(String[] a) {
    System.out.println(new Operator().compute()); // => 42
  }
}

class Operator {
  boolean b;
  int i;

  public int compute() {
    b = true && false;
    b = 6 < 7;
    b = !false;
    b = !!true;
    i = 6 + 7;
    i = 6 - 7;
    i = 6 * 7;
    return i;
  }
}
