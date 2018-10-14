package javabasic.mytest;


public class Test1004 {
	int a = 0;
	double d = 2;
	
	public static void main(String[] args) {
		
		System.out.println(new Test1004() instanceof Object);

		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		long temp;
		temp = Double.doubleToLongBits(d);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test1004 other = (Test1004) obj;
		if (a != other.a)
			return false;
		if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
			return false;
		return true;
	}
	
	
}

class Test02 extends Test1004 {
	
}





