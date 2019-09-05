package book_9787121310928;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 丑数：
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）。
 * 例如6、8都是丑数，但14不是，因为它包含因子7。 习惯上我们把1当
 * 做是第一个丑数。 求从小到大顺序下的第1500个丑数。
 *
 * @author Apollo4634
 * @create 2019/09/04
 */

public class Problem_49 {

    /**
     * 假设有一个数集A，将所有元素分别乘以2 3 5得到数集A2 A3 A5，
     * 并设A2 A3 A5的并集为B。可知，B中可能存在元素b小于A中的最大数。
     * 因此，不能通过上述更新方式计算得到N个数后，就认为第N个数为所求。
     *
     * 为了消除更新前后两个数集的存在非空交集的问题。这里采用以下方法：
     * 可知B中最小的数为2^k，其中k为集合更新次数。
     * 那么后续不会出现数值小于2^k的数，因此可以得到一个新的集合，
     * 即{1,2,3,5,6,...,3*2^(k-1)}。如果这个集合中元素个数大于N，
     * 那么集合中第N大的数即为所求。
     *
     * 这种方法以2^k作为分界，但是集合B中最大的数为5^k，在没有找前5^k可能就溢出了。
     * 因此这个方法只能求N比较小的情况。当然也可以改为基于字符串的大数计算。
     */
    static class Solution {
        int findUglyNumber(int index) {
            if (index < 1) return 0;
            if (index == 1) return 1;
            int count = index;

            Set<Integer> current = new HashSet<>() { { add(1); } };
            TreeSet<Integer> treeSet = new TreeSet<>() { { add(1); } };
            int thresh = 2;
            while (!current.isEmpty()) {
                Set<Integer> next = new HashSet<>();
                for (Integer integer : current) {
                    next.addAll(Arrays.asList(integer * 2, integer * 3, integer * 5));
                    treeSet.addAll(next);
                }

                thresh *= 2;
                for (int i = count; i > 0 && treeSet.first() < thresh; i--) {
                    if (count == 1) {
                        Integer number = treeSet.pollFirst();
                        return number == null ? 0 : number;
                    }
                    treeSet.pollFirst();
                    count -= 1;
                }
                current = next;
            }
            return 0;
        }
    }


    /**
     * 参考书上的方法
     */
    static class Solution2 {
        int findUglyNumber(int index) {
            if (index < 1) return 0;
            if (index == 1) return 1;

            int m2 = 0, m3 = 0, m5 = 0, idx = 0;
            List<Integer> list = new ArrayList<>(index) { { add(1); } };
            while (idx < index) {
                int maxVal = list.get(idx);
                while (2*list.get(m2) <= maxVal) m2 += 1;
                while (3*list.get(m3) <= maxVal) m3 += 1;
                while (5*list.get(m5) <= maxVal) m5 += 1;
                int min = Math.min(2*list.get(m2), 3*list.get(m3));
                min = Math.min(min, 5*list.get(m5));
                list.add(min);
                idx += 1;
            }
            return list.get(index-1);
        }
    }


    public static void main(String[] args) {
//        System.out.println(new Solution().findUglyNumber(-1)); //0
//        System.out.println(new Solution().findUglyNumber(0)); //0
//        System.out.println(new Solution().findUglyNumber(1)); //1
//        System.out.println(new Solution().findUglyNumber(2)); //2
//        System.out.println(new Solution().findUglyNumber(3)); //3
//        System.out.println(new Solution().findUglyNumber(4)); //4
//        System.out.println(new Solution().findUglyNumber(5)); //5
//        System.out.println(new Solution().findUglyNumber(6)); //6
//        System.out.println(new Solution().findUglyNumber(7)); //8
//        System.out.println(new Solution().findUglyNumber(8)); //9
//        System.out.println(new Solution().findUglyNumber(1500)); //859963392

        System.out.println(new Solution2().findUglyNumber(-1)); //0
        System.out.println(new Solution2().findUglyNumber(0)); //0
//        System.out.println(new Solution2().findUglyNumber(1)); //1
//        System.out.println(new Solution2().findUglyNumber(2)); //2
//        System.out.println(new Solution2().findUglyNumber(3)); //3
//        System.out.println(new Solution2().findUglyNumber(4)); //4
//        System.out.println(new Solution2().findUglyNumber(5)); //5
//        System.out.println(new Solution2().findUglyNumber(6)); //6
        System.out.println(new Solution2().findUglyNumber(7)); //8
        System.out.println(new Solution2().findUglyNumber(8)); //9
        System.out.println(new Solution2().findUglyNumber(1500));
    }
}
