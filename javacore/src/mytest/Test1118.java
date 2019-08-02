package mytest;


/** 
 * @author Apollo4634 
 * @create 2018/11/18
 */
public class Test1118 {
	
	class A {}
	
	class B extends Test1118.A {}
	
	public static void main(String[] args) {
		
		Test1118 t = new Test1118();
		Test1118.A b = t.new B();
		System.out.println(b.getClass());
	}
}
