package communication;

import Database.createDB;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;


public class TCP_Client extends Thread {
    public static void goClient(String MSG, int port) throws SQLException {
       
        String DB_NAME = "DB_MSG.db";
        createDB DB = new createDB(DB_NAME);
        System.out.println("MSG histpry" +DB.selectAllMsgHistory(DB_NAME));

        Thread envoi = new Thread(new Runnable() {
            public void run() {


                    Socket clientSocket;
                    //  TCP_Server.SenderThread(clientSocket, MSG);

                    //  DB.insertHistory(MSG, "10/12/22", "sissou", clientSocket.getLocalSocketAddress().toString(), clientSocket[0].getPort(), DB_NAME);


            }
        }); envoi.start();
    }}
