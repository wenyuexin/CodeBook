import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

/**
 * @author Apollo4634
 * @create 2019/08/03
 */

public class ExecutorTest {
    private static final ExecutorService exec = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Runnable task1 = ()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A "+i);
            }
        };

        Callable<Integer> task2 = ()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println("B "+i);
            }
            return 100;
        };

        Future<?> ret1 = exec.submit(task1);
        Future<Integer> ret2 = exec.submit(task2);

        try {
            System.out.println(ret2.get());
        } catch (ExecutionException e) {
            System.out.println(e.toString());
        } catch (InterruptedException e) {
            System.out.println(e.getCause().toString());
        }

        exec.shutdown();//每次使用完后，都要关闭线程池
    }
}
