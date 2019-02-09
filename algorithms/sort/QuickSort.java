package sort;


/**
 * @author Apollo4634
 * @create 2019/02/07
 * @see SortUtils
 */

public class QuickSort {
	
	private static <T extends Comparable<? super T>>
	int partion(T[] arr, int left, int right) {
		T v = arr[left];
		int i=left+1, j=right;
		while (i<j) {
			while (i<j && !SortUtils.less(v,arr[i])) i+=1;
			while (i<j && !SortUtils.less(arr[j],v)) j-=1;
			if (i<j) SortUtils.swap(arr, i, j);
		}
		int idx = (SortUtils.less(v, arr[i]))? i-1 : i;
		SortUtils.swap(arr, left, idx);
		return idx;
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
