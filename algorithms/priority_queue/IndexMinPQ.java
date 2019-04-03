package priority_queue;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * @author Apollo4634
 * @create 2019/03/26
 */

public class IndexMinPQ<E> extends PriorityQueue<E> {


	private PriorityQueue<E> pq;
	
	transient Object[] queue;
	
	public IndexMinPQ(int capacity) {
		pq = new PriorityQueue<>(capacity);
	}
	
	public IndexMinPQ(int capacity, Comparator<? super E> comparator) {
		pq = new PriorityQueue<>(capacity, comparator);
	}
	
	public void change(int w, E e) {
		
	}
	
	public void insert(int w, E e) {
		
	}
}
