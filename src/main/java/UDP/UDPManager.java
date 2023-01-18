package UDP;

import Database.createDB;
import Interface.List_Connected;
import Interface.MenuController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

//import static Interface.MenuController.coo;

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
        List_Connected co = new List_Connected();

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
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1).trim();
            IP_addr monIP = new IP_addr();

            String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();
            System.out.println("je suis ici addr = " + addr + "mon ip = " + mine);
            System.out.println("§"+addr+"§");
            System.out.println("§"+mine+"§");
            System.out.println(  addr.equals(mine));
            if (!addr.equals(mine)){
                System.out.println("je suis ici addr = " + addr + "mon ip = " + monIP.get_my_IP().toString() );
                co.add_co(pseudo3);
                MenuController.update_list();
                //coo.add(pseudo3);
            DB.insertConnected(pseudo3.trim(), port, name_db);
            }
           // System.out.println("I am adding to the list co" + pseudo3);
         //   co.add_co(pseudo3);
         //   co.print_co(co.get_List());
            //  DB.insertConnected(pseudo3.trim(), port, name_db);

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
