package basic.mytest;

public class TestInnerClass {
	public static void main(String[] args) {
		Outer ot = new Outer();
		ot.in.in_print();
		ot.in.in_print2();
		//ot.ot_fun();
		
		Outer.Inner2 in2 = new Outer.Inner2();
		in2.print2();
	}
}


class Outer {
	Inner in = new Inner();
	Inner2 in2 = new Inner2();
	int a = 10;

	public void ot_fun() {
		in.in_print();
	}
	
	class Inner {
		static final int b = 20;
		public void in_print() {
			System.out.println("test");
			System.out.println(a);
		}
		
		public void in_print2() {
			System.out.println("b="+b);
		}
	}
	
	static class Inner2 {
		int a = 0;
		void print2() {
			System.out.println("in2");
		}
	}
	
}
