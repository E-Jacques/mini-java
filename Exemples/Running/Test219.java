// Tableaux de booléens
class Test209 {
  public static void main(String[] a) {
    System.out.println(new Test().test()); // 42
  }
}

class Test {
  boolean[] x;
  boolean[] z;

  public int test() {
    int res;
    int foo;
    foo = this.set(13, true);
    foo = this.print(); // 1 0 1 0 1 0 1 0 1 0 1 0 1
    res = this.count();
    foo = this.set(13, false);
    foo = this.print(); // 0 1 0 1 0 1 0 1 0 1 0 1 0
    return res * this.count();
  }

  public int set(int n, boolean b) {
    z = new boolean[n];
    x = z;
    z[0] = b;
    int i;
    i = 1;
    while (i < x.length) {
      z[i] = !x[i - 1];
      i = i + 1;
    }
    return x.length;
  }

  public int count() {
    int res;
    res = 0;
    int i;
    i = 0;
    while (i < x.length) {
      if (x[i])
        res = res + 1;
      else {
      }
      i = i + 1;
    }
    return res;

  }

  public int print() {
    System.out.println(66666);
    int i;
    i = 0;
    while (i < x.length) {
      if (x[i])
        System.out.println(1);
      else
        System.out.println(0);
      i = i + 1;
    }
    System.out.println(66666);
    return 0;
  }
}
