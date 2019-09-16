package book_9787121310928;

import java.util.ArrayList;

/**
 * 和为s的数字：
 * 和为s的两个数字：
 * 输入一个递增排序的数组和一个数字S,在数组中查找两个数,
 * 使得他们的和正好是S,如果有多对数字的和等于S,输出两个数的乘积最小的。
 *
 * @author Apollo4634
 * @create 2019/09/16
 */

public class Problem_57_1 {
    public static class Solution {
        ArrayList<Integer> FindNumbersWithSum(int[] nums, int sum) {
            ArrayList<Integer> list = new ArrayList<>(2);
            if (nums == null || nums.length == 0) return list;
            int left = 0;
            int right = nums.length - 1;
            while (left < right) {
                int temp = nums[left] + nums[right];
                if (temp == sum) {
                    list.add(nums[left]);
                    list.add(nums[right]);
                    break;
                } else if (temp > sum) {
                    right -= 1;
                } else {
                    left += 1;
                }
            }
            return list;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 1,2,4,7,11,15 };
        ArrayList<Integer> list = new Solution().FindNumbersWithSum(nums, 15);
        System.out.println(list);
    }
}
