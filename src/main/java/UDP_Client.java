

/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class UDP_Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
       Socket link = new Socket ("localhost",4000);
       BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream())); //getting the input flows
       PrintWriter out = new PrintWriter(link.getOutputStream(),true);//getting the output flows
        out.println("data");
       String input = in.readLine();
       System.out.println(input);
       link.close();
    }
}*/