package socket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Apollo4634
 * @create 2019/06/30
 */

public class CloseSocket {
    public static void main(String[] args) {
        Socket s = new Socket();
        try {
            s.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
