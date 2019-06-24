package nowcoder.netease;

/**
 * https://www.nowcoder.com/questionTerminal/711badc732d84bd98e4ac684e09791b8
 *
 * @author Apollo4634
 * @create 2019/06/24
 */

class Base {
    public void method() {
        System.out.println("Base");
    }
}

class Son extends Base {
    public void method() {
        System.out.println("Son");
    }

    public void methodB() {
        System.out.println("SonB");
    }
}


public class PolymorphismTest {
    public static void main(String[] args) {
        Base base = new Son();
        base.method();
        //base.methodB(); //编译不通过
    }
}
