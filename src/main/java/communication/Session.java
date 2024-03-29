package communication;

import Database.createDB;
import Interface.SessionChatController;
import USERS.List_USers;
import javafx.application.Platform;

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
    private Boolean running;
    private Socket socket;

    private SessionChatController sessionchat;
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
        //this.running=true;
    }


    //fonction qui  cherche un utilisateur dans notre base de données
    /*private String adresse(InetAddress ip){
            return "ip";
    }

     */

    public  void addSock(String pseudo, Socket socket){
        this.map_socket.put(pseudo,socket);
    }

    public Socket getSock(String pseudo){
        return this.map_socket.get(pseudo);
    }

public void run () {

        String DB_name = "DB_MSG.db";
        String ip;
        createDB DB = null;
        String pseudo;

    try {

        setRunning(true);
            while (running) {
                socket = user.accept();
                if (socket != null) {
                    String addr = socket.getInetAddress().toString().substring(socket.getInetAddress().getHostAddress().toString().indexOf("/") + 2);
                    pseudo = List_USers.get_pseudo_user(addr);
                    map_socket.put(pseudo, socket);
                    Launch_receive receiver = new Launch_receive(socket, pseudo);
                    Launch_receive.sessions.add(receiver);
                    receiver.start();

                }

             running=running();

            }
    } catch (IOException e) {
        e.printStackTrace();
    }
    if(!running){
        try {
            user.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    public void close_sess() throws IOException {
       this.running = false;
       System.out.println("MY RUNNING =" + running + "/ " +this.running);

    }

    public boolean running(){
        return running;
    }
    public void setRunning(boolean running){
        this.running=running;
    }
}
