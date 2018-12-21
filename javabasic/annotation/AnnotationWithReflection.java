package annotation;

import java.lang.annotation.Annotation;

/**
 * 测试自定义的注解
 * 使用反射获取注解相关信息
 * @author Apollo4634
 */
@MyAnnotation4(msg="AnnotationWithReflection")
public class AnnotationWithReflection {

	@MyAnnotation //RetentionPolicy的取值为SOURCE，不能通过反射获取
	@MyAnnotation2(msg="UsingAnnotation")
	@MyAnnotation3
	static class UsingAnnotation {
		@MyAnnotation
		UsingAnnotation() {
			System.out.println("构造 UsingAnnotation");
		}
		
		@MyAnnotation2
		void print() {
			System.out.println("print");
		}
	}

	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		@MyAnnotation
		int a = 3;

		@SuppressWarnings("all")
		int b = 2;
		
		/**
		 * 第1节尝试实现：
		 * - 通过反射获取TestMyAnnotation中的所有注解并打印
		 */
		System.out.println("===== 1 =====");
		AnnotationWithReflection myAnnotationTest = new AnnotationWithReflection();
		Class<?> class_myAnnotation = myAnnotationTest.getClass();
		Annotation[] annoArr = class_myAnnotation.getAnnotations();
		for(Annotation anno: annoArr) {
			System.out.println(anno);
		}
		
		/**
		 * 第2节尝试实现：
		 * - 通过反射获取UsingAnnotation中的所有注解并打印
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
		 * getAnnotations的功能虽然是获取所有注解，
		 * 但是不能获取该类内部所有字段、方法、构造器等的注解。
		 * 不同内部元素的注解，需要通过各自相应的Class类的getAnnotations方法区获取
		 */		
	}
}


