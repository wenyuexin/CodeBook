package javabasic.generic;

/** 
* @author Apollo4634 
* @creation 2018/11/11
*/
public class TestGeneric3 {
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		//限制泛型
		
		/**
		 * <? extends ClassB> 该类型必须继承ClassB
		 */
		//MyClass3<? extends ClassB> obj1 = new MyClass3<>(new ClassA()); //Error
		MyClass3<? extends ClassB> obj2 = new MyClass3<>(new ClassB());
		obj2.printClassInfo();
		MyClass3<? extends ClassB> obj3 = new MyClass3<>(new ClassC());
		obj3.printClassInfo();
		
		/**
		 * <? super ClassB> 该类型必须是ClassB的超类
		 */
		MyClass3<? super ClassB> obj4 = new MyClass3<>(new ClassA());
		obj4.printClassInfo();
		MyClass3<? super ClassB> obj5 = new MyClass3<>(new ClassB());
		obj5.printClassInfo();
		MyClass3<? super ClassB> obj6 = new MyClass3<>(new ClassC()); //居然可以编译？
		obj6.printClassInfo();
		//MyClass3<? super ClassB> obj6_2 = new MyClass3<ClassC>(new ClassC()); //Error
		
		System.out.println("===== 2 =====");
		//MyClass3<? extends ClassB> obj7 = new MyClass3<>(new String("asdf")); //Error
		//obj7.print();
		//MyClass3<? super ClassB> obj8 = new MyClass3<String>(new String("asdf")); //Error
		MyClass3<? super ClassB> obj8 = new MyClass3<>(new String("asdf"));
		obj8.printClassInfo();
		
		System.out.println("===== 3 =====");
		/**
		 * 通过以下代码猜测，第1节的28行之所以可以运行，是因为
		 * 实例化MyClass3过程中将参数ClassC向上转型为ClassB或者ClassB的超类
		 * 
		 * 那么现在的问题是，在使用泛型的条件下 如何判断某个实例存在向上转型
		 */
		ClassB cl = new ClassC();
		System.out.println(cl.getClass().getName());
		System.out.println(cl.getClass().getTypeName());
		//cl.testC(); //向上转型后不能调用本类特有的方法
		
		System.out.println(cl instanceof ClassB);
		System.out.println(cl instanceof ClassC);
		
		ClassB cl2 = new ClassB();
		System.out.println(cl2 instanceof ClassA);
		System.out.println(cl2 instanceof ClassB);
		System.out.println(cl2 instanceof ClassC);
		
		System.out.println("===== 4 =====");
		/**
		 * 测试发现实际上以下代码直接将
		 */
		MyClass4<? super ClassB> object1 = new MyClass4<>(new ClassC());
		object1.printClassInfo();
		
		System.out.println("===== 5 =====");
		/**
		 * 进一步学习发现，运行时的类型查询仅适用于原始类型，
		 * 在本例中，并没有对MyClass3的泛型类型做限定，
		 * 编译后的T将被Object替换，
		 */
	}
}

//===================================

class MyClass3<T> {
	T obj;
	
	public MyClass3(T obj) {
		super();
		this.obj = obj;
	}

	void printClassInfo() {
		System.out.println(obj.getClass().getName());
	}
}

class MyClass4<T extends ClassB> {
	T obj;
	
	public MyClass4(T obj) {
		super();
		this.obj = obj;
	}

	void printClassInfo() {
		System.out.println(obj.getClass().getName());
		//System.out.println(obj.getClass().getTypeName());
	}
}

//===================================

class ClassA {
	ClassA() {
		System.out.println("A");
	}
	
	void testA() {
		System.out.println("testA");
	}
}

class ClassB extends ClassA {
	ClassB() {
		System.out.println("B");
	}
	
	void testB() {
		System.out.println("testB");
	}
}

class ClassC extends ClassB {
	ClassC() {
		System.out.println("C");
	}
	
	void testC() {
		System.out.println("testC");
	}
}
