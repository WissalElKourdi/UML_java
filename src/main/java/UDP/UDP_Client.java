package UDP;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDP_Client extends Thread {

    public void run (){
        DatagramSocket socket ;
        boolean running;
        running = true;

        try {
            socket = new DatagramSocket(UDP_Server.port);

            System.out.println("Creating Socket");
            byte[] buffer = new byte[256];

            while (running) {
                System.out.println("Ready to receive broadcast packets!");
                //Receive a packet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                //String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("received");

                //Packet received
                String msg_rcv = new String (packet.getData(), 0, packet.getLength());
                System.out.println(msg_rcv);
                if (msg_rcv.equals("end")){
                    running = false;
                    System.out.println("Socket closed");
                    continue;
                }
            }
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
