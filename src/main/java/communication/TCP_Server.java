package communication;

import Database.createDB;
import Interface.MenuController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;

public class TCP_Server extends Thread {


    public static void launchReceiverThread(Socket socket)  {

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

    public static void SenderThread(Socket socket, String Message) throws SQLException {
        Scanner sc=new Scanner(System.in);
        String DB_NAME = "DB_MSG.db";
        createDB DB = new createDB(DB_NAME);
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
        Thread envoi= new Thread(new Runnable() {// la création des 2 threads a pour but de permettre l'envoi et la réception simultanément
          //  String Message;
            public void run() {
                while (true) { //teste la connexion
                    //comment récupérer le message tapé dans le zone de test de chatcontroller
                    //pour ensuite l'envoyer ????
                    //Message = ChatSessionController.message
                  //  Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
                    LocalTime time = LocalTime.now();
                    //db

DB.insertHistory(Message, time.toString(),"sissou",socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
System.out.println("MSG histpry" +DB.selectAllMsgHistory(DB_NAME));
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
    public static void goThread( int port)  {
        try {
            ServerSocket socketserver = new ServerSocket(port);
            Thread envoi= new Thread(new Runnable() {
                    public void run(){
                 while (true) {
                     System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
                     Socket clientSocket = null;
                     try {
                         clientSocket = socketserver.accept();
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                     System.out.println("Connecté");
                    // TCP_Server.SenderThread(clientSocket);
                     TCP_Server.launchReceiverThread(clientSocket);
                 }}} );
            envoi.start();

        }catch(IOException e){
            e.printStackTrace();
        }
        }
    public static void goThreadsend( int port, String msg)  {
        try {
            ServerSocket socketserver = new ServerSocket(port);
            Thread envoi= new Thread(new Runnable() {
                public void run(){
                    while (true) {
                        System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
                        Socket clientSocket = null;
                        try {
                            clientSocket = socketserver.accept();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Connecté");
                        try {
                            TCP_Server.SenderThread(clientSocket, msg);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        TCP_Server.launchReceiverThread(clientSocket);
                    }}} );
            envoi.start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

        public void main(){}

    }
