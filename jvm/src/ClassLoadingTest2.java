/**
 * 这里主要测试类的加载顺序，
 * 具体考察在继承关系下，类加载和实例初始化之间的关系
 *
 * 测试发现将按以下顺序执行：
 * 父类的静态代码块 -> 子类的静态代码块 -> 父类的构造函数 -> 子类的构造函数
 *
 * @author Apollo4634
 * @create 2019/07/29
 */

class B {
    static {
        System.out.println("Load B");
    }

    B() {
        System.out.println("Create B");
    }
}

class A extends B {
    static {
        System.out.println("Load A");
    }

    A() {
        System.out.println("Create A");
    }
}

public class ClassLoadingTest2 {
    public static void main(String[] args) {
        new A();
    }
}
