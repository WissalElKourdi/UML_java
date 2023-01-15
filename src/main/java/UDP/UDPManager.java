package UDP;

import Database.createDB;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPManager extends UDP_Client{

    public UDPManager(int port) throws SocketException, SQLException {
        super(port);
    }

    public static void update(String msg, createDB DB, DatagramPacket packet, DatagramSocket socket) throws SQLException {
        String name_db = "DB_MSG.db";

        if (msg.startsWith("new pseudo :")) {
            String pseudo1 = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/")+1 );
            DB.insertIpseudo(pseudo1.trim(), addr, name_db);


        }else  if (msg.startsWith("change pseudo :")) {
            String pseudo2 = msg.substring(msg.lastIndexOf(':') + 1);
            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/")+1 );
            String old = DB.getPseudo(addr,name_db);
            DB.changeIpseudo(pseudo2.trim(), addr, name_db,old );


        } else  if (msg.startsWith("Connected :")) {
            String pseudo3 = msg.substring(msg.lastIndexOf(':') + 1);
            int min = 1000;
            int max = 10000;
            int port = (int)Math.floor(Math.random() * (max - min + 1) + min);
            DB.insertConnected(pseudo3.trim(),port, name_db);

        } else  if (msg.startsWith("Deconnected :")) {
            String pseudo = msg.substring(msg.lastIndexOf(':') + 1);
            DB.deleteConnected(pseudo.trim(), name_db);
        }
    }
}
