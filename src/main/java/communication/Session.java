package communication;

import Interface.ServerTcp;
import Interface.SessionChatController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Session extends Thread {
    private ServerSocket user;
   /* private static Session Session;
    // cette fonction permet qu'à chaque initiation de conversation avec un client un socket se crée pour lui

    static {
        try {
            Session= new Session();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
   public Map<String, Socket> map_socket = new HashMap<>();

   /* public static Session getInstance(){
        return Session;
}*/
    public Session() throws IOException { // ce thread crée le serveur principal et attribue à chaque client un socket
        user= new ServerSocket(5000);
        map_socket=new HashMap<>();

    }

    //fonction qui  cherche un utilisateur dans notre base de données


    public  void addSock(String pseudo, Socket socket){
        this.map_socket.put(pseudo,socket);
    }

    public Socket getSock(String pseudo){
        return this.map_socket.get(pseudo);
    }

public void run () {
        Socket socket;
        String pseudo = null;
        while(true){
            System.out.println("okay i am launched ");
            try {
                socket = user.accept();
                System.out.println("Client has been added ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(socket!=null){
                //search for pseudo dans la base de données
                //search for ip adresse
                map_socket.put(pseudo,socket);
                if(!pseudo.equals("")){
                    Launch_receive receiver = new Launch_receive(socket,pseudo);
                    Launch_receive.sessions.add(receiver);
                    receiver.start();
                }
            }
        }

}



}
