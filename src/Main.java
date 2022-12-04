import game.Session;

public class Main {
    public static void main(String[] args) {
        var session = new Session();
        boolean run = true;
        while(run) {
            run = session.run();
        }
    }
}