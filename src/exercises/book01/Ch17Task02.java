package exercises.book01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** 
 * @author Apollo4634 
 * @creation 2018/11/15
 */
public class Ch17Task02 {
	
	/**
	 * Task2
	 * 定义泛型类，使用extends限制该泛型类的类型为List，
	 * 并分别创建两个泛型对象
	 * Task3
	 * 
	 */
	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(Integer.valueOf(10));
		arrayList.add(Integer.valueOf(12));
		arrayList.add(Integer.valueOf(14));		
		arrayList.add(Integer.valueOf(16));		
		System.out.println(arrayList);
		
		MyGeneric<ArrayList<Integer>> mg = new MyGeneric<>();
		mg.setObj(arrayList);
		System.out.println(mg.getObj());
		
		LinkedList<Double> linkedList = new LinkedList<>();
		linkedList.add(Double.valueOf(1));
		linkedList.add(Double.valueOf(2.56));
		linkedList.add(Double.parseDouble("3.14"));
		
		//下面这句将报错，因为实例化后编译器将自动插入LinkedList<Double>
		//的强制类型转换
		//mg.setObj(linkedList); //Error
		
		MyGeneric<LinkedList<Double>> mg2 = new MyGeneric<>(linkedList);
		System.out.println(mg2.getObj());
	}
}

/**
 * 定义泛型类并使用通配符
 */
class MyGeneric<T extends List<?>> {
	private T obj;
	
	MyGeneric() {}
	
	MyGeneric(T obj) {
		this.obj = obj;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
