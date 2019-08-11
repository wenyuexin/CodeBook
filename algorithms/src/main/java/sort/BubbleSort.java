package sort;


/**
 * @author Apollo4634
 * @create 2018/12/22
 * @see SortUtils
 */

public class BubbleSort {
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		for (int i = arr.length-1; i > 0; i--) {
			for(int j=0; j<i; j++) {
				if(SortUtils.less(arr[j+1], arr[j])) 
					SortUtils.swap(arr, j, j+1);
			}
		}
	}
	
	public static void sort(int[] arr) {
		for (int i = arr.length-1; i > 0; i--) {
			for(int j=0; j<i; j++) {
				if(SortUtils.less(arr[j+1], arr[j])) 
					SortUtils.swap(arr, j, j+1);
			}
		}
	}
	
	public static void sort(long[] arr) {
		for (int i = arr.length-1; i > 0; i--) {
			for(int j=0; j<i; j++) {
				if(SortUtils.less(arr[j+1], arr[j])) 
					SortUtils.swap(arr, j, j+1);
			}
		}
	}
	
	public static void sort(double[] arr) {
		for (int i = arr.length-1; i > 0; i--) {
			for(int j=0; j<i; j++) {
				if(SortUtils.less(arr[j+1], arr[j])) 
					SortUtils.swap(arr, j, j+1);
			}
		}
	}
	
}
