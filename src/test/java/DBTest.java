
import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class DBTest {

    private String DB_NAME = "MyTestDB.db";
    private String Name_DB = "DB_MSG.db";
    @Test
    public void dbCreationTest() throws SQLException, IOException, InterruptedException {
  /*      System.out.println("-----------------------------TEST DATABASE -----------------------");
       createDB DB = new createDB(DB_NAME);
       DB.connect(DB_NAME);
       DB.createNewDB("DB_Test.db");
       DB.deletefile("DB_Test.db");
       DB.creatTablehistory(DB_NAME);
       DB.creatTablepseudo(DB_NAME);
       DB.creatTableconnected(DB_NAME);
       DB.insertHistory("Bonjour !", "2023-01-02 1:50","Leonie", "149.255.255.255", 5000, "MyTestDB.db");
        DB.insertIpseudo("Leonie","149.255.255.255",DB_NAME );
        DB.insertConnected("Leonie",DB_NAME);
        DB.selectAllConnected(DB_NAME);
        DB.deleteConnected("Leonie",DB_NAME);
        DB.selectAllMsgHistory(DB_NAME);
        DB.selectAllMsgIPseudo(DB_NAME);
        DB.selectAllConnected(DB_NAME);
        DB.check("Wissal", DB_NAME);
        DB.insertIpseudo("Wissal","10.10.2.0",DB_NAME );
        DB.check("Wissal", DB_NAME);
        DB.changeIpseudo("Wiwi", "10.10.2.0", DB_NAME);
        DB.getPseudo("10.10.2.0",DB_NAME);


*/
        System.out.println("-----------------------------TEST DATABASE UDP-----------------------");
       // broadcast_ChangePseudo
        createDB DB = new createDB(Name_DB);
        new UDP_Client().start();
        new UDP_Server().broadcast("TEST");
        Thread.sleep(9000);
        new UDP_Server().broadcast_Pseudo("Sirine");
        System.out.println("here");
        Thread.sleep(9000);
        DB.selectAllMsgIPseudo(Name_DB);
        new UDP_Server().broadcast_ChangePseudo("Sissou");
        Thread.sleep(9000);
        DB.selectAllMsgIPseudo(Name_DB);
        new UDP_Server().broadcast_connection("WIWI");
        Thread.sleep(9000);
        DB.selectAllConnected(Name_DB);
        new UDP_Server().broadcast_deconnection("Leo");
        Thread.sleep(9000);
        DB.selectAllConnected(Name_DB);
        new UDP_Server().broadcast("end");

    }
}