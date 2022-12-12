import java.io.IOException;
import java.util.ArrayList;
import java.util.AbstractCollection;


public class test_UDP {
    public static void main (String[] args) throws IOException, InterruptedException {
        //tests UDP
        new UDP_Client().start();
        new UDP_Server().broadcast("YOO");
      //  Thread.sleep(3000);
        // new UDP_Server().broadcast("end");

        //création liste : créer liste commune où on va ajouter tous les noms
        //à faire la prochaine fois


        //tests changepseudo
        //à faire : changer arguments tests
        /*
        new UDP_Server().broadcast_connection("leo");
        new UDP_Server().broadcast_connection("wiss");
        new UDP_Server().broadcast_connection("leo");
         */
        //System.out.println(ArrayList.toString(new List().Users_connected));


    }
}