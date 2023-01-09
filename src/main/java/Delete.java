
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;

public class Delete {

    private Connection connect(String fileName ) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/"+ fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public  void deletefile (String fileName){
        try
        {
            File f= new File("/home/chaouch/Bureau/4A/projet_chat/UML_java/sqlite/"+ fileName);
            if(f.delete()) {
                System.out.println(f.getName()+"deleted");}
            else
            {
                System.out.println("failed");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void delete(String fileName) {
        String sql = "DELETE history";

        try (Connection conn = this.connect(fileName);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {

    }

}