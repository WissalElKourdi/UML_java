
import Database.createDB;
import org.junit.Test;
public class DBTest {

    private String DB_NAME = "MyTestDB.db";
    @Test
    public void dbCreationTest() {
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

        createDB DB = new createDB(DB_NAME);
        //DB.connect(DB_NAME);
        //DB.creatTablehistory(DB_NAME);
        //DB.creatTablepseudo(DB_NAME);
        DB.insertHistory("yoo", "2023-01-02 1:50","lelo", "149.255.255.255", 5000, "MyTestDB.db");
        DB.insertIpseudo("MA","10.10.2",DB_NAME );
        DB.selectAllMsgHistory(DB_NAME);
        DB.selectAllMsgIPseudo(DB_NAME);
        DB.changeIpseudo("MATH", "10.10.2", DB_NAME);
        DB.selectAllMsgIPseudo(DB_NAME);


    }
}