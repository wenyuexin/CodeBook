package collection;

import java.util.HashMap;

public class TestHashMap {
	
	public static void test1() {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(Integer.valueOf(10),"aab");
		map.put(Integer.valueOf(20),"bbc");
		map.put(Integer.valueOf(40),"ccd");
		map.put(Integer.valueOf(40),"asd"); //key值具有唯一性
		map.put(Integer.valueOf(30),null);
		System.out.println(map);
	}
	
	public static void main(String[] args) {
		TestHashMap.test1();
	}
}
