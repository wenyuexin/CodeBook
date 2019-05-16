package sort;

import java.util.Arrays;


/**
 * @author Apollo4634
 * @create 2019/02/03
 */

public class TestSort {

	private enum ALG { 
		BubbleSort, 
		SelectionSort, 
		InsertionSort, 
		ShellSort,
		MergeSort, //Top-down
		MergeSort2, //Bottom-up
		QuickSort,
		HeapSort
	}


	private static <T extends Comparable<? super T>>
	double doSort(ALG alg, T[] arr, boolean showData, boolean showResults) {
		if (showResults) System.out.println(alg.name());
		if (showData) System.out.println("Input:  "+Arrays.toString(arr));

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
		case QuickSort:
			QuickSort.sort(arr); break;
		case HeapSort:
			HeapSort.sort(arr); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		if (showData)  System.out.println("Output: "+Arrays.toString(arr));
		if (showResults) {
			System.out.println("nElements = "+arr.length);
			System.out.println("isSorted = "+SortUtils.isSorted(arr));
			System.out.println("Runtime = "+(t2-t1)/1.0E6+" ms\n");
		}
		return (t2-t1)/1.0E6;
	}

	
	public static double doSort2(ALG alg, int[] arr, boolean showData, boolean showResults) {
		if (showResults) System.out.println(alg.name());
		if (showData) System.out.println("Input:  "+Arrays.toString(arr));

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
		case QuickSort:
			//QuickSort.sort(arr); break;
		case HeapSort:
			HeapSort.sort(arr); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		if (showData) System.out.println("Output: "+Arrays.toString(arr));
		if (showResults) {
			System.out.println("nElements = "+arr.length);
			System.out.println("isSorted = "+SortUtils.isSorted(arr));
			System.out.println("Runtime = "+(t2-t1)/1.0E6+" ms\n");
		}
		return (t2-t1)/1.0E6;
	}

	
	public static void compare(int dataSize, int nLoop) {
		double[] runTime = new double[8];
		for (int iLoop = 0; iLoop<nLoop; iLoop++) {
			System.out.println("loop: "+iLoop);
			Double[] arr = SortUtils.doubleWrappers(dataSize,0,1);
			//Integer[] arr = SortUtils.integerWrappers(20000,-1000,1000);

			runTime[ALG.BubbleSort.ordinal()] +=
				TestSort.doSort(ALG.BubbleSort, arr.clone(), false, false);
			runTime[ALG.SelectionSort.ordinal()] +=
				TestSort.doSort(ALG.SelectionSort, arr.clone(), false, false);
			runTime[ALG.InsertionSort.ordinal()] +=
				TestSort.doSort(ALG.InsertionSort, arr.clone(), false, false);
			runTime[ALG.ShellSort.ordinal()] +=
				TestSort.doSort(ALG.ShellSort, arr.clone(), false, false);
			runTime[ALG.MergeSort.ordinal()] +=
				TestSort.doSort(ALG.MergeSort, arr.clone(), false, false);
			runTime[ALG.MergeSort2.ordinal()] +=
				TestSort.doSort(ALG.MergeSort2, arr.clone(), false, false);
			runTime[ALG.QuickSort.ordinal()] +=
				TestSort.doSort(ALG.QuickSort, arr.clone(), false, false);
			runTime[ALG.HeapSort.ordinal()] +=
				TestSort.doSort(ALG.HeapSort, arr.clone(), false, false);
		}
		System.out.println();
		for (int i = 0; i < runTime.length; i++)
			System.out.println(ALG.values()[i]+"\t"+runTime[i]/nLoop+" ms");
	}

	
	public static void main(String[] args) {
		/* test for basic.array of wrappers */
		//Double[] wrappers = SortUtils.doubleWrappers(20,-1,1);
		//Integer[] wrappers = SortUtils.integerWrappers(2000,-100,100);
		//TestSort.doSort(ALG.HeapSort, wrappers, false, true);
		
		
		/* test for basic.array of primitive data */
		//double[] arr = SortUtils.doubles(dataSize,-1,1);
		//int[] arr = SortUtils.ints(dataSize,-1000,1000);
		//TestSort.doSort2(ALG.QuickSort, 3000, false);
		
		
		/* compare */
		//TestSort.compare(20000, 20);
	}
}
