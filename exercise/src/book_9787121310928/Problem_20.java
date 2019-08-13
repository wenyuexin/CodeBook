package book_9787121310928;

/**
 * 判断是否为表示数值的字符串（整数、小数、科学计数法）
 *
 * @author Apollo4634
 * @create 2019/08/13
 */

public class Problem_20 {

    static class Solution {
        private static final int dot = '.' - '0';
        boolean isDigital(String str) {
            if (str == null || str.length() == 0) return false;
            String lowerStr = str.toLowerCase();
            int eIdx = lowerStr.indexOf('e');
            if (eIdx == str.length()-1) return false;
            char[] chars = lowerStr.toCharArray();
            if (eIdx >= 0 && eIdx+1 < str.length()) {
                int ch = chars[eIdx+1];
                int from = (ch == '+' || ch == '-')? eIdx+2 : eIdx+1;
                for (int i = from; i < str.length(); i++) {
                    ch = chars[i] - '0';
                    if (ch < 0 || ch > 9) return false;
                }
            } else {
                eIdx = str.length();
            }

            boolean containsDot = false;
            int from = (chars[0] == '+' || chars[0] == '-')? 1 : 0;
            for (int i = from; i < eIdx; i++) {
                int ch = chars[i] - '0';
                if (!containsDot && ch == dot) { containsDot = true; continue; }
                if (ch < 0 || ch > 9) return false;
            }
            return true;
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
        System.out.println(new Solution().isDigital("-E-16"));
        System.out.println(new Solution().isDigital("-123"));
        System.out.println(new Solution().isDigital("3.14"));
        System.out.println(new Solution().isDigital(".14"));
        System.out.println(new Solution().isDigital("3."));
        System.out.println();
        System.out.println(new Solution().isDigital("12e"));
        System.out.println(new Solution().isDigital("1a3.14"));
        System.out.println(new Solution().isDigital("1.2.3"));
        System.out.println(new Solution().isDigital("+-5"));
        System.out.println(new Solution().isDigital("12e+5.4"));
    }
}
