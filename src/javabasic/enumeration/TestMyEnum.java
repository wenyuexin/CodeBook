package javabasic.enumeration;

/**
 * @author Apollo4634
 * @creation 2018/11/06
 */
public class TestMyEnum {
	
	enum MyEnum {
		C1,
		C2,
		C3;
	}
	
	enum MyEnum2 {
		C1(2),
		C2(0),
		C3(1);
		
		final int e;
		
		MyEnum2(int e) {
			this.e = e;
		}
	}
	
	enum MyEnum3 {
		C1{
			@Override
			void print() {
				System.out.println("print C1");
			}
		},
		C2,
		C3;
		
		abstract void print();
		
		final int e;
		
		MyEnum3() {
		}
		
		MyEnum3(int e) {
			this.e = e;
		}
	}
	
	public static void main(String[] args) {
		/**
		 * 枚举类的实现原理：
		 * 使用关键字enum创建枚举类型并编译后，
		 * 编译器会为我们生成一个相关的类，
		 * 这个类继承了Java API中的java.lang.Enum类
		 * 
		 * 枚举类的参考：
		 * java.base模块中的java.math定义了：
		 * public enum RoundingMode
		 */
		
		System.out.println("===== 1 =====");
		System.out.println(MyEnum.C1+" "+MyEnum.C1.ordinal());
		System.out.println(MyEnum.C1);
		
		
	}
}
