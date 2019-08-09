package book_9787302444541;

public class Ch12Task03 {
	public static void main(String[] args) {
		MaxFactor mf;
		try {
			mf = new MaxFactor(12,-60);
			int a = mf.getMaxFactor();
			System.out.println(a);
		} catch (MyException2 e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class MaxFactor {
	private int a = 0;
	private int b = 0;
	private int c = -1;
	
	MaxFactor(int a, int b) throws MyException2 {
		if(a>0 && b>0) {
			this.a = a;
			this.b = b;
		} else {
			throw new MyException2();
		}
		
	}
	
	int getMaxFactor() {
		int min = (a<b)?a:b;
		for(int i=min; i>=min/2d; i--) {
			if((a%i==0) && (b%i==0)) {
				this.c = i;
				break;
			}
		}
		
		return this.c;
	}
}

/**
 * 自定义的异常类
 */
class MyException2 extends Exception {
	MyException2() {}
}
