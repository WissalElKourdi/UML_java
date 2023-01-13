package UDP;

import Database.createDB;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class UDP_Server {
    private InetAddress Address;
    public static int port = 4000;
    private static final String Name_DB = "DB_MSG.db";
    public static void send_udp(String broadcastMSg, InetAddress Address) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        socket.send(packet);
        //socket.close();
    }

    //-----------------------------BROADCAST--------------------------------------------
    public static void broadcast(String broadcastMSg) throws IOException {
         send_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"));
    }
    public static boolean broadcast_Pseudo (String pseudo ) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);
        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
            return false;
        } else {
            broadcast("new pseudo :" + pseudo);
            System.out.println("Pseudo chosen:"+ pseudo);
            return true;
        }

    }

    public static boolean broadcast_ChangePseudo (String newpseudo) throws IOException, SQLException {

        createDB DB = new createDB(Name_DB);

        if ( DB.check(newpseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
            return false;
        } else {
            broadcast("change pseudo :" + newpseudo);
            System.out.println("Pseudo changed:" + newpseudo);
            return true;
        }
    }


    //broadcast la connection auprès des autres utilisateurs
    public static void broadcast_connection (String pseudo) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Failed Choose new pseudo : this one is already taken");

        } else {
            broadcast("Connected :" + pseudo);
            System.out.println("connected :" + pseudo);
        }

    }

    //se déconnecter et broadcast auprès des autres utilisateurs
    public static void broadcast_deconnection (String pseudo) throws IOException, SQLException {
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Failed ");

        } else {
            broadcast("Deconnected :" + pseudo);
            System.out.println("Deconnected :" + pseudo);
        }

    }
    public static void broadcast_end() throws IOException {

        broadcast("end");}


  /*  //static ou pas ??
    public void broadcast_ChangePseudo (String pseudo,ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            broadcast(pseudo);
            InsertData DB = new InsertData();
            DB.insert_pseudo(pseudo, InetAddress.getLocalHost());
*/


}

