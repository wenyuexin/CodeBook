package book_9787121310928;

import java.security.InvalidParameterException;

/**
 * 数组中数字出现的次数：
 * 数组中唯一只出现一次的数字：
 * 在一个数组中除一个数字只出现一次之外，其他数字都出现了三次。
 * 找出那个只出现一次的数字。
 *
 * 感觉这种题考得就是一些奇技淫巧
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_56_2 {
    public static class Solution {
        int FindNumberAppearingOnce(int[] nums) {
            if (nums == null || nums.length == 0)
                throw new InvalidParameterException();

            int[] bitSum = new int[32];
            for (int num : nums) {
                for (int j = 0, flag = 1; j < 32; j++, flag = flag << 1) {
                    bitSum[j] += ((num&flag)>0 ? 1 : 0);
                }
            }

            StringBuilder sb = new StringBuilder(32);
            for (int i = 0; i < bitSum.length; i++) {
                sb.append(bitSum[i] % 3);
            }
            int ret = Integer.parseInt(sb.reverse().toString(), 2);
            //if (bitSum[31]%3 == 1) ret = -ret;
            return ret;
        }
    }


    public static void main(String[] args) {
        //int[] nums = new int[] { 1,1,1,2,2,2,3,4,4,4 };
        //int[] nums = new int[] { 1,1,1,2,2,2,0,4,4,4 };
        int[] nums = new int[] { 1,1,1,2,2,2,-3,4,4,4 };
        int ret = new Solution().FindNumberAppearingOnce(nums);
        System.out.println(ret);
    }
}
