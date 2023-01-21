package UDP;

import Database.createDB;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class UDP_Server {
    private DatagramSocket socket;
    private static final String Name_DB = "DB_MSG.db";

    private createDB DB = new createDB(Name_DB);
    public UDP_Server() throws SocketException, SQLException {
        this.socket = new DatagramSocket();
    }
    public void send_udp(String broadcastMSg, InetAddress Address, int port) throws IOException {
       // DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        this.socket.send(packet);
       // this.socket.close();
    }

    //-----------------------------BROADCAST--------------------------------------------
    public void broadcast(String broadcastMSg, int port) throws IOException {
        this.send_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"),port);
    }
    public boolean broadcast_Pseudo (String pseudo, int port ) throws IOException, SQLException {
        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
            return false;
        } else {
            broadcast("new pseudo :" + pseudo, port);
            System.out.println("Pseudo chosen:"+ pseudo);
            return true;
        }
    }

    public boolean broadcast_ChangePseudo (String newpseudo, int port) throws IOException, SQLException {
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
    public void broadcast_connection (String pseudo, int port) throws IOException, SQLException {


        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Failed Choose new pseudo : this one is already taken");

        } else {
            broadcast("Connected :" + pseudo, port);
            System.out.println("connected :" + pseudo);
        }
    }

    //se déconnecter et broadcast auprès des autres utilisateurs
    public void broadcast_deconnection (String pseudo, int port) throws IOException, SQLException {

            broadcast("Deconnected :" + pseudo, port);

    }
    public void broadcast_end(int port) throws IOException {
        broadcast("end", port);
    }
    public void broadcast_MyState (int port) throws IOException, SQLException {
        broadcast("UpdtateState :" + DB.getMonPseudo(Name_DB), port);
        System.out.println("UpdtateState :" + DB.getMonPseudo(Name_DB));
    }

    public void broadcast_AskState (String pseudo, int port) throws IOException, SQLException {
        broadcast("AskForState :" + pseudo, port);
        System.out.println("AskForState :" + pseudo);
    }

    public void broadcast_info(String pseudo, String addr, int port) throws IOException {
        broadcast("MY INFOS :" + pseudo + "/" + addr, port);
        System.out.println("My INFOS :" + pseudo + "/" + addr);

    }
    public void broadcast_change_info(String pseudo, String addr, int port) throws IOException {
        broadcast("MY INFOS CHANGE :" + pseudo + "/" + addr, port);
        System.out.println("My INFOS CHANGE :" + pseudo + "/" + addr);

    }
    public void broadcast_je_vais_change_mon_pseudo(String pseudo, int port) throws IOException {
        broadcast("MY old pseudo :" + pseudo , port);
        System.out.println("My old CHANGE :" + pseudo );

    }


}

