package communication;

import Database.createDB;
import Interface.MenuController;
import Interface.SessionChatController;

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
    //private view.SessionChatController sess;

    public static List<Launch_receive> sessions = new ArrayList<>();

    public Launch_receive(Socket socket, String pseudo){
        try{
            this.socket=socket;
            this.pseudo=pseudo;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // receiving thread
    public void run (){
        while(socket.isConnected()){
            try {
                createDB DB = new createDB(DB_NAME);
                System.out.println("coucouuuuuuuuuuuuuuuuuuuu");
                String message = bufferedReader.readLine();
                System.out.println(pseudo + " sent me :  " + message);
                LocalTime time = LocalTime.now();
                DB.insertHistory(message, time.toString(), pseudo, socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
                //Récupérer le message et le mettre dans la sessio
            } catch (IOException | SQLException e) {
                System.out.println("erreur receiving from client");
                throw new RuntimeException(e);
            }
        }
    }
    }

