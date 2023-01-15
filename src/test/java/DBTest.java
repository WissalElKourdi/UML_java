
import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DBTest {

    private String DB_NAME = "MyTestDB.db";
    private String Name_DB = "DB_MSG.db";
    @Test
    public void dbCreationTest() throws SQLException, IOException, InterruptedException {
   /*    System.out.println("-----------------------------TEST DATABASE -----------------------");
      createDB DB = new createDB(DB_NAME);
        //Connection conn = DB.connect("DB_Test.db");

      // assert DB.connect(DB_NAME) != null;

       assert DB.createNewDB("DB_Test.db");
     //   conn.close();
        DB.deletefile("DB_Test.db");
     // assert DB.deletefile("DB_Test.db")== true;;
        // need to close connection before deleting file

      assert DB.creatTablehistory(DB_NAME);
      assert DB.creatTablepseudo(DB_NAME);
      assert DB.creatTableconnected(DB_NAME);
      assert DB.insertHistory("Bonjour !", "2023-01-02 1:50", "Leonie", "149.255.255.255", 5000, "MyTestDB.db");
      assert DB.insertIpseudo("Leonie", "149.255.255.255", DB_NAME);
       assert DB.insertConnected("Leonie", DB_NAME);
       System.out.println(DB.selectAllConnected(DB_NAME));
       System.out.println("yey");
       assert Objects.equals(DB.selectAllConnected(DB_NAME), "Leonie");
        assert DB.deleteConnected("Leonie", DB_NAME);
        System.out.println(DB.selectAllMsgHistory(DB_NAME));

        /*assert Objects.equals(DB.selectAllMsgHistory(DB_NAME), "Bonjour !	2023-01-02 1:50	Leonie	149.255.255.255	5000");
        assert Objects.equals(DB.selectAllMsgIPseudo(DB_NAME), "Leonie\t149.255.255.255");
        assert Objects.equals(DB.selectAllConnected(DB_NAME), "");
        assert !DB.check("Wissal", DB_NAME);
        assert DB.insertIpseudo("Wissal", "10.10.2.0", DB_NAME);
        assert DB.check("Wissal", DB_NAME);
        assert DB.changeIpseudo("Wiwi", "10.10.2.0", DB_NAME);
        assert Objects.equals(DB.getPseudo("10.10.2.0", DB_NAME), "Wiwi");

/*

        //createDB DB_UDP = new createDB(Name_DB);
       // System.out.println(DB_UDP.selectAllMsgIPseudo(Name_DB));
       // DB.connect(Name_DB);
       // System.out.println("-----------------------------TEST DATABASE UDP-----------------------");
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
*/
        createDB DB = new createDB("DB_MSG.db");
      //  System.out.println(DB.selectAllConnected(Name_DB));
        System.out.println("---");
        System.out.println(DB.selectAllMsgIPseudo(Name_DB));
        System.out.println("---");
        System.out.println(DB.selectAllConnected(Name_DB));
        System.out.println("---");
        System.out.println(DB.selectAllMsgHistory(Name_DB));
        System.out.println("---");
    }
}