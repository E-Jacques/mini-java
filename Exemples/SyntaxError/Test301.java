/** Erreurs Lexicales (/ syntax) */
class Test301 {
  // /* So should this /* and this still */ but not this */ // invalid C89 Comment
  public static void main(String[] args) {
    System.out.println(new Bar().Compute());
  }
}

class Bar {
  // int 0a; // invalid identifiier
  boolean b;

  public int Compute() {
    // a=01; // invalid Decimal Literral (should be Octal!)
    // b=True; // not a Literral
    // a = 3 / 4; // unknown caractere "/"
    // String s; // Stinrg only in main
    // String[] args; // only in main, (and not usable)
    // int this; // this reserved keyword
    return 0;
  }
}
