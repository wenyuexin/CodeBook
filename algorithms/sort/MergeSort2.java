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
		for (int sz = 1; sz < arrLen; sz *= 2)
			for (int mid = sz-1; mid < arrLen; mid += 2*sz)
				merge(arr, aux, mid-sz+1, mid, Math.min(mid+sz,arrLen-1));
	}


	//int[]
	private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
		for (int i = left; i <= right; i++) aux[i] = arr[i];

		int i=left, j=mid+1, k=left;
		while (i<=mid || j<=right) {
			if (i>mid) arr[k++] = aux[j++];
			else if (j>right) arr[k++] = aux[i++];
			else if (SortUtils.less(aux[j], aux[i])) arr[k++] = aux[j++];
			else arr[k++] = aux[i++];
		}
	}

	public static void sort(int[] arr) {
		int arrLen = arr.length;
		int[] aux = Arrays.copyOf(arr, arrLen);
		for (int sz = 1; sz < arrLen; sz *= 2)
			for (int mid = sz-1; mid < arrLen; mid += 2*sz)
				merge(arr, aux, mid-sz+1, mid, Math.min(mid+sz,arrLen-1));
	}


	//long[]
	private static void merge(long[] arr, long[] aux, int left, int mid, int right) {
		for (int i = left; i <= right; i++) aux[i] = arr[i];

		int i=left, j=mid+1, k=left;
		while (i<=mid || j<=right) {
			if (i>mid) arr[k++] = aux[j++];
			else if (j>right) arr[k++] = aux[i++];
			else if (SortUtils.less(aux[j], aux[i])) arr[k++] = aux[j++];
			else arr[k++] = aux[i++];
		}
	}

	public static void sort(long[] arr) {
		int arrLen = arr.length;
		long[] aux = Arrays.copyOf(arr, arrLen);
		for (int sz = 1; sz < arrLen; sz *= 2)
			for (int mid = sz-1; mid < arrLen; mid += 2*sz)
				merge(arr, aux, mid-sz+1, mid, Math.min(mid+sz,arrLen-1));
	}
}
