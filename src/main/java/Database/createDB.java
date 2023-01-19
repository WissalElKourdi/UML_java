package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class createDB {
        private static Connection conn;
        public createDB(String Name_DB) throws SQLException {
            conn = this.connect(Name_DB);
            creatTablehistory(Name_DB);
            creatTablepseudo(Name_DB);
            creatTableconnected(Name_DB);
            creatTableMonPSeudo(Name_DB);
        }

        private synchronized Connection connect(String fileName ) {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/"+ fileName;
            conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }
        public void disconnect (String fileName ) {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/"+ fileName;
             conn = null;
            try {
                conn = DriverManager.getConnection(url);
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        public void deletefile (String fileName){

            try {
                File file = new File(fileName);
                String path = file.getAbsolutePath();
                File f= new File(path);

              //  this.deletefile(fileName);
                if( f.delete()) {
                    System.out.println(f.getName()+"deleted");
                } else {
                    System.out.println("failed");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //return false;
        }
        public synchronized boolean creatTablehistory(String fileName) throws SQLException {
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
                conn.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        public synchronized boolean creatTablepseudo(String fileName) {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/" + fileName;

            // SQL statement for creating a new table

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
            String url = "jdbc:sqlite:sqlite/" + fileName;

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
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/" + fileName;

        // SQL statement for creating a new table

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

        public synchronized boolean insertHistory(String message, String date, String pseudo, String addr, int port, String filename) {
            String sql = "INSERT INTO history(message,date, pseudo, addr, port) VALUES(?,?,?,?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.setString   (2, date);
                stmt.setString(3, pseudo);
                stmt.setString(4, String.valueOf(addr));
                stmt.setDouble(5, port);
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

        public synchronized List<String> selectAllMsgHistory(String filename){
            //String sql = "SELECT message, date, pseudo, addr, port FROM history";
            String sql = "SELECT pseudo, message, date FROM history";
            List<String> list = new ArrayList<>();

            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                while (rs.next()) {
                    list.add(rs.getString("pseudo").trim()+  "\t" + rs.getString("message")+  "\t"
                                    + rs.getString("date")+  "\t");
                    /*
                    list.add( rs.getString("message")+  "\t" +
                            rs.getString("date")+  "\t" +
                            rs.getString("pseudo").trim()+  "\t" +
                            rs.getString("addr")+  "\t" +
                            rs.getInt("port"));

                     */
                    // System.out.println(rs.getString("pseudo"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            //conn.close();

            return list;
        }


        public synchronized String selectAllMsgIPseudo(String filename){
            String sql = "SELECT pseudo, addr FROM IPseudo";
            String result ="";
            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    result += "\n" + rs.getString("pseudo")+  "\t" + rs.getString("addr");
                   // System.out.println(rs.getString("pseudo")+  "\t" + rs.getString("addr"));

                }
                conn.close();



            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return result;
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

    public synchronized int selectPort(String pseudo,String filename) throws SQLException {
        String sql = "SELECT port FROM Connected WHERE pseudo= ?";
        int port = 0;
        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){
            stmt.setString(1,pseudo);
            ResultSet rs    = stmt.executeQuery();
            while (rs.next()) {
                port = rs.getInt("port") ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return port;
    }
        public synchronized String getPseudo(String addr, String filename) throws SQLException {
            String sql = "SELECT  pseudo,addr FROM IPseudo WHERE addr= ?";
            String result ="";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt  = conn.prepareStatement(sql)){
                stmt.setString(1,addr);
                ResultSet rs    = stmt.executeQuery();

                // loop through the result set
                while (rs.next()) {

                    result = rs.getString("pseudo") ;
                  //  System.out.println(
                    //        rs.getString("pseudo") +  "\t" +
                      //              rs.getString("addr"));

                }
                conn.close();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return result;
        }
    public synchronized String getADDR(String pseudo, String filename) throws SQLException {
        String sql = "SELECT addr FROM IPseudo WHERE pseudo= ?";
        String result ="";

        try (Connection conn = this.connect(filename);
             PreparedStatement stmt  = conn.prepareStatement(sql)){
            stmt.setString(1,pseudo);
            ResultSet rs    = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {

                result = rs.getString("addr") ;
                //  System.out.println(
                //        rs.getString("pseudo") +  "\t" +
                //              rs.getString("addr"));

            }
            conn.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
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
                //  System.out.println(
                //        rs.getString("pseudo") +  "\t" +
                //              rs.getString("addr"));

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

        public synchronized boolean check1(String pseudo, String filename) throws SQLException {
            String sql = "SELECT EXISTS(SELECT 1 FROM IPseudo WHERE pseudo=" + pseudo + ");";
            Boolean exist = false;
            try (Connection conn = this.connect(filename);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                if (rs.next()) {

                    boolean found = rs.getBoolean(1);

                    conn.close();
                    if (found) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }



                // assert

        //
    //changeMonpseudo

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


          //  return false;
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



        public void getMessagefromdate(String date, String filename){
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
                conn.close();
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



        }
    }

