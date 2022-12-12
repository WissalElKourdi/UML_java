
import java.net.InetAddress;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class InsertData {

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/MyDataBase.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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


    public void insert(String message, Time date, double port) {
            String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

            try (Connection conn = this.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.setTime(2, date);
               // stmt.setString(3, String.valueOf(addr_sender));
                //stmt.setString(4, String.valueOf(addr_receier));
                stmt.setDouble(3, port);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
        }
    public void insert_pseudo(String pseudo, InetAddress addr) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, String.valueOf(addr));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        public static void main(String[] args) {

            InsertData app = new InsertData();
            createNewTable();

            // insert three new rows
          /* app.insert("yoo", 15-12-7895,"155.92.0.1", "149.255.255.255", 5000);
             app.insert("Semifinished Goods", 4000,2);
            app.insert("Finished Goods", 5000,3);
        */}

    }

