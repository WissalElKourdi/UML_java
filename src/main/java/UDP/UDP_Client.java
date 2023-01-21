package UDP;

import Database.createDB;
import USERS.List_Connected;
import Interface.MenuController;
import javafx.application.Platform;
import javafx.scene.control.Menu;

import java.io.IOException;
import java.net.*;
import java.lang.*;
import java.sql.SQLException;

public class UDP_Client extends Thread {
    static DatagramSocket socket = null;
    private final String Name_DB = "DB_MSG.db";
    private boolean running;
    private MenuController menu;
    private createDB DB;


    public UDP_Client(int port) throws SocketException, SQLException {
        socket = new DatagramSocket(port);
        // if this fails (SocketException), the exception is non-recoverable and is propagated
        System.out.println("Creating Socket");
        DB = new createDB(Name_DB);

    }

    public UDP_Client() {

    }

    public void run() {
        byte[] buffer = new byte[1024];
        running = true;

        while (running) {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String msg_rcv = new String(packet.getData(), 0, packet.getLength());
            msg_rcv = msg_rcv.trim();
            System.out.println("msg received :" + msg_rcv);
            String pseudo = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1).trim();
            //String finalMsg_rcv = msg_rcv;
            String finalMsg_rcv = msg_rcv;

                    // if(finalMsg_rcv.startsWith("Connected :") ){
                    //List_Connected.add_co("abcde");
                    String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
                    IP_addr monIP = new IP_addr();
                    String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();

                    if (!addr.equals(mine) && !List_Connected.exists(pseudo) && !(finalMsg_rcv.startsWith("MY INFOS :")) && !(finalMsg_rcv.startsWith("MY INFOS CHANGE :"))) {
                        if (!pseudo.equals("")) {
                          /*  if (finalMsg_rcv.startsWith("MY INFOS CHANGE :")) {
                                String pseudo1 = pseudo.substring(0, pseudo.indexOf("/"));
                                List_Connected.add_co(pseudo1);
                                System.out.println("je addd " + pseudo);
                                List_Connected.print_co();

                            }else*/

                                List_Connected.add_co(pseudo);
                                System.out.println("je addd " + pseudo);
                                //menu.update_list();

                        }}
                        if (finalMsg_rcv.startsWith( "MY old pseudo :")) {
                            List_Connected.delete_co(pseudo);
                            System.out.println("je supp " + pseudo);
                            List_Connected.print_co();


                        }  Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if ( menu != null){
                        System.out.println("48H JAVA sasn fermer l'oeilv S'en SOUVIENDRA ");
                        List_Connected.print_co();
                        menu.update_list();
                    }  }});



            try {
                UDPManager manager = new UDPManager();
                System.out.println("UPDATE  MANAGER");
                manager.update(msg_rcv, DB, packet, socket);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            if (msg_rcv.equals("end")) {
                running = false;
            }
        }
        socket.close();
        System.out.println("socket closed port : " + socket.getPort());
    }
    public void setMenu(MenuController Menu){
        this.menu = Menu;
    }

}