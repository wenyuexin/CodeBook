package javabasic.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Class类和Java反射
 * @author Apollo4634
 * 
 */

public class TestReflection {
	public static void main(String[] args) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<Stu> c = Stu.class;
		String modifier = "";
		try {
			int mod = c.getDeclaredField("id").getModifiers();
			modifier = Modifier.toString(mod);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		System.out.println(modifier);
		
		System.out.println("==========");
		Stu stu = new Stu();
		stu.setId(1000);
		stu.setName("Asdf Qew");
		stu.setAge(15);

		String classNameOfStu = stu.getClass().getName();
		System.out.println(classNameOfStu); //获取类名
		System.out.println(stu.getClass().getPackageName()); //获取包名
		
		@SuppressWarnings("rawtypes")
		Class class_stu = Class.forName(classNameOfStu); //根据类名字符串创建Class类对象
		System.out.println(class_stu.getName());
		
		@SuppressWarnings("rawtypes")
		Class class2_stu = Stu.class; //根据类直接返回相关的Class类的对象
		System.out.println(class2_stu.getSuperclass().getName()); //获取父类的名称
		
		//虚拟机为每个类型管理一个Class对象
		System.out.println("==========");
		System.out.println(class_stu.hashCode());
		System.out.println(class2_stu.hashCode());
		System.out.println(class_stu == class2_stu);
		System.out.println(class_stu.equals(class2_stu));
		
		@SuppressWarnings("deprecation")
		Stu stu2 = (Stu) class_stu.newInstance(); //生成Stu实例
		stu2.setId(2000);
		System.out.println(stu2.getId());
		
		System.out.println("==========");
		@SuppressWarnings("unchecked")
		Constructor<Stu>[] constructors = class_stu.getDeclaredConstructors();
		Stu stu3 = null;
		try {
			stu3 = constructors[1].newInstance(4004,"ddd",20);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		stu3.setId(4005);
		System.out.println(stu3);
	}
}


