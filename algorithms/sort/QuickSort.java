package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @create 2019/02/07
 * @see SortUtils
 */

public class QuickSort {
	
	private static <T extends Comparable<? super T>>
	int partion(T[] arr, int left, int right) {
		System.out.println(arr[left]+"("+left+")  "+arr[right]+"("+right+")");
		
		System.out.println(Arrays.toString(arr));
		
		T v = arr[left];
		int i=left+1, j=right;
		while (i<j) {
			while (i<j && !SortUtils.less(v,arr[i])) i+=1;
			while (i<j && !SortUtils.less(arr[j],v)) j-=1;
			if (i<j) SortUtils.swap(arr, i, j);
			System.out.print(Arrays.toString(arr)+" ");
			System.out.println(arr[i]+"("+i+")"+" "+arr[j]+"("+j+")");
		}
		SortUtils.swap(arr, left, i-1);
		System.out.print(Arrays.toString(arr)+" ");
		System.out.println(arr[i]+"("+i+")"+" "+arr[j]+"("+j+")\n");
		return i-1;
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
	
	public static void main(String[] args) {
		Integer[] arr = {527, -499, -715, 916, -795, -330, 722, -512, 369, -434, 13, 386, -287, 859, 969, 909, 907, -470};
		QuickSort.sort(arr);
		
	}
}
