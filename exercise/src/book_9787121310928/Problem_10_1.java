package book_9787121310928;

import java.math.BigInteger;

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

            long n1 = 0, n2 = 1, temp;
            while (index > 0) {
                temp = n2;
                n2 += n1;
                n1 = temp;
                index -= 1;
            }
            return n2;
        }
    }


    static class Solution2 {
        BigInteger fibonacci(int index) {
            if (index < 0) throw new IllegalArgumentException("Wrong index");
            if (index < 2) return new BigInteger(String.valueOf(index));
            index -= 1;

            BigInteger n1 = BigInteger.ZERO;
            BigInteger n2 = BigInteger.ONE;
            BigInteger temp;
            while (index > 0) {
                temp = new BigInteger(n2.toByteArray());
                n2 = n2.add(n1);
                n1 = temp;
                index -= 1;
            }
            return n2;
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution2().fibonacci(10));
        System.out.println(new Solution2().fibonacci(92));
        System.out.println(new Solution2().fibonacci(93));
        System.out.println(new Solution2().fibonacci(1000));
        //System.out.println(new Solution2().fibonacci(Integer.MAX_VALUE));
    }
}
