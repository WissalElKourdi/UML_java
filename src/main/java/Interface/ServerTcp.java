package Interface;

import javafx.scene.layout.VBox;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTcp extends Thread {

    public Socket socket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public ArrayList<ServerTcp> sessionsList;
    public ServerTcp(Socket socket, ArrayList<ServerTcp> sessionsList ) {

        this.sessionsList = sessionsList;
        this.socket = socket;
    }



    /* this function takes the message and the client in argument and sends him the message*/
    public void sendMessageToClient(String messageToClient, Socket socket){
        try{
            BufferedWriter bW = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bW.write(messageToClient);
            bW.newLine();
            bW.flush();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vBox,Socket socket){
        new Thread(new Runnable() {
            @Override
            public void run() {

             //   BufferedReader in = null;
                try {
                    BufferedReader  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String messageFromClient = null;

                    messageFromClient = in.readLine();

                while(messageFromClient!=null){
                    try{    messageFromClient = in.readLine();
                        SessionChatController.addLabel(messageFromClient, vBox);


                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Client!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }}  } catch (IOException e) {
                throw new RuntimeException(e);
            }

            }
        }).start();
    }

    public static void sock_acc(ServerSocket srvsocket){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  Socket socket1= srvsocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

/* thread pour recevoir */
    public void rcv(Socket socket,VBox vBoxMessages, ServerTcp server){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket_accept =  MenuController.Srvsocket.accept();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    server.receiveMessageFromClient(vBoxMessages, socket);
                    sessionsList.add(server);

                    // ArrayList<ClientTcp> sessionsList = new ArrayList<>();
                    //    sessionsList.add(client);
                    System.out.println("Connected to Server");
                }
            }}).start();
    }


    /*  thread pour envoyer */
    public void send(ServerSocket srvsocket,String msg,ServerTcp server){
        Socket socket;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.sendMessageToClient(msg, srvsocket.accept());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }}).start();
    }

/* this function closes all the buffers */
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}