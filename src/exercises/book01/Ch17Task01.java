package exercises.book01;

/** 
 * @author Apollo4634 
 * @creation 2018/11/15
 */
public class Ch17Task01 {
	public static void main(String[] args) {
		
		MyEnum enum1 = MyEnum.E3;
		
		switch (enum1.ordinal()) {
		case 0:
			System.out.println("a");
			break;
		case 1:
			System.out.println("b");
			break;
		case 2:
			System.out.println("c");
			break;
		case 3:
			System.out.println("d");
			break;
		case 4:
			System.out.println("e");
			break;
		case 5:
			System.out.println("f");
			break;
		default:
			break;
		}
	}
}

enum MyEnum {
	E1, E2, E3, E4, E5, E6;
}
