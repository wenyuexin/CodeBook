package sort;

import java.util.Arrays;

/**
 * Bottom-up merge sort
 * 
 * @author Apollo4634
 * @create 2019/02/05
 * @see SortUtils
 * @see MergeSort
 */

public class MergeSort2 {

	//T[]
	private static <T extends Comparable<? super T>> 
	void merge(T[] arr, T[] aux, int left, int mid, int right) {
		for (int i = left; i <= right; i++) aux[i] = arr[i];

		int i=left, j=mid+1, k=left;
		while (i<=mid || j<=right) {
			if (i>mid) arr[k++] = aux[j++];
			else if (j>right) arr[k++] = aux[i++];
			else if (SortUtils.less(aux[j], aux[i])) arr[k++] = aux[j++];
			else arr[k++] = aux[i++];
		}
	}

	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int arrLen = arr.length;
		T[] aux = Arrays.copyOf(arr, arrLen);
		
		int sz = 1;
		int SZ = (arrLen+1)/2;
		while (sz<SZ) {
			for (int i = 0; i < arrLen; i += 2*sz) {
				int left = i;
				int right = i+2*sz-1;
				if(!(right<arrLen)) right=arrLen-1;
				int mid = (left+right)/2;
				merge(arr, aux, left, mid, right);
			}
			sz *= 2;
		}
	}

}
