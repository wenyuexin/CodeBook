package book_9787121310928;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

/**
 * 数组中的逆序对：
 * 在数组中的两个数字如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组，求出这个数组中的逆序对的总数。例如在数组｛7，5，6，4｝中，
 * 一共存在5对逆序对，分别是(7,6)，(7,5)，(7,4)，(6,4)，(5,4)。
 *
 * @author Apollo4634
 * @create 2019/09/06
 */

public class Problem_51 {

    //这种方法运行速度慢，复杂度偏高
    static class Solution {
        int InversePairs(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            TreeSet<Integer> set = new TreeSet<>();
            set.add(nums[0]);

            int count = 0;
            for (int i = 1; i < nums.length; i++) {
                count += set.tailSet(nums[i]).size();
                set.add(nums[i]);
            }
            return count%1000000007;
        }
    }


    static class Solution2 {
        private int[] copy;

        int InversePairs(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            copy = new int[nums.length];
            int count = inverse(nums, 0, nums.length-1);
            return count%1000000007;
        }

        private int inverse(int[] nums, int from, int to) {
            if (from >= to) return 0;
            int mid = (from + to) / 2;
            int count = inverse(nums, from, mid);
            count += inverse(nums, mid+1, to);

            int idx1 = mid, idx2 = to, idx = to;
            while (idx1 >= from || idx2 > mid) {
                if (idx1 >= from && (idx2 <= mid || nums[idx1] > nums[idx2])) {
                    copy[idx--] = nums[idx1--];
                } else {
                    copy[idx--] = nums[idx2--];
                    count += (mid - idx1);
                }
            }
            System.arraycopy(copy, from, nums, from, to-from+1);
            return count;
        }
    }


    //以下解法来自 Github - CyC2018/CS-Notes
    static class Solution3 {
        private long cnt = 0;
        private int[] tmp;  // 在这里声明辅助数组，而不是在 merge() 递归函数中声明

        int InversePairs(int[] nums) {
            tmp = new int[nums.length];
            mergeSort(nums, 0, nums.length - 1);
            return (int) (cnt % 1000000007);
        }

        private void mergeSort(int[] nums, int l, int h) {
            if (h - l < 1)
                return;
            int m = l + (h - l) / 2;
            mergeSort(nums, l, m);
            mergeSort(nums, m + 1, h);
            merge(nums, l, m, h);
        }

        private void merge(int[] nums, int l, int m, int h) {
            int i = l, j = m + 1, k = l;
            while (i <= m || j <= h) {
                if (i > m)
                    tmp[k] = nums[j++];
                else if (j > h)
                    tmp[k] = nums[i++];
                else if (nums[i] <= nums[j])
                    tmp[k] = nums[i++];
                else {
                    tmp[k] = nums[j++];
                    this.cnt += m - i + 1;  // nums[i] > nums[j]，说明 nums[i...mid] 都大于 nums[j]
                }
                k++;
            }
            for (k = l; k <= h; k++)
                nums[k] = tmp[k];
        }
    }


    public static void main(String[] args) {
//        int[] nums = new int[] { 7,5,6,4 };
//        int[] nums = new int[] {
//                364,637,341,406,747,995,234,971,571,219,993,407,416,366,315,301,601,650,418,355,460,505,360,965,516,
//                648,727,667,465,849,455,181,486,149,588,233,144,174,557, 67,746,550,474,162,268,142,463,221,882,576,
//                604,739,288,569,256,936,275,401,497, 82,935,983,583,523,697,478,147,795,380,973,958,115,773,870,259,
//                655,446,863,735,784,  3,671,433,630,425,930, 64,266,235,187,284,665,874, 80, 45,848, 38,811,267,575};

        String filepath = "./exercise/src/book_9787121310928/data/P51_0_01.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String[] numStrs = reader.readLine().split(",");
            int[] nums = Arrays.stream(numStrs)
                    .filter(Objects::nonNull)
                    .filter(t -> t.length()>0)
                    .mapToInt(Integer::valueOf)
                    .toArray();
            long t1 = System.nanoTime();
            System.out.println(new Solution2().InversePairs(nums.clone()));
            long t2 = System.nanoTime();
            System.out.println(new Solution3().InversePairs(nums.clone()));
            long t3 = System.nanoTime();
            System.out.println((t2-t1)/1e6 + " ms");
            System.out.println((t3-t2)/1e6 + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
