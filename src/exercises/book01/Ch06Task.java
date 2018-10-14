package exercises.book01;

import java.util.Arrays;

public class Ch06Task {
	static void test1() {
		int arr1[] = {11,22,33,44,55};
		//int arr2[];
		
		int arr2[] = Arrays.copyOfRange(arr1, 0, 4);
		for(int x : arr2) {
			System.out.println(x);
		}
	}
	
	static void test2() {
		int arr[] = {99,55,0,5,4,54,86,4,5,54};
		int arr2[] = arr;
		Arrays.sort(arr);
		System.out.println(arr[0]);
		System.out.println(arr2[0]);
	}
	
	static void test3() {
		String arr[] = {"a","","dds","ee"};
		
		for(String x: arr) {
			System.out.println(x);
		}
		
		arr[2] = "bb";
		System.out.println("-------");
		for(String x: arr) {
			System.out.println(x);
		}

	}
	
	static void test4() {
		int arr[][] = {{1,2,3},{4,5,6},{7,8,9}};
		int arr2[][] = arr.clone();
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				arr2[i][j] = arr[j][i];
			}
		}
		
		for(int i[]: arr2) {
			for(int j: i) {
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		test4();
	}
}
