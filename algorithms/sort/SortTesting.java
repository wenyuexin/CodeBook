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
		MergeSort2, //Bottom-up
		QuickSort,
		HeapSort
	}

	
	public static <T extends Comparable<? super T>>
	double doSort(ALG alg, T[] arr, boolean showDatas, boolean showResults) {
		if (showResults) System.out.println(alg.name());
		if (showDatas) System.out.println("Input:  "+Arrays.toString(arr));

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

		if (showDatas)  System.out.println("Output: "+Arrays.toString(arr));
		if (showResults) {
			System.out.println("nElements = "+arr.length);
			System.out.println("isSorted = "+SortUtils.isSorted(arr));
			System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
		}
		return (t2-t1)/1.0E6;
	}

	
	public static 
	double doSort2(ALG alg, int[] arr, boolean showDatas, boolean showResults) {
		if (showResults) System.out.println(alg.name());
		if (showDatas) System.out.println("Input:  "+Arrays.toString(arr));

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
			//HeapSort.sort(arr); break;
		default:
			break;
		}
		long t2 = System.nanoTime();

		if (showDatas) System.out.println("Output: "+Arrays.toString(arr));
		if (showResults) {
			System.out.println("nElements = "+arr.length);
			System.out.println("isSorted = "+SortUtils.isSorted(arr));
			System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
		}
		return (t2-t1)/1.0E6;
	}

	
	public static void sortCompare(int dataSize, int nLoop) {
		double runtime[] = new double[7];
		for (int iLoop = 0; iLoop<nLoop; iLoop++) {
			System.out.println("loop: "+iLoop);
			Double[] arr = SortUtils.doubleWrappers(dataSize,0,1);
			//Integer[] arr = SortUtils.integerWrappers(20000,-1000,1000);

			runtime[ALG.BubbleSort.ordinal()] += 
				SortTesting.doSort(ALG.BubbleSort, arr.clone(), false, false );
			runtime[ALG.SelectionSort.ordinal()] += 
				SortTesting.doSort(ALG.SelectionSort, arr.clone(), false, false);
			runtime[ALG.InsertionSort.ordinal()] += 
				SortTesting.doSort(ALG.InsertionSort, arr.clone(), false, false);
			runtime[ALG.ShellSort.ordinal()] += 
				SortTesting.doSort(ALG.ShellSort, arr.clone(), false, false);
			runtime[ALG.MergeSort.ordinal()] += 
				SortTesting.doSort(ALG.MergeSort, arr.clone(), false, false);
			runtime[ALG.MergeSort2.ordinal()] += 
				SortTesting.doSort(ALG.MergeSort2, arr.clone(), false, false);
			//runtime[ALG.QuickSort.ordinal()] +=
			//	SortTesting.doSort(ALG.QuickSort, arr.clone(), false, false);
			//runtime[ALG.HeapSort.ordinal()] +=
			//	SortTesting.doSort(ALG.HeapSort, arr.clone(), false, false);
		}
		System.out.println();
		for (int i = 0; i < runtime.length; i++)
			System.out.println(ALG.values()[i]+"\t"+runtime[i]/nLoop+" ms");
	}

	
	public static void main(String[] args) {
		/*** test for array of wrappers ***/
		//Double[] wrappers = SortUtils.doubleWrappers(20,-1,1);
		Integer[] wrappers = SortUtils.integerWrappers(20,-100,100);
		SortTesting.doSort(ALG.HeapSort, wrappers, false, true);
		
		
		/*** test for array of primmitive data ***/
		//double[] arr = SortUtils.doubles(dataSize,-1,1);
		//int[] arr = SortUtils.ints(dataSize,-1000,1000);
		//SortTesting.doSort2(ALG.QuickSort, 3000, false);
		
		
		/*** compare ***/
		//SortTesting.sortCompare(20000, 20);
	}
}
