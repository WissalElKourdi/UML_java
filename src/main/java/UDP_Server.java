
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

    //static ou pas ??
    public void broadcast_connection (String pseudo, ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            AddConnectedUser(pseudo,userList);
            broadcast(pseudo);
            System.out.println("connected");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }
    }

    //static ou pas ??
    public void broadcast_deconnection (String pseudo,ArrayList userList) throws IOException{
        broadcast(pseudo);
        DeleteInactiveUser(pseudo,userList);
        System.out.println("deconnection");
    }

    //static ou pas ??
    public void broadcast_ChangePseudo (String pseudo,ArrayList userList) throws IOException{
        if (check_pseudo(pseudo,userList)) {
            broadcast(pseudo);
            System.out.println("Pseudo changed:");
        } else {
            System.out.println("Choose new pseudo : this one is already taken");
        }
    }


    //-----------------------------LIST--------------------------------------------
    public void AddConnectedUser(String Name, ArrayList userList){
        userList.add(Name);
    }

    /*public void SendActiveUserList(){
        System.out.println("TO DO: SendActiveUserList()");
    }*/

    public void  DeleteInactiveUser(String Name,ArrayList userList ){
        userList.remove(Name);
    }

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
