package sort;

/**
 * @author Apollo4634
 * @create 2019/02/10
 * @see SortUtils
 */

public class HeapSort {
	
	private class MinPriorityQueue<T extends Comparable<? super T>> {
		
		private T[] a;
		
		MinPriorityQueue(T[] arr) {
		    this.a = arr;
		}
		
		public void swim(T[] arr, int i) {
			
		}
		
		public void sink(T[] arr, int i) {
			
		}
		
		public void insert(T[] arr, int i) {
			
		}
		
		//构造堆
		
		public void construct() {
			
		}
		
		//删除最小值
		
		
	}
	
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		
		MinPriorityQueue pq = new MinPriorityQueue(arr);
		
		//java.util.PriorityQueue
	}
}
