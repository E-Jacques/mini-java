/* erreur syntaxiques */
class Test302 {
  public static void main(String[] args) {
    {
      System.out.println(new Fac().ComputeFac());
      //System.out.println.(new Fac().ComputeFac()); //Statement Error
      foo=foo+1;
    }
    // while (x<y) {} // Only 1 statement in main
  }
}

class Fac {
  // public int ErrorTime (int){ } //Formal Parameter Error
  public int ComputeFac(int num) {
    int y;
    // int ; //Variable Declaration Error
    if (num < 1)
      num_aux = 1;
    else {
      // num_aux = num * (this.ComputeFac num-1)) ; //Actual Parameter Error
    }
    //    System.out.println(3, 2); // Fail arg Num
    return num_aux;
  }
}

// Foo{ } //Class Body Error


class Error {
  // public Foo (int num){ return 1; } //Method Body Error
}

// } // Error
