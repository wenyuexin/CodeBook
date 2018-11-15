package exercises.book01;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author Apollo4634 
 * @creation 2018/11/15
 */
public class Ch17Task02 {
	public static void main(String[] args) {
		MyGeneric<ArrayList<?>> mg = new MyGeneric<>();
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(Integer.valueOf(10));
		arrayList.add(Integer.valueOf(12));
		arrayList.add(Integer.valueOf(14));		
		arrayList.add(Integer.valueOf(16));		
		System.out.println(arrayList);
		
		mg.setObj(new ArrayList<Integer>());
		@SuppressWarnings("unchecked")
		ArrayList<Integer> arrayList2 = (ArrayList<Integer>) mg.getObj();
		System.out.println(arrayList2);
	}
}

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
