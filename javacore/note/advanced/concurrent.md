# 并发 - Concurrent

**Volatile**

在IntelliJ IEDA中执行Thread.activeCount()会发现运行的线程数比预期的多。这是因为IDEA在执行用户代码的时候，实际是通过反射方式去调用，
而与此同时会创建一个Monitor Ctrl-Break线程用于监控目的。
可以再代码中通过Thread.currentThread().getThreadGroup().list()打印出正在运行的线程。
