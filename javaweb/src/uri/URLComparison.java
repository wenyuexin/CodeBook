package uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * equals: Two URL objects are equal if they have the same protocol,
 * reference equivalent hosts, have the same port number on the host,
 * and the same file and fragment of the file.
 *
 * equals: Compares two URLs, including the fragment component.
 * sameFile: Compares two URLs, excluding the fragment component.
 *
 * @author Apollo4634
 * @create 2019/06/15
 */

public class URLComparison {
    public static void main(String[] args) {
        String str1 = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URL.html#%3Cinit%3E(java.lang.String)";
        String str2 = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URL.html#equals(java.lang.Object)";

        try {
            URL u1 = new URL(str1);
            URL u2 = new URL(str2);

            System.out.println(u1.equals(u2));
            System.out.println(u1.sameFile(u2));

        } catch (MalformedURLException ex) {
            System.err.println(ex.toString());
        }
    }
}
