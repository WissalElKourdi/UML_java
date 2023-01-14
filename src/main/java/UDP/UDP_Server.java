package UDP;

import Database.createDB;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class UDP_Server {
    private static final String Name_DB = "DB_MSG.db";
    public static void send_udp(String broadcastMSg, InetAddress Address, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
       // socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        socket.send(packet);
        socket.close();
    }

    //-----------------------------BROADCAST--------------------------------------------
    public static void broadcast(String broadcastMSg, int port) throws IOException {
         UDP_Server.send_udp(broadcastMSg, InetAddress.getLocalHost(),port);
    }
    public static boolean broadcast_Pseudo (String pseudo, int port ) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);
        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
            return false;
        } else {
            broadcast("new pseudo :" + pseudo, port);
            System.out.println("Pseudo chosen:"+ pseudo);
            return true;
        }

    }

    public static boolean broadcast_ChangePseudo (String newpseudo, int port) throws IOException, SQLException {

        createDB DB = new createDB(Name_DB);

        if ( DB.check(newpseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
            return false;
        } else {
            broadcast("change pseudo :" + newpseudo, port);
            System.out.println("Pseudo changed:" + newpseudo);
            return true;
        }
    }


    //broadcast la connection auprès des autres utilisateurs
    public static void broadcast_connection (String pseudo, int port) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Failed Choose new pseudo : this one is already taken");

        } else {
            broadcast("Connected :" + pseudo, port);
            System.out.println("connected :" + pseudo);
        }

    }

    //se déconnecter et broadcast auprès des autres utilisateurs
    public static void broadcast_deconnection (String pseudo, int port) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Failed ");

        } else {
            broadcast("Deconnected :" + pseudo, port);
            System.out.println("Deconnected :" + pseudo);
        }

    }
    public static void broadcast_end(int port) throws IOException {

        broadcast("end", port);}


  /*  //static ou pas ??
    public void broadcast_ChangePseudo (String pseudo,ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            broadcast(pseudo);
            InsertData DB = new InsertData();
            DB.insert_pseudo(pseudo, InetAddress.getLocalHost());
*/


}

