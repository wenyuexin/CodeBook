package interview.tencent;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/20
 */

public class P3 {
    private static boolean[] visited;
    private static int delta;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(sc.nextLine().trim());
        int[][] result = new int[t][2];
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int j = 0; j < n; j++) {
                nums[j] = sc.nextInt();
            }
            result[i] = new int[2];
            divideIntoTwoParts(n, nums, result[i]);
        }

        for (int[] ints : result) {
            System.out.println(ints[0]+" "+ints[1]);
        }
    }

    private static void divideIntoTwoParts(int n, int[] nums, int[] ret) {
        int n1 = n/2;
        int n2 = n - n1;
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        visited = new boolean[n];
        delta = Integer.MAX_VALUE;
        traverse(n1, nums, totalSum, 0, 0);
        ret[0] = (totalSum - delta)/2;
        ret[1] = (totalSum + delta)/2;
    }

    private static void traverse(int n1, int[] nums, int totalSum, int sum, int idx) {
        if (idx == n1) {
            int temp = Math.abs(2*sum - totalSum);
            if (temp < delta) delta = temp;
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                traverse(n1, nums, totalSum, sum+nums[idx], idx+1);
                visited[i] = false;
            }
        }
    }
}
