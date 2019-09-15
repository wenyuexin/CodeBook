package book_9787121310928;

/**
 * 在排序数组中查找数字：
 * 数字在排序数组中出现的次数：
 * 统计一个数字在排序数组中出现的次数，比如
 * 排序数组为{1,2,3,3,3,4,5}，那么数字3出现的次数就是3。
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_53_1 {
    static public class Solution {
        public int GetNumberOfK(int[] nums, int k) {
            if (nums == null || nums.length == 0) return 0;
            int maxIdx = nums.length - 1;
            if (nums[0] > k || nums[maxIdx] < k) return 0;

            int mid, left = 0, idx = maxIdx, right = maxIdx;
            while (left < idx) {
                mid = (left + idx) / 2;
                if (nums[mid] == k) {
                    if (mid == 0 || nums[mid-1] < k) {
                        left = mid; break;
                    }
                    idx = mid - 1;
                } else if (nums[mid] < k) {
                    left = mid + 1;
                } else {
                    idx = mid - 1;
                    right = mid - 1;
                }
            }
            if (nums[left] != k) return 0;

            idx = left;
            while (idx < right) {
                mid = (idx + right) / 2;
                if (nums[mid] == k) {
                    if (mid == maxIdx || nums[mid+1] > k) {
                        right = mid; break;
                    }
                    idx = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return (right - left + 1);
        }
    }


    public static void main(String[] args) {
        //int[] nums = new int[] { 1,2,3,3,3,3,4,5 };
        //int[] nums = new int[] { 1,2,3,3,3,5,5,7,8 };
        int[] nums = new int[] { 3,3,3,3 };
        int ret = new Solution().GetNumberOfK(nums, 4);
        System.out.println(ret);
    }
}
