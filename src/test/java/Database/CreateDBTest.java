



package Database;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class CreateDBTest {
    private String Name_DB;
    private createDB DB;

    @Before
    public void setup() throws SQLException {
        Name_DB = "DB_MSG.db";
        DB=new createDB(Name_DB);
    }
    public CreateDBTest() throws SQLException {
    }

    @Test
    public void testCreateDB() throws SQLException {
        assert DB.creatTablepseudo(Name_DB);
        assert DB.creatTableconnected(Name_DB);
        assert DB.insertIpseudo("Leonie", "149.255.255.255", Name_DB);
        assert DB.deleteConnected("Leonie", Name_DB);
        assert !DB.check("Wissal", Name_DB);
        assert DB.insertIpseudo("Wissal", "10.10.2.0", Name_DB);

    }}


