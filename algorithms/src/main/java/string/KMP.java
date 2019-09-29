package string;

/**
 * @author Apollo4634
 * @create 2019/09/29
 */

public class KMP {
    private static int[] getNextArray(char[] t) {
        int[] next = new int[t.length];
        next[0] = -1;
        next[1] = 0;
        int k;
        for (int j = 2; j < t.length; j++) {
            k=next[j-1];
            while (k!=-1) {
                if (t[j - 1] == t[k]) {
                    next[j] = k + 1;
                    break;
                }
                else {
                    k = next[k];
                }
                next[j] = 0;
            }
        }
        return next;
    }

    static int match(String s, String p) {
        char[] s_arr = s.toCharArray();
        char[] p_arr = p.toCharArray();
        int[] next = getNextArray(p_arr);
        int i = 0, j = 0;
        while (i < s_arr.length && j < p_arr.length){
            if(j == -1 || s_arr[i]==p_arr[j]){
                i++;
                j++;
            }
            else
                j = next[j];
        }
        return (j == p_arr.length)? i-j : -1;
    }
}
