package interview.iqiyi;

import java.util.Scanner;

/**
 * 没有AC 不知道为什么
 *
 * @author Apollo4634
 * @create 2019/09/08
 */

public class ArrangementCounter {
    private static int N;
    private static boolean[] visited;
    private static int count;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        N = sc.nextInt();
//        int[] A = new int[N-1];
//        for (int i = 0; i < N-1; i++) {
//            A[i] = sc.nextInt();
//        }

        N = 5;
        int[] A = new int[] { 1,1,0,1 };

        count = 0;
        visited = new boolean[N+1];
        for (int n = 1; n <= N; n++) {
            visited[n] = true;
            arrange(A, 0, n);
            visited[n] = false;
        }
        count %= 1000000007;
        System.out.println(count);
    }

    private static void arrange(int[] A, int idx, int P) {
        if (idx == N-2) {
            for (int n = 1; n <= N; n++) {
                if (visited[n]) continue;
                if ((A[N-2] == 1 && P > n) || (A[N-2] == 0 && P < n)) {
                    increase();
                }
            }
            return;
        }

        for (int n = 1; n <= N; n++) {
            if (visited[n]) continue;
            if ((A[idx] == 1 && P > n) || (A[idx] == 0 && P < n)) {
                visited[n] = true;
                arrange(A, idx+1, n);
                visited[n] = false;
            }
        }
    }

    private static void increase() {
        count += 1;
        if (count > 1000000007) count %= 1000000007;
    }
}
