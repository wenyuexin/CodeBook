# JDBC基础

JDBC的全称是Java Database Connectivity，即Java数据库连接，它是一种**可以执行SQL语句的Java API**。程序可通过JDBC API连接到关系数据库，并使用结构化查询语言（SQL，数据库标准的查询语言）来完成对数据库的查询、更新。

与其他数据库编程环境相比，JDBC为数据库开发提供了标准的API，使用JDBC开发的数据库应用可以跨平台运行，而且还可以跨数据库（如果全部使用标准的SQL语句）。也就是说如果使用JDBC开发一个数据库应用，则该应用既可以在Windows操作系统上运行，又可以在Unix等其他操作系统上运行，既可以使用MySQL数据库，又可以使用Oracle等其他的数据库，应用程序不需要做任何的修改。

通过JDBC可以实现以下3个功能：

* 链接数据库
* 执行SQL语句
* 获得SQL语句的执行结果

<br>

### JDBC驱动程序

使用JDBC的一大好处就是通过同一套Java API就可以访问不同的数据库。但是数据库多种多样，因此Sun制定了一套标准API，它们是接口，而具体实现类由各数据库厂商实现，这些实现类就是驱动程序。程序员使用数据库时，只要面向JDBC API编程即可。

![img](https://segmentfault.com/img/bVHUkd?w=467&h=287)

数据库驱动程序是JDBC程序和数据库之间的转换层，数据库驱动程序负责将JDBC调用映射成特定的数据库调用。JDBC的访问示意图如下：

![jdbc](https://segmentfault.com/img/bVHUtP?w=638&h=302)

**JDBC与ODBC**  

1. ODBC几乎能在所有平台上连接几乎所有的数据库。为什么 Java 不使用 ODBC？
    答：Java 可以使用 ODBC，但最好是以JDBC-ODBC桥的形式使用（Java连接总体分为Java直连和JDBC-ODBC桥两种形式）。

2. 那为什么还需要 JDBC？  
   答：因为ODBC 不适合直接在 Java 中使用，因为它使用 C 语言接口。从Java 调用本地 C代码在安全性、实现、坚固性和程序的自动移植性方面都有许多缺点。从 ODBC C API 到 Java API 的字面翻译是不可取的。例如，Java 没有指针，而 ODBC 却对指针用得很广泛（包括很容易出错的指针"void *"）。

   另外，ODBC 比较复杂，而JDBC 尽量保证简单功能的简便性，同时在必要时允许使用高级功能。如果使用ODBC，就必须手动地将 ODBC 驱动程序管理器和驱动程序安装在每台客户机上。如果完全用 Java 编写 JDBC 驱动程序则 JDBC代码在所有 Java 平台上（从网络计算机到大型机）都可以自 动安装、移植并保证安全性。
    总之，JDBC 在很大程度上是借鉴了ODBC的，从他的基础上发展而来。JDBC 保留了 ODBC 的基本设计特征，因此，熟悉 ODBC 的程序员将发现 JDBC 很容易使用。它们之间最大的区别在于：JDBC 以 Java 风格与优点为基础并进行优化，因此更加易于使用。

<br>

### SQL

SQL是对所有关系型数据库都通用的命令语句，而JDBC API只是执行SQL语句的工具。JDBC允许不同平台、不同数据库采用相同的编程接口来执行SQL语句。关于SQL请参考database模块下mysql相关内容。

<br>

---

**参考资料**

1. 《疯狂Java讲义（第4版）》李刚，电子工业出版社，第13章
2.  [ODBC与JDBC比较](https://segmentfault.com/img/bVHUkd?w=467&h=287)