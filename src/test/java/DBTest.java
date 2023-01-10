
import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class DBTest {

    private String DB_NAME = "MyTestDB.db";
    String Name_DB = "DB_MSG.db";
    @Test
    public void dbCreationTest() throws SQLException, IOException, InterruptedException {
        /*System.out.println("coucou");
        Delete appp = new Delete ();
        appp.deletefile("MyTestDB.db");
        // supprimer ou reinitialise pour les tests qu'ils comencent avec le mm etat de de la BDD
        createNewDataBase DB = new createNewDataBase();
        DB.createNewDatabase("MyTestDB.db");
        InsertData app = new InsertData();
        app.createNewTable("MyTestDB.db");
        System.out.println("new table created");
        app.insert("yoo", "2023-01-02 1:50","lelo", "149.255.255.255", 5000, "MyTestDB.db");
        Select_Data_DB app1 = new Select_Data_DB();
        app1.selectAll("MyTestDB.db");
        app1.getMessagefromdate("2023-01-02 1:37","MyTestDB.db");
        app1.getMessagefrom("lelo","MyTestDB.db");
        app.delete("MyTestDB.db");
        app1.selectAll("MyTestDB.db");*/

       createDB DB = new createDB("DB_MSG.db");
        //DB.connect(DB_NAME);
        //DB.creatTablehistory(DB_NAME);
        //DB.creatTablepseudo(DB_NAME);
      /*  DB.insertHistory("yoo", "2023-01-02 1:50","lelo", "149.255.255.255", 5000, "MyTestDB.db");
        DB.insertIpseudo("MA","10.10.2",DB_NAME );
        DB.selectAllMsgHistory(DB_NAME);
        DB.selectAllMsgIPseudo(DB_NAME);
        DB.changeIpseudo("MATH", "10.10.2", DB_NAME);
        DB.selectAllMsgIPseudo(DB_NAME);*/
        //DB.selectAllMsgHistory("DB_MSG.db");
        //DB.getPseudo("/127.0.0.1:61936","DB_MSG.db");

       // broadcast_ChangePseudo
        new UDP_Client().start();
       // new UDP_Server().broadcast("ok","YOO");

        new UDP_Server().broadcast("change : Sirine","ok");
        Thread.sleep(3000);
       // new UDP_Server().broadcast_Pseudo("ALBAN");
        DB.selectAllMsgIPseudo("DB_MSG.db");
        System.out.println("fin");
        //DB.selectAllMsgHistory("DB_MSG.db");

/*
       createDB DB = new createDB("DB_MSG.db");
           DB.insertIpseudo("Sirine","/172.17.112.1", "DB_MSG.db");

        System.out.println("done");
        DB.selectAllMsgIPseudo(Name_DB);

*/
    }
}