package uri.communication;

import java.net.*;
import java.io.*;

/**
 * @author Apollo4634
 * @create 2019/06/19
 */

public class WithSeverSide {
    public static void main(String[] args) {
        //QueryString qs =  new QueryString();
        //qs.add("q", "java");

        String urlStr = "https://www.baidu.com/s?wd=java&rsv_spt=1&rsv_iqid=0xeed6029200000e26&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=8&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&inputT=3623&rsv_sug4=16024";
        //String urlStr = "https://www.google.com/search?q=java";
        //String urlStr = "https://www.baidu.com/s?wd=java";

        try {
            URL url = new URL(urlStr);

            try (BufferedInputStream in = new BufferedInputStream(url.openStream())) {
                InputStreamReader reader = new InputStreamReader(in);

                int c;
                while ((c=in.read()) != -1) {
                    System.out.print((char) c);
                }

            } catch (IOException ex) {
                System.out.println(ex.toString());
            }

        } catch (MalformedURLException ex) {
            System.out.println(ex.toString());
        }
    }
}
