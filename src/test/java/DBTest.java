import org.junit.Test;

public class DBTest {

    @Test
    public void dbCreationTest() {
        System.out.println("coucou");
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
        app1.selectAll("MyTestDB.db");
    }


    }
