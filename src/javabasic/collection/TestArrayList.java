package javabasic.collection;

import java.util.List;
import java.util.ArrayList;

public class TestArrayList {
	public static void main(String[] args) {
		//����ArrayList
		MyArrayList ta = new MyArrayList();//ArrayList extends AbstractList
		//ta.test(); //����ArrayList����غ���
		ta.test2();
	}
}

class MyArrayList {
	public void test() {
		//ArrayList1
		List<String> alist = new ArrayList<String>(); //����ת��		
		alist.add("a");
		alist.add("b");
		alist.add("c");
		System.out.println("alist: "+alist);
		
		//ArrayList2
		List<String> alist2 = new ArrayList<String>();
		alist2.add("a");
		alist2.add("d");
		System.out.println("alist2: "+alist2);
		
		//�ж�ArrayList1�Ƿ����ArrayList2������Ԫ��
		System.out.println(alist.containsAll(alist));
		System.out.println(alist.containsAll(alist2));
		
		//����ArrayList1����������ArrayList2��Ԫ�أ��൱���󽻼�
		alist.retainAll(alist2);
		System.out.println(alist);
		
		//�Ƴ�ArrayList1����������ArrayList2��Ԫ�أ��൱����
		alist.removeAll(alist2);
		System.out.println(alist);
	}
	
	/* ˳����������ת�ͺ�ĺ����������� */
	public void test2() {
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
		
		//alist.trimToSize();  //����ת�ͺ���û�еķ������ܵ���
		//System.out.println(alist);
		
		// ��ArrayListʵ����capacityֵ�޸�Ϊ��ǰ��sizeֵ
		alist2.trimToSize(); 
		System.out.println("trimToSize: "+alist2);
		//System.out.println(alist2.capacity); //��λ�ȡcapacity��
	}
}




