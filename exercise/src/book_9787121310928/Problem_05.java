package book_9787121310928;

import java.util.LinkedList;
import java.util.List;

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

    static class Solution2 {
        public String replace(String str) {
            int blankCount = 0;
            List<Integer> list = new LinkedList<>();
            for (int i = str.length()-1; i >= 0; i--) {
                if (str.charAt(i) == ' ') blankCount += 1;
                list.add(i);
            }
            if (blankCount == 0) return str;

            int left = list.remove(0);
            StringBuilder sb = new StringBuilder(str.length()+2*blankCount);
            if (left != 0) {
                sb.append(str.substring(0, left));
            } else {
                for (int idx : list) {

                }

            }

            return sb.toString();
        }
    }


    public static void main(String[] args) {
        //String str = "asdfqqq";
        String str = "  as df  qqq   ";
        System.out.println(str);

        String ret = new Solution().replace(str);
        System.out.println(ret);
    }
}
