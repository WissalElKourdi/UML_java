
import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {

    private String DB_NAME = "MyTestDB.db";
    private String Name_DB = "DB_MSG.db";
    @Test
    public void dbCreationTest() throws SQLException, IOException, InterruptedException {
       System.out.println("-----------------------------TEST DATABASE -----------------------");
       createDB DB = new createDB(DB_NAME);
        Connection conn = DB.connect("DB_Test.db");

       assert DB.connect(DB_NAME) != null;

       assert DB.createNewDB("DB_Test.db")== true;
        conn.close();
        DB.deletefile("DB_Test.db");
     // assert DB.deletefile("DB_Test.db")== true;;
        // need to close connection before deleting file

      assert DB.creatTablehistory(DB_NAME) == true;
      assert DB.creatTablepseudo(DB_NAME) == true;
      assert DB.creatTableconnected(DB_NAME) == true;
      assert  DB.insertHistory("Bonjour !", "2023-01-02 1:50","Leonie", "149.255.255.255", 5000, "MyTestDB.db") == true;
      assert DB.insertIpseudo("Leonie","149.255.255.255",DB_NAME ) == true;
       assert DB.insertConnected("Leonie",DB_NAME) == true;
       System.out.println(DB.selectAllConnected(DB_NAME));
       System.out.println("yey");
       assert DB.selectAllConnected(DB_NAME)=="Leonie";
        assert DB.deleteConnected("Leonie",DB_NAME)==true;
        assert DB.selectAllMsgHistory(DB_NAME)=="Bonjour !\t2023-01-02 1:50\tLeonie\t149.255.255.255\t5000";
        assert DB.selectAllMsgIPseudo(DB_NAME)=="Leonie\t149.255.255.255";
        assert DB.selectAllConnected(DB_NAME)=="";
        assert DB.check("Wissal", DB_NAME)==false;
        assert    DB.insertIpseudo("Wissal","10.10.2.0",DB_NAME )==true;
        assert  DB.check("Wissal", DB_NAME)==true;
        assert   DB.changeIpseudo("Wiwi", "10.10.2.0", DB_NAME)==true;
        assert  DB.getPseudo("10.10.2.0",DB_NAME)=="Wiwi";


        createDB DB_UDP = new createDB(Name_DB);
        DB.connect(Name_DB);
        System.out.println("-----------------------------TEST DATABASE UDP-----------------------");
        new UDP_Client().start();
       new UDP_Server().broadcast("TEST");
        new UDP_Server().broadcast_end();
       new UDP_Server().broadcast_Pseudo("Sirine");
        new UDP_Server().broadcast_end();
        System.out.println(DB_UDP.selectAllMsgIPseudo(Name_DB));
       new UDP_Server().broadcast_ChangePseudo("Sissou");
        new UDP_Server().broadcast_end();
        System.out.println(DB_UDP.selectAllMsgIPseudo(Name_DB));
        new UDP_Server().broadcast_connection("WIWI");
        new UDP_Server().broadcast_end();
        new UDP_Server().broadcast_connection("LEo");
        new UDP_Server().broadcast_end();
        System.out.println(DB_UDP.selectAllConnected(Name_DB));
        new UDP_Server().broadcast_deconnection("WIWI");
        new UDP_Server().broadcast_end();
        System.out.println(DB_UDP.selectAllConnected(Name_DB));
        DB.selectAllMsgIPseudo(Name_DB);

    }
}