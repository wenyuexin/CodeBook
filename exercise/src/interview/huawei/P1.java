package interview.huawei;

import java.util.Scanner;

/**
 * int整数的二进制流中101出现的次数和首次出现的位置。
 * 位置从又开始计数，最右边的字符位置是0。
 *
 * @author Apollo4634
 * @create 2019/08/31
 */

public class P1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        String str = Integer.toBinaryString(num);
        str = new StringBuilder(str).reverse().toString();

        int count = 0;
        int firstIndex = -1;
        for (int i = 0; i < str.length()-2; i++) {
            char ch1 = str.charAt(i);
            char ch2 = str.charAt(i+1);
            char ch3 = str.charAt(i+2);
            if (ch1=='1' && ch2=='0' && ch3=='1') {
                if (count == 0) firstIndex = i;
                count += 1;
            }
        }
        System.out.println(count+" "+firstIndex);
    }
}
