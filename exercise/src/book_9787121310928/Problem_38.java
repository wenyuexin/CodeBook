package book_9787121310928;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 字符串的排列：
 * 输入一个字符串，输出字符串包含字符的所有可能的字符串组合
 *
 * 可以参考leetcode 第40题 Combination Sum II（解法有点类似）
 * 不过leetcode这题要求将所有和为设定值的组合找出来
 *
 * 对于长度为N的字符串，可以将搜索的过程视为遍历一颗高度为N的树，
 * 并且每个节点都有N个子节点。不过为了提出重复解，需要对搜索树进行剪枝。
 * 为此，先将字符串转换为字符数组，然后对数组进行排序。
 * 对于某个节点，如果字符都互异，则自然就需要遍历所有的N个子节点。
 * 但是如果这些字符有重复，那么这个位置上的相同字符只需要遍历一次。
 *
 * @author Apollo4634
 * @create 2019/08/16
 */

public class Problem_38 {

    static class Solution {
        private List<String> ret;
        private boolean[] visited;

        List<String> combinations(String string) {
            ret = new LinkedList<>();
            if (string == null) return ret;
            if (string.length() < 2) { ret.add(string); return ret; }

            char[] chars = string.toCharArray();
            Arrays.sort(chars);

            visited = new boolean[chars.length];
            StringBuilder sb = new StringBuilder(chars.length);
            travel(chars, 0, sb);
            return ret;
        }

        private void travel(char[] chars, int idx, StringBuilder sb) {
            if (idx == chars.length)
                ret.add(sb.toString());
            char prev = ' ';
            for (int i = 0; i < chars.length; i++) {
                if (!visited[i] && chars[i] != prev) {
                    visited[i] = true;
                    sb.append(chars[i]);
                    prev = chars[i];
                    travel(chars, idx+1, sb);
                    visited[i] = false;
                    sb.deleteCharAt(sb.length()-1);
                }
            }
        }
    }


    public static void main(String[] args) {
        //String string = null;
        //String string = "a";
        //String string = "abc";
        String string = "abcb";
        //String string = "abcbc";
        System.out.println(new Solution().combinations(string).toString());
    }
}
