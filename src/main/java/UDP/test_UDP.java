/*/*package UDP;

import UDP.UDP_Server;

import java.io.IOException;
import java.util.ArrayList;

//faire jenkins + créer branches pour travailler mieux
public class test_UDP {
    public static void main (String[] args) throws IOException, InterruptedException {
        //tests UDP
        //new UDP_Client().start();
        //new UDP_Server().broadcast("YOO");
        //Thread.sleep(3000);
        //new UDP_Server().broadcast("end");

        new UDP_Server().broadcast("j'adore java", "sirine");


        //création liste : créer liste commune où on va ajouter tous les noms
        ArrayList<String> userList = new ArrayList<String>();

        //tests changepseudo
        /*
        new UDP_Server().broadcast_connection("leo",userList);
        new UDP_Server().broadcast_connection("wiss",userList);
        new UDP_Server().broadcast_connection("leo",userList);
        new UDP_Server().broadcast_deconnection("leo", userList);
        new UDP_Server().broadcast_connection("leo",userList);
        new UDP_Server().broadcast_ChangePseudo("wiss","sissou",userList);
        */


        //System.out.println(ArrayList.toString(new List().Users_connected));


