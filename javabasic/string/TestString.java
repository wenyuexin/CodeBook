package string;

/** 
 * @author Apollo4634 
 * @creation 2018/12/08 14:12
 */

public class TestString {
	public static void main(String[] args) {
		
		//作为一个常量，存储在方法区的常量池
		String s1 = "aabb";
		String s1_2 = s1;
		//在堆上创建的对象
		String s2 = new String("aabb");
		String s2_2 = new String("aabb");
		String s2_3 = s2; //与s2引用了同一个对象
		
		//String的hashcode值的计算方法如下：
		//s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
		//即字符串的散列码由字符串的内容计算得出
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		
		//对于非基本类型，==直接比较两个类是否为同一个对象
		System.out.println("s1==s2   -> "+(s1==s2));
		System.out.println("s1==s1_2 -> "+(s1==s1_2));
		System.out.println("s2==s2_2 -> "+(s2==s2_2));
		System.out.println("s2==s2_3 -> "+(s2==s2_3));
		
		//string的equals方法直接比较两个字符串的内容是否相同
		System.out.println("s1.equals(s2) -> "+s1.equals(s2)); 
		
		
		
		
	}
}

