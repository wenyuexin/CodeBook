package book_9787121310928;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 队列的最大值：
 * 滑动窗口的最大值：
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
 * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，
 * 他们的最大值分别为{4,4,6,6,6,5}；针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
 * {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 *
 * @author Apollo4634
 * @create 2019/09/16
 */

public class Problem_59_1 {
    public static class Solution {
        ArrayList<Integer> maxInWindows(int[] nums, int size) {
            ArrayList<Integer> list = new ArrayList<>();
            if (nums == null || nums.length == 0 || size <= 0 || size > nums.length) return list;

            PriorityQueue<Integer> pq = new PriorityQueue<>(size, Comparator.reverseOrder());
            for (int i = 0; i < size; i++) {
                pq.add(nums[i]);
            }
            list.add(pq.peek());

            int firstNumIdx = 0;
            for (int i = size; i < nums.length; i++) {
                pq.remove(nums[firstNumIdx]);
                pq.add(nums[i]);
                list.add(pq.peek());
                firstNumIdx += 1;
            }
            return list;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 2,3,4,2,6,2,5,1 };
        System.out.println(new Solution().maxInWindows(nums, 3));
        System.out.println(new Solution().maxInWindows(nums, 4));
    }
}
