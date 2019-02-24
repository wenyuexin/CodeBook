package book_978_7_115_29380_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Ch14Task {
	/**
	 * Task1：
	 * 将1至100之间的正整数存放在List集合中，
	 * 并将集合中索引位置是10的对象从集合中移除
	 */
	private static void task1() {
		System.out.println("=== task1 ===");
		ArrayList<Integer> numList = new ArrayList<Integer>();
		for(int i=0; i<100; i++) {
			numList.add(Integer.valueOf(i+1));
		}
		System.out.println(numList);
		
		numList.remove(10);
		System.out.println(numList);
	}
	
	/**
	 ** Task2：
	 ** 向set和list中添加字符串A a c C a等元素
	 ** 观察元素能否重复
	 */
	private static void task2() {
		System.out.println("=== task2 ===");
		HashSet<String> hashSet = new HashSet<>();
		hashSet.add("A");
		hashSet.add("a");
		hashSet.add("c");
		hashSet.add("C");
		hashSet.add("a");
		System.out.println(hashSet);
		
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("A");
		arrayList.add("a");
		arrayList.add("c");
		arrayList.add("C");
		arrayList.add("a");
		System.out.println(arrayList);
	}
	
	/**
	 ** Task3：
	 ** 创建Map集合，创建Emp对象，并将Emp对象添加到集合中
	 ** Emp对象的id作为Map集合的键，并将id为“015”的对象从集合移除
	 */
	static class Emp_t3 {
		String id = "";
		String name = "";
		
		public Emp_t3(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}		

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "[id=" + id + ",name=" + name + "]";
		}
	}
	
	private static void task3() {
		System.out.println("=== task3 ===");
		Emp_t3[] empArr = new Emp_t3[4];
		empArr[0] = new Emp_t3("001","asdf");
		empArr[1] = new Emp_t3("002","qwerty");
		empArr[2] = new Emp_t3("003","zxcvb");
		empArr[3] = new Emp_t3("015","plmko");
		
		//添加元素
		HashMap<String, Emp_t3> empMap = new HashMap<>();
		for(Emp_t3 emp: empArr) {
			empMap.put(emp.getId(), emp); 
		}
		System.out.println(empMap);
		
		//删除元素
		empMap.remove("015");
		System.out.println(empMap);
	}
	
	/**
	 * Chapter14 Collection
	 */
	public static void main(String[] args) {
		task1();
		task2();
		task3();
	}
}


