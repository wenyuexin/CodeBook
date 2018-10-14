package javabasic.exception;

public class TestTryCatch {
	public static void main(String[] args) {
		int a = 10;
		
		try {
			a = 1/0;
			System.out.println("test1");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("test2");
			e.printStackTrace();
		}
		finally {
			System.out.println("test3");
		}
		
		System.out.println(a);
	}
}

