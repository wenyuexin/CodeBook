package book_9787121310928;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * 数据流中的中位数
 *
 * 虽说是数据流，但和数据流关系不大。可以简单的理解成从整数数组中找中位数。
 * 这里强调数据流，只不过是可以按数据到达的顺序将其插入某种数据结构中。
 *
 * 有一个问题，即是否可以在不知道数据总数的前提下，将数据插入自定义的结构中，
 * 并且提前将部分数据删除。之所以有这个问题，是受到以下问题的影响：
 * 从大量数据中找最小的k个数（该问题可以用最大堆实现，而不需要保存所有数据）。
 * 实际上不能，假设已经接收了n个数的集合A1，那么后续可能收到m个数的集合A2，
 * 有可能min(A2)>max(A1)，有可能max(A2)<min(A1)，也有可能是A1和A2的交集不为空，
 * 那么已经收到的A1中的n个数都可以是中位数。因此，总体上需要保存所有收到的数。
 *
 * 至此，可知数据流相当于先对给定的数组循环一遍，最终仍然需要根据所有数据来计算。
 * 这里追求的只是循环结束之后可以直接得到结果。
 *
 *
 *
 * @author Apollo4634
 * @create 2019/08/19
 */

public class Problem_41 {

    static class Solution {
        double findMedian(BufferedReader reader) throws IOException {
            int n1, n2;
            String line;
            while ((line = reader.readLine()) != null) {
                n1 = Integer.parseInt(line);

            }
            return 0;
        }
    }


    public static void main(String[] args) {
        int numsLen = 10;
        int[] nums = new Random().ints(numsLen, -100, 100).toArray();
        System.out.println(Arrays.toString(nums));
        try (FileWriter fileWriter = new FileWriter("./data.txt")) {
            for (int num : nums) {
                fileWriter.write(num+"\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        double midVal = nums.length%2==0 ? (nums[numsLen/2-1]+nums[numsLen/2])/2.0 : nums[numsLen/2];
        System.out.println(midVal);

        try (FileReader fileReader = new FileReader("./data.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            double ret = new Solution().findMedian(reader);
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
