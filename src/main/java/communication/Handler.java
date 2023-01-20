package communication;

import Database.createDB;
import Interface.MenuController;
import USERS.List_USers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

public class Handler {
    private Socket socket;
    private static Handler handler;
    static {handler= new Handler();}
    private Handler(){}

    public Socket startConnection(String pseudo) throws IOException {
        String ip;
        ip = List_USers.get_IP_user(pseudo);
        System.out.println("Connection : IP: " +ip+ "--> pseudo : "+pseudo);
        Socket sock= new Socket("10.1.5.14",1234);
        Session.getInstance().addSock(pseudo,sock);
        return sock;}
    public boolean isEtablished(String pseudo){
        if(Session.getInstance().getSock(pseudo)!=null)
            return true;
        else
            return false;
    }

    public static Handler getInstance() {
        return handler;
    }
    public Socket getSock(){return this.socket;}
}
