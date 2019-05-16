package basic.lambda;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * @author Apollo4634 
 * @create 2018/12/27 23:12
 */

/**
 * lambda表达式就是带参数变量的表达式
 *
 */

public class TestLambda {
	
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		//int a = 1;
		//int b = 2;
		//System.out.println((a, b)->(a+b));
		
		int[] arr = new int[] {5,6354,89, 11};
		//arr.forEach((int e)->System.out.println(e));
		
		System.out.println("===== 2 =====");
		Integer[] integerArr = new Integer[] {5,6354,89, 11};
		Arrays.sort(integerArr, (x,y)->(x-y));
		System.out.println(Arrays.toString(integerArr));
		
		Arrays.sort(integerArr, (x,y)->(y-x));
		System.out.println(Arrays.toString(integerArr));
		
		System.out.println("===== 3 =====");
		ArrayList<Integer> list = new ArrayList<>();
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.forEach(x->System.out.print(2*x+" "));
		System.out.println();
		System.out.println(list);
		
		System.out.println("===== 4 =====");
		
	}
}
