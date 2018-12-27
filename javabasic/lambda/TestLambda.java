package lambda;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * @author Apollo4634 
 * @creation 2018/12/27 23:12
 */

/**
 * lambda表达式就是带参数变量的表达式
 *
 */

public class TestLambda {
	
	public static void main(String[] args) {
		//int a = 1;
		//int b = 2;
		//System.out.println((a, b)->(a+b));
		
		int[] arr = new int[] {5,6354,89};
		//arr.forEach((int e)->System.out.println(e));
		
		
		ArrayList<Integer> list = new ArrayList<>();
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.forEach(x->System.out.print(2*x+" "));
		
		System.out.println();
		System.out.println(list);
	}
}
