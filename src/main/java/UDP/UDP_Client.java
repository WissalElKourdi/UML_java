package UDP;

import Database.createDB;

import java.io.IOException;
import java.net.*;
import java.lang.*;

public class UDP_Client extends Thread {


public void handler1(String msg, createDB DB, DatagramPacket packet) {
    System.out.println("11111111111111&");
    if (msg.startsWith("new pseudo :")) {
        String pseudo1 = msg.substring(msg.lastIndexOf(':') + 1);
        DB.insertIpseudo(pseudo1.trim(), packet.getAddress().toString(), "DB_MSG.db");
        System.out.println("je suis sortie et j'ai fini");
    }
}
    public void handler2(String msg, createDB DB, DatagramPacket packet){
        System.out.println("222222222222222é");
        if (msg.startsWith("change pseudo :")) {
            String pseudo2 = msg.substring(msg.lastIndexOf(':') + 1);
            DB.changeIpseudo(pseudo2.trim(), packet.getAddress().toString(), "DB_MSG.db");

        }
    }
        public void handler3(String msg, createDB DB, DatagramPacket packet){
            System.out.println("33333333333333");
            if (msg.startsWith("Connected :")) {
                System.out.println("---------------------------HERE");
                String pseudo3 = msg.substring(msg.lastIndexOf(':') + 1);
                DB.insertConnected(pseudo3.trim(), "DB_MSG.db");

            }
        }
            public void handler4(String msg, createDB DB, DatagramPacket packet) {
    //deconnection
                System.out.println("44444444444");
                if (msg.startsWith("Deconnected :")) {
                    String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
                    DB.deleteConnected(pseudo.trim(), "DB_MSG.db");

                }
            }


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
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                String msg_rcv = new String (packet.getData(), 0, packet.getLength());
             /*

                int port = packet.getPort();
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                //String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("received");
              //  Thread sendMessage = new Thread(new Runnable());
                        //Packet received
              */
                msg_rcv = msg_rcv.trim();
                System.out.println("msg received : "+msg_rcv+ "!   ");
               UDPManager.update(msg_rcv,DB,packet);
               msg_rcv = "";
               // while (!msg_rcv.equals("end")){
               //     System.out.println("I am waiting");
               // wait(10);
               // }

                /*
                handler1(msg_rcv,DB,packet);
                System.out.println("je suis sortie");
                Thread.sleep(90000);
                handler2(msg_rcv,DB,packet);
                Thread.sleep(90000);
                handler3(msg_rcv,DB,packet);
                Thread.sleep(90000);
                handler4(msg_rcv,DB,packet);
                Thread.sleep(90000);
*/

                if (msg_rcv.equals("end")){
                    running = false;
                    System.out.println("Socket closed");
                }
            }
Thread.yield();

           // socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
