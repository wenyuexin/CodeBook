/**
 * 此代码修改自《深入理解Java虚拟机：JVM高级特性与最佳实践》中
 * P366的12.3.3节上的代码
 *
 * 书中本意在于说明volatile关键字不能保证变量的一致性，
 * 但实际运行发现程序会进入死循环，调试发现在程序运行的后期，
 * 始终存在2个线程在运行
 *
 * 查阅相关资料发现：
 * IDEA在执行用户代码的时候，实际是通过反射方式去调用，
 * 而与此同时会创建一个Monitor Ctrl-Break 用于监控目的
 *
 * 通过Thread.currentThread().getThreadGroup().list()测试可以验证，
 * 除了main以外，还多了一个预期外的 Monitor Ctrl-Break 线程
 *
 * @author Apollo4634
 * @create 2019/05/16
 */

public class VolatileTest {
    private static volatile int cnt = 0;

    private static void increase() {
        cnt++;
    }

    public static void main(String[] args) {
        final int threadNum = 5;

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }

        //Thread.currentThread().getThreadGroup().list();
        while (Thread.activeCount() > 2) {
            //System.out.println(Thread.activeCount());
            Thread.yield();
        }
        //Thread.currentThread().getThreadGroup().list();
        System.out.println(cnt);
    }
}