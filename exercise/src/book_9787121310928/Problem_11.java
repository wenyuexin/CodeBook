package book_9787121310928;

import java.util.Objects;

/**
 * 找出递增数组的旋转数组中的最小数字
 *
 * 可以参考leetcode的第33题 Search in Rotated Sorted Array
 * 不过leetcode上的这个题要复杂一些
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_11 {
    static class Solution {
        int findMinimumInSortedArray(int[] nums) {
            Objects.requireNonNull(nums);
            if (nums[0] <= nums[nums.length-1]) return nums[0];
            int index = binarySearch(nums, 1, nums.length-1);
            return nums[index];
        }

        int binarySearch(int[] nums, int from, int to) {
            int mid, midVal;
            while (from < to) {
                mid = (from + to)/2;
                midVal = nums[mid];
                if (midVal < nums[0]) {
                    to = mid - 1;
                } else {
                    from = mid + 1;
                }
            }
            mid = (from + to)/2;
            if (mid+1 < nums.length && nums[mid+1] < nums[mid]) mid += 1;
            return mid;
        }
    }


    public static void main(String[] args) {
        Solution test = new Solution();
        //System.out.println(test.findMinimumInSortedArray(null));
        System.out.println(test.findMinimumInSortedArray(new int[] { 5,6,7,1,2,3 }));
        System.out.println(test.findMinimumInSortedArray(new int[] { 5,6,7,1 }));
        System.out.println(test.findMinimumInSortedArray(new int[] { 1,6,7 }));
        System.out.println(test.findMinimumInSortedArray(new int[] { 4,1,2,3 }));
        System.out.println(test.findMinimumInSortedArray(new int[] { 1 }));
        System.out.println(test.findMinimumInSortedArray(new int[] { 5,1 }));
    }
}
