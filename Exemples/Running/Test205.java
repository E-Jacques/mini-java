// Appel de fonction et arguments (>=4)
class Test204 {
  public static void main(String[] a) {
    System.out.println(2 * new FunctArg().permut(6, 1, 2, 3, 4, 5, 6));
  }
}

class FunctArg {
  int a;
  boolean c;

  public int permut(int N, int a, int b, int c, int d, int e, int f) {
    int res;
    if (N < 1)
      res = 0;
    else
      res = e + this.permut(N - 1, f, a, b, c, d, e);
    return res;
  }
}

