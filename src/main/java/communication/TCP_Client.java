package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;


public class TCP_Client {

    public static void main(String[] args) {

        final Socket clientSocket;
        try {
            clientSocket = new Socket("localhost",50000);
            TCP_Server.SenderThread(clientSocket);
            TCP_Server.launchReceiverThread(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
