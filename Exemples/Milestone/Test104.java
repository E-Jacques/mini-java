// milestone 4 classes et méthodes (complet)
class Test104 {
  public static void main(String[] args) {
    System.out.println(new Test3().Start(6, 7));
  }
}

class Test2 {
  int a;

  public int Start(int i, int j) {
    int k;
    return i * j;
  }

  public int un() { return 1; }

  int b;
}


class Test3 extends Test2 {
  public int zero() {
    return 0;
  }
}
