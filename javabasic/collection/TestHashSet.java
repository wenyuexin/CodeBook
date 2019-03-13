package collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试HashSet
 * @author Apollo4634
 * 说明：
 *  HashSet内部由HashMap实现，每个值都作为map的key
 */
public class TestHashSet {
	
	private static void test1() { 
		System.out.println("=== 1 ===");
		HashSet<String> set = new HashSet<>();
		
		//增加元素
		set.add("aaa");
		set.add("bbb");
		System.out.println(set);
		System.out.println("size = "+set.size());
		
		//set元素具有不可重复性
		set.addAll(set);
		System.out.println(set);
		
		//set元素具有不可重复性
		@SuppressWarnings("unchecked")
		HashSet<String> set2 = (HashSet<String>) set.clone();
		set.addAll(set2);
		System.out.println(set);
		
		set2.add("ccc");
		System.out.println("hset :"+set);
		System.out.println("hset2:"+set2);
		set.addAll(set2);
		System.out.println("hset:"+set);
		
		//clone
		//HashSet的clone方法是浅复制，返回的是Object类
		
		//元素包含关系
		System.out.println(set.contains("aa"));
		System.out.println(set.contains("aaa"));
		
		//移除
		set.remove("bbb");
		set.add("eee");
		System.out.println(set);
		
		//NULL
		set.add(null);
		System.out.println(set.size());
		System.out.println(set);
	}
	
	
	private static void test2() {
		System.out.println("=== 2 ===");
		Integer[] arr = new Integer[10];
		arr[0] = 1;
		arr[1] = 2;
		arr[2] = 3;
		System.out.println(Arrays.toString(arr));
		
		List<Integer> list = Arrays.asList(arr);
		Set<Integer> set = new HashSet<Integer>(list);
		System.out.println(set);
	}
	
	
	public static void main(String[] args) {
		TestHashSet.test1();
		TestHashSet.test2();
	}
}
