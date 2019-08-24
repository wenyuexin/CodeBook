package interview.acmcoder;

import java.util.Scanner;

/**
 * 给定一个长度为n的整数数组a，元素均不相同，
 * 问数组是否存在这样一个片段，只将该片段翻转就可以使整个数组升序排列。
 *
 * @author Apollo4634
 * @create 2019/08/22
 */

public class ReverseArray {

    static class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }




            //System.out.println(Arrays.toString(nums));
        }
    }
}
