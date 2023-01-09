package Database;

import java.net.InetAddress;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class InsertData {

    public static void createNewTable(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/" + fileName;

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
                + " message NOT NULL ,\n"
                + " date NOT NULL,\n"
                + " pseudo NOT NULL,\n"
                + " addr NOT NULL,\n"
                + " port NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void insert(String message, String date, String pseudo, String addr, int port, String filename) {
            String sql = "INSERT INTO history(message,date, pseudo, addr, port) VALUES(?,?,?,?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.setString   (2, date);
                stmt.setString(3, pseudo);
                stmt.setString(4, String.valueOf(addr));
                stmt.setDouble(5, port);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
        }
    public void insert_pseudo(String pseudo, InetAddress addr, String filename) {
        String sql = "INSERT INTO history(pseudo , addr) VALUES(?,?)";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, String.valueOf(addr));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        public void delete(String filename) {
            String sql = "DROP TABLE IF EXISTS history;";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    private Connection connect(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
        public static void main(String[] args) {

           // InsertData app = new InsertData();
          //  createNewTable(test.db);

            // insert three new rows
          /* app.insert("yoo", 15-12-7895,"155.92.0.1", "149.255.255.255", 5000);
             app.insert("Semifinished Goods", 4000,2);
            app.insert("Finished Goods", 5000,3);
             DeleteApp app = new DeleteApp();
            // delete the row with id 3
            app.delete(3);
        */}

    }

