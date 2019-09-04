package book_9787121310928;

import java.util.Stack;

/**
 * 礼物的最大价值：
 * 在一个 m*n 的棋盘中的每一个格都放一个礼物，每个礼物都有一定的价值（价值大于0）。
 * 你可以从棋盘的左上角开始拿各种里的礼物，并每次向左或者向下移动一格，直到到达棋盘的右下角。
 * 给定一个棋盘及上面个的礼物，请计算你最多能拿走多少价值的礼物？
 *
 * @author Apollo4634
 * @create 2019/09/04
 */

public class Problem_47 {

    static class Solution {
        private int M, N, maxValue;

        int getMaxValue(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return 0;
            if (matrix[0] == null || matrix[0].length == 0) return 0;
            M = matrix.length;
            N = matrix[0].length;
            if (M == 1 && N == 1) return matrix[0][0];
            maxValue = Integer.MIN_VALUE;
            update(matrix, 0, 0, 0);
            return maxValue;
        }

        private void update(int[][] matrix, int value, int row, int col) {
            value += matrix[row][col];
            if (row == M - 1 && col == N - 1 && value > maxValue) maxValue = value;
            if (row + 1 < M) update(matrix, value, row + 1, col);
            if (col + 1 < N) update(matrix, value, row, col + 1);
        }
    }


    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                { 1,10,3,8 },
                { 12,2,9,6 },
                { 5,7,4,11 },
                { 3,7,16,5 }
        };

//        int[][] matrix = new int[][] {
//                { 1,10,3,8 }
//        };

//        int[][] matrix = new int[][] {
//                { 1 }, { 12 }, { 5 }, { 3 }
//        };

//        int[][] matrix = new int[][] {
//                { 1 }
//        };

//        int[][] matrix = null;

        int ret = new Solution().getMaxValue(matrix);
        System.out.println(ret);
    }
}
