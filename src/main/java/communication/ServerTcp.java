package communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



//on suppose cette classe est la classe qui envoie les messages
public class ServerTcp extends Thread {

    //private  ServerSocket servsocket;
    //private int port;


    private Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private ArrayList<ServerTcp> sessionsList;

    private InputStream inputStream; // to read the input data
    private OutputStream outputStream; // read the output data

    //private User userDist;
    //private User myUser = UserController.getMyUser();




    // Constructeur

    public Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket= this.socket;
    }

    public InputStream getInputStream (){
        return inputStream;
    }
    public void setInputStream(){
        this.inputStream=inputStream;
    }

    public OutputStream getOutputStream(){
        return outputStream;
    }

    public void setOutputStream(){
        this.outputStream=outputStream;
    }







    public ServerTcp(Socket socket, ArrayList<ServerTcp> sessionsList )  {

        this.sessionsList = sessionsList;
        this.socket = socket;

    }


    public void sendMessageToClient(String messageToClient, Socket socket){
        setSocket(socket);
        try{
            BufferedWriter bW = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bW.write(messageToClient);
            bW.newLine();
            bW.flush();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            //closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    public void send(ServerSocket srvsocket,String msg,ServerTcp server){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=srvsocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                server.sendMessageToClient(msg, socket);
            }}).start();
    }









    public static void receiveMessageFromClient(/*VBox vBox,*/ Socket socket){
        new Thread(new Runnable() {
            @Override
            public void run() {

                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String messageFromClient = null;
                try {
                    messageFromClient = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                /*while(messageFromClient!=null){
                    try{    messageFromClient = in.readLine();
                        SessionChatController.addLabel(messageFromClient, vBox);


                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Client!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }}*/

            }
        }).start();
    }

    public static void rcv(Socket socket /*, VBox vBoxMessages*/){

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    //Socket socket_accept =  MenuController.Srvsocket.accept();
                    receiveMessageFromClient(/*vBoxMessages, */socket);

                    // ArrayList<ClientTcp> sessionsList = new ArrayList<>();
                    //    sessionsList.add(client);
                    System.out.println("Connected to Server");
                }
            }}).start();
    }


    private static void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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