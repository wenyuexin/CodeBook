package book_9787121310928;

import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 数据流中的中位数
 *
 * 设置一个数据集，每次插入两个数，然后删除最大值和最小值，
 * 直到数据流排空。然后计算中间两个数的平均值，或者中间数的数值
 *
 * @author Apollo4634
 * @create 2019/08/19
 */

public class Problem_41 {

    static class Solution {

        private int[] integerDeque = new int[] {
                0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0
        };

        private void insert(int n1, int n2) {
            integerDeque[0] = n1;
            integerDeque[3] = n2;
            Arrays.sort(integerDeque);
        }

        private int[] getNums() {
            return new int[] { integerDeque[1], integerDeque[2] };
        }

        int findMedian(Reader reader) {
            int n1, n2;

//            while (n1=reader.() != -1) {
//
//            }

            return 0;
        }
    }


    public static void main(String[] args) {
//        int[] nn = new Random().ints(12, -100, 100).toArray();
//        System.out.println(Arrays.toString(nn));

//        int[] nnn = new int[] {92, 34, -32, 34, 55, 52, -62, -13, 5, 25, -12, 77};
//        Arrays.sort(nnn);
//        System.out.println(Arrays.toString(nnn));

        Scanner sc = new Scanner(System.in);


        List<Integer> nums = new LinkedList<>();
        String[] strs = sc.nextLine().split(" ");
        for (String str : strs) {
            if ("".equals(str)) continue;
            nums.add(Integer.parseInt(str));
        }

        System.out.println(nums);
    }
}
