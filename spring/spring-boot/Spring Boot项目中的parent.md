# Spring Boot项目中的parent

parent并不是字面意义上的某个模块的夫模块，而是spring boot中的一种固定的资源配置方式（个人理解）。

当我们创建一个 Spring Boot 工程时，可以继承自一个 spring-boot-starter-parent ，也可以不继承自它。如果继承了parent，那么就自带一些特性或者说基本功能：

1. 定义了 Java 编译版本为 1.8 。

2. 使用 UTF-8 格式编码。

3. 继承自 spring-boot-dependencies，这个里边定义了依赖的版本，也正是因为继承了这个依赖，所以我们在写依赖时才不需要写版本号。

4. 执行打包操作的配置。

5. 自动化的资源过滤。

6. 自动化的插件配置。

7. 针对 application.properties 和 application.yml 的资源过滤，包括通过 profile 定义的不同环境的配置文件，例如 application-dev.properties 和 application-dev.yml。

注意，由于application.properties和application.yml文件接受Spring样式占位符 $ {...} ，因此 Maven 过滤更改为使用 @ .. @ 占位符，当然开发者可以通过设置名为 resource.delimiter 的Maven 属性来覆盖 @ .. @ 占位符。

<br>

你的项目pom.xml文件中，应该存在如下代码：

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
</parent>
```

你是否知道这是干啥的？
这是Spring Boot的父级依赖，这样当前的项目就是Spring Boot项目了。
spring-boot-starter-parent 是一个特殊的starter，它用来提供相关的Maven默认依赖。使用它之后，常用的包依赖可以省去version标签。
当我们搭建web应用的时候，可以像下面这样添加spring-boot-starter-web依赖：

```
<dependencies>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
</dependencies>
```

<br>
总感觉理解不够深刻，应该系统性的看一下spring boot

---

参考资料

1. [详解Spring Boot 项目中的 parent](https://www.jb51.net/article/159729.htm)
2. [Spring Boot依赖spring-boot-starter-parent是干啥的？](https://blog.csdn.net/William0318/article/details/89854891)
3. 