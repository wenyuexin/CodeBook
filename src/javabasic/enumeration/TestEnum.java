package javabasic.enumeration;

import java.math.RoundingMode;

/** 
 * @author Apollo4634 
 * @creation 2018/11/06
 */
public class TestEnum {
	enum Constants {
		C1,
		C2,
		C3
	}
	
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		System.out.println(Constants.C1);
		System.out.println(Constants.C2.ordinal()); //返回枚举量的序数
		System.out.println(Constants.C3 instanceof Constants);
		
		System.out.println("===== 2 =====");
		Constants constants = Constants.valueOf("C3"); //返回对应字符串的枚举实例
		int num = constants.ordinal();
		String name = constants.name(); //获取枚举量对应的名字
		System.out.println(name+" -- "+num);
		System.out.println();
		
		System.out.println("===== 3 =====");
		Constants[] arr = Constants.values(); //返回包含所有枚举量的数组
		for(Constants c: arr) {
			System.out.println(c);
		}
		
		System.out.println("===== 4 =====");
		Class<?> cl = constants.getDeclaringClass();
		System.out.println(cl);
		
		System.out.println("===== 5 =====");
		RoundingMode rm = RoundingMode.valueOf(0);//RoundingMode自定义的方法
		System.out.println(rm);
		
		RoundingMode rm2 = RoundingMode.valueOf("DOWN"); //来自Enum的方法
		System.out.println(rm2);
		
		System.out.println("===== 6 =====");
		System.out.println(rm.compareTo(rm2));
		System.out.println(rm.compareTo(rm));
		System.out.println(rm2.compareTo(rm));
	}
}
