package book_9787121310928;

/**
 * 求解数值的整数次方（不用考虑数值溢出）
 * 参考leetcode的第50题 Pow(x, n)
 *
 * 二分法
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_16 {

    static class Solution {
        double pow(double x, int n) {
            int m = n < 0 ? -n - 1 : n;
            double p = 1.0;
            for (double q = x; m > 0; m = m/2) {
                System.out.println(m+" "+p+" "+q);
                if ((m & 1) != 0) {
                    p *= q;
                }
                q *= q;
                System.out.println(m+" "+p+" "+q);
                System.out.println();
            }
            return n < 0 ? 1.0 / p / x : p;
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().pow(2, 21));
    }
}
