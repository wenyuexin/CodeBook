package sort;

/**
 * @author Apollo4634
 * @create 2019/02/02
 * @see SortUtils
 */

public class InsertionSort {
	
	public static <T extends Comparable<? super T>>
	void sort(T[] arr) {
		int arrLen = arr.length;
		for (int i = 0; i < arrLen; i++) {
			for (int j = i; j > 0; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}


	public static void sort(int[] arr) {
		int arrLen = arr.length;
		for (int i = 0; i < arrLen; i++) {
			for (int j = i; j > 0; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
	
	
	public static void sort(double[] arr) {
		int arrLen = arr.length;
		for (int i = 0; i < arrLen; i++) {
			for (int j = i; j > 0; j--) {
				if(!SortUtils.less(arr[j], arr[j-1])) break;
				SortUtils.swap(arr, j, j-1);
			}
		}
	}
}
