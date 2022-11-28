

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.UnknownHostException;
import java.lang.*;



public class UDP_Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
/*
       Socket link = new Socket ("localhost",4000);
       BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream())); //getting the input flows
       PrintWriter out = new PrintWriter(link.getOutputStream(),true);//getting the output flows
        out.println("data");
       String input = in.readLine();
       System.out.println(input);
       link.close();
    }
}*/
        rcv_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"));
    }

    public static void rcv_udp(String broadcastMSg, InetAddress Address) throws IOException {
        socketr = new DatagramSocket(4000);
        socketr.setBroadcast(true);
        while (true) {
            System.out.println("Ready to receive broadcast packets!");
            //Receive a packet
            byte[] recvBuf = new byte[15000];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socketr.receive(packet);

            //Packet received
            System.out.println("Discovery packet received from: " + packet.getAddress().getHostAddress());
            System.out.println("Packet received; data: " + new String(packet.getData()));

            //See if the packet holds the right command (message)
            String message = new String(packet.getData()).trim();
            if (message.equals(broadcastMSg)) {
                byte[] sendData = broadcastMSg.getBytes();

                //Send a response
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socketr.send(sendPacket);

                System.out.println( "Sent packet to: " + sendPacket.getAddress().getHostAddress());
            }
        }
    }
}


>>>>>>> 1de6bd1967f9e72fce94d3b92e53d8458d43646d
