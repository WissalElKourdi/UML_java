package communication;

import Database.createDB;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// this class will be used to start a session
//setting the ouput/input readers etc..
public class Launch_receive extends Thread  {

    static final String DB_NAME ="DB_MSG.db" ;
    private Socket socket;
    private String pseudo;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public static List<Launch_receive> sessions = new ArrayList<>();

    public Launch_receive(Socket socket, String pseudo){
        try{
            this.socket=socket;
            this.pseudo=pseudo;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // receiving thread
    public void run (){
        while(socket.isConnected()){
            try {
                createDB DB = new createDB(DB_NAME);
                String message = bufferedReader.readLine();
                System.out.println(pseudo + " sent me :  " + message);
                LocalTime time = LocalTime.now();
                DB.insertHistory(message, time.toString(), pseudo, socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
                //MenuController.c
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void closeEverything (Socket socket,BufferedReader bufferedReader) throws IOException {
        if(bufferedReader!=null){
            bufferedReader.close();
        }
        if(socket!=null){
            socket.close();
        }
    }



}
