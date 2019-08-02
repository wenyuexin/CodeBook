package generic;

/** 
* @author Apollo4634 
* @create 2018/11/11
*/
public class TestGeneric2 {
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		MyClass2<?,?> mc = new MyClass2<>(new String("asdf"), new String("qwerty"));
		mc.print();
		
		System.out.println("===== 2 =====");
		//实例化后泛型不能直接改
		//mc2.set(Integer.valueOf(123), Character.valueOf('c'));
		
		System.out.println("===== 3 =====");
		MyClass2<Integer,Character> mc2 = new MyClass2<>();
		mc2.set(Integer.valueOf(123), Character.valueOf('c'));
		mc2.print();
	}
}

//===================================

class MyClass2<K,V> {
	private K key;
	private V value;
	
	MyClass2() {
	}
	
	public MyClass2(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	void set(K key, V value) {
		this.key = key;
		this.value = value;
	}

	void print() {
		System.out.println(key+" - "+value);
	}
}

