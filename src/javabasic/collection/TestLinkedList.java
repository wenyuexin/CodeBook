package javabasic.collection;

import java.util.List;
import java.util.LinkedList;

public class TestLinkedList {
	public static void main(String[] args) {
		//测试LinkedList
		Mylist mylist = new Mylist();//ArrayList extends AbstractList
		
		mylist.test(); 
		//ta.test2();
	}
}

class Mylist {

	public void test() {
		// TODO Auto-generated method stub
		LinkedList<String> mylist = new LinkedList<String>();
		mylist.add("aaa");
		mylist.add("bbb");
		
		System.out.println(mylist);
		
	}
	
}







