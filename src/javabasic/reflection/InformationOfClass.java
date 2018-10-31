package javabasic.reflection;

import java.util.ArrayList;

/**
 * Class类和Java反射
 * get information with methods of Class
 * @author Apollo4634
 */

public class InformationOfClass {
	public static void main(String[] args) {
		//测试Class类的toString等相关方法
		
		Class<? extends String[]> class_strArr = args.getClass();
		Class<?> class_bag = Stu.BagOfStu.class;
		
		//Class类的toString该方法的内部实现如下
		//(isInterface() ? "interface ":(isPrimitive() ? "":"class "))+getName();	
		System.out.println("toString():");
		System.out.println(int.class.toString());
		System.out.println(String.class.toString());
		System.out.println(class_strArr.toString());
		System.out.println(class_bag.toString());
		//调用Object中的toString方法，返回getName()+hashCode
		System.out.println(args.toString()); 
		System.out.println();
		
		//Returns the name of the entity (class, interface, array class,primitive type, or void) 
		//represented by this Class object,as a String. 
		System.out.println("getName():");
		System.out.println(int.class.getName());
		System.out.println(String.class.getName()); //包名加类名
		System.out.println(class_strArr.getName());
		System.out.println(class_bag.getName());
		System.out.println();
		
		//Returns the simple name of the underlying class as given in the source code
		System.out.println("getSimpleName():");
		System.out.println(int.class.getSimpleName());
		System.out.println(String.class.getSimpleName());
		System.out.println(class_strArr.getSimpleName());
		System.out.println(class_bag.getSimpleName());
		System.out.println();
		
		//Returns a string describing this Class, 
		//including information about modifiers and type parameters
		System.out.println("toGenericString():");
		System.out.println(int.class.toGenericString());
		System.out.println(String.class.toGenericString());
		System.out.println(class_strArr.toGenericString());
		System.out.println(class_bag.toGenericString());
		System.out.println();
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("asdf");
		Class<?> class_arraylist = arr.getClass();
		System.out.println(class_arraylist.getName());
		System.out.println(class_arraylist.toString());
		System.out.println(class_arraylist.toGenericString());
		System.out.println();
		
		System.out.println("getPackageName():");
		System.out.println(int.class.getSimpleName());
		System.out.println(String.class.getSimpleName()); //类名
		System.out.println(class_strArr.getPackageName());//包名
		System.out.println(class_bag.getPackageName());
		System.out.println();
		
		//getTypeName: Return an informative string for the name of this type
		//内部通过isArray()对数组进行了单独的处理，非数组则直接返回getName()
		System.out.println("getTypeName():");
		System.out.println(int.class.getTypeName());
		System.out.println(String.class.getTypeName());
		System.out.println(class_strArr.getTypeName());
		System.out.println(class_bag.getTypeName());
		System.out.println();
		
		//getName()返回的是虚拟机里面的class的表示
		//getCanonicalName()返回的是更容易理解的表示
		//
		//String getCanonicalName()：
		//Returns the canonical name of the underlying class 
		// as defined by the Java Language Specification.
		//
		//Returns null if the underlying class does not have a canonical name (i.e., if
	    //it is a local or anonymous class or an array whose component
	    //type does not have a canonical name). - 对局部类或匿名类单独判断
		System.out.println("getCanonicalName():");
		System.out.println(int.class.getCanonicalName());		
		System.out.println(String.class.getCanonicalName());
		System.out.println(class_strArr.getCanonicalName());
		System.out.println(class_bag.getCanonicalName());
	}
}


