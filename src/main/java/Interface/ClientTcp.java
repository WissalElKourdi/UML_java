package Interface;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class ClientTcp {

     private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public BufferedWriter bW;


    public ClientTcp(Socket socket) {

        try{
            System.out.println(" creating Client ... ");
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //System.out.println(" creating Client ... ");
        }catch(IOException e){
            System.out.println("Error creating Client!");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessageToServer(String messageToServer, Socket socket) {
        try {

            bW.write(messageToServer);
            bW.newLine();
            bW.flush();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Server!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    public static void sock_acc(ServerSocket srvSock){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    srvSock.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void receiveMessageFromServer(VBox vbox_messages, Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        String messageFromServer = bufferedReader.readLine();
                        SessionChatController.addLabel(messageFromServer, vbox_messages);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Server!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    } /*  new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                String MessageFromServer;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    MessageFromServer = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (MessageFromServer != null) {

                    try {
                        MessageFromServer = in.readLine();

                        SessionChatController.addLabel(MessageFromServer, vbox_messages);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Server!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();*/



    public void rcv(Socket socket, VBox vBoxMessages, ClientTcp client) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                client.receiveMessageFromServer(vBoxMessages, socket);


            }
        }).start();
    }

    public void send(Socket socket, String msg, ClientTcp client) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                client.sendMessageToServer(msg, socket);


            }
        }).start();
    }
}