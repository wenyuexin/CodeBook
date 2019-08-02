package reflection;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过反射获取内部类
 * @author Apollo4634
 * 
 * Class<?>[] getDeclaredClasses()
 * - Returns an array of Class objects reflecting all the classes and interfaces
 * - declared as members of the class represented by this Class object.
 * - 获取调用此方法的类或接口中的内部类
 * - （获取被定义的类）
 * 
 * Class<?> getDeclaringClass()
 * - If the class or interface represented by this Class object is a member of another class, 
 * - returns the Class object representing the class in which it was declared.
 * - 如果调用此方法的类或接口是其他类的成员（是内部类），则返回定义此类的所在外部类的Class实例
 * - （获取定义自身的类）
 */
public class GetInnerClass {
	@MyAnnotation(value="InnerClass")
	class InnerClass {
		@MyAnnotation(value="InnerClass Constructor")
		InnerClass() {
			System.out.println("Construct InnerClass");
		}
	}
	
	
	public static void main(String[] args) {
		Class<?> cl_outer = GetInnerClass.class;
		Class<?>[] cl_inner = cl_outer.getDeclaredClasses();
		for(Class<?> cl: cl_inner) {
			System.out.println(cl); //类名
			Annotation[] annoArr = cl.getAnnotations();
			for(Annotation a: annoArr) {
				System.out.println(a); //注解
			}
		}
	}
}

@Target({CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
	String value() default "test";
}
