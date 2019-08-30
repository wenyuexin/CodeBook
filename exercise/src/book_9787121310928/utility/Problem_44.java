package book_9787121310928.utility;

/**
 * 数字序列中某一位的数字：
 * 数字以0123456789101112131415...的格式排列。
 * 在这个序列中，第5位（从0开始计）是5，第13位是1，第19位是4，等等。
 * 写一个函数，求任意第n位对应的数字。
 *
 * @author Apollo4634
 * @create 2019/08/30
 */

public class Problem_44 {

    static class Solution {
        int digitAtIndex(int index) {
            if (index < 0) return -1;
            if (index < 10) return index;

            int k = (int) Math.log10(index) + 1;
            int base = calcBase(k) - 1;
            if (base >= index) {
                base = calcBase(--k) - 1;
            }

            int offset = (int) Math.ceil(1.0*(index-base) / (k+1)) - 1;
            int num = (int) Math.pow(10, k) + offset;

            int idxAtNum = index - base - offset*(k+1);
            idxAtNum = k - idxAtNum + 1;
            for (int i = 0; i < idxAtNum; i++) {
                num /= 10;
            }
            return num%10;
        }

        private int calcBase(int k) {
            return ((int) Math.pow(10, k)*(9*k-1)+10)/9;
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().digitAtIndex(-2));
        System.out.println(new Solution().digitAtIndex(0));
        System.out.println(new Solution().digitAtIndex(1));
        System.out.println(new Solution().digitAtIndex(9));

        System.out.print(new Solution().digitAtIndex(10));
        System.out.println(new Solution().digitAtIndex(11)); //10

        System.out.print(new Solution().digitAtIndex(12));
        System.out.println(new Solution().digitAtIndex(13)); //11

        System.out.print(new Solution().digitAtIndex(18));
        System.out.println(new Solution().digitAtIndex(19)); //14

        System.out.print(new Solution().digitAtIndex(190-2));
        System.out.println(new Solution().digitAtIndex(190-1)); //99

        System.out.print(new Solution().digitAtIndex(190));
        System.out.print(new Solution().digitAtIndex(191));
        System.out.println(new Solution().digitAtIndex(192)); //100

        System.out.print(new Solution().digitAtIndex(2890-3));
        System.out.print(new Solution().digitAtIndex(2890-2));
        System.out.println(new Solution().digitAtIndex(2890-1)); //999

        System.out.print(new Solution().digitAtIndex(2890));
        System.out.print(new Solution().digitAtIndex(2891));
        System.out.print(new Solution().digitAtIndex(2892));
        System.out.println(new Solution().digitAtIndex(2893)); //1000

        System.out.print(new Solution().digitAtIndex(2894));
        System.out.print(new Solution().digitAtIndex(2895));
        System.out.print(new Solution().digitAtIndex(2896));
        System.out.println(new Solution().digitAtIndex(2897)); //1001
    }
}
