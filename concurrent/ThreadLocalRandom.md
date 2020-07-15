# ThreadLocalRandom

避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed 导致的性能下降。

说明：Random实例包括java.util.Random 的实例或者Math.random()实例。

正例：在JDK7之后，可以直接使用API ThreadLocalRandom，在JDK7之前，可以做到每个线程一个实例。

<br>

在多线程下，使用 `java.util.Random` 产生的实例来产生随机数是线程安全的，但深挖 `Random` 的实现过程，会发现多个线程会竞争同一 `seed` 而造成性能降低。

其原因在于：

`Random` 生成新的随机数需要两步：

- 根据老的 `seed` 生成新的 `seed`
- 由新的 `seed` 计算出新的随机数

其中，第二步的算法是固定的，如果每个线程并发地获取同样的 `seed`，那么得到的随机数也是一样的。为了避免这种情况，`Random` 使用 CAS 操作保证每次只有一个线程可以获取并更新 seed，失败的线程则需要自旋重试。

因此，在多线程下用 `Random` 不太合适，为了解决这个问题，出现了 `ThreadLocalRandom`。











---

参考资料

1. [多线程下ThreadLocalRandom用法 - 简书](https://www.jianshu.com/p/89dfe990295c)
2. 