package interview.tencent;

import java.util.Scanner;

/**
 * 90%
 *
 * @author Apollo4634
 * @create 2019/09/20
 */

public class P1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(sc.nextLine().trim());

        boolean[] result = new boolean[t];
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(sc.nextLine().trim());
            String s = sc.nextLine().trim();
            result[i] = isValid(n, s);
        }

        for (boolean bool : result) {
            System.out.println(bool ? "YES" : "NO");
        }

//        String s = "81234567890";
//        System.out.println(isValid(s.length(), s));
    }

    private static boolean isValid(int n, String str) {
        if (n < 11) return false;
        char[] chars = str.toCharArray();
        if (n == 11) return chars[0] == '8';
        int m = n - 10;

        int idx = -1;
        for (int i = 0; i < m; i++) {
            if (chars[i] == '8') {
                idx = i; break;
            }
        }
        return (idx != -1);
    }
}
