package book_9787121310928;

import java.util.regex.Pattern;

/**
 * 正则表达式匹配
 * 实现对'.'和'*'的匹配
 *
 * 参考leetcode第10题 Regular Expression Matching
 * 单独的'.'或者'*'都容易处理，主要是得考虑".*"
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_19 {

    static class Solution {
        boolean match(String str, String partten) {
            return Pattern.matches(partten, str);
        }
    }


    static class Solution2 {
        char[] chars;
        char[] parttens;
        boolean[] mode;
        int nPattens;

        boolean match(String str, String partten) {
            chars = str.toCharArray();
            parttens = new char[partten.length()];
            mode = new boolean[partten.length()];
            nPattens = 0;
            for (int i = 0; i < partten.length(); i++) {
                if (partten.charAt(i) != '*') {
                    parttens[nPattens] = partten.charAt(i);
                    nPattens += 1;
                } else {
                    mode[nPattens-1] = true; //可以匹配多个字符
                }
            }
            return matchHepler(0, 0);
        }

        boolean matchHepler(int strIdx, int pattenIdx) {
            int iPartten = pattenIdx;
            for (; iPartten < nPattens; iPartten++) {
                if (strIdx >= chars.length) break;
                if (!mode[iPartten]) {
                    if (parttens[iPartten] == '.') {
                        //System.out.println(strIdx+" "+chars[strIdx]+" "+iPartten+" "+parttens[iPartten]+" *");
                        strIdx += 1;
                        continue;
                    }
                    if (chars[strIdx] != parttens[iPartten]) {
                        //System.out.println(strIdx+" "+chars[strIdx]+" "+iPartten+" "+parttens[iPartten]+" ");
                        break;
                    }
                    //System.out.println(strIdx+" "+chars[strIdx]+" "+iPartten+" "+parttens[iPartten]+" *");
                    strIdx += 1;
                } else {
                    int cnt = 0;
                    while (strIdx+cnt < chars.length && (parttens[iPartten] == '.' || chars[strIdx+cnt] == parttens[iPartten])) {
                        //System.out.println(strIdx+" "+chars[strIdx+cnt]+" "+iPartten+" "+parttens[iPartten]+" *");
                        if (matchHepler(strIdx+cnt, iPartten+1)) return true;
                        cnt += 1;
                    }
                    strIdx += cnt;
                }
            }

            if (strIdx < chars.length) return false;
            boolean flag = true;
            for (int i = iPartten; i < nPattens; i++) {
                flag &= mode[i];
            }
            return flag;
        }
    }


    public static void main(String[] args) {
        //String str = "aaab";
        String str = "asdab";
        String pattern = "asdabe*";
        //String pattern = ".*";
        //String pattern = ".*b";
        //String pattern = ".*c";
        //String pattern = "ab*.*ac*a.";

        System.out.println(new Solution().match(str, pattern));
        System.out.println(new Solution2().match(str, pattern));
    }
}
