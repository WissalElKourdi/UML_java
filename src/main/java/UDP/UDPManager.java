package UDP;

import Database.createDB;
import Interface.List_Connected;
import Interface.MenuController;
import Interface.Remote_Users;
import Interface.MenuController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPManager extends UDP_Client{

    static UDP_Server serv_udp;

    static {
        try {
            serv_udp = new UDP_Server();
        } catch (SocketException | SQLException e) {
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
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1).trim();
            IP_addr monIP = new IP_addr();
            String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();

            if (!addr.equals(mine)){
            DB.insertConnected(pseudo3.trim(), port, name_db);
            }


        } else if (msg.startsWith("Deconnected :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            DB.deleteConnected(pseudo.trim(), name_db);
        } else if (msg.startsWith("UpdtateState :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1).trim();
            IP_addr monIP = new IP_addr();
            String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();
            if (!addr.equals(mine)){
                System.out.println("ADDR" + addr);
            List_Connected.add_co(pseudo);
             //  menu.update_list();


            }

        } else if (msg.startsWith("AskForState :")) {
            //broadcast udpdate state
           // String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            serv_udp.broadcast_MyState( port);
            // add parametre addresse pour envoyer a la personne qui nous a demande
        }else if (msg.startsWith("MY INFOS :")){
          String pseudo =  msg.substring(msg.indexOf(":") + 1);
            pseudo = pseudo.substring(0, pseudo.indexOf("/"));
            String addr = msg.substring(msg.lastIndexOf('/') + 1);
            Remote_Users user = new Remote_Users(pseudo,addr);

        }

    }  }
