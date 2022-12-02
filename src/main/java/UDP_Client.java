

import java.io.IOException;
import java.net.*;
import java.lang.*;



public class UDP_Client extends Thread {
    private static DatagramSocket socket ;
    private boolean running;


    public void rcv_udp() throws IOException {
        socket = new DatagramSocket(UDP_Server.port);
        System.out.println("Creating Socket");
    }

    public void run (){
        running = true;
        byte[] buffer = new byte[256];

        while (running) {
            System.out.println("Ready to receive broadcast packets!");
            //Receive a packet
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Packet received
            System.out.println("Discovery packet received from: " + packet.getAddress().getHostAddress());
            System.out.println("Packet received; data: " + new String(packet.getData()));
            String msg_rcv = new String (packet.getData(), 0, packet.getLength());

            if (msg_rcv.equals("end")){
                running = false;
                System.out.println("Socket closed");
                continue;
            }
        }
        socket.close();
    }

}





/*


    public static void main(String[] args) throws UnknownHostException, IOException {

       Socket link = new Socket ("localhost",4000);
       BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream())); //getting the input flows
       PrintWriter out = new PrintWriter(link.getOutputStream(),true);//getting the output flows
        out.println("data");
       String input = in.readLine();
       System.out.println(input);
       link.close();
    }
}
        rcv_udp(broadcastMSg, InetAddress.getByName("255.255.255.255"));
    }*/

/*
            //See if the packet holds the right command (message)
            String message = new String(packet.getData()).trim();
            if (message.equals(broadcastMSg)) {
                byte[] sendData = broadcastMSg.getBytes();

                //Send a response
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socketr.send(sendPacket);

                System.out.println( "Sent packet to: " + sendPacket.getAddress().getHostAddress());
            }
        }
    }
}


>>>>>>> 1de6bd1967f9e72fce94d3b92e53d8458d43646d
*/