# 并发 - Concurrent

**Volatile**

​    在IntelliJ IEDA中执行Thread.activeCount()会发现运行的线程数比预期的多。这是因为IDEA在执行用户代码的时候，实际是通过反射方式去调用，
而与此同时会创建一个Monitor Ctrl-Break线程用于监控目的。
可以再代码中通过Thread.currentThread().getThreadGroup().list()打印出正在运行的线程。



**同步容器（例如java.util.Vector）是否一定是线程安全的？**  
    同步容器中的所有自带方法都是线程安全的，因为方法都使用synchronized关键字标注。但是，对这些集合类的复合操作无法保证其线程安全性。需要主动加锁来保证安全性。
    例如，

```
for (int i = 0; i < vector.size(); i++) {
    vector.remove(i);
}
```

​    这里复合使用了Vector的size和remove方法。虽然两个方法本身都通过synchronized修饰，从而保证了方法本身是安全的。但是复合使用两者时不能保证线程安全。假设线程1通过size方法得到了容器的大小，并准备删除索引为i的元素，而线程2在线程1删除之前先把索引为i的元素删除了，那么此时会抛出ArrayIndexOutOfBoundsException的异常。如果需要保证复合使用方法时的线程安全性，应该对容器加锁：

```
synchronized (vector) {
    for (int i = 0; i < vector.size(); i++) {
        vector.remove(i);
    }
}
```

