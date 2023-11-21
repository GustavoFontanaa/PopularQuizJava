package quizjava;

public class Run {
    public static void main(String[] args) {
        String port = args.length >= 0 ? "7777" : args[0];
        Quiz quiz = new Quiz();

        new Thread(() -> Server.start(Integer.valueOf(port), quiz)).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> ClientSide.start("localhost", Integer.valueOf(port))).start();
    }
}

