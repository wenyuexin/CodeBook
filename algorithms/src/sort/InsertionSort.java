package sort;

/**
 * @author Apollo4634
 * @create 2019/02/02
 * @see SortUtils
 */

public class InsertionSort {

	/**
	 * @param arr
	 * @param from inclusive
	 * @param to exclusive
	 */
	
	public static <T extends Comparable<? super T>>
	void sort(T[] arr, int from, int to) {
		for (int i = from+1; i < to; i++) {
			for (int j = i; j > from; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
	
	public static <T extends Comparable<? super T>>
	void sort(T[] arr) {
		sort(arr, 0, arr.length);
	}
	

	//int
	public static void sort(int[] arr, int from, int to) {
		for (int i = from+1; i < to; i++) {
			for (int j = i; j > from; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
	
	public static void sort(int[] arr) {
		sort(arr, 0, arr.length);
	}
	
	
	//long
	public static void sort(long[] arr, int from, int to) {
		for (int i = from+1; i < to; i++) {
			for (int j = i; j > from; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
	
	public static void sort(long[] arr) {
		sort(arr, 0, arr.length);
	}
	
	
	//double
	public static void sort(double[] arr, int from, int to) {
		for (int i = from+1; i < to; i++) {
			for (int j = i; j > from; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
	
	public static void sort(double[] arr) {
		sort(arr, 0, arr.length);
	}
}
