package book_9787121310928;

import java.util.Objects;

/**
 * 矩阵中的路径
 * 给定一个字符矩阵和一个字符串，
 * 判断矩阵中是否存在由字符串中字符构成的四邻域路径
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_12 {

    static class Solution {
        boolean[][] marked;
        int rows;
        int cols;

        boolean exist(char[][] matrix, String string) {
            Objects.requireNonNull(matrix);
            Objects.requireNonNull(string);
            rows = matrix.length;
            cols = matrix[0].length;
            marked = new boolean[rows][cols];
            char[] chars = string.toCharArray();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (search(matrix, r, c, chars, 0)) return true;
                }
            }
            return false;
        }

        private boolean search(char[][] matrix, int row, int col, char[] chars, int idx) {
            if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
            if (!marked[row][col] && matrix[row][col] == chars[idx]) {
                System.out.println(row+" "+col+" "+matrix[row][col]);
                if (idx == chars.length-1) return true;
                marked[row][col] = true;
                if (search(matrix, row-1, col, chars, idx+1)) return true; //upper
                if (search(matrix, row, col+1, chars, idx+1)) return true; //right
                if (search(matrix, row+1, col, chars, idx+1)) return true; //lower
                if (search(matrix, row, col-1, chars, idx+1)) return true;; //left
                marked[row][col] = false;
            }
            return false;
        }
    }


    public static void main(String[] args) {
        char[][] matrix = new char[][] {
                { 'a','b','t','g' },
                { 'c','f','c','s' },
                { 'j','d','e','b' }
        };
        //String string = "bfce";
        String string = "bfcb";

        System.out.println(new Solution().exist(matrix, string));
    }
}
