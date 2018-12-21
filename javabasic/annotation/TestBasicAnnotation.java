package annotation;

/**
 * 测试jdk中常见的几个Annotation（注解）
 * @author apollo4634
 * 
 * 说明：
 * - 常见的注解有 Deprecated Override SuppressWarnings
 * - 此外java.lang中还有
 */
public class TestBasicAnnotation {
	/**
	 * Override 表示此方法是对父类的重新，无参
	 * 
	 * 元注解：
	 * - @Target(METHOD)
	 * - @Retention(SOURCE)
	 */
	@Override
	public String toString() {
		/**
		 * 顺带提一个知识点：
		 * super.getClass()并不会返回父类的Class类，
		 * super.getClass()的意思是调用父类的getClass()方法
		 * 即实际调用的是Object的getClass()
		 * Object类，它返回对象在运行时的类型，
		 * 由于运行时是TestAnnotation，所以返回的是本类
		 * 
		 * super并没有代表超类的一个引用的能力，只是代表调用父类的方法而已
		 */
		return new String("override\t"+super.getClass().getName());
	}
	
	void printSuperClass() {
		@SuppressWarnings("rawtypes") //压制警告
		Class taClass = this.getClass();
		System.out.println("SuperClass\t"+taClass.getSuperclass().getName());
	}
	
	
	public static void main(String[] args) {
		TestBasicAnnotation ta =  new TestBasicAnnotation();
		System.out.println(ta); //toString
		ta.printSuperClass(); //打印超类	
		
		/**
		 * SuppressWarnings: 
		 * 带有参数String[] Value
		 * value包含若干需要压制的warning
		 * 
		 * 元注解：
		 * - @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
		 * - @Retention(RetentionPolicy.SOURCE)

		 * 例如:
		 * - @SuppressWarnings(value={"unused","rawtypes"})
		 */
		@SuppressWarnings("unused")
		int a = 0; //变量a未使用，使用"unused"
		//int b = 0; //未压制，直接warning
		
		/**
		 * Deprecated 参数：
		 * - String since: 表示版本号，即从xx版本后不推荐使用，默认""
		 * - boolean forRemoval: 未来是否会被移除，默认false
		 * 
		 * 元注解：
		 * - @Documented
		 * - @Retention(RUNTIME)
		 * - @Target({CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
		 */
		@SuppressWarnings("deprecation")
		Integer num = new Integer(123); //@Deprecated(since="9")
		System.out.println("Deprecated\t"+num.intValue());
	}
}
