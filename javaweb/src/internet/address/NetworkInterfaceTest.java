package internet.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Apollo4634
 * @create 2019/06/13
 */

public class NetworkInterfaceTest {

    private static void getByNameTest() {
        String netInterface = "eth1";
        //String netInterface = "CE31";
        //String netInterface = "ELX100";

        try {
            NetworkInterface ni = NetworkInterface.getByName(netInterface);
            if (ni == null) {
                System.err.println("No such interface: "+netInterface);
            } else {
                System.out.println(ni);
            }
        } catch (SocketException ex) {
            System.err.println("SocketException: "+netInterface);
        }
    }


    private static void getByInetAddressTest() {
        String ip = "127.0.0.1"; //本地回送地址名 lo

        try {
            InetAddress ia = InetAddress.getByName(ip);
            NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
            if (ni == null) {
                System.err.println("No such interface: "+ip);
            } else {
                System.out.println(ni);
            }
        } catch (SocketException ex) {
            System.err.println("SocketException");
        } catch (UnknownHostException ex) {
            System.err.println("UnknownHostException: ");
        }
    }


    public static void main(String[] args) {
        getByNameTest();
        getByInetAddressTest();
    }
}
