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
    private Socket sock;
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
            System.out.println(" ip de DB : " +ip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("pseudo to connect with : "+pseudo);
        sock= new Socket(ip.trim(),1234);
        System.out.println(" ip : " +ip);
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
    public Socket getSock(){return this.sock;}
}
