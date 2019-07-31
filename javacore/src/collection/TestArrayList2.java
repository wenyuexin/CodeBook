package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
 * @author Apollo4634 
 * @create 2019/01/06 16:01
 */

public class TestArrayList2 {
	
	public static void main(String[] args) {
		System.out.println("=== 1 ===");
		List<Integer> list1 = new ArrayList<Integer>() {
			private static final long serialVersionUID = 130580347069923639L;
			{
				add(1);
				add(1);
				add(3);
			}
		};
		
		List<Integer> list2 = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1497953889653505565L;
			{
				add(3);
				add(1);
				add(1);
			}
		};
		
		System.out.println(list1);
		System.out.println(list2);
		System.out.println(list1.equals(list2));
		System.out.println(list1.containsAll(list2));
		
		System.out.println("=== 2 ===");
		List<Integer> list3 = Arrays.asList(1,2,3);
		System.out.println(list3);
		
		
		//ArrayList<Integer> list = (ArrayList<Integer>) Arrays.asList(1,2,3); //Error
		
		ArrayList<Integer> list4 = new ArrayList<>(
				Arrays.asList(1,2,3,5,9));
		System.out.println(list4);
	}
}

