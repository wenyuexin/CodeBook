package book_9787121310928;

import java.util.ArrayList;

/**
 * 和为s的数字：
 * 和为s的连续正数序列：
 * 输入一个正数s，输出所有和为S的连续正数序列（至少含有两个数）。
 * 序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
 *
 * @author Apollo4634
 * @create 2019/09/16
 */

public class Problem_57_2 {
    public static class Solution {
        ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
            ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

            int low = 1, high = sum - 1;
            int left = 1, right = high;
            while (left <= sum/2) {
                while (low < high) {
                    int temp = (low + high) * (high - low + 1) / 2;
                    if (temp == sum) {
                        left = low;
                        right = high;
                        ArrayList<Integer> list = new ArrayList<>();
                        for (int i = left; i <= right; i++)
                            list.add(i);
                        lists.add(list);
                        break;
                    } else if (temp < sum) {
                        low += 1;
                    } else {
                        high -= 1;
                    }
                }
                low = ++left;
                high = ++right;
                if (high >= sum) high = sum - 1;
            }
            return lists;
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().FindContinuousSequence(10));
        System.out.println(new Solution().FindContinuousSequence(15));
    }
}
