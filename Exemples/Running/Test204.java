// Appel de fonction et arguments (<4)
class Test204 {
  public static void main(String[] a) {
    System.out.println(2 * new FunctArg().permut(6, 3, 4));
  }
}

class FunctArg {
  int a;
  boolean c;

  public int permut(int N, int a, int b) {
    int res;
    if (N < 1)
      res = 0;
    else
      res = a + this.permut(N - 1, b, a);
    return res;
  }
}

