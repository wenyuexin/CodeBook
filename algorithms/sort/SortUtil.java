package sort;

/**
 * @author Apollo4634
 * @date 2019/02/01
 */

public class SortUtil {

	//ensuring non-instantiability
	private SortUtil() {
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
	 * @param a
	 * @param b
	 * @return true if a less than b
	 */
	public static <T extends Comparable<? super T>> 
	boolean less(T a, T b) {
		return a.compareTo(b)<0;
	}
	
	public static boolean less(byte a, byte b) {
		return a<b;
	}
	
	public static boolean less(char a, char b) {
		return a<b;
	}
	
	public static boolean less(short a, short b) {
		return a<b;
	}
	
	public static boolean less(int a, int b) {
		return a<b;
	}
	
	public static boolean less(long a, long b) {
		return a<b;
	}
	
	public static boolean less(float a, float b) {
		return a<b;
	}
	
	public static boolean less(double a, double b) {
		return a<b;
	}
}
