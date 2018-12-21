package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Class类和Java反射 - 测试构造器
 * @author Apollo4634
 */

public class TestConstructor {
	public static void main(String[] args) {

		//获取所有public修饰的构造器
		System.out.println("===== 1 =====");
		@SuppressWarnings("unchecked")
		Constructor<Stu>[] stuArr = (Constructor<Stu>[]) Stu.class.getConstructors();
		for(Constructor<Stu> constructor: stuArr) {
			System.out.println(constructor.toString());
		}
		System.out.println();
		
		//获取所有声明的构造器
		@SuppressWarnings("unchecked")
		Constructor<Stu>[] stuArr2 = (Constructor<Stu>[]) Stu.class.getDeclaredConstructors();
		for(Constructor<Stu> constructor: stuArr2) {
			System.out.println(constructor.toString()); //打印所有构造器
		}
		
		//打印构造器
		System.out.println("===== 2 =====");
		Class<Stu> stuClass = Stu.class;
		TestConstructor.printConstructors(stuClass);
		
	}
	
	//打印构造器 - 包含修饰符、构造器名称、形参列表
	static void printConstructors(Class<?> cl) {
		String className = cl.getSimpleName(); //获取类名
		Constructor<?>[] classConstructor = cl.getDeclaredConstructors();
		
		System.out.println(cl.getName()+":");
		for(Constructor<?> constructor: classConstructor) {
			//获取修饰符，若修饰符不为空则打印
			int modifier = constructor.getModifiers(); 
			if(modifier != 0) {
				System.out.print(Modifier.toString(modifier)+" "); //打印修饰符
			}
			
			//打印构造器名称
			System.out.print(className+"("); 
			
			//使用迭代器遍历构造器的参数
			Class<?>[] paramTypes = constructor.getParameterTypes(); //获取参数类型 class数组
			ArrayList<Class<?>> list = new ArrayList<>(Arrays.asList(paramTypes));
			for(Iterator<?> iterator = list.iterator();iterator.hasNext();) {
				//打印参数类型
				//System.out.print(iterator.next().toString()); //方法1
				
				Class<?> cl_p = (Class<?>) iterator.next(); //方法2
				System.out.print(cl_p.getSimpleName());
				
				if(iterator.hasNext()) {
					System.out.print(", "); //用逗号间隔
				}
			}
			System.out.println(")");
		}
	}
}


