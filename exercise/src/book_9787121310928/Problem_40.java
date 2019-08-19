package book_9787121310928;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 找出最小的k个数：
 * 输入n个整数，找出其中最小的k个数
 *
 * 用最小堆处理一下就可以
 *
 * @author Apollo4634
 * @create 2019/08/19
 */

public class Problem_40 {

    static class Solution {
        List<Integer> smallestKElements(int[] nums, int k) {
            if (nums == null || k < 1) return null;
            if (nums.length < k)
                throw new IllegalArgumentException("the length of nums is less than k");
            Queue<Integer> queue = new PriorityQueue<>();
            for (int num : nums) {
                queue.add(num);
            }

            List<Integer> ret = new LinkedList<>();
            for (int i = 0; i < k; i++) {
                ret.add(queue.remove());
            }
            return ret;
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 4,5,6,2,7,3,8 };
        int k = 4;
        System.out.println(new Solution().smallestKElements(nums, k));
    }
}
