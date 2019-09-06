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

    static class Solution {
        int inversePairs(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            TreeSet<Integer> set = new TreeSet<>();
            set.add(nums[0]);

            int count = 0;
            for (int i = 1; i < nums.length; i++) {
                //SortedSet<Integer> integers = set.tailSet(nums[i]);
                count += set.tailSet(nums[i]).size();
                set.add(nums[i]);
            }
            return count%1000000007;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 7,5,6,4 };
        System.out.println(new Solution().inversePairs(nums));
    }
}
