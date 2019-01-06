package collection;

import java.util.List;
import java.util.ArrayList;

public class TestArrayList {
	
	public static void test() {
		//ArrayList1
		List<String> alist = new ArrayList<String>();	
		alist.add("a");
		alist.add("b");
		alist.add("c");
		System.out.println("alist: "+alist);
		
		//ArrayList2
		List<String> alist2 = new ArrayList<String>();
		alist2.add("a");
		alist2.add("d");
		System.out.println("alist2: "+alist2);
		
		//containsAll
		System.out.println(alist.containsAll(alist));
		System.out.println(alist.containsAll(alist2));
		
		//retainAll
		alist.retainAll(alist2);
		System.out.println(alist);
		
		//removeAll
		alist.removeAll(alist2);
		System.out.println(alist);
	}
	

	public static void test2() {
		List<String> alist = new ArrayList<String>(10);
		alist.add("a");
		alist.add("bc");
		alist.add("ccd");
		
		System.out.println("size="+alist.size());
		System.out.println(alist);
		
		ArrayList<String> alist2 = (ArrayList<String>) alist;
		System.out.println(alist2);
		
		alist.set(1, "asdf");
		System.out.println(alist);
		System.out.println(alist2);
		
		//alist.trimToSize(); 
		//System.out.println(alist);
		
		
		alist2.trimToSize(); 
		System.out.println("trimToSize: "+alist2);
		//System.out.println(alist2.capacity); 
	}
	
	public static void main(String[] args) {
		System.out.println("=== test ===");
		TestArrayList.test();
		System.out.println("=== test2 ===");
		TestArrayList.test2();
	}
}

