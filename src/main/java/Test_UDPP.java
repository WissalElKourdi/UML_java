

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.UnknownHostException;
import java.lang.*;
import java.lang.Thread;

//new connection.verifyId() (server)
//new ecoute.connect() client

public class Test_UDPP {
    public static void main(String[] args){
        UDP_Client client = new UDP_Client client;
        client.start();
        UDP_Server server = new UDP_Server;
        server.broadcast_ChangePseudo("ee");
        System.out.println("test");

    }

}

/*
public class Test_UDPP extends UDP_Client {
    UDP_Server client;
    public void setup(){
        new UDP_Client.start();
        client = new UDP_Client();
    }

    public void whenCanSendAndReceivePacket_thenCorrect() {
        String echo = client.broadcast_ChangePseudo("leo");;
        assertEquals("hello server", echo);
        echo = client.broadcast_ChangePseudo("server is working");
        assertFalse(echo.equals("hello server"));
    }

    public void tearDown() {
        client.broadcast_deconnection("end");
        client.close();
    }
}
*/

