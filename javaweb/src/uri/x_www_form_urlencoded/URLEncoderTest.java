package uri.x_www_form_urlencoded;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Apollo4634
 * @create 2019/06/18
 */

public class URLEncoderTest {
    private static void encode(String s) {
        String encodedStr = URLEncoder.encode(s, StandardCharsets.UTF_8);
        System.out.println(encodedStr);
    }


    public static void main(String[] args) {
        System.out.println();
        encode("https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/net/URLEncoder.html#encode(java.lang.String)");
        encode("x_www_form_urlencoded");
        encode("I/O");
        encode("-_.!~*'");
        encode("~-@-#-$-%-^-&-(-)-=-+-;-:-'-\"-,-<->-/-?- ");
    }
}
