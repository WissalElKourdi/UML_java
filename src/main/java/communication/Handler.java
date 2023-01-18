package communication;

import Database.createDB;
import Interface.MenuController;

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
        //User usr = User.getUser(pseudo);
        String DB_name = "DB_MSG.db";
        String ip;
        createDB DB = null;
        try {
            DB = new createDB(DB_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ip = DB.getADDR(pseudo, DB_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("pseudo to connect with : "+pseudo);
        Socket socket= new Socket(InetAddress.getLocalHost(),5000);
        System.out.println("the adresse im talking to is " +InetAddress.getLocalHost());

     Session.getInstance().addSock(pseudo,socket);
     return socket;}

    public boolean isEtablished(String pseudo){
        if(Session.getInstance().getSock(pseudo)!=null)
            return true;
        else
            return false;
    }

    public static Handler getInstance() {
        return handler;
    }
}
