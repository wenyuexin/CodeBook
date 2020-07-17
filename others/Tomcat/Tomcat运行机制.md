# Tomcat运行机制

1. Tomcat是运行在JVM中的一个进程。它定义为中间件，是一个在Java项目与JVM之间的中间容器。

2. Web项目的本质，是一大堆的资源文件和方法。Web项目没有入口方法(main方法)，，意味着Web项目中的方法不会自动运行起来。

3. Web项目部署进Tomcat的webapp中的目的是很明确的，那就是希望Tomcat去调用
   写好的方法去为客户端返回需要的资源和数据。

4. Tomcat可以运行起来，并调用写好的方法。那么，Tomcat一定有一个main方法。
5. 对于Tomcat而言，它并不知道我们会有什么样的方法，这些都只是在项目被部署进webapp下后才确定的，由此分析，必然用到了Java的反射来实现类的动态加载、实例化、获取方法、调用方法。但是我们部署到Tomcat的中的Web项目必须是按照规定好的接口来进行编写，以便进行调用

<br>

从`main`方法开始打断点，逐步调试，了解程序运行过程

> 全局唯一的**`public static void main(String[] args)`**

1. **Springboot 内置`tomcat`，开发的时候还是可以看到`main`方法的
2. 对于`tomcat`外置的情况，在我们开发的工程代码里是看不到`main`方法的，使用的是`tomcat`里的`main`方法

- 此时`main`方法位于`org.apache.catalina.startup.Bootstrap`
- 此类项目里引入`tomcat`依赖，方便`tomcat`源码调试

```xml
<dependency>
  <groupId>org.apache.tomcat</groupId>
  <artifactId>tomcat-catalina</artifactId>
  <version>9.0.27</version>
</dependency>
```





---

参考资料

1. [Tomcat工作原理之运行机制_- CSDN_tomcat运行原理](https://blog.csdn.net/qq_32951553/article/details/79686367)
2. [Tomcat启动原理/使用tomcat的应用是如何从tomcat的main函数开始运行的 - 博客园](https://www.cnblogs.com/shengulong/p/11846619.html)

