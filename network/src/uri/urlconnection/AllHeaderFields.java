package uri.urlconnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Apollo4634
 * @create 2019/06/20
 */

public class AllHeaderFields {
    private static void getHeaders(String urlStr) {
        if (urlStr == null) return;

        try {
            URL url = new URL(urlStr);
            try {
                URLConnection uc = url.openConnection();
                System.out.println(uc.getHeaderField(0));

                for (int i = 1; ; i++) {
                    String headerKey = uc.getHeaderFieldKey(i);
                    if (headerKey == null) break;
                    String header = uc.getHeaderField(i);
                    System.out.println(headerKey+": "+header);

                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println();
        String urlStr = "https://www.baidu.com";
        getHeaders(urlStr);
    }
}
