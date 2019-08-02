/**
 * 这个测试倒更像是脑筋急转弯，主要考察线程的调用方式
 *
 * 线程只用通过start的方式启动才能实现多线程的效果。
 * 通过run方法调用仍然是单线程顺序执行
 *
 * @author Apollo4634
 * @create 2019/07/29
 */

public class ThreadTest {
    public static void main(String[] args) {
        Runnable task = () -> System.out.println("task");

        Thread thread = new Thread(task);
        thread.run();
        System.out.println("ThreadTest");
    }
}
