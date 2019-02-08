package sort;

import javax.sound.midi.MidiChannel;

/**
 * @author Apollo4634
 * @create 2019/02/07
 * @see SortUtils
 */

public class QuickSort {
	
	private static <T extends Comparable<? super T>>
	int partion(T[] arr, int left, int right) {
		T v = arr[left];
		left += 1;
		while (left<=right) {
			while (SortUtils.less(arr[left], v)) break;
			
			
			
		}
		
		for (int i = left; i <= right; i++) {
			if (SortUtils.less(arr[i], arr[i])) {
				
			}
		}
		return left;
	}
	
	public static <T extends Comparable<? super T>>
	void sort(T[] arr, int left, int right) {
		if (left<right) {
			int mid = partion(arr, left, right);
			sort(arr, left, mid-1);
			sort(arr, mid+1, right);
		}
	}
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		sort(arr, 0, arr.length-1);
	}
}
