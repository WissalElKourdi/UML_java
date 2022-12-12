import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

import static java.lang.System.out;

public class TCP_Server {
    private final ServerSocket socketserver;
    //Socket clientSocket ;
    public TCP_Server(int port) throws IOException {
        socketserver= new ServerSocket(port);
        out.println("Serveur est à l'écoute du port "+socketserver.getLocalPort());

    }/*
    public void envoi() throws IOException {
        final PrintWriter out; // Envoyer le texte saisi
        final Scanner sc=new Scanner(System.in); //lire les entrées du clavier
        out = new PrintWriter(clientSocket.getOutputStream());
        Thread envoi= new Thread(new Runnable() {// la création des 2 threads a pour but de permettre l'envoi et la réception simultanément
            String Message;
            public void run() {
                while (true) { //teste la connexion
                    Message = sc.nextLine();//stocke le texte. Cette méthode au scanner créé
                    LocalTime time = LocalTime.now();
                    new History().Add_Message_History(Message+time);
                    out.println(Message + " " + time); // renvoyer le message ( à changer si on va créer une classe display)
                    out.flush(); // flush les buffers pour ne pas envoyer un null au client à la fin
                }
            }
        });
        envoi.start();

    }
    public void recevoir() throws IOException {
        final BufferedReader in;//Lire le texte reçu à partir de l'émetteur
        in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
        Thread recevoir= new Thread(new Runnable() {
            String Message ;
            @Override
            public void run() {
                try {
                    Message = in.readLine();
                    //tant que le client est connecté
                    while(Message!=null){
                        out.println("Client : "+Message);
                        Message = in.readLine();
                    }
                    //sortir de la boucle si le client a déconecté
                    out.println("Client déconecté");
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
    }*/

    public static void main(String[] args) throws IOException {
        try {
            TCP_Server serv= new TCP_Server(50000);
            while (true){
                //blocks until connection occurs
                Socket clientSocket= serv.socketserver.accept();
                TCP_Client client = new TCP_Client(clientSocket);
                out.println("Connecté");
                try {
                    client.recevoir();
                    client.envoi();
        }catch (IOException e) {
                    e.printStackTrace();
                }
        }
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
     /* finally {
         serv.so
        }*/
    }

    }
