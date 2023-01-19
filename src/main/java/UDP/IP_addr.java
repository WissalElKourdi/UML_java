package UDP;

import java.net.*;
import java.util.Enumeration;

public class IP_addr {
    public static InetAddress MonIP;

    public static InetAddress get_my_IP()   {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress()) {
                        MonIP = InetAddress.getByName(interfaceAddress.getAddress().getHostAddress());
                    }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return MonIP;
    }
}