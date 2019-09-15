package book_9787121310928;

/**
 * 在排序数组中查找数字：
 * 数组中数值和下标相等的元素：
 * 假设一个单调递增的数组里的每个元素都是整数并且是唯一的。
 * 请编程实现一个函数找出数组中任意一个数值等于其下标的元素。
 * 例如，在数组{-3, -1, 1, 3, 5}中，数字3和它的下标相等。
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_53_3 {
    static public class Solution {
        int GetNumberSameAsIndex(int[] nums) {
            if (nums == null || nums.length == 0) return -1;
            int left = 0, right = nums.length-1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] == mid) {
                    return mid;
                } else if (mid > nums[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return -1;
        }
    }


    public static void main(String[] args) {
        //int[] nums = new int[] { -3,-1,1,3,5,7 };
        //int[] nums = new int[] { -3,-1,1,5,7 };
        //int[] nums = new int[] { -3,-1,1,3 };
        int[] nums = new int[] { 0,2,3,4,5 };
        //int[] nums = new int[] { 0 };
        int ret = new Solution().GetNumberSameAsIndex(nums);
        System.out.println(ret);
    }
}
