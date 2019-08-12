package book_9787121310928;

/**
 * 机器人的运动范围
 * 有一个m行n列的格盘，从(0,0)出发，求限制条件下能到达的格子总数
 * 限制如下：不能进入坐标各个位上的数字之和为k的格子
 * 例如k=18时不能进入(35,37)，因为3+5+3+7=18
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_13 {

    static class Solution {
        private boolean[][] marked;
        private int count;
        private int rows;
        private int cols;
        private int k;
        private int[] cache;

        int numberOfReachableBlocks(int m, int n, int k) {
            if (m < 1 || n < 1)
                throw new IllegalArgumentException("Invalid rows or cols");
            if (k == 0) return 1;
            if (k < 0) return m*n;

            rows = m;
            cols = n;
            this.k = k;
            marked = new boolean[m][n];
            cache = new int[Math.max(m, n)];
            count = 0;
            search(0, 0);
            return count;
        }

        private void search(int row, int col) {
            if (row < 0 || row >= rows || col < 0 || col >= cols) return;
            if (!marked[row][col] && isReachable(row, col)) {
                marked[row][col] = true;
                count += 1;
                search(row - 1, col);//upper
                search(row, col + 1);//right
                search(row + 1, col);//lower
                search(row, col - 1);//left
            }
        }

        private boolean isReachable(int row, int col) {
            int sum = 0;
            if (row > 0) sum += getCache(row);
            if (col > 0) sum += getCache(col);
            return sum != k;
        }

        private int getCache(int num) {
            if (cache[num] == 0) {
                int sum = 0;
                while (num != 0) {
                    sum += num%10;
                    num /= 10;
                }
                cache[num] = sum;
            }
            return cache[num];
        }
    }


    public static void main(String[] args) {
        System.out.println(new Solution().numberOfReachableBlocks(5, 4, 3));
        System.out.println(new Solution().numberOfReachableBlocks(5, 4, -1));
        System.out.println(new Solution().numberOfReachableBlocks(5, 4, 0));
        System.out.println(new Solution().numberOfReachableBlocks(15, 20, 7));
    }
}
