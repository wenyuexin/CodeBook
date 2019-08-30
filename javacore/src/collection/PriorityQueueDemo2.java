package collection;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 如果元素没有实现 java.lang.Comparable接口，
 * 那么运行时会抛出ClassCastException异常
 *
 * @author Apollo4634
 * @create 2019/08/30
 */

public class PriorityQueueDemo2 {

    private static class UncomparableClass {
        private int id;
        UncomparableClass(int id) { this.id = id; }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }

    private static void test() {
        Queue<UncomparableClass> pq = new PriorityQueue<>();
        pq.add(new UncomparableClass(3));
        pq.add(new UncomparableClass(1));
        pq.add(new UncomparableClass(2));
        System.out.println(pq.peek());
    }


    public static void main(String[] args) {
        test();
    }
}
