package basic.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @author Apollo4634 
 * @create 2018/12/31 16:12
 * 
 * java日志系统
 */

public class TestJavaUtilLogger {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger("basic/logging");
		logger.setLevel(Level.ALL);
		try {
			Handler handler = new FileHandler("logging_TestJavaUtilLogger.log");
			logger.addHandler(handler);
			
			logger.throwing("TestJavaUtilLogger", "main", null);
		} catch (SecurityException e) {
			logger.log(Level.INFO, "error for test", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(Level.INFO, "error2 for test", e);
			e.printStackTrace();
		}
		
	}
}
