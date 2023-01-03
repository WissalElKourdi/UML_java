package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class TCP_Server {
    public static void main(String[] args) throws IOException{
        int port = 50000;
        final ServerSocket socketserver ; // final indique que l'élément ne peut pas être changé dans la suite
        final Socket clientSocket ;
        final BufferedReader in;//Lire le texte reçu à partir de l'émetteur
        final PrintWriter out; // Envoyer le texte saisi
        final Scanner sc=new Scanner(System.in); //lire les entrées du clavier

        try {
            socketserver= new ServerSocket(port);
            System.out.println("Serveur est à l'écoute du port "+socketserver.getLocalPort());
            clientSocket= socketserver.accept();
            System.out.println("Connecté");
            out = new PrintWriter(clientSocket.getOutputStream()); // get the output flow
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream())); //get the input flow
            Thread envoi= new Thread(new Runnable() {// la création des 2 threads a pour but de permettre l'envoi et la réception simultanément
                String Message;
                public void run() {
                    while (true) { //teste la connexion
                        /*if(in.read() == -1) {// true if client is disconnected
                            // Notify via terminal, close connection
                            System.out.println("client disconnected. Socket closing...");
                            socketserver.close();
                        }*/
                        Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
                        LocalTime time = LocalTime.now();
                        //new History().Add_Message_History(Message+time);
                        out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
                        out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin
                    }
                }
            });
            envoi.start();

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
                        out.close();
                        clientSocket.close();
                        socketserver.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}