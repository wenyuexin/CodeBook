package javabasic.enumeration;

/** 
* @author Apollo4634 
* @creation 2018/11/06
*/
public class TestEnum {
	enum Constants {
		c1,
		c2,
		c3
	}
	
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		System.out.println(Constants.c1);
		System.out.println(Constants.c2.ordinal());
		
		Constants constants = Constants.valueOf("c3");
		System.out.println(constants.ordinal());
		
		System.out.println("===== 2 =====");
		Constants[] arr = Constants.values();
		for(Constants c: arr) {
			System.out.println(c);
		}
		
		
	}
}
