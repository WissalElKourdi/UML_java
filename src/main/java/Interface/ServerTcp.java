package Interface;

import javafx.scene.layout.VBox;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class ServerTcp extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ServerTcp(ServerSocket serverSocket)  {

        System.out.println("je suis ici");
        new Thread(new Runnable() {
            private BufferedReader bufferedReader;
            private Socket socket;
            private BufferedWriter bufferedWriter;
            @Override
            public void run() {
        try{
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            System.out.println("Error creating Server!");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
        System.out.println("je suis la");
            }

    }).start();
    }

    public void sendMessageToClient(String messageToClient, Socket accept){
        try{
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public static void sendMessage(String Message,Socket socket) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
        LocalTime time = LocalTime.now();
        out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
        out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin
    }

    public void receiveMessageFromClient(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        String messageFromClient = bufferedReader.readLine();
                        ServerController.addLabel(messageFromClient, vBox);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Client!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

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