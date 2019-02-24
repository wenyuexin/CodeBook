package book_978_7_302_44454_1;

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
		
		switch (enum1) {
		case E0:
			System.out.println(0);
			break;
		case E1:
			System.out.println(1);
			break;
		case E2:
			System.out.println(2);
			break;
		case E3:
			System.out.println(3);
			break;
		case E4:
			System.out.println(4);
			break;
		case E5:
			System.out.println(5);
			break;
		default:
			break;
		}
	}
}

enum MyEnum {
	E0, E1, E2, E3, E4, E5, E6;
}
