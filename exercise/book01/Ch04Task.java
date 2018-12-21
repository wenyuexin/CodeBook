package book01;

public class Ch04Task {
	public static void main(String[] args) {
		//Task1
		Ch04Task01 t1 = new Ch04Task01();
		System.out.println(t1.isOdd(19));
		System.out.println(t1.isOdd(20));
	}
}

/**
 * T1: 判断变量的奇偶
 */
class Ch04Task01 {
		
	boolean isOdd(int num) {
		return 1==num%2;
	}
}
