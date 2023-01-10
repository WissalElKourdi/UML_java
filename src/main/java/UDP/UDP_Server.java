package UDP;

import Database.createDB;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class UDP_Server {
    private static DatagramSocket socket = null;
    private InetAddress Address;
    public static int port = 4000;
    private static String Name_DB = "DB_MSG.db";
    public static void send_udp(String broadcastMSg, InetAddress Address, String pseudo) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        socket.send(packet);
        socket.close();
    }

    //-----------------------------BROADCAST--------------------------------------------
    public static void broadcast(String broadcastMSg, String pseudo) throws IOException {
         send_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"), pseudo);
    }
    public void broadcast_Pseudo (String pseudo ) throws IOException{
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            System.out.println("Choose new pseudo : this one is already taken");
        } else {
            System.out.println("here");

            DB.insertIpseudo(pseudo, socket.getLocalSocketAddress().toString(), Name_DB);
            broadcast("change",pseudo);
            System.out.println("Pseudo changed:"+ pseudo);

        }
    }



    public void broadcast_ChangePseudo (String pseudo, String newpseudo) throws IOException{
        createDB DB = new createDB(Name_DB);

        if ( DB.check(newpseudo,Name_DB) ) {
            DB.insertIpseudo(newpseudo, socket.getLocalSocketAddress().toString(), Name_DB);
            broadcast("change",newpseudo);
            System.out.println("Pseudo changed:");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }
    }


    //broadcast la connection auprès des autres utilisateurs
    public void broadcast_connection (String pseudo) throws IOException{
        createDB DB = new createDB(Name_DB);

        if ( DB.check(pseudo,Name_DB) ) {
            DB.insertIpseudo(pseudo, socket.getLocalSocketAddress().toString(), Name_DB);
            broadcast("change",pseudo);
            System.out.println("connected");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }

    }

    //se déconnecter et broadcast auprès des autres utilisateurs
    public void broadcast_deconnection (String pseudo,ArrayList userList) throws IOException{
        broadcast("change pseudo",pseudo);
        DeleteInactiveUser(pseudo,userList);
        System.out.println("deconnection");
    }


  /*  //static ou pas ??
    public void broadcast_ChangePseudo (String pseudo,ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            broadcast(pseudo);
            InsertData DB = new InsertData();
            DB.insert_pseudo(pseudo, InetAddress.getLocalHost());
*/
    //changer de pseudo et le broadcast auprès des autres utilisateurs



    //-----------------------------LIST--------------------------------------------

    //ajouter à la liste un utilisateur qui vient de se connecter
    public void AddConnectedUser(String Name, ArrayList userList){
        userList.add(Name);
    }

    //ajouter à la liste un utilisateur qui vient de se déconnecter
    public void  DeleteInactiveUser(String Name,ArrayList userList ){
        userList.remove(Name);
    }

    //vérifier que le pseudo choisi par l'utilisateur n'est pas déjà utilisé
    public boolean check_pseudo (String pseudo, ArrayList userList) {
        boolean result = true;

        for (int i=0 ; i < userList.size(); i++) {
            if (userList.get(i) == pseudo) {
                result = false;
                break;
            }
        }
        return result;
    }
}
