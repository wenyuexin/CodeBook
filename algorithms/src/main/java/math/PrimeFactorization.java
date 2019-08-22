package math;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Apollo4634
 * @create 2019/08/22
 */

public class PrimeFactorization {

    static class Solution {
        List<Integer> decompose(int num) {
            List<Integer> factors = new LinkedList<>();
            if (num < 1) return factors;
            if (num < 4) { factors.add(num); return factors; }

            num = calc(num, 2, factors);
            num = calc(num, 3, factors);
            num = calc(num, 5, factors);
            double max = Math.sqrt(num) + 1;
            for (int i = 7; i < max && num > 2; i += 2) {
                if (i%3 == 0 || i%5 == 0) continue;
                num = calc(num, i, factors);
                max = Math.sqrt(num) + 1;
            }
            if (num > 1) factors.add(num);
            return factors;
        }

        private int calc(int num, int factor, List<Integer> factors) {
            if (num < factor) return num;
            while (num%factor == 0) {
                factors.add(factor);
                num /= factor;
            }
            return num;
        }
    }


    public static void main(String[] args) {
        //int num = Integer.MAX_VALUE-1;
        int num = 331;
        System.out.println(num);
        System.out.println(new Solution().decompose(num));
    }
}
