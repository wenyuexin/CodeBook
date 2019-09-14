package proxy;

import java.lang.reflect.Proxy;

/**
 * @author Apollo4634
 * @create 2019/09/13
 */

public class DynamicProxyDemo {

    public interface Adder {
        int add(int x, int y);
    }

    public static class AdderImpl implements Adder {
        @Override
        public int add(int x, int y) { return x+y; }
    }


    static class MyProxy {

        private Adder adder;

        MyProxy(Adder adder) { this.adder = adder; }

        Object getProxyInstance() {
            return Proxy.newProxyInstance(
                    adder.getClass().getClassLoader(),
                    adder.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("Do something before");
                        System.out.println("method: " + method.getName());
                        Object result = method.invoke(adder, args);
                        System.out.println("Do something After");
                        return result;
                    });
        }
    }

    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy(new AdderImpl());
        Adder adder = (Adder) myProxy.getProxyInstance();
        System.out.println(adder.add(9, 14));
    }
}
