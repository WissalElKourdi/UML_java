import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.lang.System.out;


public class TCP_Client {

    //private final Socket clientSocket;

/*
        try {
            clientSocket = new Socket("localhost",5000);
            out = new PrintWriter(clientSocket.getOutputStream()); //flux pour envoyer
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//flux pour recevoir
            Thread envoyer = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    while (true) {
                        msg = sc.nextLine();
                        LocalDateTime currentLocalDateTime = LocalDateTime.now();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String time = currentLocalDateTime.format(dateTimeFormatter);
                        out.println(msg + " " + time);
                        InsertData data = new InsertData();
                        int num = clientSocket.getPort();
                        data.insert(msg, time, num);

                        out.flush();
                    }
                }
            }
    }

 */
}
/*

    public TCP_Client (int port) throws IOException {
        clientSocket = new Socket("localhost", port);
    }
    public TCP_Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void envoi() throws IOException {

        final Scanner sc = new Scanner(System.in);//pour lire à partir du clavier
        final PrintWriter out;
        out = new PrintWriter(clientSocket.getOutputStream()); //flux pour envoyer
        Thread envoyer = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
                while(true){
                    msg = sc.nextLine();
                    LocalTime time = LocalTime.now();
                    out.println(msg + " "+time);
                    out.flush();

                }
            }
        });
        envoyer.start();
    }

    public void recevoir() throws IOException {
        final BufferedReader in;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Thread recevoir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while(msg!=null){
                        out.println("Serveur : "+msg);
                        msg = in.readLine();
                    }
                    out.println("Serveur déconecté");
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        recevoir.start();
    }
    public static void main(String[] args) {

        try {
            TCP_Client client = new TCP_Client(50000);
            client.envoi();
            client.recevoir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/