package book_9787121310928;

/**
 * 判断是否为表示数值的字符串（整数、小数、科学计数法）
 *
 * @author Apollo4634
 * @create 2019/08/13
 */

public class Problem_20 {

    static class Solution {
        boolean isDigital(String str) {
            if (str == null || str.length() == 0) return false;
            char[] chars = str.toLowerCase().toCharArray();
            int eIdx = str.indexOf('e');
            int ch;
            if (eIdx >= 0) {
                String tailingStr = str.substring(eIdx+1);
                ch = tailingStr.charAt(0);
                int from = (ch == '+' || ch == '-')? 1 : 0;
                for (int i = from; i < tailingStr.length(); i++) {
                    ch = tailingStr.charAt(i) - '0';
                    if (ch < 0 || ch > 9) return false;
                }
            } else {
                eIdx = str.length();
            }

            boolean containsDot = false;
            ch = str.charAt(0) - '0';
            int from = (ch == '+' || ch == '-')? 1 : 0;
            for (int i = 0; i < eIdx; i++) {
                if (!containsDot && ch == ('.'-'0')) { containsDot = true; continue; }
                if (ch < 0 || ch > 9) return false;
            }
            return false;
        }
    }


    public static void main(String[] args) {
//        System.out.println(-.1);
//        System.out.println((int)'+');
//        System.out.println((int)'-');
//        System.out.println((int)'.');
//        System.out.println((int)'e');

        System.out.println(new Solution().isDigital("+100"));
        System.out.println(new Solution().isDigital("5E2"));
        System.out.println(new Solution().isDigital("5e2"));
        System.out.println(new Solution().isDigital("-123"));
        System.out.println(new Solution().isDigital("3.14"));
        System.out.println(new Solution().isDigital("-E-16"));
        System.out.println(new Solution().isDigital("12e"));
        System.out.println(new Solution().isDigital("1a3.14"));
        System.out.println(new Solution().isDigital("1.2.3"));
        System.out.println(new Solution().isDigital("+-5"));
        System.out.println(new Solution().isDigital("12e+5.4"));
    }
}
