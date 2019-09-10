package interview.baidu;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/10
 */
public class P2 {
    private static long count;
    private static int k;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        k = sc.nextInt();
        count = 0;
        divide(n, k);
        System.out.println(count);
    }

    private static void divide(int num, int k) {
        if (num <= k+1) { count += 1; return; }
        if ((num-k)%2 != 0) { count += 1; return; }
        int n1 = (num-k)/2;
        int n2 = n1 + k;
        divide(n1, k);
        divide(n2, k);
    }
}
