
import java.io.*;
import java.net.*;
import java.lang.Object;
import java.util.*;

public class UDP_Server {
    private static DatagramSocket socket = null;
    private InetAddress Address;
    public static int port = 4000;

    public static void send_udp(String broadcastMSg, InetAddress Address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        socket.send(packet);
        socket.close();
    }

    public static void broadcast(String broadcastMSg) throws IOException {
         send_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"));
    }

    public static void broadcast_connection (String pseudo) throws IOException{
        broadcast(pseudo);
        System.out.println("connected");
    }

    public static void broadcast_deconnection (String pseudo) throws IOException{
        broadcast(pseudo);
        System.out.println("deconnection");
    }

    public static void broadcast_ChangePseudo (String pseudo) throws IOException{
        broadcast(pseudo);
        System.out.println("Pseudo changed");
    }

}
