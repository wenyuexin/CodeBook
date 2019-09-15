package book_9787121310928;

/**
 * 在排序数组中查找数字：
 * 0至n-1中缺失的数字：
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，
 * 并且每个数字都在范围0~n-1之内。在范围0~n-1内的n个数字中
 * 有且只有一个数字不再该数组中，请找出这个数字。
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_53_2 {
    static public class Solution {
        int GetMissingNumber(int[] nums) {
            if (nums == null || nums.length == 0) return -1;
            int n = nums.length + 1;
            int maxIdx = nums.length - 1;
            if (nums[maxIdx] == maxIdx) return n-1;

            int mid, left = 0, right = maxIdx-1;
            while (left < right) {
                mid = (left + right) / 2;
                if (nums[mid] == mid) {
                    if (nums[mid+1] > mid+1) return mid+1;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return -1;
        }
    }


    public static void main(String[] args) {
        //int[] nums = new int[] { 0,1,2,3,5,6 };
        //int[] nums = new int[] { 0,1,2,3,4,5,6 };
        int[] nums = new int[] { 0 };
        int ret = new Solution().GetMissingNumber(nums);
        System.out.println(ret);
    }
}
