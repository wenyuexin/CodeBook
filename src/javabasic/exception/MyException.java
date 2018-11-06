package javabasic.exception;

/**
 * @author Apollo4634
 * @creation 2018/11/06
 */
public class MyException extends Exception { //自定义异常必须继承Exception

	private static final long serialVersionUID = 2727433763430126470L;

	private String errMsg = "";
	
	MyException() {
	}
	
	MyException(String msg) {
		this.errMsg = msg;
	}
	
	void print() {
		System.out.println(errMsg);
	}
	
}
