package collection;

import java.util.PriorityQueue;
import java.util.Queue;

/** 
 * @author Apollo4634 
 * @create 2019/03/25
 */

public class TestPriorityQueue {
	
	//默认情况下PriorityQueue是一个最小堆
	public static void test1() {
		System.out.println("===== test 1 =====");
		Queue<Integer> queue = new PriorityQueue<>(); //default initial capacity (11)
		queue.add(15);
		queue.add(1);
		queue.add(25);
		queue.add(15);
		System.out.println(queue);
		
		//Retrieves, but does not remove, the head of this queue
		System.out.println(queue.peek());
		System.out.println(queue);
		
		//Retrieves and removes the head of this queue
		System.out.println(queue.poll());
		System.out.println(queue);
	}
	
	
	public static void main(String[] args) {
		TestPriorityQueue.test1();
	}
}
