package interview.yuanfudao;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/16
 */

public class P2 {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int s = sc.nextInt();
//        int[] nums = new int[n];
//        for (int i = 0; i < n; i++) {
//            nums[i] = sc.nextInt();
//        }

        int n = 6;
        int s = 5;
        int[] nums = new int[] { 5,1,1,1,2,3 };

        int left = 0;
        int right = 0;
        int sum = 0;
        int maxLen = 0;
        for (int i = left; i < n; i++) {
            if (nums[i] <= s && sum + nums[i] <= s) {
                sum += nums[i];
                right = i;
                if (maxLen < right - left + 1)
                    maxLen = right - left + 1;
            } else {
                sum -= nums[left];
                left += 1;
                i -= 1;
            }
        }
        System.out.println(maxLen);
    }
}
