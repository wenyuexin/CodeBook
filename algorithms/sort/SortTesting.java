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

		//ints
		int[] ints = SortUtils.generateInts(25);
		System.out.println(Arrays.toString(ints));
		BubbleSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] wrappers = SortUtils.generateDoubleWrappers(20);
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
		int[] ints = SortUtils.generateInts(25);
		System.out.println(Arrays.toString(ints));
		SelectionSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] doubleWrappers = SortUtils.generateDoubleWrappers(20);
		System.out.println(Arrays.toString(doubleWrappers));
		SelectionSort.sort(doubleWrappers);
		System.out.println(Arrays.toString(doubleWrappers));
		System.out.println(">> nElements = "+doubleWrappers.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(doubleWrappers)+"\n");
	}


	//InsertionSort
	public static void testInsertionSort() {
		System.out.println("===== InsertionSort =====");

		//ints
		int[] ints = SortUtils.generateInts(25);
		System.out.println(Arrays.toString(ints));
		InsertionSort.sort(ints);
		System.out.println(Arrays.toString(ints));
		System.out.println(">> nElements = "+ints.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(ints)+"\n");

		//DoubleWrappers
		Double[] doubleWrappers = SortUtils.generateDoubleWrappers(20);
		System.out.println(Arrays.toString(doubleWrappers));
		InsertionSort.sort(doubleWrappers);
		System.out.println(Arrays.toString(doubleWrappers));
		System.out.println(">> nElements = "+doubleWrappers.length);
		System.out.println(">> isSorted = "+SortUtils.isSorted(doubleWrappers)+"\n");
	}


	public static void main(String[] args) {
		SortTesting.testBubbleSort();
		SortTesting.testSelectionSort();
		SortTesting.testInsertionSort();
	}
}
