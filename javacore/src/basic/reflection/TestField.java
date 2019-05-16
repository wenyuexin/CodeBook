package basic.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class类和Java反射 - 测试构造器
 * @author Apollo4634
 */

public class TestField {
	public static void main(String[] args) 
			throws NoSuchFieldException, SecurityException, 
				IllegalArgumentException, IllegalAccessException, 
				NoSuchMethodException, InvocationTargetException {
		Class<Stu> stuClass = Stu.class;
		
		//获取成员变量的修饰符
		System.out.println("===== 1 =====");
		int mod = stuClass.getDeclaredField("id").getModifiers();
		String modifier = Modifier.toString(mod);
		System.out.println(modifier);
		
		//打印所有的成员变量
		System.out.println("===== 2 =====");
		TestField.printFields(stuClass);
		
		//动态修改成员变量
		System.out.println("===== 3 =====");
		Stu stu = new Stu(1001,"Asdf Qwert",19);		
		
		Field field_name = stuClass.getDeclaredField("name");
		field_name.setAccessible(true);
		Object name = field_name.get(stu);
		System.out.println("name:\t"+name);
		
		System.out.println("before:\t"+stu.getAge());
		Method m = stuClass.getMethod("setAge", int.class);
		m.invoke(stu, 20);
		System.out.println("after:\t"+stu.getAge());
		
		System.out.println("===== 4 =====");
		Field field_id = stuClass.getDeclaredField("id");
		int n = field_id.getInt(stu);
		System.out.println(n);

	}
	
	static void printFields(Class<?> cl) {
		Field[] fields_stu = cl.getDeclaredFields();
		System.out.println(fields_stu);
		System.out.println();
		
		for(Field f: fields_stu) {
			System.out.println(f);
		}
	}
}
