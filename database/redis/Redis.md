# Redis

Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker.

Redis是一个开源的、**基于内存**的**数据结构存储器**，可以用作**数据库**、**缓存**和**消息中间件**。

REmote DIctionary Server(Redis) 是一个由Salvatore Sanfilippo写的**key-value存储系统**。

简单理解，redis背后就是通过hash实现的map。redis是CS架构，map放在服务器端，客户端通过命令行或各种语言的API进行操作。

redis是单线程服务器，基于**Event-Loop模式**来处理Client的请求。这么做不必考虑线程安全问题，也减少了线程切换带来的损耗时间。不过这样不能充分利用多处理器，为此可以使用集群。一种简单的框架是一台客户端、一台服务器。当客户端变多时可以增加服务器，并通过负载均衡算法把客户端的请求分散到各个服务器上。而为了保证数据的可用性，又有主从复制的问题。

此外，还有数据结构（例如String、List、Set、SortedSet、Map）的问题。有剔除策略、负载均衡、在线扩容、数据持久化、数据同步等问题。



---

参考资料

1. Redis 官网：https://redis.io/
2. Redis 在线测试：http://try.redis.io/
3. Redis 命令参考：http://doc.redisfans.com/
4. [Redis简明教程]<https://zhuanlan.zhihu.com/p/37055648>
5. [Redis 教程 | 菜鸟教程]<https://www.runoob.com/redis/redis-tutorial.html>