
import java.io.*;
import java.net.*;

public class UDP_Server {
    private static DatagramSocket socket = null;
    public static void main(String[] args) throws IOException {
        send_udp("I am connected", InetAddress.getByName("255.255.255.255"));
        System.out.println("Sent");
    }
    public static void send_udp(String broadcastMSg, InetAddress Address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, 4000);
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