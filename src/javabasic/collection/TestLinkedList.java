package javabasic.collection;

import java.util.LinkedList;

public class TestLinkedList {
	public static void main(String[] args) {
		// 测试LinkedList
		Mylist mylist = new Mylist();
		
		mylist.test(); 
		//ta.test2();
	}
}

class Mylist {

	public void test() {
		/**
		 * LinkedList extends AbstractSequentialList<E>
		 * Constructors: 
		 * - LinkedList() 
		 * - LinkedList​(Collection<? extends E> c)
		 * By comparison, we can construct ArrayList in this way:
		 * - ArrayList​(int initialCapacity)
		 */
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
		
		//LinkedList<int> llist2 = new LinkedList<int>(); 
		// 注意：这里不能使用基本数据类型
	}
	
}







