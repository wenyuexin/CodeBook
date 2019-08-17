package book_9787121310928;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 数组中出现次数超过一半的数字：
 *
 * 很明显可以用一个hashmap存储每个数字出现的次数，
 * 只需要遍历一遍数组即可，这个方法的时间复杂度取决于map
 *
 * 书上提示，如果将数组排序，那么排序数组的中位数就是所求。
 * 除了直接排序后找到中间那个数，还可以使用最大堆，
 * 也可以像快排那样，选一个数为分界将数组分为大于和小于两部分
 *
 * @author Apollo4634
 * @create 2019/08/17
 */

public class Problem_39 {

    static class Solution {
        int findNumberOfOccurrencesMoreThanHalf(int[] nums) {
            Objects.requireNonNull(nums);
            if (nums.length == 0) throw new IllegalArgumentException();
            if (nums.length == 1) return nums[0];
            int halfLength = (int) (nums.length/2.0 + 1);
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                if (!map.containsKey(num)) { map.put(num, 1); continue; }
                int count = map.get(num) + 1;
                if (count >= halfLength) return num;
                map.put(num, count);
            }
            throw new IllegalArgumentException("Number of occurrences more than half does not exist");
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] { 1,2,3,2,2,2,5,4,2 };
        int ret = new Solution().findNumberOfOccurrencesMoreThanHalf(nums);
        System.out.println(ret);
    }
}
