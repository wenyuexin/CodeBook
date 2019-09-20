package interview.tencent;

import java.util.Arrays;
import java.util.Scanner;

/**
 * AC
 *
 * @author Apollo4634
 * @create 2019/09/20
 */

public class P2 {
    private static class XY implements Comparable {
        int x; int y;

        @Override
        public int compareTo(Object obj) {
            return ((XY) obj).y - this.y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        XY[] arr = new XY[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new XY();
            arr[i].x = sc.nextInt();
            arr[i].y = sc.nextInt();
        }
        Arrays.sort(arr);

        int max = Integer.MIN_VALUE;
        int left = 0;
        int right = arr.length - 1;
        while (right >= 0 && arr[right].x > 0) {
            while (arr[right].x > 0) {
                if (left < right && arr[left].x == 0) left += 1;
                int value = arr[left].y + arr[right].y;
                arr[left].x -= 1;
                arr[right].x -= 1;
                if (value > max) max = value;
            }
            right -= 1;
        }
        System.out.println(max);
    }
}
