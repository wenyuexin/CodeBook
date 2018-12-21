package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * 测试自定义的注解
 * 使用反射获取注解相关信息
 * @author Apollo4634
 */
@MyAnnotation
@MyAnnotation3(msg="Annotation3")
@MyAnnotation4(msg="AnnotationWithReflection")
public class AnnotationWithReflection2 {
	
	@MyAnnotation //RetentionPolicy的取值为SOURCE，不能通过反射获取
	@MyAnnotation2(msg="UsingAnnotation")
	@MyAnnotation3
	static class UsingAnnotation2 {
		@MyAnnotation
		@MyAnnotation2(msg="UsingAnnotation2的构造函数")
		UsingAnnotation2() { //注意这里不能用 getConstructor()获取
			System.out.println("构造 class UsingAnnotation");
		}
		
		@MyAnnotation2
		void print() {
			System.out.println("print");
		}
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		@MyAnnotation
		int num = 3;
		@SuppressWarnings("all")
		int num2 = 2;
		
		//获取并打印指定的注解
		System.out.println("===== 1 =====");
		AnnotationWithReflection2 ar2 = new AnnotationWithReflection2();
		Annotation anno = ar2.getClass().getAnnotation(MyAnnotation4.class);
		System.out.println(anno+"\t"+((MyAnnotation4)anno).msg());
		
		//获取并打印所有注解
		System.out.println("===== 2 =====");
		Annotation[] annoArr = ar2.getClass().getAnnotations();
		for(Annotation a: annoArr) {
			System.out.println(a);
		}
		
		//获取并打印局部内部类的所有注解
		System.out.println("===== 3 =====");
		//获取内部类的Class - 方式1
		Class<AnnotationWithReflection2.UsingAnnotation2> cl_ar2_using2 = 
				AnnotationWithReflection2.UsingAnnotation2.class;
		//获取内部类的Class - 方式2
		// - 通过外部类的Class获取内部类？
		
		//补充说明：
		//cl_inner.getEnclosingClass() 获取定义cl_inner类的Class类，例如：
		//Class<?> cl_ar2_using2_ = cl_ar2_using2.getEnclosingClass();
		//返回定义UsingAnnotation2的AnnotationWithReflection2的Class类
		
		//打印输出
		Annotation[] annoArr3 = cl_ar2_using2.getAnnotations();
		for(Annotation a: annoArr3) {
			System.out.println(a);
		}
		
		//获取并输出构造器的注解
		System.out.println("===== 4 =====");
		try {
			Constructor<?> constructor_using2 = cl_ar2_using2.getDeclaredConstructor();
			Annotation[] annoArr4 = constructor_using2.getAnnotations();
			for(Annotation a: annoArr4) {
				System.out.println(a);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}
}


