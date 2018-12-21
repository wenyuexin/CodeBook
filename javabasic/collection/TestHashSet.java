package collection;

import java.util.HashSet;

/**
 * 测试HashSet
 * @author Apollo4634
 * 说明：
 *  HashSet内部由HashMap实现，每个值都作为map的key
 */
public class TestHashSet {
	public static void main(String[] args) {
		MyHashSet myHashSet = new MyHashSet();
		myHashSet.test1();
	}
}

class MyHashSet {
	//元素修改、判断
	public void test1() { 
		System.out.println("=== test1 ===");
		HashSet<String> hset = new HashSet<>();
		
		//增加元素
		hset.add("aaa");
		hset.add("bbb");
		System.out.println(hset);
		System.out.println("size = "+hset.size());
		
		//set元素具有不可重复性
		hset.addAll(hset);
		System.out.println(hset);
		
		//set元素具有不可重复性
		@SuppressWarnings("unchecked")
		HashSet<String> hset2 = (HashSet<String>) hset.clone();
		hset.addAll(hset2);
		System.out.println(hset);
		
		hset2.add("ccc");
		System.out.println("hset :"+hset);
		System.out.println("hset2:"+hset2);
		hset.addAll(hset2);
		System.out.println("hset:"+hset);
		
		//clone
		//HashSet的clone方法是浅复制，返回的是Object类
		
		//元素包含关系
		System.out.println(hset.contains("aa"));
		System.out.println(hset.contains("aaa"));
		
		//移除
		hset.remove("bbb");
		hset.add("eee");
		System.out.println(hset);
		
		//NULL
		hset.add(null);
		System.out.println(hset.size());
		System.out.println(hset);
		
	}
}