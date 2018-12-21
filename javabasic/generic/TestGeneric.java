package generic;

/**
 * @author Apollo4634
 * @creation 2018/11/08
 */
public class TestGeneric {

	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		MyClass<Integer> mc = new MyClass<Integer>();
		Integer num = Integer.valueOf(12);
		mc.setObj(num);
		mc.print();
	}
}

class MyClass<T> {
	T obj;
	
	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	void print() {
		System.out.println(obj);
	}
}


