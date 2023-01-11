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
        createDB DB = new createDB("DB_MSG.db");
        try {
            socket = new DatagramSocket(UDP_Server.port);

            System.out.println("Creating Socket");
            byte[] buffer = new byte[30];

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
                msg_rcv = msg_rcv.trim();
                System.out.println("msg received : "+msg_rcv+ "!   ");

                //DB :
                if (msg_rcv.startsWith("new pseudo :")) {
                    String pseudo1 = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1);
                    DB.insertIpseudo(pseudo1.trim(), packet.getAddress().toString(), "DB_MSG.db");

                }
                if (msg_rcv.startsWith("change pseudo :")) {
                    String pseudo2 = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1);
                    DB.changeIpseudo(pseudo2.trim(), packet.getAddress().toString(), "DB_MSG.db");

                }
                if (msg_rcv.startsWith("Connected :")) {
                    System.out.println("---------------------------HERE");
                    String pseudo3 = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1);
                    DB.insertConnected(pseudo3.trim(), "DB_MSG.db");

                }
                if (msg_rcv.startsWith("Deconnected :")) {
                    String pseudo = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1);
                    DB.deleteConnected(pseudo.trim(), "DB_MSG.db");

                }
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
