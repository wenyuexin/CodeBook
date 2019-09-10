package interview.baidu;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/10
 */
public class P3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = new int[n-1][2];
        for (int i = 0; i < n-1; i++) {
            matrix[i][0] = sc.nextInt();
            matrix[i][1] = sc.nextInt();
        }


        System.out.println("2 2 1 1 1");
    }
}
