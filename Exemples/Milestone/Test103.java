// milestone 3 classes et méthodes (simple)
class Test103 {
    public static void main(String[] args) {
        System.out.println(new Test2().Start());
    }
}

class Test2 {
    public int Start() {
        return 7 * 6;
    }
}

class Test3 extends Test2 {
    public int zero() {
        return 0;
    }
}
