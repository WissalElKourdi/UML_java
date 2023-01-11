package communication;

import Database.InsertData;
import Database.Select_Data_DB;
import Database.createDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class TCP_Server {

    public static void launchReceiverThread(Socket socket) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread recevoir= new Thread(new Runnable() {
                String Message ;
                @Override
                public void run() {

                    try {
                        Message = in.readLine();
                        //tant que le client est connecté
                        while(Message!=null){
                            System.out.println("Client : "+Message);
                            Message = in.readLine();
                        }
                        //sortir de la boucle si le client a déconecté
                        System.out.println("Client déconnecté");
                        //fermer le flux et la session socket
                        socket.close();
                        //socketserver.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } );
            recevoir.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void SenderThread(Socket socket){
        Scanner sc=new Scanner(System.in);
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        Thread envoi= new Thread(new Runnable() {// la création des 2 threads a pour but de permettre l'envoi et la réception simultanément
            String Message;
            public void run() {
                while (true) { //teste la connexion
                    Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
                    LocalTime time = LocalTime.now();
                    //db
                    String DB_NAME = "DB_MSG.db";
                    createDB app = new createDB(DB_NAME);
                    app.insertHistory(Message, time.toString(),"sissou",socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);

                    out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
                    out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin
                }
            }
        });
        envoi.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
   public static void main(String[] args) throws IOException {
        int port = 50000;
        try {
            ServerSocket socketserver = new ServerSocket(port);
            while (true) {
                System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
                Socket clientSocket = socketserver.accept();
                System.out.println("Connecté");
                TCP_Server.SenderThread(clientSocket);
                TCP_Server.launchReceiverThread(clientSocket);
            }
        }catch(IOException e){
                e.printStackTrace();
            }


    }
}