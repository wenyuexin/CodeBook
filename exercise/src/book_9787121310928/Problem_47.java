package book_9787121310928;

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
        int getMaxValue(int[][] matrix) {
            if (matrix == null || matrix.length == 0 ) return 0;
            if (matrix[0] == null || matrix[0].length == 0) return 0;
            int mm = matrix.length - 1;
            int nn = matrix[0].length - 1;
            int maxValue = matrix[0][0];

            for (int r = 0; r < mm; r++) {
                for (int c = 0; c < nn; c++) {

                }
                
            }
            return maxValue;
        }
    }
}
