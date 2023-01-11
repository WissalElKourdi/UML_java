package Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.*;


    public class createDB<Name_DB> {
        String Name_DB;
        public createDB(String Name_DB){
            creatTablehistory(Name_DB);
            creatTablepseudo(Name_DB);
            creatTableconnected(Name_DB);
        }




        public static boolean createNewDB(String fileName) {

            String url = "jdbc:sqlite:sqlite/" + fileName;

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                    return true;
                }
                return false;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        public Connection connect(String fileName ) {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/"+ fileName;
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }
        public Connection deconnect (String fileName ) {
            // SQLite connection string
            String url = "jdbc:sqlite:sqlite/"+ fileName;
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }

        public boolean deletefile (String fileName){
            try

            {

                File file = new File(fileName);
                String path = file.getAbsolutePath();
                File f= new File(path);
                System.out.println(path);
                if(file.delete()) {
                    System.out.println(f.getName()+"deleted");
                    return true;
               } else {
                    System.out.println("failed");
                    return false;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //return false;
            return false;
        }
        public static boolean creatTablehistory(String fileName) {
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
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public static boolean creatTablepseudo(String fileName) {
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
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean creatTableconnected(String fileName) {
            String url = "jdbc:sqlite:sqlite/" + fileName;


            String sql = "CREATE TABLE IF NOT EXISTS Connected (\n"
                    + " pseudo NOT NULL);";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                // create a new table
                stmt.execute(sql);
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean insertHistory(String message, String date, String pseudo, String addr, int port, String filename) {
            String sql = "INSERT INTO history(message,date, pseudo, addr, port) VALUES(?,?,?,?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.setString   (2, date);
                stmt.setString(3, pseudo);
                stmt.setString(4, String.valueOf(addr));
                stmt.setDouble(5, port);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        public boolean insertIpseudo(String pseudo, String addr, String filename) {
            String sql = "INSERT INTO IPseudo( pseudo, addr) VALUES(?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pseudo);
                stmt.setString(2,  String.valueOf(addr));
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
            return false;
        }

        public boolean insertConnected(String pseudo, String filename) {
            String sql = "INSERT INTO Connected( pseudo) VALUES(?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pseudo);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
            return false;
        }


        public boolean deleteConnected (String pseudo, String filename) {
            String sql = "DELETE FROM Connected WHERE pseudo = ?";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                stmt.setString(1, pseudo);
                // execute the delete statement
                stmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean selectAllMsgHistory(String filename){
            String sql = "SELECT message, date, pseudo, addr, port FROM history";

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
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean selectAllMsgIPseudo(String filename){
            String sql = "SELECT pseudo, addr FROM IPseudo";
            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("pseudo")+  "\t" +
                                    rs.getString("addr"));

                }
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean selectAllConnected(String filename){
            String sql = "SELECT pseudo FROM Connected";
            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("pseudo"));

                }
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        public boolean getPseudo (String addr, String filename) throws SQLException {
            String sql = "SELECT  pseudo,addr FROM IPseudo WHERE addr= ?";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt  = conn.prepareStatement(sql)){
                stmt.setString(1,addr);
                ResultSet rs    = stmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("pseudo") +  "\t" +
                                    rs.getString("addr"));

                }
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean check(String pseudo, String filename) {
            String sql = "SELECT EXISTS(SELECT * FROM IPseudo WHERE pseudo= ?);";
            System.out.println("oooo");
            createDB DB = new createDB("MyTestDB.db");
            DB.selectAllMsgIPseudo("MyTestDB.db");
            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)){
                 stmt.setString(1,pseudo);
                 ResultSet rs = stmt.executeQuery() ;
                System.out.println("here");
                // loop through the result set
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    System.out.println(found);
                    System.out.println("oooo");
                   return found;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }

        public boolean check1(String pseudo, String filename) throws SQLException {
            String sql = "SELECT EXISTS(SELECT 1 FROM IPseudo WHERE pseudo=" + pseudo + ");";
            Boolean exist = false;
            try (Connection conn = this.connect(filename);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                if (rs.next()) {

                    boolean found = rs.getBoolean(1);
                    System.out.println(found);
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

        public boolean changeIpseudo(String pseudo, String addr, String filename) {
            String sql = "UPDATE IPseudo SET pseudo=? WHERE addr = ?;";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pseudo);
                stmt.setString(2, String.valueOf(addr));
                stmt.executeUpdate();
                return true;
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



        }
    }

