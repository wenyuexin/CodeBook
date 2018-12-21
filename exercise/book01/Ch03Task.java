package book01;

public class Ch03Task {
	public static void main(String[] args) {
		//task1
		Ch03Task1 t1 = new Ch03Task1();
		t1.add(12,34);
		
		//task2
		Ch03Task2 t2 = new Ch03Task2();
		t2.age = 20;
		//t2.name; 不能直接访问
		t2.test();
		
		//Task3
		byte a = 10;
		System.out.println((int)a);
	}
}

/**
 * task1: 将两个数相加的结果输出
 */
class Ch03Task1 {	
	public void add(double a, double b) {
		System.out.println(a+b);
	}
}

/**
 * task2: 比较成员变量与局部变量 
 */
class Ch03Task2 {
	int age = 18;
	
	public void test() {
		String name = "asdf";
		System.out.println(name);
	}
}

/**
 * task3: 转int
 */
class Ch03Task3 {
}
