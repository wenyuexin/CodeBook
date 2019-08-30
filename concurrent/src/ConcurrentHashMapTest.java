import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Apollo4634
 * @create 2019/08/30
 */

public class ConcurrentHashMapTest {

    private static void test() throws InterruptedException {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                Random r = new Random();
                for (int j = 0; j < 1000; j++) {
                    int num = r.nextInt(10);
                    map.put(num, map.getOrDefault(num, 0)+1);
                }
            });
            t.start();
        }
        TimeUnit.SECONDS.sleep(1);
        printMap(map);
    }

    private static void test2() throws InterruptedException {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                Random r = new Random();
                synchronized (map) {
                    for (int j = 0; j < 1000; j++) {
                        int num = r.nextInt(10);
                        map.put(num, map.getOrDefault(num, 0)+1);
                    }
                }
            });
            t.start();
        }

        TimeUnit.SECONDS.sleep(1);
        printMap(map);
    }

    private static void printMap(Map<Integer, Integer> map) {
        int sum = 0;
        for (Integer value : map.values()) {
            sum += value;
        }
        System.out.print(sum+" ");
        System.out.println(map);
    }


    public static void main(String[] args) throws InterruptedException {
        test();
        test2();
    }
}
