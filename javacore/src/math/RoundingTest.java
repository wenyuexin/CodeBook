package math;

/**
 * 枚举类RoundingMode（主要用于BigDecimal类）
 *
 * CEILING: 向负无穷取整
 * FLOOR: 向正无穷取整
 * DOWN: 向零取整
 * UP：向远离零的方向取整
 * HALF_DOWN: 向最近的整数取整，如果相等则向下取整
 * HALF_EVEN: 向最近的整数取整，如果相等则向偶数
 * HALF_UP: 向最近的整数取整，如果相等则向上取整
 * UNNECESSARY：没必要取整（即数字的小数部分已经等于0），若不满足则抛出ArithmeticException
 *
 * @author Apollo4634
 * @create 2019/09/09
 */

public class RoundingTest {
    public static void main(String[] args) {
        System.out.println(1.0);

        System.out.println(Math.round(-1.2));
        System.out.println(Math.round(-1.5));
        System.out.println(Math.round(-1.6));

        System.out.println(Math.ceil(-1.2));
        System.out.println(Math.ceil(-1.5));
        System.out.println(Math.ceil(-1.6));

        System.out.println(Math.floor(-1.2));
        System.out.println(Math.floor(-1.5));
        System.out.println(Math.floor(-1.6));
    }
}
