package book_9787121310928;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 替换字符串中的空格
 *
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_05 {

    static class Solution {
        String replace(String str) {
            String[] strs = str.split(" ");
            String ret = String.join("%20", strs);
            int idx = str.lastIndexOf(strs[strs.length-1]);
            int nTailingBlanck = str.length() - (idx+strs[strs.length-1].length());
            if (nTailingBlanck > 0) ret += "%20".repeat(nTailingBlanck); //jdk 11
            return ret;
        }
    }

    /**
     * 第一次遍历录空格的位置
     * 第二次遍历复制不含空格的子字符串，并用%20替换空格
     */
    static class Solution2 {
        String replace(String str) {
            int strLen = str.length();
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = strLen-1; i >= 0; i--) {
                if (str.charAt(i) == ' ') list.add(i);
            }
            if (list.size() == 0) return str;

            StringBuilder sb = new StringBuilder(str.length() + 2*list.size());
            int from = 0;
            int to;
            while (!list.isEmpty()) {
                to = Objects.requireNonNullElse(list.pollLast(), strLen);
                System.out.println(to);

                if (to > from) {
                    sb.append(str.substring(from, to));
                }
                sb.append("%20");
                from = to + 1;
                System.out.println(sb);
            }
            return sb.toString();
        }
    }


    public static void main(String[] args) {
        //String str = "asdfqqq";
        String str = "  AB CD  EFG   ";
        System.out.println(str);

        String ret = new Solution2().replace(str);
        System.out.println(ret);
    }
}
