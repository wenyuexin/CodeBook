package advanced.concurrent;

import java.util.Vector;

/**
 * @author Apollo4634
 * @create 2019/05/21
 */

public class ThreadSafetyTestOfVector {
    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 20; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(()->{
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            Thread printThread = new Thread(()->{
                for (int i = 0; i < vector.size(); i++) {
                    System.out.println(vector.get(i));
                }
            });

            removeThread.start();
            printThread.start();

            while (Thread.activeCount() > 20) {}
        }
    }
}
