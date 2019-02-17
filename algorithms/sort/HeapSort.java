package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @create 2019/02/10
 * @see SortUtils
 * @see priority_queue.MaxPriorityQueue
 * @see java.util.PriorityQueue
 */

public class HeapSort {

	public static <T extends Comparable<? super T>>
	void sink(T[] arr, int idx, int N) {
		System.out.println(Arrays.toString(arr));
		
		for (int i = idx+1; 2*i <= N; i *= 2) {
			int j = SortUtils.less(arr[2*i-1],arr[2*i])? 2*i : 2*i-1;
			if (!SortUtils.less(arr[i-1],arr[j])) break;
			else SortUtils.swap(arr, i-1, j);
		}
	}

	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int N = arr.length;
		System.out.println(Arrays.toString(arr));
		
		for (int i = 0; i < N/2; i++) { sink(arr, i, N); }
		System.out.println(Arrays.toString(arr));
		
		for (int i = N-1; i > 0; i++) {
			SortUtils.swap(arr, 1, N);
			N -= 1;
			sink(arr, 1, N);
			System.out.println(Arrays.toString(arr));
		}
	}
}
