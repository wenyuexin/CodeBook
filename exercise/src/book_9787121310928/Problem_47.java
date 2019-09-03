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
        int getMaxValue(int[][] matrix) {
            if (matrix == null || matrix.length == 0 ) return 0;
            if (matrix[0] == null || matrix[0].length == 0) return 0;
            int mm = matrix.length - 1;
            int nn = matrix[0].length - 1;
            if (mm == 0 && nn == 0) return matrix[0][0];
            int maxValue = Integer.MIN_VALUE;

            //采用类似BSF的方法搜索
            int mCount = 0;
            int nCount = 0;
            int value;
            boolean flag = true;
            Stack<Boolean> toLeft = new Stack<>();
            toLeft.add(false);
            toLeft.add(true);

            value = matrix[0][0];
            while (!toLeft.isEmpty()) {
                boolean direction = toLeft.pop();
                if (direction) {
                    mCount += 1;

                }

                for (int i = 0; i < mm; i++) {
                    toLeft.add(true);
                }
                if (mCount == mm && nCount == nn) flag = false;
                for (int i = 0; i < nn; i++) {


                }
                if (value > maxValue) maxValue = value;
            }

            return maxValue;
        }
    }
}
