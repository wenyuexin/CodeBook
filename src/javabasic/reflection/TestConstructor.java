package javabasic.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class类和Java反射
 * @author Apollo4634
 * 
 */

public class TestConstructor {
	public static void main(String[] args) {
		Class<Stu> stuClass = Stu.class;
		String modifier = "";
		try {
			int mod = stuClass.getDeclaredField("id").getModifiers();
			modifier = Modifier.toString(mod);
		} catch (NoSuchFieldException e) { 
			e.printStackTrace();
		} catch (SecurityException e) { 
			e.printStackTrace();
		}
		System.out.println(modifier);
		
		//获取所有public修饰的构造器
		System.out.println("===============");
		@SuppressWarnings("unchecked")
		Constructor<Stu>[] stuArr = (Constructor<Stu>[]) Stu.class.getConstructors();
		for(Constructor<Stu> constructor: stuArr) {
			System.out.println(constructor.toString());
		}
		System.out.println("");
		
		//获取所有声明的构造器
		@SuppressWarnings("unchecked")
		Constructor<Stu>[] stuArr2 = (Constructor<Stu>[]) Stu.class.getDeclaredConstructors();
		for(Constructor<Stu> constructor: stuArr2) {
			System.out.println(constructor.toString()); //打印所有构造器
		}
		
		//打印构造器
		System.out.println("===============");
		TestConstructor.printConstructors(stuClass);
		
	}
	
	//打印构造器
	static void printConstructors(Class<?> cl) {
		String className = cl.getSimpleName(); //获取类名
		Constructor<?>[] classConstructor = cl.getDeclaredConstructors();
		
		System.out.println(cl.getName()+":");
		for(Constructor<?> constructor: classConstructor) {
			int modifier = constructor.getModifiers(); //获取修饰符
			Class<?>[] paramTypes = constructor.getParameterTypes(); //获取参数类型
			
			if(modifier != 0) {
				System.out.print(Modifier.toString(modifier)+" "); //打印修饰符
			}
			System.out.print(className+"("); //打印构造器名称
			
			/*
			ArrayList<class> list = paramTypes;
			 
			for(Iterator<?> iterator = list.iterator();iterator.hasNext();) {
				System.out.print(iterator.next().toString());
			}
			*/
			
			System.out.println(")");
		}
		
	}
}


