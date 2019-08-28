package wrapper;

import java.util.concurrent.TimeUnit;

/**
 * 1）Integer是一个不可变类，内部使用private final int value记录数值
 * 2）Integer重写了hashCode方法，调用后直接返回数值
 * 3）Java的运行时常量池缓存了-128~127之间的数，
 * 在使用valueOf时如果常量池中存在对应数值，则会直接只读而不是创建新变量
 *
 * @author Apollo4634
 * @create 2019/08/28
 */

public class IntegerWrapper {

    public static void main(String[] args) {
        Integer integer = 127;
        Integer integer2 = 127;
        System.out.println(integer.hashCode());
        System.out.println(integer2.hashCode());
        System.out.println(integer == integer2); //true

        Integer integer3 = 128;
        Integer integer4 = 128;
        System.out.println(integer3.hashCode());
        System.out.println(integer4.hashCode());
        System.out.println(integer3 == integer4); //false

        Integer integer5 = new Integer(126); //Deprecated since java 9
        Integer integer6 = new Integer(126);
        System.out.println(integer5 == integer6); //false
    }
}
