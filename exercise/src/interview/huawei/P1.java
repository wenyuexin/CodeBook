package interview.huawei;

import java.util.LinkedList;
import java.util.Queue;

/**
 * int整数的二进制流中101出现的次数和首次出现的位置
 *
 * @author Apollo4634
 * @create 2019/08/31
 */

public class P1 {

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int num = sc.nextInt();

        System.out.println(0x80000000);

        int num = 0;
        if (num == Integer.MIN_VALUE) {
            System.out.println(0+" "+-1); return;
        } else if (num == Integer.MAX_VALUE) {
            System.out.println(0+" "+-1); return;
        }

        //String str = Integer.toBinaryString(num);

        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            String str = Integer.toBinaryString(num);

            int flag = 1;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 32; j++) {
                sb.append(flag&num);
                flag = flag << 1;
            }
            System.out.println(sb.toString());
        }




//        int count = 0;
//        int firstIndex = -1;
//        for (int i = 0; i < str.length()-2; i++) {
//            char ch1 = str.charAt(i);
//            char ch2 = str.charAt(i+1);
//            char ch3 = str.charAt(i+2);
//            if (ch1=='1' && ch2=='0' && ch3=='1') {
//                if (count == 0) firstIndex = i;
//                count += 1;
//            }
//        }
//        System.out.println(count+" "+firstIndex);
    }
}
