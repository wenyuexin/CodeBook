package book_9787121310928;

import java.security.InvalidParameterException;

/**
 * 翻转字符串：
 * 左旋转字符串：
 * 字符串的左旋操作是把字符串前面的若干字符移到字符串的尾部。
 * 例如"abcdefg"和数字2，得到"cdefgab"
 *
 * @author Apollo4634
 * @create 2019/09/16
 */

public class Problem_58_2 {
    public static class Solution {
        String LeftRotateString(String str, int n) {
            if (str == null || str.length() == 0 || n == 0) return str;
            if (n < 0) throw new InvalidParameterException();
            int move = n % str.length();
            if (move == 0) return str;
            String header = str.substring(0, move);
            String tailer = str.substring(move);
            return tailer + header;
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().LeftRotateString("", 2));
        System.out.println(new Solution().LeftRotateString("abcdefg", 0));
        System.out.println(new Solution().LeftRotateString("abcdefg", 1));
        System.out.println(new Solution().LeftRotateString("abcdefg", 2));
        System.out.println(new Solution().LeftRotateString("abcdefg", 6));
        System.out.println(new Solution().LeftRotateString("abcdefg", 7));
        System.out.println(new Solution().LeftRotateString("abcdefg", 8));
    }
}
