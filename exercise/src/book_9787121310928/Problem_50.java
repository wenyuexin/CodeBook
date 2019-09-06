package book_9787121310928;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第一个只出现一次的字符
 * @author Apollo4634
 * @create 2019/09/06
 */

public class Problem_50 {

    static class Solution {
        char findFirstNotRepeatingChar(String str) {
            if (str == null || str.length() == 0) return '\0';
            char[] chars = str.toCharArray();

            HashMap<Character, Integer> map = new LinkedHashMap<>();
            for (char c : chars) {
                map.put(c, map.getOrDefault(c, 0)+1);
            }

            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 1) return entry.getKey();
            }
            return '\0';
        }
    }


    public static void main(String[] args) {
        String str = "abaccdeff";
        System.out.println(new Solution().findFirstNotRepeatingChar(str));

        System.out.println(new Solution().findFirstNotRepeatingChar(null));
        System.out.println(new Solution().findFirstNotRepeatingChar(" "));
        System.out.println(new Solution().findFirstNotRepeatingChar("a"));
        System.out.println(new Solution().findFirstNotRepeatingChar("aaaa"));
        System.out.println(new Solution().findFirstNotRepeatingChar("asdfgh"));
    }
}
