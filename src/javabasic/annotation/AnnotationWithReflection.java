package javabasic.annotation;

import java.lang.annotation.Annotation;

/**
 * 测试自定义的注解
 * 使用反射获取注解相关信息
 * @author Apollo4634
 */
public class AnnotationWithReflection {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		@MyAnnotation
		int a = 3;

		@SuppressWarnings("all")
		int b = 2;
		
		/**
		 * 第1节功能：
		 * - 通过反射获取TestMyAnnotation中的所有注解并打印
		 * 结果：
		 * - 控制台输出：空
		 */
		System.out.println("===== 1 =====");
		AnnotationWithReflection myAnnotationTest = new AnnotationWithReflection();
		Class<?> class_myAnnotation = myAnnotationTest.getClass();
		Annotation[] annoArr = class_myAnnotation.getAnnotations();
		for(Annotation anno: annoArr) {
			System.out.println(anno);
		}
		
		/**
		 * 第2节功能：
		 * - 通过反射获取UsingAnnotation中的所有注解并打印
		 * 结果：
		 * - 控制台输出 UsingAnnotation
		 * 
		 * 说明：
		 * - TestMyAnnotation中也使用了注解，
		 * - 但是SuppressWarnings
		 */
		System.out.println("===== 2 =====");
		AnnotationWithReflection.UsingAnnotation myAnnotation = 
				new AnnotationWithReflection.UsingAnnotation(); //生成局部类的实例
		Class<?> class_using = myAnnotation.getClass(); //反射获取Class类
		Annotation[] annoArr2 = class_using.getAnnotations(); //获取注解
		for(Annotation anno: annoArr2) {
			System.out.println(anno.toString());
		}
		
		
		/**
		 * 上面的理解好像有问题
		 * getAnnotations() Returns annotations that are present on this element
		 * 
		 */
		System.out.println("===== 3 =====");
		Annotation[] annoArr3 = class_using.getAnnotations(); //获取注解
		for(Annotation anno: annoArr3) {
			System.out.println(anno.toString());
		}
		
		
		
	}
	
	
	static class UsingAnnotation {
		@MyAnnotation
		UsingAnnotation() {
			System.out.println("UsingAnnotation");
		}
		
		void print() {
			System.out.println("print");
		}
		
	}
}


