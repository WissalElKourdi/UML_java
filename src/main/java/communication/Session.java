package communication;

import Database.createDB;
import Interface.ServerTcp;
import Interface.SessionChatController;
import UDP.UDP_Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Session extends Thread {
    private ServerSocket user;
    private static Session Session;
    // cette fonction permet qu'à chaque initiation de conversation avec un client un socket se crée pour lui


   private Map<String, Socket> map_socket = new HashMap<>();
    static {
        try {
            Session= new Session();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session getInstance(){
        return Session;
}
    public Session() throws IOException { // ce thread crée le serveur principal et attribue à chaque client un socket
        user= new ServerSocket(1234);
        map_socket=new HashMap<>();
    }


    //fonction qui  cherche un utilisateur dans notre base de données
private String adresse(InetAddress ip){
        return "ip";
}

    public  void addSock(String pseudo, Socket socket){
        this.map_socket.put(pseudo,socket);
    }

    public Socket getSock(String pseudo){
        return this.map_socket.get(pseudo);
    }

public void run () {
        Socket socket;
        String DB_name = "DB_MSG.db";
        String ip;
        createDB DB = null;
        String pseudo;


        while(true){
            System.out.println("okay i am launched ");
            try {
                //System.out.println("okay i am launched 2 ");
                socket = user.accept();
                System.out.println("Client has been added ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(socket!=null){
                try {
                    System.out.println("from lis ip = "+socket.getLocalSocketAddress() +"///"+ socket.getInetAddress().getHostAddress() +"///" + socket.getLocalAddress() +"///"+ socket.getRemoteSocketAddress() +"///" + socket.getReuseAddress());
                    String addr = socket.getInetAddress().getHostAddress().toString().substring(socket.getInetAddress().getHostAddress().toString().indexOf("/") + 1);
                    System.out.println("from lis ip = " + addr);
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }

                //    getAddress().toString().getHostAddress()+" port = "+socket.getPort());

              //  String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
                try {
                    DB = new createDB(DB_name);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println("ALL IPSEUDO :");
                    DB.selectAllMsgIPseudo(DB_name);
                    pseudo = DB.getPseudo(adresse(socket.getInetAddress()), DB_name);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
