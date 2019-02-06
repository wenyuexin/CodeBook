package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @date 2019/02/03
 */

public class SortTesting {

	private enum ALG { 
		BubbleSort, 
		SelectionSort, 
		InsertionSort, 
		ShellSort,
		MergeSort, //Top-down
		MergeSort2 //Bottom-up
	}

	public static void doSort(ALG alg, int dataSize, boolean doShow) {
		//Double[] wrappers = SortUtils.doubleWrappers(dataSize,-1,1);
		Integer[] arr = SortUtils.integerWrappers(dataSize,-1000,1000);
		if (doShow) System.out.println("Input:  "+Arrays.toString(arr));

		long t1 = System.nanoTime();
		switch (alg) {
		case BubbleSort:
			BubbleSort.sort(arr); break;
		case SelectionSort:
			SelectionSort.sort(arr); break;
		case InsertionSort:
			InsertionSort.sort(arr); break;
		case ShellSort:
			ShellSort.sort(arr); break;
		case MergeSort:
			MergeSort.sort(arr); break;
		case MergeSort2:
			MergeSort2.sort(arr); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		if (doShow)  System.out.println("Output: "+Arrays.toString(arr));
		System.out.println("nElements = "+arr.length);
		System.out.println("isSorted = "+SortUtils.isSorted(arr));
		System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
	}

	public static void doSort2(ALG alg, int dataSize, boolean doShow) {
		//double[] arr = SortUtils.doubles(dataSize,-1,1);
		int[] arr = SortUtils.ints(dataSize,-1000,1000);
		if (doShow) System.out.println("Input:  "+Arrays.toString(arr));

		long t1 = System.nanoTime();
		switch (alg) {
		case BubbleSort:
			BubbleSort.sort(arr); break;
		case SelectionSort:
			SelectionSort.sort(arr); break;
		case InsertionSort:
			InsertionSort.sort(arr); break;
		case ShellSort:
			ShellSort.sort(arr); break;
		case MergeSort:
			MergeSort.sort(arr); break;
		case MergeSort2:
			MergeSort2.sort(arr); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		if (doShow) System.out.println("Output: "+Arrays.toString(arr));
		System.out.println("nElements = "+arr.length);
		System.out.println("isSorted = "+SortUtils.isSorted(arr));
		System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
	}


	public static void main(String[] args) {
		SortTesting.doSort(ALG.MergeSort2, 3000, false);
		SortTesting.doSort2(ALG.MergeSort, 3000, false);
	}
}
