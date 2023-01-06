
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Select_Data_DB {

        private Connection connect() {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/MyDataBase.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }


        public void selectAll(){
            String sql = "SELECT all";

            try (Connection conn = this.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("message")+  "\t" +
                            rs.getTime("date")+  "\t" +
                            rs.getString("addr_sender")+  "\t" +
                            rs.getString("addr_receier")+  "\t" +
                            rs.getInt("port"));

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            Select_Data_DB app = new Select_Data_DB();
            app.selectAll();
        }

    }

