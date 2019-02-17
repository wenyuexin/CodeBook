package priority_queue;

import sort.SortUtils;

/** 
 * @author Apollo4634 
 * @create 2019/02/17
 */

public class MaxPriorityQueue<T extends Comparable<? super T>> {

	private T[] arr;
	private int N;

	MaxPriorityQueue(T[] a) {
		this.arr = a;
		this.N = a.length;
		construct();
	}

	public void swim(int idx) {
		for (int i = idx; i>0 && SortUtils.less(arr[i-1],arr[i/2-1]); i /= 2)
			SortUtils.swap(arr, i-1, i/2-1);
	}

	public void sink(int idx) {
		for (int i = idx; 2*i < N; i *= 2) {
			int j = SortUtils.less(arr[2*i-1],arr[2*i])? 2*i : 2*i-1;
			if (!SortUtils.less(arr[i],arr[j])) break;
			else SortUtils.swap(arr, i, j);
		}
	}

	public void insert(int idx) {

	}

	//构造堆
	public void construct() {
		for (int i = 0; i < N; i++) sink(i);
	}

	//删除最小值
	public T deleteMax() {
		T max = arr[0];



		return max;
	}

	public void sort() {

	}

}
