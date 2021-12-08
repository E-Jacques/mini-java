// Test sémantique : Undefined identifier (var, méthode, classe)
class Test405 {
  public static void main(String[] a) {
    System.out.println(new Operator().compute());
  }
}

class Op2 {
  public int start() {
    return 42;
  }
}

class Foo {  Bar bar;  public Bar getBar() { return bar; } }
class Bar {  Foo foo;  public Foo getFoo() { return foo; } }

class Operator {
  boolean b; // Unused
  int i;
  Operator op;
  boolean i; // Duplication de champs
  int k;
  int l;
  FooBar m; // Fail Type/Class Undef
  
  public int k(){ return k;} // Not a duplication (field/method)
  
  public boolean l(int l){
    int l; // may be duplication with formal
    return l < l;
  } 

  public int over(int x){ return 0; }
  public int over(int x, int y){ return 0; } // Override or Duplication ?!?

  public Operator get() {
    Operator oper;
    return op;
  }
  
  public Operator get() {return op;}      // Fail Duplication
  public int get() { return 0;} // Fail Duplication


  public int compute() {
    op = oper; // FAIL : "oper" undef (+ type)
    i = i.compute(); // FAIL : undef Class (+...)
    i = op.compute(); // OK
    i = op.start(); // FAIL : undef method
    op = new i(); // FAIL : class undef (+ type)
    op = new op(); // FAIL : class undef (+ type)
    i1 = 10; // FAIL : var undef
    return new Op2().start();
  }

  // ...
  boolean b; // Redefinition
  boolean i; // Redefinition2

  public int next(boolean i) {
    int i; // Redefinition3
    return 0;
  }
}


