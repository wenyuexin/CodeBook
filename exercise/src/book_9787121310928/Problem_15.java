package book_9787121310928;

/**
 * 二进制整数中1的个数
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_15 {

    static class Solution {
        int countNumberOfOne(int num) {
            int cnt = ((num >>> 31) > 0)? 1 : 0;
            num &= 0x7FFFFFFF;
            while (num != 0) {
                cnt += (num&1);
                num = num >>> 1;
            }
            return cnt;
        }
    }


    public static void main(String[] args) {
        //System.out.println(new Solution().countNumberOfOne(0));
        //System.out.println(new Solution().countNumberOfOne(Integer.MAX_VALUE)); //0x7fffffff
        //System.out.println(new Solution().countNumberOfOne(Integer.MIN_VALUE)); //0x80000000
        //System.out.println(new Solution().countNumberOfOne(-1));
        System.out.println(new Solution().countNumberOfOne(0x110));
        //System.out.println(new Solution().countNumberOfOne(0xFFFFFFFF));
    }
}
