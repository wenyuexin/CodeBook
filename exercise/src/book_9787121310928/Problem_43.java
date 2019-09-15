package book_9787121310928;


/**
 * 1~n整数中1出现的次数：
 * 输入一个整数n，求1~n这n个整数的十进制表示中1出现的次数
 *
 * 未解决
 *
 * @author Apollo4634
 * @create 2019/08/29
 */

public class Problem_43 {

    static class Solution {
        int numberOf1Between1AndN(int n) {
            if (n < 1) return 0;
            if (n < 10) return 1;
            final int SIZE = (int) Math.ceil(Math.log10(n) + 0.1);
            int[] digits = new int[SIZE];

            for (int copy = n, i = 0; copy != 0;) {
                digits[i++] = copy%10;
                copy /= 10;
            }

            int lastCount = 1;
            int newCount = 0;
            for (int i = 1; i < SIZE; i++) {
                int digit = digits[i];
                if (digit == 0) {
                    newCount = 9*lastCount;
                } else if (digit != 1) { //2-9
                    newCount = (int) Math.pow(10, i) + digit*lastCount;
                } else { //1
                    newCount = lastCount;
                    newCount += (n % (int) Math.pow(10, i)) + 1;
                }
                lastCount = newCount;
                System.out.println(newCount);
            }
            return newCount;
        }
    }


    //这个解法求得是1~n中出现过1的数字的个数
    static class Solution_addtion {
        public int numberOf1Between1AndN(int n) {
            if (n < 1) return 0;
            if (n < 10) return 1;
            final int SIZE = (int) Math.ceil(Math.log10(n) + 0.1);
            int[] digits = new int[SIZE];

            for (int copy = n, i = 0; copy != 0;) {
                digits[i++] = copy%10;
                copy /= 10;
            }

            int lastCount = 1;
            int newCount = 0;
            for (int i = 1; i < SIZE; i++) {
                int digit = digits[i];
                if (digit == 0) {
                    newCount = 9*lastCount;
                } else if (digit != 1) { //2-9
                    newCount = (int) Math.pow(10, i) + digit*lastCount;
                } else { //1
                    newCount = lastCount;
                    newCount += (n % (int) Math.pow(10, i)) + 1;
                }
                lastCount = newCount;
                System.out.println(newCount);
            }
            return newCount;
        }
    }


    public static void main(String[] args) {
        int num = 55;
        int ret = new Solution().numberOf1Between1AndN(num);
        System.out.println(ret);
    }
}
