package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @date 2019/02/03
 */

public class SortTesting {
	
	//BubbleSort
	public static void testBubbleSort() {
		System.out.println("===== BubbleSort =====");

		double[] doubles = SortUtils.doubles(25);
		System.out.println(Arrays.toString(doubles));
		BubbleSort.sort(doubles);
		
		//ints
		int[] ints = SortUtils.ints(25);
		System.out.println(Arrays.toString(ints));
		BubbleSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] wrappers = SortUtils.doubleWrappers(20,-100,100);
		System.out.println(Arrays.toString(wrappers));
		BubbleSort.sort(wrappers);
		System.out.println(Arrays.toString(wrappers));
		System.out.println(">> nElements = "+wrappers.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(wrappers)+"\n");
	}


	//SelectionSort
	public static void testSelectionSort() {
		System.out.println("===== SelectionSort =====");

		//ints
		int[] ints = SortUtils.ints(25);
		System.out.println(Arrays.toString(ints));
		SelectionSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] wrappers = SortUtils.doubleWrappers(20,-100,100);
		System.out.println(Arrays.toString(wrappers));
		SelectionSort.sort(wrappers);
		System.out.println(Arrays.toString(wrappers));
		System.out.println(">> nElements = "+wrappers.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(wrappers)+"\n");
	}


	//InsertionSort
	public static void testInsertionSort() {
		System.out.println("===== InsertionSort =====");

		//ints
		int[] ints = SortUtils.ints(25);
		System.out.println(Arrays.toString(ints));
		InsertionSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] wrappers = SortUtils.doubleWrappers(20,-100,100);
		System.out.println(Arrays.toString(wrappers));
		InsertionSort.sort(wrappers);
		System.out.println(Arrays.toString(wrappers));
		System.out.println(">> nElements = "+wrappers.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(wrappers)+"\n");
	}


	public static void main(String[] args) {
		//SortTesting.testBubbleSort();
		//SortTesting.testSelectionSort();
		SortTesting.testInsertionSort();
		
	}
}
