package UDP;

import Database.createDB;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPManager extends UDP_Client{

    public UDPManager(int port) throws SocketException, SQLException {
        super(port);
    }

    public static void update(String msg, createDB DB, DatagramPacket packet, DatagramSocket socket){
        String name_db = "DB_MSG.db";
        System.out.println("je suis  dans update");
        if (msg.startsWith("new pseudo :")) {
            String pseudo1 = msg.substring(msg.lastIndexOf(':') + 1);
            DB.insertIpseudo(pseudo1.trim(), packet.getAddress().toString(), name_db);
            // pseudo ok commencer chat session

        }else  if (msg.startsWith("change pseudo :")) {
            String pseudo2 = msg.substring(msg.lastIndexOf(':') + 1);
            DB.changeIpseudo(pseudo2.trim(), packet.getAddress().toString(), name_db);

        } else  if (msg.startsWith("Connected :")) {
            String pseudo3 = msg.substring(msg.lastIndexOf(':') + 1);
            DB.insertConnected(pseudo3.trim(), name_db);

        } else  if (msg.startsWith("Deconnected :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            DB.deleteConnected(pseudo.trim(), name_db);

        }
        System.out.println("je suis sortie et j'ai fini update");
    }
}
