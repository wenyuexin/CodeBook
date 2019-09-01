package book_9787121310928;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 把数组排成最小的数：
 * 输入一个正整数组，把数组里所有数字拼接起来排成一个数，
 * 打印能够拼接处的所有数字中最小的一个数。
 * 例如，数组{3,32,321}，可以得到最小数字321323。
 *
 * @author Apollo4634
 * @create 2019/08/31
 */

public class Problem_45 {

    static class Solution {
        String minNumber(int[] nums) {
            if (nums == null || nums.length == 0) return "";
            if (nums.length == 1) return String.valueOf(nums[0]);

            String[] strs = new String[nums.length];
            for (int i = 0; i < nums.length; i++) {
                strs[i] = String.valueOf(nums[i]);
            }

            Arrays.sort(strs, (String s1, String s2) -> {
                if (s1.equals(s2)) return 0;
                int size = Math.min(s1.length(), s2.length());
                for (int i = 0; i < size; i++) {
                    char c1 = s1.charAt(i);
                    char c2 = s2.charAt(i);
                    if (c1 < c2) return -1;
                    else if (c1 > c2) return 1;
                }
                if (s1.length() == s2.length()) return 0;
                return s1.length() < s2.length() ? -1 : 1;
            });

            System.out.println(Arrays.toString(strs));
            //StringBuilder sb = new StringBuilder();

            return null;
        }
    }


    public static void main(String[] args) {
        //System.out.println("123".compareTo("12"));

        int[] nums = new int[] { 397, 86, 12, 124, 120, 2, 25 };
        new Solution().minNumber(nums);

    }
}