package book_978_7_115_29380_0;

public class Ch11Task03 {
	public static void main(String[] args) {
		A.B b = new A.B();
		b.fff();
	}
}

class A {
	static int num = 123;
	
	static class B {
		public void fff() {
			System.out.println("test"+num);
		}
	}
}