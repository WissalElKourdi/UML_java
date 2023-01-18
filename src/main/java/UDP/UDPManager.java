package UDP;

import Database.createDB;
import Interface.MenuController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPManager extends UDP_Client{

    static UDP_Server serv_udp;

    static {
        try {
            serv_udp = new UDP_Server();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public UDPManager(int port) throws SocketException, SQLException {
        super(port);
    }

    public static void update(String msg, createDB DB, DatagramPacket packet, DatagramSocket socket) throws SQLException, IOException {
        String name_db = "DB_MSG.db";

        int port = 2000;
        if (msg.startsWith("new pseudo :")) {
            String pseudo1 = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
            System.out.println("ADDR DB" + addr);
            DB.insertIpseudo(pseudo1.trim(), addr, name_db);


        } else if (msg.startsWith("change pseudo :")) {
            String pseudo2 = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
            String old = DB.getMonPseudo(name_db);
            DB.changeIpseudo(pseudo2.trim(), addr, name_db, old);
            //  DB.changeMonpseudo(pseudo2.trim());


        } else if (msg.startsWith("Connected :")) {
            String pseudo3 = msg.substring(msg.lastIndexOf(':') + 1);
            int min = 1000;
            int max = 10000;
            //port = 2000;
                    //(int) Math.floor(Math.random() * (max - min + 1) + min);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);

            if (addr != IP_addr.get_my_IP().toString(){
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
System.out.println("addrrrrrr" + InetAddress.getLocalHost().toString());
            if ( addr == InetAddress.getLocalHost().toString() ) {
                MenuController.connected.add(pseudo3);
            }
            DB.insertConnected(pseudo3.trim(), port, name_db);

        } else if (msg.startsWith("Deconnected :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            DB.deleteConnected(pseudo.trim(), name_db);
        } else if (msg.startsWith("UpdtateState :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
            System.out.println("ADDR DB" + addr);
            DB.insertIpseudo(pseudo.trim(), addr, name_db);
            DB.insertConnected(pseudo.trim(), port, name_db);


            // envoyer broadcast a tt le moned
            // tt le monde check ds sa db
            // tout le monde repond avec son pseudo
            DB.deleteConnected(pseudo.trim(), name_db);
        } else if (msg.startsWith("AskForState :")) {
            //broadcast udpdate state
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            serv_udp.broadcast_MyState(pseudo, port);
            // add parametre addresse pour envoyer a la personne qui nous a demande
        }
    }  }
