package time;

import java.util.Calendar;
import java.util.Date;

/** 
 * @author Apollo4634 
 * @create 2018/12/07 14:12
 */

/**
 * Java中和时间相关的类：
 * - java.util.Date
 * - java.util.Calendar 处理年月日
 * - java.text.DateFormat 抽象类，主要用于实现Date对象和字符串之间相互转换
 * - java.text.SimpleDateFormat 是DateFormat的实现类
 * 
 */

public class TestDate {
	
	public static void main(String[] args) {
		System.out.println("===== 1 =====");
		Date date = new java.util.Date();
		System.out.println(date); //Fri Dec 07 14:33:24 CST 2018
		System.out.println(date.getTime());
		System.out.println(date.toInstant());
		
		System.out.println(date.before(new Date()));
		System.out.println(date.after(new Date()));
		
		System.out.println("===== 2 =====");
		//java.util.Date类中的大多数方法都是Deprecated（不推荐使用）
		//主要被Calendar类和DateFormat类的相关方法替代
		//Deprecated: As of JDK version 1.1, replaced by xxx...
		System.out.print(date.getYear()+" ");
		System.out.print(date.getMonth()+" ");
		System.out.print(date.getDate()+" ");
		System.out.println(date.getDay());
		System.out.print(date.getHours()+" ");
		System.out.print(date.getMinutes()+" ");
		System.out.println(date.getSeconds()+" ");
		System.out.println(date.getTimezoneOffset());
		
		System.out.println("===== 3 =====");
		//Calendar calendar = new Calendar(); //Calendar类由abstract修饰，构造器由protected修饰
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(5));
		System.out.println(Calendar.DAY_OF_MONTH);
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		
		calendar.set(2019, 2, 12, 15, 2, 45);
		System.out.println(calendar.get(Calendar.AM_PM));
		System.out.println(calendar.get(Calendar.YEAR));
		
		
		//LocalDate
		//DateFormat 
		//java.time.LocalTime
	}
}
