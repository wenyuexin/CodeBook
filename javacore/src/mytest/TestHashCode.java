package mytest;

public class TestHashCode
{
	static int n = 12;
	int nn = 0;

	public static void main(String[] args)
	{

		String ss = "adsf";

		System.out.println("ss = \"" + ss + "\" at " +
				System.identityHashCode(ss));

		System.out.println("ss.trim() = \"" + ss.trim() + "\" at " + 
				System.identityHashCode(ss.trim()));

		String ss2 = ss.replace('f', 'g');
		System.out.println("ss2 = \"" + ss2 + "\" at " + ss2.hashCode());

		System.out.println("ss.replace() = \"" + ss.replace("d", "k") + "\" at " + ss2.replace('f', 'k').hashCode());

		System.out.println("ss2: " + ss2);

		System.out.println("asdf".hashCode());

		//String ss3 = ss.replace('f', 'k');
		System.out.println(System.identityHashCode("asdf"));

		// ��������
		System.out.println("===============");
		int m = 3;
		int n = 3;
		System.out.println(System.identityHashCode(m));
		System.out.println(System.identityHashCode(n));
	}
}
