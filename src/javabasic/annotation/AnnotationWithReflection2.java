package javabasic.annotation;

import java.lang.annotation.Annotation;

/**
 * 测试自定义的注解
 * 使用反射获取注解相关信息
 * @author Apollo4634
 */
@MyAnnotation4(msg="AnnotationWithReflection")
public class AnnotationWithReflection2 {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		@MyAnnotation
		int a = 3;
		@SuppressWarnings("all")
		int b = 2;
		
		//获取注解的成员
		System.out.println("===== 1 =====");
		AnnotationWithReflection2 ar = new AnnotationWithReflection2();
		Annotation anno = ar.getClass().getAnnotation(MyAnnotation4.class);
		System.out.println(anno+"\t"+((MyAnnotation4)anno).msg());
		
		//
		System.out.println("===== 2 =====");
		
		
		
		
		
	}
	
	@MyAnnotation //RetentionPolicy的取值为SOURCE，不能通过反射获取
	@MyAnnotation2(msg="UsingAnnotation")
	@MyAnnotation3
	static class UsingAnnotation2 {
		@MyAnnotation
		UsingAnnotation2() {
			System.out.println("构造 UsingAnnotation");
		}
		
		@MyAnnotation2
		void print() {
			System.out.println("print");
		}
		
	}
}


