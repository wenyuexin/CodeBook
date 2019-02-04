package sort;

import java.util.Arrays;

/**
 * @author Apollo4634
 * @date 2019/02/03
 */

public class SortTesting {

	enum ALG { BubbleSort, SelectionSort, InsertionSort, ShellSort }
	
	public static void doSort(ALG alg, int dataSize) {
		Double[] wrappers = SortUtils.doubleWrappers(dataSize,-1,1);
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
		double[] doubles = SortUtils.doubles(dataSize,-1,1);
		System.out.println("Input:  "+Arrays.toString(doubles));

		long t1 = System.nanoTime();
		switch (alg) {
		case BubbleSort:
			BubbleSort.sort(doubles); break;
		case SelectionSort:
			SelectionSort.sort(doubles); break;
		case InsertionSort:
			InsertionSort.sort(doubles); break;
		case ShellSort:
			//ShellSort.sort(doubles); break;
		default:
			break;
		}
		long t2 = System.nanoTime();
		
		System.out.println("Output: "+Arrays.toString(doubles));
		System.out.println("nElements = "+doubles.length);
		System.out.println("isSorted = "+SortUtils.isSorted(doubles));
		System.out.println("Rumtime = "+(t2-t1)/1.0E6+" ms\n");
	}
	

	public static void main(String[] args) {
		SortTesting.doSort(ALG.ShellSort, 20);
		//SortTesting.doSort2(ALG.BubbleSort, 20);
	}
}
