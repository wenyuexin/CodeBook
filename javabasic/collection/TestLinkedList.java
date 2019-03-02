package collection;

import java.util.LinkedList;


public class TestLinkedList {
	
	public static void test1() {
		/**
		 * LinkedList extends AbstractSequentialList<E>
		 * Constructors: 
		 * - LinkedList() 
		 * - LinkedList​(Collection<? extends E> c)
		 * By comparison, we can construct ArrayList in this way:
		 * - ArrayList​(int initialCapacity)
		 */
		System.out.println("=== test1 ===");
		LinkedList<String> list = new LinkedList<String>(); 
		
		// 增加和移除元素
		list.add("aaa");
		list.add("bbb");
		System.out.println(list);
		
		list.addFirst("first"); 
		list.addLast("last"); //和add()方法一样都是通过linkLast(e)实现
		System.out.println(list);
		
		list.remove("bbb");
		System.out.println(list);
		System.out.println(list.remove("cc"));
		
		list.add(1, "ccc");
		System.out.println(list);
	}
	
	public static void test2() {
		/**
		 ** 以下两种方法都能移除元素
		 * - E remove​(int index)
		 * - boolean remove​(Object o)
		 ** 那么是否存在冲突？其实方法2中的o必须是对象，所以不影响
		 */
		System.out.println("=== test2 ===");
		
		//LinkedList<int> list2 = new LinkedList<int>(); 
		// 注意：这里不能使用基本数据类型
	}
	
	public static void test3() {
		System.out.println("=== test3 ===");
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.offer(Integer.valueOf(11)); //作为Queue使用时的方法，对应的有offer/poll/take
		list.offer(Integer.valueOf(22));
		list.offer(Integer.valueOf(33));
		list.offer(Integer.valueOf(2));
		list.offer(Integer.valueOf(22)); //55
		list.offer(Integer.valueOf(44));
		System.out.println(list);
		
		int index22 = list.indexOf(Integer.valueOf("22"));
		System.out.println(index22);
		System.out.println(list.lastIndexOf(Integer.valueOf("22")));
		
		list.set(4, Integer.valueOf("55"));
		System.out.println(list);
		
		// 转为数组
		//Integer[] intArr = llist.toArray(); //返回的是Object类
		Object[] objArr = list.toArray();
		System.out.println(objArr);
		for(Object val: objArr) {
			System.out.print(val+" ");
		}
		System.out.println();
		
		//Integer[] intArr = (Integer[])objArr; //不能这样转
		//System.out.println(intArr);
	}
	
	
	public static void main(String[] args) {
		// 测试LinkedListMylist();
		
		TestLinkedList.test1(); 
		TestLinkedList.test2();
		TestLinkedList.test3();
	}
}








