package book_9787121310928;

/**
 * 数组中数字出现的次数：
 * 数组中只出现一次的两个数字：
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。
 * 找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 *
 * 解法就是分两组亦或
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_56_1 {
    //num1,num2分别为长度为1的数组。传出返回结果
    public class Solution {
        public void FindNumsAppearOnce(int[] nums, int[] num1, int[] num2) {
            if (nums == null || nums.length < 2)  return;

            int xor = nums[0];
            for (int i = 1; i < nums.length; i++) {
                xor ^= nums[i];
            }

            int flag = 1;
            while ((xor & flag) == 0) {
                flag = flag << 1;
            }

            int xor1 = 0;
            for (int num : nums) {
                if ((num & flag) > 0) {
                    xor1 ^= num;
                }
            }

            int xor2 = xor1 ^ xor;
            num1[0] = xor1;
            num2[0] = xor2;
        }
    }
}
