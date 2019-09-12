# Spring对事务的支持

Spring为事务提供了一致的编程模板，在更高层次上建立了统一的事物抽象。像Spring DAO为不同的持久化实现提供了模板类一样，Spring事务管理继承了这一风格，也提供了**事务模板类TransactionTemplate**。通过TransactionTemplate并配合使用事务回调TransactionCallback指定具体的持久化操作，就可以通过编程方式实现事务管理，而无须关注资源获取、复用、释放、事务同步和异常处理的操作。

Spring事务管理的两点在于声明式事务管理。Spring允许通过声明的方式，在IOC配置中指定事务的边界和事务的属性，Spring自动在事务边界上使用声明的属性。

## 事务管理关键抽象

在Spring事务管理SPI（Service Provider Interface）的抽象层主要是`org.springframework.transation`包中的3个接口：PlatformTransactionManager、TransactionDefinition、TransactionStatus。

![img](https://images2015.cnblogs.com/blog/801000/201704/801000-20170404170820753-1576535619.png)

**TransactionDefinition：**定义了Spring兼容的事务属性，包含：事务隔离级别、事务传播、事务超时、只读状态；

**TransactionStatus：**代表一个事务的具体运行状态。事务管理器通过该接口得到事务运行的状态信息，也可以通过它回滚事务等；

**PlatformTransactionManager**：事务只可以提交或者回滚（或回滚到某个保存点后提交），该接口就描述了事务管理的概念。该接口是事务管理器的顶级接口，它的常用实现类如下图所示：

![img](https://images2015.cnblogs.com/blog/801000/201704/801000-20170404170821269-326583856.png)

Spring配置文件中关于事务配置总是由三个组成部分，分别是**DataSource**、**TransactionManager**和**代理机制**这三部分，无论哪种配置方式，一般变化的只是代理机制这部分。

DataSource、TransactionManager这两部分只是会根据数据访问方式有所变化，比如使用Hibernate进行数据访问时，DataSource实际为SessionFactory，TransactionManager的实现为HibernateTransactionManager。

![img](https://images2015.cnblogs.com/blog/801000/201704/801000-20170404170822082-985524799.png)

<br>

## Spring的事务管理器实现

不同的持久层实现框架有不同的实现类，如果使用Spring JDBC或者MyBatis，由于它们都是基于数据源的Connection访问数据库的，所以可以使用DatasourceTransationManager。

在幕后，DatasourceTransationManager使用的是Datasourcede的Connection的commit()、rollback()等方法进行事务管理。

<br>

## 事务同步管理器

Spring将JDBC的Connection、Hibernate的Session等访问数据库的连接或会话对象统称为资源。

这些资源在同一时刻不能多线程共享，为了让DAO、Service类可以可能做到singleton，Spring的事务同步管理器类`org.springframework.transation.support.TransactionSynchronizationManager`使用ThreadLocal为不同事务线程提供独立的资源副本，同时维护事务配置的属性和运行状态信息。

<br>

## 事务传播行为

当我们调用一个基于Spring的Service接口方法(如UserService#addUser() )时，它将运行于Spring管理的事务环境中，Service接口方法可能会在内部调用其它的Service接口方法以共同完成一一个完整的业务操作，因此就会产生服务接口方法嵌套调用的情况，**Spring通过事务传播行为控制当前的事务如何传播到被嵌套调用的目标服务接口方法中**。

| 事务传播行为类型          | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。 |
| PROPAGATION_SUPPORTS      | 支持当前事务，如果当前没有事务，就以非事务方式执行。         |
| PROPAGATION_MANDATORY     | 使用当前的事务，如果当前没有事务，就抛出异常。               |
| PROPAGATION_REQUIRES_NEW  | 新建事务，如果当前存在事务，把当前事务挂起。                 |
| PROPAGATION_NOT_SUPPORTED | 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。   |
| PROPAGATION_NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常。             |
| PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |

<br>

事务传播行为（propagation behavior）指的就是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行。   

例如：**methodA事务方法调用methodB事务方法时，methodB是继续在调用者methodA的事务中运行呢，还是为自己开启一个新事务运行，这就是由methodB的事务传播行为决定的**。

- PROPAGATION_REQUIRED

  如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。

- PROPAGATION_SUPPORTS

  如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行。

- PROPAGATION_MANDATORY

  如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常

- PROPAGATION_REQUIRES_NEW

   需要使用 JtaTransactionManager作为事务管理器。 它会开启一个新的事务。如果一个事务已经存在，则先将这个存在的事务挂起。

- PROPAGATION_NOT_SUPPORTED

   总是非事务地执行，并挂起任何存在的事务。

- PROPAGATION_NEVER

   总是非事务地执行，如果存在一个活动事务，则抛出异常。

- PROPAGATION_NESTED

  如果一个活动的事务存在，则运行在一个嵌套的事务中。 如果没有活动事务, 则按TransactionDefinition.PROPAGATION_REQUIRED 属性执行。 

![img](https://img-blog.csdn.net/20170420212829825?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc29vbmZseQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

**说明**：

1）当业务方法被设置为**PROPAGATION_MANDATORY**时，它就不能被非事务的业务方法调用。
如将ForumService#addTopic ()设置为PROPAGATION_MANDATORY，如果展现层的Action直接调用addTopic()方法，将引发一个异常。
正确的情况是： addTopic()方法必须被另一个带事务的业务方法调用（如ForumService#otherMethod()）。
所以 PROPAGATION_MANDATORY的方法一般都是被其它业务方法**间接调用**的。

2）当业务方法被设置为**PROPAGATION_NEVER**时，它将不能被拥有事务的其它业务方法调用。假设UserService#addCredits ()设置为PROPAGATION_NEVER，当ForumService# addTopic()拥有一个事务时，addCredits()方法将抛出异常。所以PROPAGATION_NEVER方法一般是被**直接调用**的。

3）当方法被设置为PROPAGATION_NOT_SUPPORTED时，外层业务方法的事务**会被挂起**，当内部方法运行完成后，外层方法的事务重新运行。如果外层方法没有事务，直接运行，不需要做任何其它的事。

<br>

---

**术语说明**

JPA，Java Persistence API，Java持久层API

JDO，Java Data Object，Java数据对象

<br>

**参考资料**

1. 《精通Spring 4.x 企业应用开发实战》陈雄化，电子工业出版社，第11章
2.  [Spring事务SPI及配置介绍](http://blog.csdn.net/fouy_yun/article/details/45786689)
3.   [Spring事务传播机制 - 沧海一滴 - 博客园](https://www.cnblogs.com/softidea/p/5962612.html)
4.  [看完就明白_spring事务的7种传播行为 - weixin_39625809的博客 - CSDN博客](https://blog.csdn.net/weixin_39625809/article/details/80707695)