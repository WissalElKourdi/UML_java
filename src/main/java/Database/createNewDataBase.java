package Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.*;


    public class createNewDataBase {


        public static void createNewDatabase(String fileName) {

            String url = "jdbc:sqlite:sqlite/" + fileName;

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
        public static void creatTablehistory(String fileName) {
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

        public static void creatTablepseudo(String fileName) {
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

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }



        public void insertHistory(String message, String date, String pseudo, String addr, int port, String filename) {
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
                System.out.println(e.getMessage());
            }
        }
        public void insertIpseudo(String pseudo, String addr, String filename) {
            String sql = "INSERT INTO IPseudo( pseudo, addr) VALUES(?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pseudo);
                stmt.setString(2, String.valueOf(addr));
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
        }

        public void selectAllMsgHistory(String filename){
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

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public void selectAllMsgIPseudo(String filename){
            String sql = "SELECT pseudo, addr FROM IPseudo";
            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(
                            rs.getString("pseudo")+  "\t" +
                                    rs.getInt("addr"));

                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        public boolean check(String element, String filename){
            String sql = "SELECT EXISTS(SELECT 1 FROM IPseudo WHERE pseudo=" + element + ");" ;
            try (Connection conn = this.connect(filename);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    if (found) {
                       return false;
                    } else {
                       return true;
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
        //        Afinir change pseudo
        // finir udp
        // tester udp avec db
        // assert
        //
/*
        public void changeIpseudo(String pseudo, String addr, String filename) {
            String sql = " REPLACE INTO IPseudo(pseudo) VALUES(value_list); " +
                    "INSERT INTO IPseudo( pseudo, addr) VALUES(?,?)";

            try (Connection conn = this.connect(filename);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pseudo);
                stmt.setString(2, String.valueOf(addr));
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(
                        e.getMessage());
            }
        }
*/
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

            createNewDatabase("MyDataBase.db");

        }
    }

