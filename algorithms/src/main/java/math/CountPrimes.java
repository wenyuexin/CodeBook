package math;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Apollo4634
 * @create 2019/08/22
 */

public class CountPrimes {
    static class Solution {
        int countPrimes(int n) {
            if (n <= 2) return 0;
            boolean[] sieve = new boolean[n+1];
            sieve[2] = true;
            for(int i = 3; i < n; i+=2) {
                sieve[i] = true;
            }

            for (int p = 3; p*p<=n; p++){
                if (sieve[p]){
                    for (int i = p+p; i<=n; i += p ){
                        sieve[i] = false;
                    }
                }
            }

            int count = 1;
            //List<Integer> list = new LinkedList<>();
            for (int i = 3; i < n; i+=2){
                count += sieve[i] ? 1:0;
                //list.add(i);
            }
            return count;
        }
    }


    public static void main(String[] args) {
        int num = 1000000;
        System.out.println(new Solution().countPrimes(num));
    }
}
