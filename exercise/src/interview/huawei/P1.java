package interview.huawei;

import java.util.Scanner;

/**
 * int整数的二进制流中101出现的次数和首次出现的位置。
 *
 * 1、输出次数和首次出现的位置中间用空格隔开。
 * 2、位置从0开始,即最右边位置为0 ,向左依次增加。
 * 3、如果该int型整数中没有找到该比特块,次数返回0，位置返回-1.
 * 4、比特位允许重复使用，如10101中的中间的比特1即可以与前面的01组成101,
 * 也可以与后面的10组成101.
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
