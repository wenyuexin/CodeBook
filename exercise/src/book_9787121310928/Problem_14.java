package book_9787121310928;

/**
 * 剪绳子
 * 将长度为n的绳子剪成m段（n和m都是大于1的整数），求所有段的长度之积的最大值
 *
 * 虽然题干看不出，但是下文的描述就是认为每段绳子的长度也是整数
 * 这题其实没什么好做的，书上的意思是估计是整个某某高深算法，
 * 然而数学上可以证明当所有子段等长时乘积最大
 * 如果是长度任意（不一定是整数），可以用拉格朗日乘数法证明
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_14 {

    static class Solution {
        int maxProduct(int n, int m) {
            if (n < m)
                throw new IllegalArgumentException("n < m");

            int k = n/m;
            int p = n - k*m;
            int q = m - p;
            return (int) Math.pow(k, q) * (int) Math.pow(k+1, p);
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().maxProduct(8, 3));
    }
}
