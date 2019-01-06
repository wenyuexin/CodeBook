package collection;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author Apollo4634 
 * @creation 2019/01/06 16:01
 */

public class TestArrayList2 {
	
	public static void main(String[] args) {
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
	}
}

