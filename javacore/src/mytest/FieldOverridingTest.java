package mytest;

/**
 * java中的字段不能重写
 *
 * @author Apollo4634
 * @create 2019/09/04
 */

public class FieldOverridingTest {
    static class Super {
        final String s = "Super";
    }

    static class Sub extends Super {
        final String s = "Sub";
    }

    public static void main(String[] args) {
        Sub c1 = new Sub();
        Super c2 = new Sub();
        System.out.println(c1.s);
        System.out.println(c2.s);
    }
}
