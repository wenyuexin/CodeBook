package uri.x_www_form_urlencoded;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Apollo4634
 * @create 2019/06/18
 */

public class URLDecoderTest {
    private static String decode(String s, Charset set) {
        String str = URLDecoder.decode(s, set);
        System.out.println(str);
        return str;
    }

    private static String decode(String s) {
        return decode(s, StandardCharsets.UTF_8);
    }


    public static void main(String[] args) {
        System.out.println();
        String baidu = "https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&tn=baidu&wd=%E6%B5%8B%E8%AF%95&oq=java&rsv_pq=fe6f596a00288a54&rsv_t=7564cTBrzAzhRCqghHI%2BVwFeun7c4iLgYKUa2WXaJks7AmxBisxnSQi0%2FEE&rqlang=cn&rsv_enter=0&rsv_sug3=6&rsv_sug1=4&rsv_sug7=100&prefixsug=%25E6%25B5%258B%25E8%25AF%2595&rsp=0&inputT=5650&rsv_sug4=5651";
        String baidu2 = decode(baidu, StandardCharsets.UTF_8);
        decode(baidu2, StandardCharsets.UTF_8);

        decode(baidu, StandardCharsets.UTF_16);
        decode(baidu, StandardCharsets.UTF_16BE);
        decode(baidu, StandardCharsets.UTF_16LE);
        decode(baidu, StandardCharsets.ISO_8859_1);
        decode(baidu, StandardCharsets.US_ASCII);

        System.out.println();
        String google = "https://www.google.com/search?source=hp&ei=C9kIXbP1FZH8wQOB36roDQ&q=%E6%B5%8B%E8%AF%95&oq=&gs_l=psy-ab.1.0.35i39l6.0.0..2317...1.0..0.227.227.2-1......0......gws-wiz.....6.Ui_wWOhL92Y";
        decode(google, StandardCharsets.UTF_8);
    }
}
