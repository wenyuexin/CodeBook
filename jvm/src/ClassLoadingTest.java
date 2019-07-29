/**
 * @author Apollo4634
 * @create 2019/06/10
 */

public class ClassLoadingTest {
    private String baseName = "base";

    public ClassLoadingTest() { //被调用
        System.out.println("create Base");
        call();//执行的是Sub的callName方法
    }

    public void call() {
        System.out.println(baseName); //没有执行
    }

    //静态内部类
    static class Sub extends ClassLoadingTest {
        private String baseName = "sub";

        public Sub() {
            System.out.println("create Sub");
        }

        public void call() {
            System.out.println(baseName); //执行并输出null
        }
    }

    public static void main(String[] args) {
        ClassLoadingTest b = new Sub();
    }
}
