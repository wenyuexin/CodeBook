package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @create 2019/02/04
 * @see SortUtils
 */

public class MergeSort {

	private static <T extends Comparable<? super T>> 
	void merge(T[] arr, T[] aux, int left, int mid, int right) {
		System.out.println(left+" "+mid+" "+right);
		if(left==0 && mid==4 && right==9) {
			System.out.println(Arrays.toString(arr));			
		}
		
		for (int i = left; i <= right; i++) {
			aux[i] = arr[i];
		}
		
		int i=left, j=mid+1, k=left;
		while (i<=mid || j<=right) {
			if (j>right || !SortUtils.less(arr[j], arr[i])) { 
				arr[k] = aux[i]; i++; k++; 
			} else if (i>mid || SortUtils.less(arr[j], arr[i])) {
				arr[k] = aux[j]; j++; k++; 
			}
		}
		System.out.println(Arrays.toString(arr));
	}

	public static <T extends Comparable<? super T>>
	void subsort(T[] arr, T[] aux, int left, int right) {
		if (left<right) {
			int mid = (left+right)/2;
			subsort(arr, aux, left, mid);
			subsort(arr, aux, mid+1, right);
			merge(arr, aux, left, mid, right);
		}
	}
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int arrLen = arr.length;
		T[] aux = Arrays.copyOf(arr, arrLen);
		subsort(arr, aux, 0, arrLen-1);
	}
	

	public static void main(String[] args) {
		Integer[] arr = new Integer[] {
			-59, -29, -49, -86, -15, 90, 31, -37, -45, -35,
			-89, 74, 76, 11, 89, -30, -58, -15, 6, -80};
		
		System.out.println(Arrays.toString(arr));
		sort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
