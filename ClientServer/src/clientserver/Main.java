package clientserver;

public class Main {

    public static void main(String[] args) {
        new DateServer().start();
        new Transfer().start();
    }
    
}
