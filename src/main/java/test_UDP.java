import java.io.IOException;

public class test_UDP {
    public static void main (String[] args) throws IOException, InterruptedException {
        new UDP_Client().start();
        new UDP_Server().broadcast("YOO");
        Thread.sleep(60);
        new UDP_Server().broadcast("end");
    }
}