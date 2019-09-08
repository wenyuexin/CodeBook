package interview.bytedance.date190908;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/09/08
 */

public class P2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        int k = sc.nextInt();
        if (x==k || y==k || z==k) {
            System.out.println(1); return;
        }
        int[] capacities = new int[] { x,y,z };
        x = capacities[0]; y = capacities[1]; z = capacities[2];

        int count = -1;
        int a = 0, b = 0, c = 0;
        Arrays.sort(capacities);


        System.out.println(count);
    }
}
