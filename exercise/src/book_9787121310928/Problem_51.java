package book_9787121310928;

import java.util.TreeSet;

/**
 * 数组中的逆序对：
 * 在数组中的两个数字如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组，求出这个数组中的逆序对的总数。例如在数组｛7，5，6，4｝中，
 * 一共存在5对逆序对，分别是(7,6)，(7,5)，(7,4)，(6,4)，(5,4)。
 *
 * @author Apollo4634
 * @create 2019/09/06
 */

public class Problem_51 {

    //这种方法运行速度慢，复杂度偏高
    static class Solution {
        int inversePairs(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            TreeSet<Integer> set = new TreeSet<>();
            set.add(nums[0]);

            int count = 0;
            for (int i = 1; i < nums.length; i++) {
                count += set.tailSet(nums[i]).size();
                set.add(nums[i]);
            }
            return count%1000000007;
        }
    }


    static class Solution2 {
        int inversePairs(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            int[] copy = new int[nums.length];
            return inverse(nums, copy, 0, 0);
        }

        private int inverse(int[] nums, int[] copy, int from, int to) {
            if (from >= to) return 0;
            if (from+1 == to) {
                if (nums[from] <= nums[to]) return 0;
                else { swap(nums, from, to); return 1; }
            }

            int mid = (from + to) / 2;
            int count = inverse(nums, copy, from, mid);
            count += inverse(nums, copy, mid+1, to);

            int idx1 = mid;
            int idx2 = to;
            int idx = to;
            while (idx1 >= 0 && idx2 >= 0) {
                if (nums[idx1] > nums[idx2]) {
                    copy[idx--] = nums[idx1--];
                } else {
                    copy[idx--] = nums[idx2--];
                    count += (mid - idx1);
                }
            }
            return count;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 7,5,6,4 };
        System.out.println(new Solution().inversePairs(nums));
    }
}
