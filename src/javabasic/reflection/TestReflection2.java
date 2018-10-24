package javabasic.reflection;

import java.lang.reflect.Modifier;

/**
 * Class类和Java反射
 * @author Apollo4634
 * 
 */

public class TestReflection2 {
	public static void main(String[] args) {
		Class<Stu> c = Stu.class;
		String modifier = "";
		try {
			modifier = Modifier.toString(c.getDeclaredField("id").getModifiers());
		} catch (NoSuchFieldException e) { 
			e.printStackTrace();
		} catch (SecurityException e) { 
			e.printStackTrace();
		}
		System.out.println(modifier);
		
		
		
		
		
	}
}


