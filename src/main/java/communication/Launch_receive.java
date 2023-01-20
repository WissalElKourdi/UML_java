package communication;

import Database.createDB;
import Interface.MenuController;
import Interface.SessionChatController;
import javafx.application.Platform;

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
    private SessionChatController sessionchat;


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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        createDB DB = new createDB(DB_NAME);
                        String message = bufferedReader.readLine();
                        System.out.println(pseudo + " sent me :  " + message);
                        int id_sess = MenuController.listTabs.indexOf(pseudo);
                      //  MenuController.get_onglet().getTabs().get(id_sess);

                        LocalTime time = LocalTime.now();

                        DB.insertMSGRcv(message, time.toString(), pseudo, socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
                        System.out.println("socket.getLocalSocketAddress().toString() : " + socket.getLocalSocketAddress().toString());


                        //Récupérer le message et le mettre dans la sessio

                        SessionChatController.sessionchat.updatercv_msg(message);

                  /*  if ( Sess != null){
                        System.out.println("48H JAVA sasn fermer l'oeilv S'en SOUVIENDRA ");
                        menu.update_list();}*/

                    } catch (IOException | SQLException e) {
                        System.out.println("erreur receiving from client");
                        throw new RuntimeException(e);
                    }

                }});



                }
        }

    public void setSession(SessionChatController Sessionchat){
        this.sessionchat = Sessionchat;
    }

}

/*
                int id_sess = MenuController.listTabs.indexOf(pseudo);
                MenuController.get_onglet().getTabs().get(id_sess);

                SessionChatController.addLabel(message);*/