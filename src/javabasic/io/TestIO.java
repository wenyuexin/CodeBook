package javabasic.io;

import java.io.File;
import java.io.IOException;

/** 
 * @author Apollo4634 
 * @creation 2018/11/18
 */
public class TestIO {
	
	
	public static void main(String[] args) throws IOException {
		File file = new File("d:/HelloWorld.txt");
		System.out.println(file);
		
		System.out.println(file.exists());
		
		if (file.exists()) {
			System.out.println("文件存在");
		} else {
			System.out.println("创建文件");
			file.createNewFile();
		}
			
		 
	}
}
