package communication;

import Database.createDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.net.Socket;
        import java.time.LocalTime;
        import java.util.Scanner;


public class TCP_Client {

    public static void main(String msg, String pseudo) {
        String name_db ="DB_MSG.db";
        final Socket clientSocket;
        try {
            createDB DB = new createDB(name_db);
            LocalTime time = LocalTime.now();

            clientSocket = new Socket("localhost", 0);
            TCP_Server.SenderThread(clientSocket, msg);
            TCP_Server.launchReceiverThread(clientSocket);
            DB.insertHistory(msg,time.toString(),pseudo,clientSocket.getLocalAddress().toString(),clientSocket.getPort(),name_db);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}