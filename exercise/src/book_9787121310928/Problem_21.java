package book_9787121310928;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * 调整数数组的元素顺序使所有奇数位于偶数前面
 * 即前半段都是奇数，后半段都是偶数
 *
 * 非常基础的two pointer题，左右两边向中间走，
 * 发现左边发现一个偶数，右边发现一个奇数那就交换，
 * 直到遍历完整个数组
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_21 {

    static class Solution {
        void reorder(int[] nums) {
            Objects.requireNonNull(nums);
            if (nums.length < 1) return;
            int left = 0;
            int right = nums.length-1;
            while (left < right) {
                while (nums[left]%2 != 0) left += 1;
                while (nums[right]%2 == 0) right -= 1;
                if (left < right) swap(nums, left++, right--);
            }
        }

        private void swap(int[] nums, int idx1, int idx2) {
            int tmp = nums[idx1];
            nums[idx1] = nums[idx2];
            nums[idx2] = tmp;
        }
    }


    public static void main(String[] args) {
        //int[] nums = null;
        //int[] nums = new int[] { };
        //int[] nums = new int[] { 1 };
        //int[] nums = new int[] { 1,2,3,4,5,6,7 };
        //int[] nums = new int[] { 2,4,6,8,1,3,5,7 };
        //int[] nums = new int[] { 1,3,5,7,2,4,6,8 };
        //int[] nums = new int[] { 95,8,-72,-4,34,-83,46,-9,-91,-4 };

        int[] nums = new Random()
                .ints(16, -100, 100)
                .toArray();
        System.out.println(Arrays.toString(nums));

        new Solution().reorder(nums);
        System.out.println(Arrays.toString(nums));
    }
}
