package Database;

import java.sql.*;

public class Select_Data_DB {

    private Connection connect(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/" + filename;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll(String filename){
        String sql = "SELECT message, date, pseudo, addr, port FROM history";
        System.out.println("yo00");
                    try (Connection conn = this.connect(filename);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("message")+  "\t" +
                        rs.getString("date")+  "\t" +
                        rs.getString("pseudo")+  "\t" +
                        rs.getString("addr")+  "\t" +
                        rs.getInt("port"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getMessagefromdate (String date, String filename){
        String sql = "SELECT message, date, pseudo,addr, port "
                + "FROM history WHERE date > ?";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){

            // set the value
            stmt.setString(1,date);
            //i:1 numero du point d'interogation au dessous
            ResultSet rs  = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("message") +  "\t" +
                        rs.getString("date")+  "\t" +
                        rs.getString("pseudo")+  "\t" +
                        rs.getString("addr")+  "\t" +
                        rs.getInt("port"));
            }
            System.out.println("hey");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getMessagefrom (String pseudo,String filename){
        String sql = "SELECT message, date, pseudo,addr, port  "
                + "FROM history WHERE pseudo = ?";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){

            // set the value
            stmt.setString(1,pseudo);
            //
            ResultSet rs  = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("message") +  "\t" +
                        rs.getString("date")+  "\t" +
                        rs.getString("pseudo")+  "\t" +
                        rs.getString("addr")+  "\t" +
                        rs.getInt("port"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        /*Select_Data_DB app = new Select_Data_DB();
        app.selectAll();
        app.getMessagefromdate("2023-01-02 1:37");
        app.getMessagefrom("lelo");
    */}

}
