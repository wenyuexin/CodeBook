package sort;


/**
 * 基于最大堆的排序算法
 *
 * @author Apollo4634
 * @create 2019/02/10
 * @see SortUtils
 * @see priority_queue.MaxPriorityQueue
 * @see java.util.PriorityQueue
 */

public class HeapSort {

	private static <T extends Comparable<? super T>>
	void sink(T[] arr, int idx, int N) {
		int j;
		for (int i = idx; 2*i+1 < N; i = j) {
			if (2*i+2==N) j = N-1; 
			else j = SortUtils.less(arr[2*i+1],arr[2*i+2])? 2*i+2 : 2*i+1;
			if (!SortUtils.less(arr[i],arr[j])) break;
			SortUtils.swap(arr, i, j);
		}
	}

	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int N = arr.length;
		for (int i = (N-1)/2; i >= 0; i--) 
			sink(arr, i, N);
		while (N>0) {
			N -= 1;
			SortUtils.swap(arr, 0, N);
			sink(arr, 0, N);
		}
	}


	//int
	private static void sink(int[] arr, int idx, int N) {
		int j;
		for (int i = idx; 2*i+1 < N; i = j) {
			if (2*i+2==N) j = N-1; 
			else j = SortUtils.less(arr[2*i+1],arr[2*i+2])? 2*i+2 : 2*i+1;
			if (!SortUtils.less(arr[i],arr[j])) break;
			SortUtils.swap(arr, i, j);
		}
	}

	public static void sort(int[] arr) {
		int N = arr.length;
		for (int i = (N-1)/2; i >= 0; i--) 
			sink(arr, i, N);
		while (N>0) {
			N -= 1;
			SortUtils.swap(arr, 0, N);
			sink(arr, 0, N);
		}
	}


	//long
	private static void sink(long[] arr, int idx, int N) {
		int j;
		for (int i = idx; 2*i+1 < N; i = j) {
			if (2*i+2==N) j = N-1; 
			else j = SortUtils.less(arr[2*i+1],arr[2*i+2])? 2*i+2 : 2*i+1;
			if (!SortUtils.less(arr[i],arr[j])) break;
			SortUtils.swap(arr, i, j);
		}
	}

	public static void sort(long[] arr) {
		int N = arr.length;
		for (int i = (N-1)/2; i >= 0; i--) 
			sink(arr, i, N);
		while (N>0) {
			N -= 1;
			SortUtils.swap(arr, 0, N);
			sink(arr, 0, N);
		}
	}


	//double
	private static void sink(double[] arr, int idx, int N) {
		int j;
		for (int i = idx; 2*i+1 < N; i = j) {
			if (2*i+2==N) j = N-1; 
			else j = SortUtils.less(arr[2*i+1],arr[2*i+2])? 2*i+2 : 2*i+1;
			if (!SortUtils.less(arr[i],arr[j])) break;
			SortUtils.swap(arr, i, j);
		}
	}

	public static void sort(double[] arr) {
		int N = arr.length;
		for (int i = (N-1)/2; i >= 0; i--) 
			sink(arr, i, N);
		while (N>0) {
			N -= 1;
			SortUtils.swap(arr, 0, N);
			sink(arr, 0, N);
		}
	}
}
