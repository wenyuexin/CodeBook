package enumeration;

import java.math.RoundingMode;

/** 
 * @author Apollo4634 
 * @create 2018/11/06
 */

public class EnumTest {

	public static void main(String[] args) {
		//以RoundingMode为例
		
		System.out.println("===== 1 =====");
		System.out.println(RoundingMode.HALF_DOWN);
		System.out.println(RoundingMode.HALF_EVEN.ordinal()); //返回枚举量的序数
		System.out.println(RoundingMode.UP instanceof RoundingMode);
		System.out.println(RoundingMode.UP instanceof Enum);
		
		System.out.println("===== 2 =====");
		RoundingMode rm = RoundingMode.valueOf("DOWN"); //返回对应字符串的枚举实例
		int num = rm.ordinal();
		String name = rm.name(); //获取枚举量对应的名字
		System.out.println(name+" - "+num);
		System.out.println(rm.toString());
		System.out.println();
		
		System.out.println("===== 3 =====");
		Enum<?>[] arr = RoundingMode.values(); //返回包含所有枚举量的数组
		for(Enum<?> e: arr) {
			System.out.println(e+" - "+e.ordinal());
		}
		
		System.out.println("===== 4 =====");
		Class<?> cl_rm = rm.getDeclaringClass();
		System.out.println(cl_rm+"\n");
		
		System.out.println("===== 5 =====");
		RoundingMode rm2 = RoundingMode.valueOf(0);//RoundingMode自定义的方法
		System.out.println(rm2);
		
		RoundingMode rm3 = RoundingMode.valueOf("DOWN"); //来自Enum的方法
		System.out.println(rm3);
		
		RoundingMode rm4 = Enum.valueOf(RoundingMode.class,"UNNECESSARY"); //来自Enum的方法
		System.out.println(rm4);
		
		System.out.println("===== 6 =====");
		System.out.println(rm.compareTo(rm2));
		System.out.println(rm.compareTo(rm));
		System.out.println(rm2.compareTo(rm));
	}
}
