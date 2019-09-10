package interview.baidu;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/10
 */
public class P1 {
    static class XY implements Comparable {
        int x; int y;

        @Override
        public int compareTo(Object obj) {
            return this.x - ((XY) obj).x;
        }
    }

    private static long count;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        XY[] arr = new XY[n];
        for (int i = 0; i < n; i++) {
            arr[i].x = sc.nextInt();
            arr[i].y = sc.nextInt();
        }
        Arrays.sort(arr);

        count = 0;
        int idx = n - 1;
        for (; idx >= 0; idx--) {
            if (arr[idx].x >= m) {
                count += arr[idx].y;
            }
        }

        int left = 0;
        for (; idx >= 0; idx--) {
            int restM = m - arr[idx].x;
            int restY = arr[idx].y;
            while (restY > 0 && left <= idx) {
                int num = (int) Math.ceil(1.0*restM/arr[left].x);
                if (arr[left].y >= num) {
                    restY -= 1;
                    arr[left].y -= num;
                    count += 1;
                } else { //last one
                    restM -= arr[left].x * arr[left].y;
                    arr[left++].y = 0;
                    while (restM > 0 && left <= idx) {
                        break;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
