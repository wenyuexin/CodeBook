package sort;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Apollo4634
 * @create 2019/02/01
 */

public class SortUtils {

	//ensuring non-instantiability
	private SortUtils() {
	}

	/**
	 * Swaps the elements at the specified positions in the specified array
	 * 
	 * @param arr The array in which to swap elements.
	 * @param i the index of one element to be swapped.
	 * @param j the index of the other element to be swapped.
	 */
	public static <T> void swap(T[] arr, int i, int j) {
		T tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(byte[] arr, int i, int j) {
		byte tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(char[] arr, int i, int j) {
		char tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(short[] arr, int i, int j) {
		short tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(long[] arr, int i, int j) {
		long tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(float[] arr, int i, int j) {
		float tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(double[] arr, int i, int j) {
		double tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}


	/**
	 * Compare the size of two variables
	 * @param a One element to be compared
	 * @param b Another element to be compared
	 * @return true if a less than b
	 */
	public static <T extends Comparable<? super T>> 
	boolean less(T a, T b) { return a.compareTo(b)<0; }

	public static  boolean less(byte a, byte b) { return a<b; }

	public static  boolean less(char a, char b) { return a<b; }

	public static  boolean less(short a, short b) { return a<b; }

	public static  boolean less(int a, int b) { return a<b; }

	public static  boolean less(long a, long b) { return a<b; }

	public static  boolean less(float a, float b) { return a<b; }

	public static  boolean less(double a, double b) { return a<b; }

	/**
	 * Determines if the search results have been successfully sorted
	 * 
	 * @param arr The array to be determined wether is sorted
	 * @return true if the array is sorted
	 */
	public static <T extends Comparable<? super T>> 
	boolean isSorted(T[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(byte[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(char[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(short[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(int[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(long[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(float[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}

	public static boolean isSorted(double[] arr) {
		for (int i = arr.length-1; i > 0; i--) 
			if(less(arr[i], arr[i-1])) return false;
		return true;
	}


	/**
	 * Generate a set of numbers for testing
	 * 
	 * @param size The size of numbers
	 * @return A set of numbers
	 */
	public static int[] ints(int size) {
		Random random = new Random();
		int[] arr = new int[size];
		for(int i=0; i<size; i++) //生成数组
			arr[i] = random.nextInt(1000);
		return arr;
	}

	public static 
	Integer[] integerWrappers(int size, int origin, int bound) {
		Random r = new Random();
		Stream<Integer> ss = r.ints(size, origin, bound).boxed();
		Integer[] arr = ss.toArray(Integer[]::new);
		return arr;
	}

	public static double[] doubles(int size) {
		Random random = new Random();
		double[] arr = new double[size];
		for(int i=0; i<size; i++) //生成数组
			arr[i] = random.nextInt(1000);
		return arr;
	}

	public static 
	Double[] doubleWrappers(int size, int origin, int bound) {
		Random r = new Random();
		Stream<Double> ss = r.doubles(size, -100, 100).boxed();
		Double[] arr = ss.toArray(Double[]::new);
		return arr;
	}
}
