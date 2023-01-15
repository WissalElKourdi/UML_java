package communication;

import Database.createDB;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;


public class TCP_Client extends Thread {
    public static void goClient(String MSG, int port) throws SQLException {
        final Socket[] clientSocket = new Socket[1];
        String DB_NAME = "DB_MSG.db";
        createDB DB = new createDB(DB_NAME);
        System.out.println("MSG histpry" +DB.selectAllMsgHistory(DB_NAME));
        port = 5000;
        int finalPort = port;
        Thread envoi= new Thread(new Runnable() {
       public void run(){
        try {


            clientSocket[0] = new Socket("localhost", finalPort);
            TCP_Server.SenderThread(clientSocket[0], MSG);
            TCP_Server.launchReceiverThread(clientSocket[0]);

            DB.insertHistory(MSG, "10/12/22","sissou",clientSocket[0].getLocalSocketAddress().toString(), clientSocket[0].getPort(), DB_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       }}); envoi.start();
    }}
