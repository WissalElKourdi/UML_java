import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class UDP_Server {
    private static DatagramSocket socket = null;
    private InetAddress Address;
    public static int port = 4000;

    public static void send_udp(String broadcastMSg, InetAddress Address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = broadcastMSg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Address, port);
        socket.send(packet);
        socket.close();
    }

    //-----------------------------BROADCAST--------------------------------------------
    public static void broadcast(String broadcastMSg) throws IOException {
         send_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"));
    }

    //broadcast la connection auprès des autres utilisateurs
    public void broadcast_connection (String pseudo, ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            AddConnectedUser(pseudo,userList);
            broadcast(pseudo);
            System.out.println("connected");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }
    }

    //se déconnecter et broadcast auprès des autres utilisateurs
    public void broadcast_deconnection (String pseudo,ArrayList userList) throws IOException{
        broadcast(pseudo);
        DeleteInactiveUser(pseudo,userList);
        System.out.println("deconnection");
    }

    //changer de pseudo et le broadcast auprès des autres utilisateurs
    public void broadcast_ChangePseudo (String pseudo, String newpseudo, ArrayList userList) throws IOException{
        if (check_pseudo(newpseudo,userList)) {
            userList.set(userList.indexOf(pseudo), newpseudo);
            broadcast(newpseudo);
            System.out.println("Pseudo changed:");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }
    }


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
