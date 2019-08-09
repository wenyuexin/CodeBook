package book_9787121310928;

import java.util.Arrays;

/**
 * 二维有序数组中查找数字
 *
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_04 {
    static class Solution {
        boolean exist(int[][] matrix, int target) {
            int maxRow = matrix[0].length;
            for (int[] ints : matrix) {
                if (ints[0] == target) return true;
                if (ints[0] > target) return false;
                maxRow = Arrays.binarySearch(ints, 1, maxRow, target);
                if (maxRow >= 0) return true;
                maxRow = -(maxRow + 1);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                { 1,2,8,9 },
                { 2,5,9,12 },
                { 4,7,10,13 },
                { 6,8,11,15 }
        };

        int target = 5;

        boolean ret = new Solution().exist(matrix, target);
        System.out.println(ret);
    }
}
