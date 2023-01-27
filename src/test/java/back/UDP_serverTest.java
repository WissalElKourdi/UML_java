



package back;

import UDP.UDP_Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;
//import org.mockito.Mockito;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.Assert.*;


public class UDP_serverTest {
    private byte[] buffer;
    private String broadcastMSg;
    private InetAddress Adress;
    private int port;
    private DatagramPacket packet;
    private UDP_Server udpServ;

    @Before
    public void setup() throws SocketException, SQLException {
        broadcastMSg="Hello world";
        buffer = broadcastMSg.getBytes();
        packet = new DatagramPacket(buffer, buffer.length, Adress, port);
        udpServ = new UDP_Server();
        port=1234;
    }


    @Test
    public void testBroadcast(){
        // Call the broadcast method
        try {
            udpServ.broadcast(broadcastMSg, port);
        } catch (IOException e) {
            fail("An IOException should not be thrown");
        }
    }
    @Test
    public void testSend_udp() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "msg";
        InetAddress address = InetAddress.getByName("255.255.255.255");
        int port = 3000;
        server.send_udp(message, address, port);
    }

    @Test
    public void testBroadcast2() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Hello World";
        int port = 3000;
        server.broadcast(message, port);
    }

    @Test
    public void testBroadcast_Pseudo() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        boolean result = server.broadcast_Pseudo(message, port);
        assertTrue(result);
    }

    @Test
    public void testBroadcast_ChangePseudo() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        boolean result = server.broadcast_ChangePseudo(message, port);
        assertTrue(result);
    }

    @Test
    public void testBroadcast_connection() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        server.broadcast_connection(message, port);
    }

    @Test
    public void testBroadcast_deconnection() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        server.broadcast_deconnection(message, port);
    }

    @Test
    public void testBroadcast_end() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        int port = 3000;
        server.broadcast_end(port);
    }

    @Test
    public void testBroadcast_MyState() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        int port = 3000;
        server.broadcast_MyState(port);
    }

    @Test
    public void testBroadcast_AskState() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        server.broadcast_AskState(message, port);
    }

    @Test
    public void testBroadcast_info() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        String adresse = "255.255.255.255";
        int port = 3000;
        server.broadcast_info(message, adresse, port);
    }

    @Test
    public void testBroadcast_change_info() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        String adresse = "255.255.255.255";
        int port = 3000;
        server.broadcast_change_info(message, adresse, port);
    }

    @Test
    public void testBroadcast_je_vais_change_mon_pseudo() throws IOException, SQLException {
        UDP_Server server = new UDP_Server();
        String message = "Wissal";
        int port = 3000;
        server.broadcast_je_vais_change_mon_pseudo(message, port);
    }


}




