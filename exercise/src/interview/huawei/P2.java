package interview.huawei;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 有一种表格数据需要存储在文件中。表格中的每行,由若干个字段组成，每个
 * 字段可以是整数或字符串，字符串只包含数字、字母以及特殊字符 !@#$%^&*()",
 * 有一种存储格式 ,采用文本的方式对表格数据进行存储。文本文件中的每一行,
 * 代表了表格数据中的一行数据。具体格式描述如下:
 * 1、采用逗号分隔不同的字段(逗号前后无空格) ;
 * 2、数字直接采用10进制的文本存储;
 * 3、字符串的存储规则如下:
 *  1)如果字符串中包含逗号以及双引号，则字符串必须在头尾各增加一个双引号,
 *   且中间的双引号需要用连续两个双引号来表示。例如: "a,""b" 表示字符串a,"b
 *  2)如果字符串中未包含逗号以及双引号，则字符串不强制要求在头尾增加双引号，
 *   可直接存储。例如: abc ,或者"abc"都可以。
 * 4、空字符串不存储任何字符，例如:a,,b中，有3个字段，分别为:a,空字符串，b
 * 设计一个算法，用来将单行文本，解析成多个字段，并输出。
 *
 * a,,1,"b,""" 输出：
 * 4  （字段总数）
 * a
 * -- （空字符串）
 * 1
 * b,"
 *
 * @author Apollo4634
 * @create 2019/08/31
 */

public class P2 {

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();

//        String str = "a,,1,\"b,\"\"\"";
        String str = ",,a,12,\",,\"";
        str += ',';

        char[] chars = str.toCharArray();
        List<String> segments = new LinkedList<>();
        for (int i = 0; i < chars.length; ) {
            if (chars[i] == ',') {
                segments.add("--");
                i += 1;
            } else if (chars[i] != '"') {
                int idx = str.indexOf(',', i);
                String sub = str.substring(i, idx);
                if (sub.contains("\"")) {
                    System.out.println("ERROR"); return;
                }
                segments.add(sub);
                i = idx + 1;
            } else {
                int from = i + 1;
                while (true) {
                    int idx = str.indexOf("\",", from);
                    String sub = str.substring(i+1, idx);
                    if (quotationMarksIsPaired(sub)) {
                        segments.add(sub.replaceAll("\"\"", "\""));
                        i = idx + 2; break;
                    } else if (idx == str.length()-2) {
                        System.out.println("ERROR"); return;
                    }
                    from = idx + 2;
                }
            }
        }

        System.out.println(segments.size());
        for (String segment : segments) {
            System.out.println(segment);
        }
    }


    private static boolean quotationMarksIsPaired(String str) {
        if ("".equals(str) || !str.contains("\"")) return true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                if (i == str.length()-1) return false;
                if (str.charAt(i+1) != '"') return false;
                i += 1;
            }
        }
        return true;
    }
}
