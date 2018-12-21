package mytest;

/** 
 * @author Apollo4634 
 * @creation 2018/11/24
 */

class A {
	String s;
}
public class Test1124 {
	public static void main(String[] args) {
		System.out.println(15%10);
		System.out.println(6%10);
		System.out.println(10%10);
		System.out.println(0%10);
		
		//if(1) { //Error	
		//	System.out.println(123);
		//}
		
		System.out.println(new A().s == null);
	}
}
