package communication;

import Database.createDB;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;

public class TCP_Server extends Thread {
    private Socket socket;
/*
    public  TCP_Server(Socket socket){
        this.socket=socket;
    }*/

      //fonction qui permet de recevoir les messages
    public static void receivemessage(String Message,Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

    //fonction qui permet d'envoyer les messages
    public static void sendMessage(String Message,Socket socket) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
        LocalTime time = LocalTime.now();
        out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
        out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin
    }
 // un thread qui tourne constamment qui permet de recevoir les messages à tout moment
    public static void launchReceiverThread(Socket socket) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread recevoir = new Thread(new Runnable() {
                String Message;

                @Override
                public void run() {
                    try {
                        receivemessage(Message,socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }} );
            recevoir.start();
            } catch(IOException e){
                System.err.println(e.getMessage());
            }
        }

        // Un thread qui permet d'envoyer les messages en boucle
        public static void SenderThread (Socket socket, String Message) throws SQLException {
            //Scanner sc = new Scanner(System.in);
            String DB_NAME = "DB_MSG.db";
        //    createDB DB = new createDB(DB_NAME);
            // PrintWriter out = new PrintWriter(socket.getOutputStream());
            Thread envoi = new Thread(new Runnable() {// la création des 2 threads a pour but de permettre l'envoi et la réception simultanément
                String Message;

                public void run() {

                    while (true) { //teste la connexion
                        try {
                            sendMessage(Message,socket);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            envoi.start();
        }
        public static void servtcp () throws IOException {
            new Thread(new Runnable() {
                public void run() {
            int port = 50000;

                    ServerSocket socketserver = null;
                    try {
                        socketserver = new ServerSocket(port);

                    while (true) {
                    System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
                    Socket clientSocket = socketserver.accept();
                    System.out.println("Connecté");
                    //TCP_Server.SenderThread(clientSocket);
                    TCP_Server.launchReceiverThread(clientSocket);
                } } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }}).start();
}}

//Message = ChatSessionController.message
//Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
// LocalTime time = LocalTime.now();
//db

//  DB.insertHistory(Message, time.toString(), "sissou", socket.getLocalSocketAddress().toString(), socket.getPort(), DB_NAME);
//System.out.println("MSG histpry" +DB.selectAllMsgHistory(DB_NAME));
// out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
//out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin

