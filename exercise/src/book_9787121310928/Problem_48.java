package book_9787121310928;

import java.util.HashMap;
import java.util.Map;

/**
 * 最长不含重复字符的子字符串：
 * 从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * 假设字符串中只包含从'a'到'z'的字符。
 * 例如，在字符串中"arabcacfr"，最长非重复子字符串为"acfr"，长度为4。
 *
 * @author Apollo4634
 * @create 2019/09/04
 */

public class Problem_48 {

    static class Solution {
        int longestSubstringWithoutDuplication(String str) {
            if (str == null || str.length() == 0) return 0;
            if (str.length() == 1) return 1;

            char[] chars = str.toCharArray();
            int left = 0;
            int maxLength = 0;
            Map<Character, Integer> map = new HashMap<>();
            map.put(chars[0], 0);
            for (int i = 1; i < chars.length; i++) {
                int idx = map.getOrDefault(chars[i], -1);
                if (idx >= left) {
                    if (i-left > maxLength) maxLength = i-left;
                    left = idx + 1;
                }
                map.put(chars[i], i);
                if (i == chars.length-1 && i-left > maxLength) {
                    maxLength = i-left+1;
                }
            }
            return maxLength;
        }
    }


    public static void main(String[] args) {
//        String str = null; //0
//        String str = ""; //0
//        String str = "a"; //1
//        String str = "aaaa"; //1
//        String str = "abcdef"; //6
//        String str = "null"; //3
//        String str = "arcabcacfr"; //4
        String str = "arcabcrcfr"; //4
        int ret = new Solution().longestSubstringWithoutDuplication(str);
        System.out.println(ret);
    }
}
