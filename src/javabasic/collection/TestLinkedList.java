package javabasic.collection;

import java.util.LinkedList;

public class TestLinkedList {
	public static void main(String[] args) {
		// 测试LinkedList
		Mylist mylist = new Mylist();
		
		mylist.test1(); 
		mylist.test2();
		mylist.test3();
	}
}

class Mylist {

	public void test1() {
		/**
		 * LinkedList extends AbstractSequentialList<E>
		 * Constructors: 
		 * - LinkedList() 
		 * - LinkedList​(Collection<? extends E> c)
		 * By comparison, we can construct ArrayList in this way:
		 * - ArrayList​(int initialCapacity)
		 */
		System.out.println("=== test1 ===");
		LinkedList<String> llist = new LinkedList<String>(); 
		
		// 增加和移除元素
		llist.add("aaa");
		llist.add("bbb");
		System.out.println(llist);
		
		llist.addFirst("first"); 
		llist.addLast("last"); //和add()方法一样都是通过linkLast(e)实现
		System.out.println(llist);
		
		llist.remove("bbb");
		System.out.println(llist);
		System.out.println(llist.remove("cc"));
		
		llist.add(1, "ccc");
		System.out.println(llist);
	}
	
	public void test2() {
		/**
		 ** 以下两种方法都能移除元素
		 * - E remove​(int index)
		 * - boolean remove​(Object o)
		 ** 那么是否存在冲突？其实方法2中的o必须是对象，所以不影响
		 */
		System.out.println("=== test2 ===");
		
		//LinkedList<int> llist2 = new LinkedList<int>(); 
		// 注意：这里不能使用基本数据类型
	}
	
	public void test3() {
		System.out.println("=== test3 ===");
		
		LinkedList<Integer> llist = new LinkedList<Integer>();
		llist.offer(Integer.valueOf(11)); //作为Queue使用时的方法，对应的有offer/poll/take
		llist.offer(Integer.valueOf(22));
		llist.offer(Integer.valueOf(33));
		llist.offer(Integer.valueOf(2));
		llist.offer(Integer.valueOf(22)); //55
		llist.offer(Integer.valueOf(44));
		System.out.println(llist);
		
		int index22 = llist.indexOf(Integer.valueOf("22"));
		System.out.println(index22);
		System.out.println(llist.lastIndexOf(Integer.valueOf("22")));
		
		llist.set(4, Integer.valueOf("55"));
		System.out.println(llist);
		
		// 转为数组
		//Integer[] intArr = llist.toArray(); //返回的是Object类
		Object[] objArr = llist.toArray();
		System.out.println(objArr);
		for(Object val: objArr) {
			System.out.print(val+" ");
		}
		System.out.println();
		
		//Integer[] intArr = (Integer[])objArr; //不能这样转
		//System.out.println(intArr);
	}
}







