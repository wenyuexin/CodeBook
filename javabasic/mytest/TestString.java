package mytest;

public class TestString {
	//�ַ����ָ�
	public void test1() {
		String ss = "as dqf ww";
		System.out.println(ss);

		String[] ssArr = ss.split(" |q");
		System.out.println(ssArr[0]);
	}

	//�ַ����ָ�
	public void test2() {
		String ss = "192.168.0.1";
		String[] ssArr = ss.split(".");
		System.out.println(ssArr[0]);
	}

	//�ַ�����Сдת�� �� �Ƚ�
	public void test3() {
		String ss = "asdERsWRHk";
		System.out.println(ss.toLowerCase());
		System.out.println(ss.toUpperCase());
		String s1 = ss.substring(1, 3);
		String s2 = ss.substring(1, 3);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s1.equalsIgnoreCase(s2));
		System.out.println(s1 == s2);
	}
	
	//����
	public void test4() {
		String ss = "18215599623";
		System.out.println(ss.matches("[1]{1}\\d{10}"));
	}
	
	//�ַ���������
	public void test5() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<10; i++) {
			sb.append(i+1);
		}
		
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) {
		TestString ts = new TestString();
		ts.test5();
	}
}
