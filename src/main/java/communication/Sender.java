package communication;

import Database.createDB;
import UDP.IP_addr;

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
         start();
    }
    public void run(){
        createDB DB = null;
        try {
            DB = new createDB(DB_NAME);
        } catch (SQLException e) {
            System.out.println("erreur création BD");
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.write(message);
        } catch (IOException e) {
            System.out.println("erreur écriture mssg dans les buffers");
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
        System.out.println("message sent  :  "+message+" to user "+pseudo);
        DateTimeFormatter time= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            String addr = socket.getInetAddress().toString().substring(socket.getInetAddress().toString().indexOf("/") + 1).trim();
            DB.insertMSG(message, time.toString(),DB.getMonPseudo(DB_NAME), addr, socket.getPort(),"sender", DB_NAME);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
