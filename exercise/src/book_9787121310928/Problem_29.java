package book_9787121310928;

import java.util.LinkedList;
import java.util.List;

/**
 * 从外向里的顺时针打印矩阵
 *
 * 参考leetcode 第54题 Spiral Matrix
 * 可以顺带参考leetcode 第48题 Rotate Image
 *
 * 方法由好几种，这里用的是由外向里逐层遍历的方式，
 * 每一层可以拆分为4条边，自认为这种思路比较直观，关键是注意范围
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_29 {

    static class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> list = new LinkedList<>();
            if (matrix == null || matrix.length == 0)  return list;
            int rows = matrix.length;
            int cols = matrix[0].length;
            int nLayer = (Math.min(rows, cols)+1) / 2;
            for (int i = 0; i < nLayer; i++) {
                addElements(matrix, list, true, i, i, cols-i-1, 1);
                addElements(matrix, list, false, cols-i-1, i+1, rows-i-1, 1);
                if (rows-i-1 != i)
                    addElements(matrix, list, true, rows-i-1, cols-i-2, i, -1);
                if (cols-i-1 != i)
                    addElements(matrix, list, false, i, rows-i-2, i+1, -1);
            }
            return list;
        }

        private void addElements(int[][] matrix, List<Integer> list, boolean inRow, int line, int from, int to, int step) {
            if (step > 0 && from > to) return;
            if (step < 0 && from < to) return;
            to += step;
            if (inRow) {
                for (int i = from; i != to; i += step)
                    list.add(matrix[line][i]);
            } else {
                for (int i = from; i != to; i += step)
                    list.add(matrix[i][line]);
            }
        }
    }
}
