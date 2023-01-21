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
    private Boolean running;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private SessionChatController sessionchat;


    public static List<Launch_receive> sessions = new ArrayList<>();

    public Launch_receive(Socket socket, String pseudo){
        try{
            this.socket=socket;
            this.pseudo=pseudo;
            this.running=true;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // receiving thread
    public void run (){
        setRunning(true);
        while(running){
            if(socket.isConnected()){
                    try {
                        createDB DB = new createDB(DB_NAME);
                        System.out.println("je suis ds le run du run de receiver");
                        //Récupérer le message et le mettre dans la sessio
                        String message = bufferedReader.readLine();
                        LocalTime time = LocalTime.now();
                        System.out.println(pseudo + " sent me :  " + message);
                        String addr = socket.getInetAddress().toString().substring(socket.getInetAddress().toString().indexOf("/") + 1).trim();
                        DB.insertMSG(message, time.toString(), pseudo, addr, socket.getPort(),"receiver", DB_NAME);
   //DB.insertMSGRcv(message, time.toString(), pseudo, addr, socket.getPort(), DB_NAME);
                        /*Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                               // MenuController.ListControllers.get(pseudo).update_chat();
                                if (MenuController.ListControllers.get(pseudo)!=null && message != null){
                              //  MenuController.ListControllers.get(pseudo).addMsg(message,true);}
                            }});*/
                  /*  if ( Sess != null){
                        System.out.println("48H JAVA sasn fermer l'oeilv S'en SOUVIENDRA ");
                        menu.update_list();}*/

                    } catch (IOException | SQLException e) {
                        System.out.println("erreur receiving from client");
                        throw new RuntimeException(e);
                    }


        }
        running = running();
        }

        /*try {

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    public void close_rcv() throws IOException {
        System.out.println("running talking false");
        this.socket.close();
        this.running=false;


    }

    public boolean running(){
        return this.running;
    }
public void setRunning(boolean running){
        this.running=running;
}


}

/*
                int id_sess = MenuController.listTabs.indexOf(pseudo);
                MenuController.get_onglet().getTabs().get(id_sess);
                SessionChatController.addLabel(message);*/