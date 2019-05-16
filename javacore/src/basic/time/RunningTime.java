package basic.time;

/** 
 * @author Apollo4634 
 * @create 2018/12/08 20:12
 */

public class RunningTime {
	public static void main(String[] args) {
		
		System.out.println("===== 1 =====");
		double num = 0;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 256; i++) {
			num += Math.pow(1.02, i);
		}
		System.out.println(num);
		long t2 = System.currentTimeMillis();
		System.out.println("t2-t1: "+(t2-t1)+" ms");
		
		System.out.println("===== 2 =====");
		long t3 = System.nanoTime();
		for (int i = 0; i < 256; i++) {
			num -= Math.pow(1.02, i);
		}
		System.out.println(num);
		long t4 = System.nanoTime();
		System.out.println("t4-t3: "+(t4-t3)+" ns");
		System.out.println("t4-t3: "+(t4-t3)/1.0e3+" us");
		System.out.println("t4-t3: "+(t4-t3)/1.0e6+" ms");
		
		System.out.println("===== 3 =====");
		num = 0;
		long t5 = System.nanoTime();
		for (int i = 0; i < 256; i++) {
			num += Math.pow(1.02, i);
		}
		System.out.println(num);
		long t6 = System.nanoTime();
		System.out.println("t6-t5: "+(t6-t5)+" ns");
		System.out.println("t6-t5: "+(t6-t5)/1.0e3+" us");
		System.out.println("t6-t5: "+(t6-t5)/1.0e6+" ms");
	}
}
