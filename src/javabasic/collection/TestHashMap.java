package javabasic.collection;

import java.util.HashMap;

public class TestHashMap {
	public static void main(String[] args) {
		MyHashMap hm = new MyHashMap();
		hm.test1();
		
	}
}

class MyHashMap {
	public void test1() {
		HashMap<Integer, String> hashMap = new HashMap<>();
		hashMap.put(Integer.valueOf(10),"aab");
		hashMap.put(Integer.valueOf(20),"bbc");
		hashMap.put(Integer.valueOf(40),"ccd");
		hashMap.put(Integer.valueOf(40),"asd"); //key值具有唯一性
		hashMap.put(Integer.valueOf(30),null);
		System.out.println(hashMap);
		
	}
}
