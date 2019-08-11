package book_9787121310928;

/**
 * 获取斐波那契数列的第n项
 *
 * @author Apollo4634
 * @create 2019/08/11
 */

public class Problem_10_1 {
    static class Solution {
        long fibonacci(int index) {
            if (index < 0) throw new IllegalArgumentException("Wrong index");
            if (index < 2) return index;
            index -= 1;

            int cnt = 0;
            long n1 = 0, n2 = 1, temp;
            while (index > 0) {
                temp = n2;
                n2 += n1;
                n1 = temp;
                index -= 1;
                cnt += 1;
            }
            return n2;
        }
    }



    public static void main(String[] args) {
        System.out.println(new Solution().fibonacci(10));
        System.out.println(new Solution().fibonacci(Integer.MAX_VALUE));
        System.out.println(new Solution().fibonacci(92));
        System.out.println(new Solution().fibonacci(93));
    }
}
