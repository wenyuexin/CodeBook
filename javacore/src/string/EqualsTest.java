package string;

/**
 * @author Apollo4634
 * @create 2019/09/09
 */

public class EqualsTest {

    public static void main(String[] args) {
        String s = "hello";
        String t = "hello";
        char[] chars = { 'h','e','l','l','o' };
        System.out.println(t.equals(chars));
        System.out.println(s == t);
    }
}
