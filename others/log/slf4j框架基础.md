# SLF4J框架基础

The **Simple Logging Facade for Java** (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at *deployment* time.

Note that SLF4J-enabling your library implies the addition of only a single mandatory dependency, namely *slf4j-api.jar*. If no binding is found on the class path, then SLF4J will default to a no-operation implementation.

它提供了Java中所有日志框架的简单抽象。从名称上可知，slf4j是基于外观模式（或称门面模式）实现的。slf4j只是一个日志标准，并不是日志系统的具体实现，实际上slf4j可以使用`java.util.logging`、`logback`、`log4j`等多种具体实现。slf4j本身只做两件事情：

- 提供日志接口
- 提供获取具体日志对象的方法

**Logback** is intended as a successor to the popular log4j project. At present time, logback is divided into three modules, `logback-core`, `logback-classic` and `logback-access`. The logback-core module lays the groundwork for the other two modules. The logback-classic module can be assimilated to a significantly improved version of log4j. Moreover, logback-classic natively implements the SLF4J API so that you can readily switch back and forth between logback and other logging frameworks such as log4j or java.util.logging (JUL). 

目前，**logback是slf4j的默认实现**。SpringBoot默认使用的日志框架就是slf4j+Logback。



<br>

---

**参考资料**

1. [slf4j.org](https://www.slf4j.org/)
2. [SLF4J user manual](https://www.slf4j.org/manual.html)
3. [Java日志框架：slf4j作用及其实现原理](https://www.cnblogs.com/xrq730/p/8619156.html)