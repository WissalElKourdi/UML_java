

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


        public static void main(String[] args) {

            createNewDatabase("MyDataBase.db");

        }
    }

