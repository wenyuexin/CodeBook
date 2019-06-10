/**
 * @author Apollo4634
 * @create 2019/06/10
 */

public class ClassLoadingTest {

    private String baseName = "base";
    public ClassLoadingTest()
    {
        callName();
    }

    public void callName()
    {
        System. out. println(baseName);
    }

    static class Sub extends ClassLoadingTest
    {
        private String baseName = "sub";
        public void callName()
        {
            System. out. println (baseName) ;
        }
    }
    public static void main(String[] args)
    {
        ClassLoadingTest b = new Sub();
    }
}
