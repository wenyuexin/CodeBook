package sort;

import java.util.Random;

/**
 * BubbleSort
 * @author apollo4634
 */
public class BubbleSort {
	
	private static void swap(int[] arr, int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}
	
	public void sort(int[] arr) {
		int flag = 0;
		int lenOfArr = arr.length;
		
		while (flag!=lenOfArr-1) {
			flag = 0;
			for(int i=0; i<lenOfArr-1; i++) {
				if(arr[i]>arr[i+1]) {
					BubbleSort.swap(arr, i, i+1);
				} else {
					flag++;
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		//随机生成数组
		Random random = new Random();
		int[] arr = new int[10];
		for(int i=0; i<arr.length; i++) {
			arr[i] = random.nextInt(1000);
		}
		
		//显示原数组
		for(int val: arr) {
			System.out.print(val+" ");
		}
		System.out.println();
		
		//排序
		BubbleSort bs = new BubbleSort();
		bs.sort(arr);
		
		//显示排序之后的数组
		for(int val: arr) {
			System.out.print(val+" ");
		}
		System.out.println();
		
	}
}
