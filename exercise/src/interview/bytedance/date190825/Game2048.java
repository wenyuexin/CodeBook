package interview.bytedance.date190825;

import java.util.Scanner;

/**
 * 2048游戏
 *
 * 在不考虑移动时随机生成的数字的情况下，计算单步运动的结果
 *
 * @author Apollo4634
 * @create 2019/08/25
 */

public class Game2048 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int direction = sc.nextInt();
        int[][] matrix = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        int[] line;
        if (direction == 3)
            for (int i = 0; i < 4; i++) lineTransform(matrix[i],false);

        if (direction == 4)
            for (int i = 0; i < 4; i++) lineTransform(matrix[i],true);

        if (direction == 1) {
            reverse(matrix);
            for (int i = 0; i < 4; i++) lineTransform(matrix[i], false);
            reverse(matrix);
        }

        if (direction == 2) {
            reverse(matrix);
            for (int i = 0; i < 4; i++) lineTransform(matrix[i], true);
            reverse(matrix);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    private static void lineTransform(int[] line, boolean flip) {
        if (flip) flipLine(line);
        int idx = 0, i = 1;
        for (; i < 4; i++) {
            if (line[i] == 0 && line[i-1] != 0) {
                line[idx++] = line[i - 1]; break;
            }
            if (line[i] == line[i-1]) {
                line[idx++] = 2*line[i++];
            } else if (line[i-1] != 0) {
                line[idx++] = line[i-1];
            }
        }
        if (i == 4) line[idx++] = line[3];
        for (int j = idx; j < 4; j++)  line[j] = 0;
        if (flip) flipLine(line);
    }

    private static void flipLine(int[] line) {
        int temp = line[0]; line[0] = line[3]; line[3] = temp;
        temp = line[1]; line[1] = line[2]; line[2] = temp;
    }

    private static void reverse(int[][] temp) {
        for (int i = 0; i < 4; i++) {
            for (int j = i; j < 4; j++) {
                int k = temp[i][j]; temp[i][j] = temp[j][i]; temp[j][i] = k ;
            }
        }
    }
}
