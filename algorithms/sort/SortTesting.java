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
		MergeSort
	}

	public static void doSort(ALG alg, int dataSize) {
		//Double[] wrappers = SortUtils.doubleWrappers(dataSize,-1,1);
		Integer[] wrappers = SortUtils.integerWrappers(dataSize,-100,100);
		System.out.println("Input:  "+Arrays.toString(wrappers));

		long t1 = System.nanoTime();
		switch (alg) {
		case BubbleSort:
			BubbleSort.sort(wrappers); break;
		case SelectionSort:
			SelectionSort.sort(wrappers); break;
		case InsertionSort:
			InsertionSort.sort(wrappers); break;
		case ShellSort:
			ShellSort.sort(wrappers); break;
		case MergeSort:
			MergeSort.sort(wrappers); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		System.out.println("Output: "+Arrays.toString(wrappers));
		System.out.println("nElements = "+wrappers.length);
		System.out.println("isSorted = "+SortUtils.isSorted(wrappers));
		System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
	}

	public static void doSort2(ALG alg, int dataSize) {
		//double[] arr = SortUtils.doubles(dataSize,-1,1);
		int[] arr = SortUtils.ints(dataSize,-100,100);
		System.out.println("Input:  "+Arrays.toString(arr));

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
		default:
			break;
		}
		long t2 = System.nanoTime();

		System.out.println("Output: "+Arrays.toString(arr));
		System.out.println("nElements = "+arr.length);
		System.out.println("isSorted = "+SortUtils.isSorted(arr));
		System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
	}


	public static void main(String[] args) {
		SortTesting.doSort(ALG.MergeSort, 20);
		SortTesting.doSort2(ALG.ShellSort, 20);
	}
}
