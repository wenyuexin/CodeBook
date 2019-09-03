package book_9787121310928;

/**
 * 把数字翻译成字符串：
 * 给定一个数字，按照如下规则翻译成字符串：0翻译成“a”，1翻译成“b”…25翻译成“z”。
 * 一个数字有多种翻译可能，例如12258一共有5种，分别是bccfi，bwfi，bczi，mcfi，mzi。
 * 实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 *
 * 附：
 * 0 a, 1 b, 2 c, 3 d, 4 e, 5 f, 6 g, 7 h, 8 i, 9 j,
 * 10 k, 11 l, 12 m, 13 n, 14 o, 15 p, 16 q, 17 r, 18 s, 19 t,
 * 20 u, 21 v, 22 w, 23 x, 24 y, 25 z
 *
 * @author Apollo4634
 * @create 2019/09/03
 */

public class Problem_46 {

    static class Solution {
        int getTranslationCount(int number) {
            if (number < 0) return 0;
            if (number < 10) return 1;
            char[] chars = String.valueOf(number).toCharArray();
            return counter(chars, 0, 0);
        }

        private int counter(char[] chars, int idx, int count) {
            if (idx > chars.length-1) return count;
            if (idx == chars.length-1) count++;
            count = counter(chars, idx+1, count);
            if (chars[idx] > '2' || chars[idx] == '0') return count;
            if (chars[idx] == '2' && idx+1 < chars.length && chars[idx+1] > '5') return count;
            if (idx+2 == chars.length) return count+1;
            return counter(chars, idx+2, count);
        }
    }


    public static void main(String[] args) {
        //int number = -2;
        //int number = 0;
        //int number = 9;
        int number = 10;
        //int number = 11;
        //int number = 25;
        //int number = 26;
        //int number = 12258;
        int ret = new Solution().getTranslationCount(number);
        System.out.println(ret);
    }
}
