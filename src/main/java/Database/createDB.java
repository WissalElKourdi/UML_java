package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class createDB {
    private String fileName;
    private String url = "jdbc:sqlite:sqlite/"+ fileName;


    private static Connection conn;

    public createDB(String Name_DB) throws SQLException {
        conn = this.connect(Name_DB);
        creatTablepseudo(Name_DB);
        creatTableconnected(Name_DB);
        creatTableMonPSeudo(Name_DB);
        creatTableMsg(Name_DB);
    }

    private synchronized Connection connect(String fileName ) {

        conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public synchronized boolean creatTablepseudo(String fileName) {

        String sql = "CREATE TABLE IF NOT EXISTS IPseudo (\n"
                + " pseudo NOT NULL ,\n"
                + " addr NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



    public synchronized boolean creatTableconnected(String fileName) {
        //String url = "jdbc:sqlite:sqlite/" + fileName;

        String sql = "CREATE TABLE IF NOT EXISTS Connected (\n"
                + " pseudo NOT NULL,\n"
                + " port NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean creatTableMonPSeudo(String fileName) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS Monpseudo (\n"
                + " pseudo NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean creatTableMsg(String fileName) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS Msg (\n"
                + " message NOT NULL,\n"
                + " date NOT NULL,\n"
                + " pseudo NOT NULL,\n"
                + " addr NOT NULL,\n"
                + " port NOT NULL,\n"
                + " sender NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean insertMonpseudo( String pseudo, String filename) {
        String sql = "INSERT INTO Monpseudo(pseudo) VALUES(?)";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pseudo);

            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean insertMSG(String message, String date, String pseudo, String addr, int port,String sender, String filename) {
        String sql = "INSERT INTO Msg (message,date, pseudo, addr, port,sender) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.setString   (2, date);
            stmt.setString(3, pseudo);
            stmt.setString(4, String.valueOf(addr));
            stmt.setDouble(5, port);
            stmt.setString(6, sender);
            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized boolean insertIpseudo(String pseudo, String addr, String filename) {
        String sql = "INSERT INTO IPseudo( pseudo, addr) VALUES(?,?)";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, (addr));
            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println(
                    e.getMessage());
        }
        return false;
    }


    public synchronized boolean insertConnected(String pseudo,int port ,String filename) {
        String sql = "INSERT INTO Connected( pseudo,port) VALUES(?,?)";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setInt(2, port);
            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println(
                    e.getMessage());
        }
        return false;
    }


    public synchronized boolean deleteConnected (String pseudo, String filename) {
        String sql = "DELETE FROM Connected WHERE pseudo = ?";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            stmt.setString(1, pseudo);

            // execute the delete statement
            stmt.executeUpdate();

            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public synchronized List<String> selectMsg(String addr,String filename){
        //String sql = "SELECT message, date, pseudo, addr, port FROM history";
        String sql = "SELECT pseudo, message, date, sender FROM Msg WHERE addr=?";
        List<String> list = new ArrayList<>();

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){
            stmt.setString(1,addr);
            ResultSet rs    = stmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("sender")+":"+rs.getString("pseudo").trim()+":"+ rs.getString("message")+":"+rs.getString("date"));
            }
            conn.close();
            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    public synchronized List<String> selectAllConnected(String filename) throws SQLException {
        String sql = "SELECT pseudo FROM Connected";
        List<String> list = new ArrayList<>();


        try (Connection conn = this.connect(filename);
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("pseudo").trim());
                // System.out.println(rs.getString("pseudo"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //conn.close();
        return list;
    }


    public synchronized String getMonPseudo(String filename) throws SQLException {
        String sql = "SELECT pseudo FROM Monpseudo ";
        String result ="";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){

            ResultSet rs    = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {

                result = rs.getString("pseudo") ;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean check(String pseudo, String filename) {
        String sql = "SELECT EXISTS(SELECT * FROM Connected WHERE pseudo= ?);";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setString(1,pseudo);
             ResultSet rs = stmt.executeQuery() ;
            // loop through the result set
            if (rs.next()) {
                boolean found = rs.getBoolean(1);
                conn.close();
               return found;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public synchronized boolean changeIpseudo(String pseudo, String addr, String filename,String old_pseudo) {
        String sql = "UPDATE IPseudo SET pseudo=? WHERE addr = ?;";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, String.valueOf(addr));
            stmt.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            System.out.println(
                    e.getMessage());
        }


         sql = "UPDATE Connected SET pseudo=? WHERE pseudo = ?;";
        try (Connection conn = this.connect(filename);
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, old_pseudo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(
                    e.getMessage());
        }



        sql = "UPDATE Monpseudo SET pseudo=? WHERE pseudo = ?;";

        try (Connection conn = this.connect(filename);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            stmt.setString(2, old_pseudo);
            stmt.executeUpdate();

            } catch (SQLException e) {
            System.out.println(
                    e.getMessage());
            }
        return false;
    }

    public String getDateFromMessage(String Message, String filename) {
        String sql = "SELECT date" + "FROM history WHERE Message = ?";
        String result = null;
        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){

            // set the value
            stmt.setString(1,Message);
            //
            ResultSet rs  = stmt.executeQuery();
            result = rs.getString("pseudo") ;


            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("date"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


}

