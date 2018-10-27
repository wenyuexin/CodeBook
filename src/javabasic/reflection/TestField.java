package javabasic.reflection;

import java.lang.reflect.Modifier;

public class TestField {
	public static void main(String[] args) {
		Class<Stu> stuClass = Stu.class;
		
		System.out.println("===== 1 =====");
		try {
			int mod = stuClass.getDeclaredField("id").getModifiers();
			String modifier = Modifier.toString(mod);
			System.out.println(modifier);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
