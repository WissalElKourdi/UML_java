package UDP;

import Database.createDB;

import java.io.IOException;
import java.net.*;
import java.lang.*;
import java.sql.SQLException;

public class UDP_Client extends Thread {
    static DatagramSocket socket = null;
    private final String Name_DB = "DB_MSG.db";
    private  boolean running;

    private createDB DB;
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
            try {
                UDPManager.update(msg_rcv,DB,packet,socket);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(msg_rcv.equals("end")) {
                    running = false;
            }  }
        socket.close();
        System.out.println("socket closed port : " + socket.getPort());
    }
}
            /*
        DatagramSocket socket ;
        boolean running;
        running = true;

        socket = new DatagramSocket(UDP_Server.port);

                System.out.println("Ready to receive broadcast packets!");
                //Receive a packet
              //

                InetAddress address = packet.getAddress();

             /*
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                //String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("received");
              //  Thread sendMessage = new Thread(new Runnable());
                        //Packet received
              */

               // while (!msg_rcv.equals("end")){
               //     System.out.println("I am waiting");
               // wait(10);
               // }

                /*
                handler1(msg_rcv,DB,packet);
                System.out.println("je suis sortie");
                Thread.sleep(90000);
                handler2(msg_rcv,DB,packet);
                Thread.sleep(90000);
                handler3(msg_rcv,DB,packet);
                Thread.sleep(90000);
                handler4(msg_rcv,DB,packet);
                Thread.sleep(90000);

                if (msg_rcv.equals("end")){
                    running = false;
                    System.out.println("Socket closed");
                }
            }
//Thread.yield();
           // socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
                 */

