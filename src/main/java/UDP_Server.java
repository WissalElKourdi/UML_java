
/*import java.io.*;
import java.net.*;
import java.lang.Object;
import java.util.*;

public class UDP_Server {
    private static DatagramSocket socket = null;
    public static void main(String[] args) throws IOException {
        //send_udp("I am connected", InetAddress.getByName("255.255.255.255"));
        broadcast_ChangePseudo("leo");
        System.out.println("Sent");
    }
    public static void send_udp(String broadcastMSg, InetAddress Address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, 4000);
        socket.send(packet);
        //stop
        System.out.println("Request packet sent to: 255.255.255.255 ");

        List<InetAddress> listAllBroadcastAddresses() throws SocketException {List<InetAddress> broadcastList = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }
                networkInterface.getInterfaceAddresses().stream()
                        .map(a -> a.getBroadcast())
                        .filter(Objects::nonNull)
                        .forEach(broadcastList::add);

          /* for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcastint = interfaceAddress.getBroadcast();
                if (broadcastint == null) {
                    continue;
                }
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, broadcastint, 8888);
                socket.send(sendPacket);
                System.out.println(" Request packet sent to: " + broadcastint.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
            }*/
            }
            System.out.println(" Done looping over all network interfaces. Now waiting for a reply!");

            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(receivePacket);
            System.out.println("Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            String message = new String(receivePacket.getData()).trim();
            if (message.equals(broadcastMSg)) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                // Controller_Base.setServerIp(receivePacket.getAddress());
                return broadcastList;
            }
        }
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


}*/