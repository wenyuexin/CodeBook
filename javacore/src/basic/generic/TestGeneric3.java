package basic.generic;

/** 
* @author Apollo4634 
* @create 2018/11/11
*/
public class TestGeneric3 {
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		//限制泛型
		
		/**
		 * <? extends ClassB> 该类型必须继承ClassB
		 */
		
		//MyClass3<ClassB> obj0 = new MyClass3<>(new ClassA()); //Error
		//MyClass3<ClassB> obj0 = new MyClass3<>(new ClassC());
		
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
		
		//MyClass3<? super ClassB> obj6 = new MyClass3<>(new ClassC()); //居然可以编译？
		//obj6.printClassInfo();
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
		
		/**
		 * 注意，使用instanceof测试类型只适用于原始类型，
		 * 事实上 a instanceof TypeClass<XXX>仅仅只能判断出
		 * a是不是任意一个类型的TypeClass，和XXX没有关系
		 */
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
		//MyClass4<? super ClassB> object1 = new MyClass4<>(new ClassC());
		//object1.printClassInfo();
		
		MyClass3<? super ClassB> obj6 = new MyClass3<>(new ClassC());
		@SuppressWarnings("unused")
		Object c = obj6.getObj();
		
		System.out.println("===== 5 =====");
		/**
		 * 进一步学习发现，运行时的类型查询仅适用于原始类型，
		 * 在本例中，并没有对MyClass3的泛型类型做限定，
		 * 编译后的T将被Object替换，
		 */
		
		/**
		 * 2018-11-18
		 * MyClass3<? super ClassB> obj6 = new MyClass3<>(new ClassC());
		 * 实际上不论ClassA、ClassB、ClassC之间是否存在继承关系，
		 * MyClass3<ClassA>和MyClass3<ClassB>以及MyClass3<ClassC>之间都没有什么联系，
		 * 
		 * ? super ClassB的写法说明，编译器并不知道传入的泛型参数具体是什么类型，
		 * 编译器只知道传入的是ClassB的超类。因此通过getXXX方法获取内部的obj时只能赋给Object类。
		 * 
		 * 而MyClass3<T>的写法表明，编译后的T其实被替换成了Object，
		 * 因此在编译器不知道传入类型是什么的情况下，读取内部obj的时候并不能做确定的类型转换（个人推测）
		 * 
		 * obj6作为new MyClass3<>(new ClassC())的引用，后者由于省略的原因，
		 * 所以其泛型类型其实是通过类型推导得出的，推导结果和obj6有关，具体是什么还不知道，
		 * 个人猜想是，ClassB和ClassC共同的超类，有待验证
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

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
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
