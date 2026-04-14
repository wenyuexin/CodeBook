# Solon

### 简介

Solon 是一个轻量级的 Java 应用开发框架，对标 Spring Boot 和 Spring Cloud，由杭州无耳科技有限公司开源，采用 Apache 2.0 协议。其设计理念强调 **克制、简洁、高效、开放和生态友好**。

主要特性包括：内核仅有 0.1MB，完整的 Web 开发项目可低至 2MB；启动速度可达 0.1 秒级别，比 Spring Boot 快 5\~10 倍；支持 HTTP、WebSocket、Socket 三种通信信号的统一接入（俗称“三源合一”），并非基于 Servlet 构建，而是采用 Context + Handler 架构。

### 与 Spring Boot 的对比

|对比维度|Solon|Spring Boot|
|-|-|-|
|**设计理念**|轻量、极简、克制，追求更小的体积和更快的速度|全面、生态完善，强调开箱即用的生产级体验|
|**内核与大小**|内核仅 0.1MB，最小 Web 开发单位约 2MB|普通 MVC 项目约 20MB 起步|
|**底层架构**|非 Servlet 框架，采用 Context + Handler 架构，不依赖 Java-EE 规范|基于 Servlet 技术构建，依赖 Servlet 容器|
|**启动速度**|约 0.1 秒完成启动，比 Spring Boot 快 5\~10 倍|通常 3 秒以上启动|
|**QPS 并发性能**|本机 HTTP 测试 QPS 可达 12\~17 万，约 Spring 的 2\~3 倍|基准对比约 2\~2.6 万 QPS|
|**内存占用**|运行时内存节省 1/3 \~ 1/2|相对较高|
|**打包体积**|可缩小到原来的 1/2 \~ 1/10|相对较大|
|**注解注入**|`@Inject` 注解集合了 `@Value`、`@Autowired`、`@Qualifier` 三者功能|分拆为 `@Autowired`、`@Value`、`@Qualifier` 等多个注解|
|**生态成熟度**|新兴框架，生态和文档相对较少，适合对性能敏感的轻量场景|生态极为成熟，社区庞大，第三方适配广泛|

### 问答

#### 问题一：什么是 Solon？它与 Spring Boot 的核心定位有何不同？

**参考答案**：

Solon 是一个轻量级的 Java 企业级应用开发框架，从零开始构建（No Java-EE），有灵活的接口规范与开放生态。Solon 的核心定位是提供 **更小、更快、更简单** 的开发体验，内核仅 0.1MB，启动速度约 0.1 秒，旨在替代 Spring Boot/Cloud 生态以满足性能敏感型场景。

Spring Boot 的核心定位则完全不同：它是基于 Spring 框架的快速开发脚手架，强调 **开箱即用、自动配置、生产就绪**（Actuator 监控、健康检查等），生态极为丰富，适用于构建传统的 Web 应用和微服务架构。概括来说，Spring Boot 追求 **全面和稳定**，而 Solon 追求 **轻量和性能**。

#### 问题二：Solon 在架构上与 Spring Boot 有何本质区别？具体体现在哪些方面？

**参考答案**：

最本质的区别是：**Solon 不是基于 Servlet 的框架，而 Spring Boot 高度依赖 Servlet 技术**。

具体体现在：

1. **底层架构不同**：Spring Boot 基于 Servlet 容器（如 Tomcat、Jetty），请求处理遵循 Servlet 规范。Solon 采用 `Context + Handler` 架构包装请求上下文，不依赖 Servlet 规范，可以适配任何基础通讯框架，实现真正的轻量化。

2. **通信协议统一**：Solon 实现了 HTTP、WebSocket、Socket 三种信号接入的“三源合一”体验，开发者使用同一套 Handler 模式处理不同类型的请求。

3. **运行环境兼容性**：Solon 同时支持 Java 8 \~ Java 24 以及 GraalVM Native Image，且不基于 Java-EE 构建，兼容范围更广。

#### 问题三：Solon 在性能方面相比 Spring Boot 有哪些优势？这些优势是如何实现的？

**参考答案**：

根据官方基准测试，Solon 相比 Spring Boot 的优势非常显著：

- **并发高 2\~3 倍**：本机 HTTP 测试中 Solon 的 QPS 约 17 万，而 Spring Boot 约 2\~2.6 万

- **内存省 1/3 \~ 1/2**

- **启动快 5\~10 倍**

- **打包小 90%**

这些优势的实现主要得益于：

1. **极简内核**：不依赖庞大的 Java-EE 规范，避免加载大量无关的类和配置。

2. **非 Servlet 架构**：减少请求处理过程中的层层封装开销，采用更直接的 Context + Handler 模型。

3. **按需插件加载**：Solon 采用插件化架构，开发者只引入需要的功能，避免传递依赖带来的臃肿。

4. **无反射优化**：在多个核心组件中使用 ASM 等技术减少反射调用开销。

#### 问题四：Solon 在使用方式和 API 设计上与 Spring Boot 有何异同？

**参考答案**：

**相同点**：Solon 提供了与 Spring Boot 极为相似的开发体验，支持注解驱动开发，包括 `@Controller`、`@Service`、`@Component`、`@Configuration`、`@Bean` 等常用注解，学习迁移成本较低，因此也被称为 **“Springboot mini”**。

**不同点**：

1. **注入注解差异**：Solon 的 `@Inject` 注解集合了 Spring 的 `@Autowired`、`@Value`、`@Qualifier` 三个注解的功能，一个注解即可完成依赖注入和配置注入。

2. **配置方式**：Spring Boot 强依赖 `application.properties` 或 `application.yml` 配置文件；Solon 允许通过 `@Inject` 直接注入配置，也可手动通过 `Solon.cfg().get()` 获取，提供了更灵活的配置管理方式。

3. **启动方式**：Spring Boot 使用 `SpringApplication.run()` 启动，依赖内嵌 Servlet 容器；Solon 使用 `Solon.start()` 启动，不依赖 Servlet，更加轻量。

#### 问题五：在什么场景下你会选择使用 Solon 而非 Spring Boot？

**参考答案**：

选择 Solon 的场景：

1. **云原生应用**：需要快速启动、低内存占用（如 Serverless、FaaS 环境），Solon 的启动速度和内存优势明显。

2. **资源受限环境**：如边缘计算、IoT 设备、嵌入式系统，Solon 约 0.1MB 的内核和最小 2MB 的 Web 项目体积非常契合。

3. **微服务网关**：需要极高吞吐量和低延迟的边缘服务，Solon 的高 QPS 和低延迟优势显著。

4. **国产化需求**：某些政企项目要求使用国产开源技术栈，Solon 作为国产框架可作为 Spring 生态的替代方案。

仍选择 Spring Boot 的场景：

1. **大型复杂企业应用**：需要成熟的生态支持，如 Spring Security、Spring Data、Spring Batch 等深度集成的组件。

2. **团队熟悉度**：团队已有深厚的 Spring 技术积累，迁移成本较高。

3. **对社区和文档依赖度高**：需要丰富的第三方适配、案例和问题解决方案。