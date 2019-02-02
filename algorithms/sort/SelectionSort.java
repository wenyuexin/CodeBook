package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/** 
 * SelectionSort
 * 
 * @author Apollo4634 
 * @create 2019/01/17
 */

public class SelectionSort {
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int arrLen = arr.length;
		int idx;
		for (int i = 0; i < arrLen-1; i++) {
			idx = i;
			for (int j = idx+1; j < arrLen; j++) 
				if(SortUtils.compare(arr[j], arr[idx])) idx = j;
			SortUtils.swap(arr, i, idx);
		}
	}
	
	public static void sort(int[] arr) {
		int arrLen = arr.length;
		int idx;
		for (int i = 0; i < arrLen-1; i++) {
			idx = i;
			for (int j = idx+1; j < arrLen; j++) 
				if(SortUtils.compare(arr[j], arr[idx])) idx = j;
			SortUtils.swap(arr, i, idx);
		}
	}
	
	public static void sort(double[] arr) {
		int arrLen = arr.length;
		int idx;
		for (int i = 0; i < arrLen-1; i++) {
			idx = i;
			for (int j = idx+1; j < arrLen; j++) 
				if(SortUtils.compare(arr[j], arr[idx])) idx = j;
			SortUtils.swap(arr, i, idx);
		}
	}
	
	
	public static void main(String[] args) {
		Random r = new Random();
		IntStream ss = r.ints(20, -100, 100);
		Integer[] arr = ss.boxed().toArray(Integer[]::new);
		
		System.out.println(Arrays.toString(arr));
		SelectionSort.sort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
