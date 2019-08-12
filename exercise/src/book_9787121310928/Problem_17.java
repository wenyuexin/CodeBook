package book_9787121310928;

import java.math.BigInteger;

/**
 * 打印从1到最大的n位整数（注意取值范围）
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_17 {

    static class Solution {
        void printNumbers(int n) {
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) {
                sb.append('9');
            }
            BigInteger max = new BigInteger(sb.toString());
            for (BigInteger i = BigInteger.ZERO; i.compareTo(max) <= 0; i = i.add(BigInteger.ONE)) {
                System.out.println(i);
            }
        }
    }


    public static void main(String[] args) {
        new Solution().printNumbers(10);
    }
}
