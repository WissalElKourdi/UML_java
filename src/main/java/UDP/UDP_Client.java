package UDP;

import Database.createDB;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDP_Client extends Thread {

    public void run (){
        DatagramSocket socket ;
        boolean running;
        running = true;
        String Name_DB = "DB_MSG.db";

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

                //DB :
                if (msg_rcv.substring(0,8).equals("change :")) {

                    String pseudo = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1);
                    System.out.println(pseudo.trim());
                    System.out.println(packet.getAddress().toString());
                    createDB DB = new createDB("DB_MSG.db");
                    DB.insertIpseudo(pseudo.trim(), packet.getAddress().toString(), "DB_MSG.db");
                    System.out.println("done");
                    DB.selectAllMsgIPseudo(Name_DB);
                }


                System.out.println("done1");
                if (msg_rcv.equals("end")){
                    running = false;
                    System.out.println("Socket closed");
                    continue;
                }
            }

            System.out.println("done2");
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
