# Bean的生命周期

spring有三种bean生存的容器： BeanFactory 、 ApplicationContext 、webApplicationContext。其中，webApplicationContext接口继承自ApplicationContext，两者的声明周期相同。

理解springBean 的生命周期主要通过两个层面来理解。其一是 Bean 的作用范围，其一是实例化 Bean 时所经历的一系列阶段。

**一、 BeanFactory**

![img](https://images2015.cnblogs.com/blog/937513/201605/937513-20160507202024015-234747937.png)

Bean的完整生命周期从 spring 容器开始实例化 bean 开始，到销毁。可以从三点来理解

1、 **Bean自身的方法**：包括构造方法、 set 方法、 init-method 指定的方法、 destroy-method 指定的方法

2、 **Bean级生命周期接口方法**：如 BeanNameAware 、 BeanFactoryAware 等这些接口方法由 bean类实现。

3、 **容器级生命周期接口方法**：上图中带星的。有InstantiationAwareBeanPostProcessor 、 BeanPostProcessor 等。一般称为后处理器。他们一般不由bean本身实现，独立存在，注册到 spring 容器中。 Spring 通过接口反射预先知道，当 spring 容器创建任何 bean 时，这些后处理器都会发生作用。所以他们是全局的，用户可以通过编码对只感兴趣的 bean 进行处理。

4、**工厂后处理器接口方法**：包括AspectJWeavingEnabler、CustomAutowireConfigurer、ConfigurationClassPostProcessor等方法。工厂后处理器也是容器级的，在应用上下文装配配置文件后立即调用。

**二、 ApplicationContext** 

![img](https://images2015.cnblogs.com/blog/937513/201605/937513-20160507202059046-84853574.png)

和BeanFactory相比只是多了一个接口， 如果Bean 实现了 ApplicationContextAwre接口， setApplicationContext() 方法被调用。



<br>

---

**参考资料**

1. 《精通Spring 4.x 企业应用开发实战》陈雄化，电子工业出版社，第4章
2.  [Spring中Bean的生命周期 - 景大爷 - 博客园](https://www.cnblogs.com/jing-daye/p/5469068.html)