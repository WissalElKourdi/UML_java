package UDP;

import Database.createDB;
import Interface.SessionChatController;
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

            String finalMsg_rcv = msg_rcv;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if ( menu != null){
                        String msg;
                        if (finalMsg_rcv.startsWith( "MY old pseudo :")) {
                            SessionChatController.close_tab_sess(pseudo);}
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