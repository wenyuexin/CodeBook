package collection;

import java.util.TreeSet;

/**
 * 测试TreeSet
 * @author Apollo4634
 * 说明：TreeSet是基于TreeMap的NavigableSet接口的实现
 *     而TreeMap是基于红黑树的 NavigableMap接口的实现
 */
public class TestTreeSet {
	public static void main(String[] args) {
		MyTreeSet ts = new MyTreeSet();
		ts.test1();
	}
}

class MyTreeSet {
	public void test1() {
		System.out.println("=== test1 ===");
		
		TreeSet<String> treeSet = new TreeSet<>();
		treeSet.add("aaa");
		treeSet.add("bbb");
		System.out.println(treeSet);
		
		
	}
}
