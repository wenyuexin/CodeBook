package book_9787121310928;

import java.util.Arrays;
import java.util.HashSet;
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

    static class Solution {
        int findUglyNumber(int index) {
            if (index < 1) return 0;
            if (index == 1) return 1;

            Set<Integer> current = new HashSet<>() { { add(1); } };
            TreeSet<Integer> treeSet = new TreeSet<>() { { add(1); } };
            while (!current.isEmpty()) {
                Set<Integer> next = new HashSet<>();
                for (Integer integer : current) {
                    next.addAll(Arrays.asList(integer * 2, integer * 3, integer * 5));
                    treeSet.addAll(next);
                }

                if (treeSet.size() > index) {
                    Integer number = 0;
                    for (int i = treeSet.size() - index; i >= 0; i--) {
                        number = treeSet.pollLast();
                    }
                    return number == null ? 0 : number;
                }
                current = next;
            }
            return 0;
        }
    }


    public static void main(String[] args) {
//        System.out.println(new Solution().findUglyNumber(-1)); //0
//        System.out.println(new Solution().findUglyNumber(0)); //0
//        System.out.println(new Solution().findUglyNumber(1)); //1
        System.out.println(new Solution().findUglyNumber(2)); //2
        System.out.println(new Solution().findUglyNumber(3)); //3
        System.out.println(new Solution().findUglyNumber(4)); //4
        System.out.println(new Solution().findUglyNumber(5)); //5
        System.out.println(new Solution().findUglyNumber(6)); //6
        System.out.println(new Solution().findUglyNumber(7)); //9
        System.out.println(new Solution().findUglyNumber(8)); //10
        System.out.println(new Solution().findUglyNumber(1500)); //1062882000
    }
}
