package interview.bytedance.date190908;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/08
 */

public class P3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int q = sc.nextInt();
        char[] chars = sc.nextLine().trim().toCharArray();
        int[][] ranges = new int[q][];
        for (int i = 0; i < q; i++) {
            ranges[i] = new int[2];
            ranges[i][0] = sc.nextInt();
            ranges[i][1] = sc.nextInt();
        }


        System.out.println(q);
    }


    private static int playGame(char[] chars, int from, int to) {
        int count = 0;
        return 0;
    }
}
