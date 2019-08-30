package book_9787121310928.utility;

/**
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
            int num;
            if (offset < 0) {
                offset = 0; num = (int) Math.pow(10, k);
            } else {
                offset += 1; num = (int) Math.pow(10, k) + offset;
            }

            int idxAtNum = index - base - offset*(k+1);
            idxAtNum = k - idxAtNum;

            int temp = num;
            for (int i = 0; i < idxAtNum; i++) {
                temp /= 10;
            }
            return temp%10;
        }

        private int calcBase(int k) {
            return ((int) Math.pow(10, k)*(9*k-1)+10)/9;
        }
    }


    public static void main(String[] args) {
//        System.out.println(new Solution().digitAtIndex(-2));
//        System.out.println(new Solution().digitAtIndex(0));
//        System.out.println(new Solution().digitAtIndex(1));
//        System.out.println(new Solution().digitAtIndex(9));

        System.out.println(new Solution().digitAtIndex(10));
//        System.out.println(new Solution().digitAtIndex(11));

//        System.out.println(new Solution().digitAtIndex(12));
//        System.out.println(new Solution().digitAtIndex(13));

//        System.out.println(new Solution().digitAtIndex(18));
//        System.out.println(new Solution().digitAtIndex(19));
    }
}
