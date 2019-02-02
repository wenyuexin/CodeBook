package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * BubbleSort
 * @author Apollo4634
 * @date 2018/12/22
 */

public class BubbleSort {
	
	public static <T extends Comparable<? super T>> 
	void sort(T[] arr) {
		int flag = 0;
		int arrLen = arr.length;
		while (flag!=arrLen-1) {
			flag = 0;
			for(int i=0; i<arrLen-1; i++) {
				if(SortUtil.less(arr[i], arr[i+1])) {
					SortUtil.swap(arr, i, i+1);
				} else {
					flag++;
				}
			}
		}
	}
	
	public static void sort(int[] arr) {
		int flag = 0;
		int arrLen = arr.length;
		while (flag!=arrLen-1) {
			flag = 0;
			for(int i=0; i<arrLen-1; i++) {
				if(arr[i]<arr[i+1]) {
					SortUtil.swap(arr, i, i+1);
				} else {
					flag++;
				}
			}
		}
	}
	
	public static void sort(double[] arr) {
		int flag = 0;
		int arrLen = arr.length;
		while (flag!=arrLen-1) {
			flag = 0;
			for(int i=0; i<arrLen-1; i++) {
				if(arr[i]<arr[i+1]) {
					SortUtil.swap(arr, i, i+1);
				} else {
					flag++;
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		Random random = new Random();
		int[] arr = new int[10];
		for(int i=0; i<arr.length; i++) {//生成数组
			arr[i] = random.nextInt(1000);
		}
		
		System.out.println(Arrays.toString(arr));//排序前
		BubbleSort.sort(arr);//排序
		System.out.println(Arrays.toString(arr));//排序后
	}
}
