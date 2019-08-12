package book_9787121310928;

/**
 * 青蛙跳台阶问题
 * 青蛙一次可以跳一阶也可以跳两阶台阶，问跳上n级台阶有多少种跳法
 *
 * f(n+1)=f(n)+f(n-1)
 *
 * @author Apollo4634
 * @create 2019/08/11
 */

public class Problem_10_2 {
    static class Solution {
        long jump(int n) {
            return new Problem_10_1.Solution().fibonacci(n);
        }
    }
}
