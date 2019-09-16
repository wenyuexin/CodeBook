# POJO与JavaBean

### 1、javaBean

javaBean是一种Java语言写成的可重用组件。为写成JavaBean，类必须是具体和公共的，并且具有无参数的构造器。JavaBean通过提供符合一致性设计模式的公共方法将内部域暴露成员属性。更多的是一种规范，即包含一组set和get方法的java对象。javaBean可以使应用程序更加面向对象，可以把数据封装起来，把应用的业务逻辑和显示逻辑分离开，降低了开发的复杂程度和维护成本。

### 2、ejb
即EnterpriseBean，也就是Enterprise JavaBean（EJB）。
ejb是JavaEE的一部分，定义了一个用于开发基于组件的企业多重应用程序标准。它被称为Java企业Bean，是java的核心代码，分别是回话Bean（Session Bean）、实体Bean（Entity Bean）、和消息驱动Bean（MessageDriven Bean）。

### 3、pojo

（Plain Ordinary Java Object）简单的Java对象，实际就是普通JavaBeans，是为了避免和EJB混淆所创造的简称。
其中有一些属性及其getter、setter方法的类，没有业务逻辑，有时可以作为VO（value-object）或DTO（Data Transfer Object）来使用。不允许有业务方法，也不能携带connection之类的方法。

与javaBean相比，javaBean则复杂很多，JavaBean是可复用的组件，对JavaBean并没有严格的规范，理论上讲，任何一个Java类都可以是一个Bean。但通常情况下，由于JavaBean是被容器创建的，所以JavaBean应具有一个无参的构造器。另外，通常JavaBean还要实现Serializable接口用于实现Bean的持久性。一般在web应用程序中建立一个数据库的映射对象时，我们只能称他为POJO。用来强调它是一个普通的对象，而不是一个特殊的对象，其主要用来指代哪些没有遵从特定的java对象模型、约定或框架（如EJB）的java对象。理想的将，一个POJO是一个不受任何限制的java对象

### 4、entity

实体bean，一般是用于ORM对象关系映射，一个实体映射成一张表，一般无业务逻辑代码。
负责将数据库中的表记录映射为内存中的Entity对象，事实上，创建一个EntityBean对象相当于创建一条记录，删除一个EntityBean对象会同时从数据库中删除对应记录，修改一个Entity Bean时，容器会自动将Entity Bean的状态和数据库同步。

### 5、DTO

数据传输对象（Data Transfer Object）。是一种设计模式之间传输数据的软件应用系统。数据传输目标往往是数据访问对象从数据库中检索数据

<br>

---

**参考资料**

1. [POJO,JAVABEAN,Entity区别](https://www.cnblogs.com/aurum324/p/8167996.html)

