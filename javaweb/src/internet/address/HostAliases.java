package internet.address;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Apollo4634
 * @create 2019/06/13
 */

public class HostAliases {

    public static void main(String[] args) {
//        String host1 = "home.shadowsocks.ch";
//        String host2 = "portal.shadowsocks.com";

        String host1 = "www.ibiblio.org";
        String host2 = "helios.ibiblio.org";

        try {
            InetAddress address1 = InetAddress.getByName(host1);
            InetAddress address2 = InetAddress.getByName(host2);

            if (address1.equals(address2)) {
                System.out.println(host1+" is the same as "+host2);
            } else {
                System.out.println(host1+" is not the same as "+host2);
            }
        } catch (UnknownHostException ex) {
            System.out.println("UnknownHostException");
        }
    }
}
