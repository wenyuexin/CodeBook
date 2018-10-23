package javabasic.reflection;

//import java.lang.reflect.Constructor;

/**
 * Class类和Java反射
 * @author Apollo4634
 * 
 */

public class TestReflection {
	public static void main(String[] args) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
		
		//Constructor<Stu>[] constructor = class_stu.getConstructors();
		
	}
}

/**
 * 用于测试的学生类
 */
class Stu {
	private int id;
	private String name;
	private int age;
	
	public Stu() {
	}
	
	public Stu(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
