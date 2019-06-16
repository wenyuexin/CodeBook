package uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Apollo4634
 * @create 2019/06/15
 */

public class URLSplitting {
    public static void main(String[] args) {
        String str = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URL.html#%3Cinit%3E(java.lang.String)";

        try {
            URL url = new URL(str);

            System.out.println("toString: "+url.toString()); //public String toString() { return toExternalForm(); }
            System.out.println("toExternalForm: "+url.toExternalForm());

            System.out.println("Protocol: "+url.getProtocol());

            System.out.println("Authority: "+url.getAuthority());
            System.out.println("UserInfo: "+url.getUserInfo());
            System.out.println("Host: "+url.getHost());
            System.out.println("DefaultPort: "+url.getDefaultPort());
            System.out.println("Port: "+url.getPort());

            System.out.println("File: "+url.getFile());
            System.out.println("Path: "+url.getPath());
            System.out.println("Query: "+url.getQuery());
            System.out.println("Fragment: "+url.getRef());

        } catch (MalformedURLException ex) {
            System.err.println(ex.toString());
        }
    }
}
