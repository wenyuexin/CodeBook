package book_9787121310928;

/**
 * 连续子数组的最大和
 * 整数数组（数值可正可负）的一个或多个连续元素组成子数组。
 * 求所有子数组的和的最大值。要求时间复杂度为O(n)
 *
 * @author Apollo4634
 * @create 2019/08/24
 */

public class Problem_42 {

    static class Solution {
        int findGreatestSumOfSubArray(int[] array) {
            if (array == null || array.length == 0)
                throw new IllegalArgumentException("Invalid array");

            int maxSum = Integer.MIN_VALUE;
            int sum = 0;
            for (int num : array) {
                if (sum <= 0) sum = 0;
                sum += num;
                if (sum > maxSum) maxSum = sum;
            }
            return maxSum;
        }
    }


    public static void main(String[] args) {
        //int[] nums = new int[] { 1,-2,3,10,-4,7,2,-5 };
        int[] nums = new int[] { -2,-8,-1,-5,-9 };
        int maxSum = new Solution().findGreatestSumOfSubArray(nums);
        System.out.println(maxSum);
    }
}
