package book_9787121310928;

import java.util.HashSet;

/**
 * 找出数组中重复的数字
 *
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_03 {

    static class Solution {
        int findDuplicate(int[] nums) {
            HashSet<Integer> set = new HashSet<>();
            for (int n : nums) {
                if (set.contains(n)) return n;
                set.add(n);
            }
            return -1; //如果没有则返回-1
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 2,3,1,0,2,5,3 };
        int duplicate = new Solution().findDuplicate(nums);
        System.out.println(duplicate);
    }
}
