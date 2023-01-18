package communication;

import Database.createDB;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import static communication.Sender.DB_NAME;

public class Sender extends Thread{
    static final String DB_NAME = null;
    private Socket socket;
    private String pseudo;
    private String message;
    private BufferedWriter bufferedWriter;

    public Sender (Socket socket, String pseudo, String message) throws IOException {
        this.socket=socket;
        this.pseudo=pseudo;
        this.message=message;
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }
    public void run(){
        createDB DB = null;
        try {
            DB = new createDB(DB_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("message send to user ");
        DateTimeFormatter time= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DB.insertHistory(message, time.toString(), "sissou", socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
        //MenuControlleur
    }

}
