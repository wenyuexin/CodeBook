package others;

/**
 * @author Apollo4634
 * @create 2019/07/12
 */

public class InputUsingKeyboard {

    static class Solution {
        int left = 5;
        int right = 6;

        private int timer(int[] nums) {
            int len = nums.length;
            int steps = 0;
            int left = 5;
            int right = 6;
            int cnt = 0;
            boolean flag;
            for (int n : nums) {
                steps += moveTo(n);
            }
            return steps;
        }

        private int moveTo(int num) {
            if (num == 0) num = 10;

            int step;
            if (Math.abs(left-num) <= Math.abs(right-num)) {
                step = Math.abs(left-num);
                left = num;
                //System.out.println(""+step+" "+left+" "+right);
            } else {
                step = Math.abs(right-num);
                right = num;
                //System.out.println(""+step+" "+left+" "+right);
            }
            return step;
        }
    }



    public static void main(String[] args) {
        int[] nums = new int[] { 3,6,8,4,2,5,7,6,1 };
        int time = new Solution().timer(nums);
        System.out.println(time);
    }
}
