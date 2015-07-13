package clientserver;

public class Main {

    public static void main(String[] args) {
        new DateServer().start();
        //new Tester().start();
        new Transfer().start();
    }
    
}
