package javabasic.reflection;

import java.lang.reflect.Modifier;

/**
 * Class类和Java反射
 * @author Apollo4634
 * 
 */

public class TestConstructor {
	public static void main(String[] args) {
		Class<Stu> c = Stu.class;
		String modifier = "";
		try {
			int mod = c.getDeclaredField("id").getModifiers();
			modifier = Modifier.toString(mod);
		} catch (NoSuchFieldException e) { 
			e.printStackTrace();
		} catch (SecurityException e) { 
			e.printStackTrace();
		}
		System.out.println(modifier);
		
		
		
		
		
	}
}


