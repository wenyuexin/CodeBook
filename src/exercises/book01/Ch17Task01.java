package exercises.book01;

/** 
 * @author Apollo4634 
 * @creation 2018/11/15
 */
public class Ch17Task01 {
	
	/**
	 * 定义一个枚举类类型，使用switch语句获取枚举类型的值
	 */
	public static void main(String[] args) {
		
		MyEnum enum1 = MyEnum.E3;
		
		switch (enum1.ordinal()) {
		case 0:
			System.out.println(0);
			break;
		case 1:
			System.out.println(1);
			break;
		case 2:
			System.out.println(2);
			break;
		case 3:
			System.out.println(3);
			break;
		case 4:
			System.out.println(4);
			break;
		case 5:
			System.out.println(5);
			break;
		default:
			break;
		}
	}
}

enum MyEnum {
	E1, E2, E3, E4, E5, E6;
}
