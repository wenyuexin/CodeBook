package interview.bytedance.date190908;

import java.util.Scanner;

/**
 * 机器人身高问题
 *
 * AC
 *
 * @author Apollo4634
 * @create 2019/09/08
 */

public class P1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        int N = sc.nextInt();
//        int[] heights = new int[N];
//        for (int i = 0; i < N; i++) {
//            heights[i] = sc.nextInt();
//        }

        int N = 4;
        int[] heights = new int[] { 6,5,3,4 };

        int[] counts = new int[N];
        for (int i = N-1; i > 0; i--) {
            int height = heights[i];
            for (int j = i-1; j >= 0; j--) {
                if (heights[j] < height) continue;
                counts[j] += 1; break;
            }
        }

        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < N; i++) {
            if (counts[i] > max) {
                max = counts[i];
                index = i;
            }
        }
        System.out.println(heights[index]);
    }
}
