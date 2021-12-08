// fonction de Ackermann
// exemple de récurrence gourmande
class TestAckermann {
  public static void main(String[] args) {
    System.out.println(new calcul().Ackermann(3, 2)); // =29
  }
}

class calcul {
  public int Ackermann(int m, int n) {
    int resu;
    if ((0 < m) && (0 < n))
      resu = this.Ackermann(m - 1, this.Ackermann(m, n - 1));
    else if (!(0 < n))
      resu = this.Ackermann(m - 1, 1);
    else
      resu = n + 1;
    return resu;
  }
}
