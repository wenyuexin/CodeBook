package sort;

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
	boolean compare(T a, T b) {
		return a.compareTo(b)<0;
	}
	
	public static  boolean compare(byte a, byte b) {
		return a<b;
	}
	
	public static  boolean compare(char a, char b) {
		return a<b;
	}
	
	public static  boolean compare(short a, short b) {
		return a<b;
	}
	
	public static  boolean compare(int a, int b) {
		return a<b;
	}
	
	public static  boolean compare(long a, long b) {
		return a<b;
	}
	
	public static  boolean compare(float a, float b) {
		return a<b;
	}
	
	public static  boolean compare(double a, double b) {
		return a<b;
	}
	
	/**
	 * Determines if the search results have been successfully sorted
	 * 
	 * @param arr The array to be determined wether is sorted
	 * @return true if the array is sorted
	 */
	public static <T extends Comparable<? super T>> 
	boolean isSorted(T[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(compare(arr[i+1], arr[i])) return false;
		return true;
	}
	
	public static boolean isSorted(byte[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
	
	public static boolean isSorted(char[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
	
	public static boolean isSorted(short[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i+1]<arr[i]) return false;
		}
		return true;
	}
	
	public static boolean isSorted(int[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
	
	public static boolean isSorted(long[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
	
	public static boolean isSorted(float[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
	
	public static boolean isSorted(double[] arr) {
		for (int i = 0; i < arr.length; i++) 
			if(arr[i+1]<arr[i]) return false;
		return true;
	}
}
