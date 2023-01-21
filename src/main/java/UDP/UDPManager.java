package UDP;

import Database.createDB;
import Interface.MenuController;
import Interface.SessionChatController;
import USERS.List_Connected;
import USERS.List_USers;
import USERS.Remote_Users;
import javafx.application.Platform;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPManager extends UDP_Client {

    static UDP_Server serv_udp;

    List_Connected co = new List_Connected();

    static {
        try {
            serv_udp = new UDP_Server();
        } catch (SocketException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String msg, createDB DB, DatagramPacket packet, DatagramSocket socket) throws SQLException, IOException {
        String name_db = "DB_MSG.db";
        List_Connected co = new List_Connected();

        int port = 2000;
        IP_addr monIP = new IP_addr();
        String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();
        String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
        String pseudo = msg.substring(msg.lastIndexOf(':') + 1);

        if (!addr.equals(mine)) {
            if ( !List_Connected.exists(pseudo) && (!msg.startsWith("MY INFOS :")) && (!msg.startsWith("MY INFOS CHANGE :"))){
                List_Connected.add_co(pseudo);
            }

        if (msg.startsWith("new pseudo :")) {
            DB.insertIpseudo(pseudo.trim(), addr, name_db);


        } else if (msg.startsWith("change pseudo :")) {
            String old = DB.getMonPseudo(name_db);
            DB.changeIpseudo(pseudo.trim(), addr, name_db, old);

        } else if (msg.startsWith("Connected :")) {
                DB.insertConnected(pseudo.trim(), port, name_db);

        } else if (msg.startsWith("Deconnected :")) {
            DB.deleteConnected(pseudo.trim(), name_db);

        } else if (msg.startsWith("UpdtateState :")) {
                 DB.insertConnected(pseudo.trim(), port, name_db);


        } else if (msg.startsWith("AskForState :")) {
            serv_udp.broadcast_MyState(port);
            // add parametre addresse pour envoyer a la personne qui nous a demande

        } else if (msg.startsWith("MY INFOS :")) {
            String pseudo1 = pseudo.substring(0, pseudo.indexOf("/"));
            String addr1 = msg.substring(msg.lastIndexOf('/') + 1);

            Remote_Users user = new Remote_Users(pseudo1, addr1);
            System.out.println("I am ADDING TO THE LIST USER ==>" + pseudo1 + "   " + addr1);
            List_USers.add_User(user);

        } else if (msg.startsWith( "MY old pseudo :")) {
                 List_Connected.delete_co(pseudo);
            Remote_Users old_user = List_USers.get_user_from_pseudo(pseudo);
            List_USers.remove_user(old_user);

        }else if (msg.startsWith("MY INFOS CHANGE :")) {
            String pseudo1 = pseudo.substring(0, pseudo.indexOf("/"));
            String addr1 = msg.substring(msg.lastIndexOf('/') + 1);
            String old = DB.getMonPseudo(name_db);
            Remote_Users old_user = List_USers.get_user_from_pseudo(old);
            List_USers.remove_user(old_user);
            Remote_Users user = new Remote_Users(pseudo1, addr1);
            List_USers.add_User(user);

            }
        }

 //commentaire pour commit
    }

    public String pseudo_udp(String msg) {
        String pseudo = msg.substring(msg.indexOf(":") + 1);
        pseudo = pseudo.substring(0, pseudo.indexOf("/"));
        return pseudo;
    }


}

