package interview.yuanfudao;

import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/16
 */

public class P1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] matrix = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        int nLayer = (int) Math.ceil(Math.min(N, M) / 2.0);
        for (int i = 0; i < nLayer; i++) {
            print(matrix, i, i, N-i-1, 1, true);
            print(matrix, N-i-1, i+1, M-i-1, 1, false);
            if (M-i-1 != i) print(matrix, M-i-1, N-i-2, i, -1, true);
            if (i != N-i-1) print(matrix, i, M-i-2, i+1, -1, false);
        }
        System.out.println();
    }

    private static void print(int[][] matrix, int line, int from, int to, int step, boolean isVetical) {
        if (step > 0 && from < to) return;
        if (step < 0 && from > to) return;

        if (!isVetical) {
            for (int i = from; i != to; i += step) {
                System.out.print(matrix[line][i] + " ");
            }
        } else {
            for (int i = from; i != to; i += step) {
                System.out.print(matrix[i][line] + " ");
            }
        }
    }
}
