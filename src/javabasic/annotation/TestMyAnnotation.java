package javabasic.annotation;

import java.lang.annotation.Annotation;

public class TestMyAnnotation {
	
	public static void main(String[] args) {
		
		
		
		/**
		 * 第1节功能：
		 * - 通过反射获取TestMyAnnotation中的所有注解并打印
		 * 结果：
		 * - 空
		 */
		System.out.println("===== 1 =====");
		TestMyAnnotation myAnnotationTest = new TestMyAnnotation();
		Class<?> class_myAnnotation = myAnnotationTest.getClass();
		Annotation[] annoArr = class_myAnnotation.getAnnotations();
		for(Annotation anno: annoArr) {
			System.out.println(anno);
		}
		
		/**
		 * 第2节功能：
		 * - 通过反射获取UsingAnnotation中的所有注解并打印
		 * 结果：
		 * - UsingAnnotation
		 * 
		 * 说明：
		 * - TestMyAnnotation中也使用了注解，
		 * - 但是SuppressWarnings
		 */
		System.out.println("===== 2 =====");
		TestMyAnnotation.UsingAnnotation myAnnotation = 
				new TestMyAnnotation.UsingAnnotation(); //生成内部类的实例
		Class<?> class_using = myAnnotation.getClass(); //反射获取Class类
		Annotation[] annoArr2 = class_using.getAnnotations(); //获取注解
		for(Annotation anno: annoArr2) {
			System.out.println(anno);
		}

	}
	
	//局部内部类
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
