package UDP;

import java.net.*;
import java.util.Enumeration;

public class IP_addr {
    private InetAddress MonIP;

    public static InetAddress get_my_IP()   {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress()) {
                        this.MonIP = InetAddress.getByName(interfaceAddress.getAddress().getHostAddress());
                    }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return MonIP;
    }
}