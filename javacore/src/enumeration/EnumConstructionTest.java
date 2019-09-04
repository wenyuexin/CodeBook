package enumeration;

/**
 * @author Apollo4634
 * @create 2019/09/04
 */

public class EnumConstructionTest {
    enum MyEnum {
        AA, BB, CC;

        MyEnum() {
            System.out.println("MyEnum");
        }
    }

    public static void main(String[] args) {
        System.out.println(MyEnum.BB);
    }
}
