package collection;

import java.util.List;
import java.util.ArrayList;

public class TestArrayList {
	
	public static void test() {
		System.out.println("=== test ===");
		
		//ArrayList1
		List<String> list = new ArrayList<String>();	
		list.add("a");
		list.add("b");
		list.add("c");
		System.out.println("list: "+list);
		
		//ArrayList2
		List<String> list2 = new ArrayList<String>();
		list2.add("a");
		list2.add("d");
		System.out.println("list2: "+list2);
		
		//containsAll
		System.out.println(list.containsAll(list));
		System.out.println(list.containsAll(list2));
		
		//retainAll
		list.retainAll(list2);
		System.out.println(list);
		
		//removeAll
		list.removeAll(list2);
		System.out.println(list);
	}
	

	public static void test2() {
		System.out.println("=== test2 ===");
		
		List<String> list = new ArrayList<String>(10);
		list.add("a");
		list.add("bc");
		list.add("ccd");
		
		System.out.println("size = "+list.size());
		System.out.println(list);
		
		ArrayList<String> list2 = (ArrayList<String>) list;
		System.out.println(list2);
		
		list.set(1, "asdf");
		System.out.println(list);
		System.out.println(list2);
		
		//alist.trimToSize(); 
		//System.out.println(alist);
		
		list2.trimToSize(); 
		System.out.println("trimToSize: "+list2);
		//System.out.println(alist2.capacity); 
	}
	
	
	public static void main(String[] args) {
		TestArrayList.test();
		TestArrayList.test2();
	}
}

