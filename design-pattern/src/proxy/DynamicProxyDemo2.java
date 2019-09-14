package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Apollo4634
 * @create 2019/09/14
 */

public class DynamicProxyDemo2 {

    interface Person {
        void say(String msg);
    }

    static class Student implements Person {
        @Override
        public void say(String msg) {
            System.out.println(msg);
        }
    }

    static class ProxyHandler implements InvocationHandler {
        private Object person;

        ProxyHandler(Object person) { this.person = person; }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("say".equals(method.getName())) {
                return method.invoke(person, args);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Person student = new Student();
        ProxyHandler proxyHandler = new ProxyHandler(student);
        Person person = (Person) Proxy.newProxyInstance(
                student.getClass().getClassLoader(),
                student.getClass().getInterfaces(),
                proxyHandler);
        person.say("study");
    }
}
