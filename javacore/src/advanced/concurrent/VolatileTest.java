package advanced.concurrent;


/**
 * 此代码修改自《深入理解Java虚拟机：JVM高级特性与最佳实践》中
 * P366的12.3.3节上的代码
 *
 * 书中本意在于说明volatile关键字不能保证变量的一致性，
 * 但实际运行发现程序会进入死循环，调试发现在程序运行的后期，
 * 始终存在两个线程在运行，推测是Thread.yield()的问题
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
        final int threadNum = 10;

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            //System.out.println(Thread.activeCount());
            Thread.yield();
        }

        System.out.println(cnt);
    }
}
