package interview.baidu;

import java.util.Arrays;

/**
 * 在不使用辅助变量的情况下交换两个数字
 *
 * 为了避免溢出的问题，这里对同号和异号两种情况做了不同处理
 *
 * @author Apollo4634
 * @create 2019/09/24
 */

public class SwapTwoNumsWithoutTempVariable {

    private static int[] swap(int a, int b) {
        if (a == b) return new int[] { a,b };

        if (a>0 ^ b>0) { //异号
            a = a + b;
            b = a - b;
            a = a - b;
        } else { //同号
            a = a - b;
            b = a + b;
            a = b - a;
        }
        return new int[] { a,b };
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(swap(10, 7)));
        System.out.println(Arrays.toString(swap(9, -8)));
        System.out.println(Arrays.toString(swap(Integer.MAX_VALUE, Integer.MAX_VALUE-1)));
        System.out.println(Arrays.toString(swap(Integer.MIN_VALUE, Integer.MIN_VALUE+1)));
        System.out.println(Arrays.toString(swap(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        System.out.println(Arrays.toString(swap(Integer.MAX_VALUE, Integer.MIN_VALUE)));
    }
}
