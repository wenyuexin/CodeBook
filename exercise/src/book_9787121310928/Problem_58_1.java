package book_9787121310928;

/**
 * 翻转字符串：
 * 翻转单词顺序：
 * 输入一个英文句子，翻转句子中单词的顺序，要求单词内的字符顺序不变。
 * 标点符号和普通字母一样处理，例如输入"ab cde fg."输出"fg. cde ab"
 *
 * @author Apollo4634
 * @create 2019/09/16
 */

public class Problem_58_1 {
    public static class Solution {
        String ReverseSentence(String str) {
            if (str == null || str.length() == 0) return "";
            if (str.trim().length() == 0) return str;
            str += " a";
            String[] strs = str.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = strs.length-1; i > 0; i--) {
                sb.append(strs[i]).append(" ");
            }
            sb.append(strs[0]);
            return sb.substring(2, sb.length());
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().ReverseSentence(""));
        System.out.println(new Solution().ReverseSentence("    "));
        System.out.println(new Solution().ReverseSentence("abcde"));
        System.out.println(new Solution().ReverseSentence("ab cde fg."));
        System.out.println(new Solution().ReverseSentence("  ab cde fg.   "));
    }
}
