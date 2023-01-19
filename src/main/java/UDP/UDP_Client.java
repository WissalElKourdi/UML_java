package UDP;

import Database.createDB;
import USERS.List_Connected;
import Interface.MenuController;
import javafx.application.Platform;

import java.io.IOException;
import java.net.*;
import java.lang.*;
import java.sql.SQLException;

public class UDP_Client extends Thread {
    static DatagramSocket socket = null;
    private final String Name_DB = "DB_MSG.db";
    private  boolean running;

    private createDB DB;
    private MenuController menu;
    List_Connected co = new List_Connected();


    public UDP_Client(int port) throws SocketException, SQLException {
        socket = new DatagramSocket(port);
        // if this fails (SocketException), the exception is non-recoverable and is propagated
        System.out.println("Creating Socket");
        DB = new createDB(Name_DB);

    }

    public void run (){
        byte[] buffer = new byte[1024];
       running = true;

        while (running){

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String msg_rcv = new String (packet.getData(), 0, packet.getLength());
            msg_rcv = msg_rcv.trim();
            System.out.println("msg received :" +msg_rcv);
                String pseudo = msg_rcv.substring(msg_rcv.lastIndexOf(':') + 1).trim();
                //String finalMsg_rcv = msg_rcv;
            String finalMsg_rcv = msg_rcv;
            Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           // if(finalMsg_rcv.startsWith("Connected :") ){
                           // List_Connected.add_co(pseudo);
                            String addr = packet.getAddress().toString().substring(packet.getAddress().toString().indexOf("/") + 1);
                            IP_addr monIP = new IP_addr();
                            String mine = monIP.get_my_IP().toString().substring(monIP.get_my_IP().toString().indexOf("/") + 1).trim();

                            if (!addr.equals(mine) && !List_Connected.exists(pseudo) && !(finalMsg_rcv.startsWith("MY INFOS :"))){
                                List_Connected.add_co(pseudo);
                                 }
                             if ( menu != null){

                                 System.out.println("48H JAVA sasn fermer l'oeilv S'en SOUVIENDRA ");
                            menu.update_list();}
                        }
                    });

            try {
                UDPManager.update(msg_rcv,DB,packet,socket);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

            if(msg_rcv.equals("end")) {
                    running = false;
            }  }
        socket.close();
        System.out.println("socket closed port : " + socket.getPort());
    }

    public void setMenu(MenuController Menu){
        this.menu = Menu;
    }
}
