package communication;

import Database.createDB;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        boolean rsslt;
        Session session = Session.getInstance();
        session.start();
        String name_db = "DB_MSG.db";
        createDB BD = new createDB(name_db);
        BD.insertIpseudo("leonie","10.32.2.25",name_db);
        BD.insertConnected("leonie",1234,name_db);
        List<String> connected = BD.selectAllConnected(name_db);
        Socket socket =Handler.getInstance().startConnection("leonie");
        rsslt = socket.isConnected();
        System.out.println(rsslt);
        System.out.println();
        Sender sender= new Sender(socket,"leonie","coucou");
session.stop();

    }
}
