package basic.enumeration;



/**
 * @author Apollo4634
 * @create 2018/11/06
 */
public class TestMyEnum {
	//这里的MyEnum相当于TestMyEnum的内部类
	enum MyEnum {
		C1, //枚举量相当于枚举类内部的几个枚举实例
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
		C1(3){
			@Override
			void print() {
				System.out.println("print C1 - "+e);
			}
			
			@SuppressWarnings("unused")
			void print2() {
				System.out.println("print2 C1");
			}
		},
		C2(4){
			@Override
			void print() {
				System.out.println("print C2 - "+e);
			}
		},
		C3(5){
			@Override
			void print() {
				System.out.println("print C3 - "+e);
			}
		};

		private MyEnum3() {}
		
		private MyEnum3(int n) { this.e = n; }
		
		//@SuppressWarnings("unused")
		int e;
		//private static int e = 0;
		
		//枚举类类似于抽象类，可以定义一些方法
		abstract void print();
	}
	
	enum MyEnum4 {
		C1(7), C2(8), C3(9);

		//@SuppressWarnings("unused")
		//int e;
		private int num = 0;
				
		private MyEnum4() {}
		
		private MyEnum4(int n) { this.num = n; }

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
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
		
		MyEnum3.C1.print();
		MyEnum3.C3.print();
		MyEnum3.C1.print();
		
		//MyEnum3.C1.print2();
		System.out.println(MyEnum3.C1.e);
		System.out.println(MyEnum4.C2.getNum());
		
		//不能实例化枚举对象
		//TestMyEnum testMyEnum = new TestMyEnum();
		//MyEnum3 me = new TestMyEnum.MyEnum3(6);
	}
}
