# Spring 


## 一、Spring 核心概念（IoC、DI、AOP）

### 1. 什么是 IoC？什么是 DI？它们之间的关系？
- **IoC（控制反转）**：将对象的创建和依赖关系的管理交给 Spring 容器，而不是由对象自身控制。  
- **DI（依赖注入）**：IoC 的一种实现方式，容器在创建 Bean 时将依赖动态注入到 Bean 中。  
- 关系：DI 是实现 IoC 的主要手段，IoC 是设计思想，DI 是具体实现。

### 2. 依赖注入和控制反转有什么区别？
两者描述同一件事的角度不同：
- **依赖注入**：从应用程序角度，应用程序依赖容器创建并注入所需的外部资源。
- **控制反转**：从容器的角度，容器控制应用程序，反向注入应用程序所需的外部资源。

### 3. Spring IoC 容器的初始化过程是怎样的？
1. 加载配置（XML、注解或 Java Config），解析 Bean 定义（`BeanDefinition`）。
2. 将 `BeanDefinition` 注册到容器中。
3. 实例化 Bean（通过反射）。
4. 处理依赖注入（填充属性）。
5. 执行 `BeanPostProcessor` 的前置/后置处理，完成 Bean 的完整生命周期。

### 4. Spring 中 Bean 的作用域有哪些？使用场景？
| 作用域 | 说明 | 使用场景 |
|--------|------|----------|
| `singleton`（默认） | 每个 Spring 容器中只有一个实例 | 无状态服务类（Service、Dao） |
| `prototype` | 每次请求都创建新实例 | 有状态的临时对象 |
| `request` | 每次 HTTP 请求创建一个实例 | Web 应用中 Request 作用域 |
| `session` | 每个 HTTP 会话创建一个实例 | 用户会话级别的对象 |
| `application` | 整个 ServletContext 只有一个实例 | 全局共享对象 |

### 5. Spring 中 Bean 的生命周期是怎样的？
1. 实例化（通过构造器或工厂方法）
2. 属性赋值（依赖注入）
3. 若实现了 `BeanNameAware`，调用 `setBeanName()`
4. 若实现了 `BeanFactoryAware`，调用 `setBeanFactory()`
5. 若实现了 `ApplicationContextAware`，调用 `setApplicationContext()`
6. 执行 `BeanPostProcessor` 的 `postProcessBeforeInitialization()`
7. 执行 `@PostConstruct` 或 `InitializingBean.afterPropertiesSet()` 或自定义 `init-method`
8. 执行 `BeanPostProcessor` 的 `postProcessAfterInitialization()`
9. Bean 就绪，供应用使用
10. 容器关闭时，执行 `@PreDestroy` 或 `DisposableBean.destroy()` 或自定义 `destroy-method`

### 6. FactoryBean 和 BeanFactory 有什么区别？
- **BeanFactory**：Spring 容器的顶级接口，负责管理和获取 Bean。
- **FactoryBean**：是一个工厂 Bean，用于创建复杂对象。实现 `FactoryBean` 接口的 Bean，通过 `getObject()` 返回实际对象。例如 `SqlSessionFactoryBean`。

### 7. Spring 中循环依赖如何解决？为什么构造器注入无法解决？
- **解决机制**：Spring 通过**三级缓存**（`singletonObjects`、`earlySingletonObjects`、`singletonFactories`）提前暴露半成品对象（ObjectFactory），从而解决 setter 注入的循环依赖。
- **构造器注入无法解决**：因为构造器注入在实例化阶段就需要依赖对象，此时无法提前暴露半成品，导致循环依赖检测失败。

### 8. Spring 中自动装配的方式有哪些？
| 方式 | 说明 |
|------|------|
| `no`（默认） | 不自动装配，需手动配置 |
| `byName` | 根据属性名匹配容器中同名的 Bean |
| `byType` | 根据属性类型匹配，若找到多个同类型 Bean 则抛异常 |
| `constructor` | 通过构造器参数类型自动装配 |
| `autodetect`（已废弃） | 先尝试 constructor，再尝试 byType |

**优缺点**：简化配置，但可能使依赖关系不明确，调试困难。


## 二、AOP 面向切面编程

### 1. 什么是 AOP？Spring AOP 的原理是什么？
- **AOP（Aspect Oriented Programming）**：将横切关注点（如日志、事务、权限）与业务逻辑分离，通过动态代理在运行时织入增强代码。
- **原理**：Spring AOP 基于**动态代理**（JDK 动态代理或 CGLIB）生成目标类的代理对象，在代理中调用切面逻辑。

### 2. Spring AOP 中 JDK 动态代理和 CGLIB 的区别？
| 对比项 | JDK 动态代理 | CGLIB |
|--------|--------------|-------|
| 目标类要求 | 必须实现至少一个接口 | 无接口要求，可代理普通类 |
| 实现机制 | 反射 + 接口 | 字节码增强（继承目标类） |
| 性能 | 较慢（反射调用） | 较快（直接调用） |
| 是否代理 final 方法 | 不涉及 | 无法代理 final 方法 |

### 3. AOP 中的术语有哪些？
- **Aspect（切面）**：横切关注点的模块化（如日志切面）。
- **Join Point（连接点）**：程序执行过程中可以插入切面的点（如方法调用、异常抛出）。
- **Advice（通知）**：切面在特定连接点执行的动作（前置、后置、环绕、异常、最终）。
- **Pointcut（切入点）**：匹配连接点的表达式（如 `execution(* com..*Service.*(..))`）。
- **Target（目标对象）**：被代理的对象。
- **Weaving（织入）**：将切面应用到目标对象的过程。

### 4. Spring AOP 支持哪些通知类型？
- `@Before`：目标方法执行前执行。
- `@AfterReturning`：目标方法正常返回后执行。
- `@AfterThrowing`：目标方法抛出异常后执行。
- `@After`（finally）：无论是否异常都执行。
- `@Around`：环绕通知，可控制目标方法是否执行及参数返回值。


## 三、事务管理

### 1. 事务的四个特性（ACID）是什么？
- **原子性**：事务中的所有操作要么全部成功，要么全部失败。
- **一致性**：事务前后数据完整性约束不被破坏。
- **隔离性**：多个事务并发执行时，相互隔离。
- **持久性**：事务提交后，数据永久保存。

### 2. Spring 事务的传播行为有哪些？`REQUIRED` 和 `REQUIRES_NEW` 的区别？

| 传播行为 | 说明 |
|----------|------|
| `REQUIRED`（默认） | 当前有事务则加入，否则新建事务 |
| `SUPPORTS` | 当前有事务则加入，否则非事务执行 |
| `MANDATORY` | 必须已有事务，否则抛异常 |
| `REQUIRES_NEW` | 挂起当前事务，新建一个独立事务 |
| `NOT_SUPPORTED` | 挂起当前事务，以非事务方式执行 |
| `NEVER` | 必须非事务执行，否则抛异常 |
| `NESTED` | 在当前事务中嵌套一个子事务（JDBC Savepoint 实现） |

**区别**：
- `REQUIRED`：内外事务共用一个事务，内事务回滚导致整体回滚。
- `REQUIRES_NEW`：内外事务独立，内事务回滚不影响外事务。

### 3. Spring 事务的隔离级别有哪些？与数据库隔离级别的关系？

| Spring 隔离级别 | 对应数据库隔离级别 | 脏读 | 不可重复读 | 幻读 |
|----------------|-------------------|------|------------|------|
| `DEFAULT` | 数据库默认（MySQL = REPEATABLE_READ） | 取决于数据库 | 取决于数据库 | 取决于数据库 |
| `READ_UNCOMMITTED` | 读未提交 | 可能 | 可能 | 可能 |
| `READ_COMMITTED` | 读已提交 | 不可能 | 可能 | 可能 |
| `REPEATABLE_READ` | 可重复读 | 不可能 | 不可能 | 可能（InnoDB 可避免） |
| `SERIALIZABLE` | 串行化 | 不可能 | 不可能 | 不可能 |

Spring 的隔离级别本质是设置 JDBC 连接的隔离级别，最终由数据库实现。

### 4. `@Transactional` 注解在哪些情况下会失效？
- 方法不是 `public` 的。
- 同一类中非事务方法调用事务方法（自调用，没有经过代理）。
- 异常被捕获且没有重新抛出（默认只对 `RuntimeException` 和 `Error` 回滚）。
- 底层数据库引擎不支持事务（如 MyISAM）。
- 传播行为配置错误（如 `NOT_SUPPORTED`）。

### 5. 同一类中，方法 A（无事务）调用方法 B（有 `@Transactional`），事务会生效吗？如何解决？
**不会生效**，因为调用是通过 `this` 直接调用，没有经过代理对象。**解决方案**：
- 将方法 B 拆分到另一个 Service 中，通过注入调用。
- 在方法 A 中通过 `AopContext.currentProxy()` 获取代理对象调用（需配置 `@EnableAspectJAutoProxy(exposeProxy = true)`）。
- 使用 `@Autowired` 注入自身，然后调用。

### 6. Spring 声明式事务的实现原理是什么？
基于 **AOP**：Spring 在启动时为目标 Bean 创建代理对象，代理根据 `@Transactional` 配置在方法执行前后加入事务管理逻辑（开启、提交、回滚）。开发者只写业务代码，事务控制由代理完成。


## 四、SpringMVC

### 1. SpringMVC 的工作流程是怎样的？
1. 用户发送请求 → `DispatcherServlet`。
2. `DispatcherServlet` 调用 `HandlerMapping` 找到对应的 `Controller` 方法。
3. `HandlerAdapter` 调用 `Controller` 执行业务逻辑，返回 `ModelAndView`。
4. `ViewResolver` 解析视图，渲染页面。
5. 返回响应。

### 2. `@Controller` 和 `@RestController` 的区别？
- `@Controller`：通常与视图模板（JSP/Thymeleaf）配合使用，方法返回字符串表示视图名。
- `@RestController` = `@Controller` + `@ResponseBody`，所有方法返回值直接写入 HTTP 响应体，常用于 RESTful API。


## 五、SpringBoot

### 1. SpringBoot 的自动配置原理是什么？
- `@SpringBootApplication` 包含 `@EnableAutoConfiguration`。
- `@EnableAutoConfiguration` 通过 `AutoConfigurationImportSelector` 读取 `META-INF/spring.factories`（Spring Boot 2.7+ 改为 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`）中配置的自动配置类。
- 自动配置类使用 `@Conditional` 系列注解（如 `@ConditionalOnClass`、`@ConditionalOnMissingBean`）按需加载 Bean。

### 2. 如何自定义一个 SpringBoot Starter？
1. 创建一个 Maven 项目，引入 `spring-boot-autoconfigure`。
2. 编写配置属性类（`@ConfigurationProperties`）和自动配置类（`@Configuration` + 条件注解）。
3. 在 `resources/META-INF/spring.factories` 中指定 `org.springframework.boot.autoconfigure.EnableAutoConfiguration` 为自动配置类的全限定名（新版使用 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`）。
4. 打包，供其他项目引用。

### 3. SpringBoot 中如何实现异步调用？
- 启动类添加 `@EnableAsync`。
- 在需要异步执行的方法上添加 `@Async` 注解。
- 建议配置自定义线程池（`TaskExecutor`），避免默认线程池耗尽。

### 4. 什么是 `@Conditional` 注解？常用的有哪些？
`@Conditional` 根据条件决定是否注册 Bean。SpringBoot 扩展了多种条件注解：
- `@ConditionalOnClass`：类路径存在指定类时生效。
- `@ConditionalOnMissingBean`：容器中没有指定 Bean 时生效。
- `@ConditionalOnProperty`：配置文件存在指定属性值时生效。
- `@ConditionalOnWebApplication`：当应用是 Web 应用时生效。


## 六、SpringSecurity

### 1. SpringSecurity 的核心过滤器链有哪些？
- `UsernamePasswordAuthenticationFilter`：处理表单登录。
- `BasicAuthenticationFilter`：处理 HTTP Basic 认证。
- `ExceptionTranslationFilter`：处理安全异常（如认证失败跳转）。
- `FilterSecurityInterceptor`：权限校验，最终决定是否放行。

### 2. 如何实现动态权限控制（如 RBAC）？
- 自定义 `FilterInvocationSecurityMetadataSource`：从数据库加载 URL → 所需角色的映射。
- 自定义 `AccessDecisionManager`：根据当前用户角色和资源所需角色进行投票决策。
- 在配置中启用自定义元数据源和决策管理器。

### 3. SpringSecurity 中如何实现 JWT 认证？
- 自定义一个过滤器继承 `OncePerRequestFilter`。
- 从请求头（`Authorization: Bearer <token>`）获取 token。
- 解析 token 获取用户信息，封装成 `Authentication` 对象。
- 调用 `SecurityContextHolder.getContext().setAuthentication(authentication)`。


## 七、Spring 事件机制

### 1. Spring 事件机制（ApplicationEvent）如何使用？
1. 定义事件类：继承 `ApplicationEvent`。
2. 发布事件：使用 `ApplicationEventPublisher.publishEvent()`。
3. 定义监听器：
   - 实现 `ApplicationListener` 接口。
   - 或使用 `@EventListener` 注解。

### 2. 事件机制是同步还是异步？如何改为异步？
- 默认是**同步**的（发布者线程执行所有监听器）。
- 改为异步：
  - 在监听器方法上添加 `@Async`（需启用 `@EnableAsync`）。
  - 或配置 `ApplicationEventMulticaster` 使用 `SimpleApplicationEventMulticaster` 并设置线程池。


## 八、设计模式在 Spring 中的应用

| 设计模式 | Spring 中的体现 |
|----------|----------------|
| **代理模式** | AOP（`@Transactional`、`@Cacheable` 等） |
| **单例模式** | Bean 默认作用域 `singleton`，`@Component` 及其派生注解 |
| **工厂模式** | `BeanFactory`、`ApplicationContext` |
| **模板模式** | `JdbcTemplate`、`RedisTemplate`、`RestTemplate`，定义骨架，具体步骤由回调实现 |
| **装饰器模式** | `HandlerInterceptor` 链式处理请求 |
| **观察者模式** | `ApplicationEvent` 事件机制 |
| **命令模式** | `JdbcTemplate` 将 SQL 操作封装为 `StatementCallback` 命令对象 |
| **责任链模式** | `Filter` 链、`HandlerExecutionChain` |
| **享元模式** | 连接池（如 `ThreadPoolTaskExecutor`）复用对象 |
| **解释器模式** | `@Value` 表达式解析（SpEL） |


## 九、补充拓展（原文档未包含，新增）

### 1. `@Import` 注解的作用是什么？
`@Import` 用于快速导入一个或多个配置类或普通 Bean，类似于 XML 中的 `<import>`。常见用法：
- 导入 `@Configuration` 类。
- 导入 `ImportSelector` 实现类，动态返回配置类名数组。
- 导入 `ImportBeanDefinitionRegistrar` 实现类，手动注册 BeanDefinition。

### 2. `@Component`, `@Service`, `@Repository`, `@Controller` 有什么区别？
- `@Component`：通用注解，标识为 Spring 管理的 Bean。
- `@Service`：标注在 Service 层，语义更明确。
- `@Repository`：标注在 DAO 层，额外提供持久层异常转换。
- `@Controller`：标注在控制器层，常与 `@RequestMapping` 配合。

本质上都是 `@Component` 的派生注解，功能相同，仅用于分层标识。

### 3. Spring 中如何配置线程池？
```java
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("my-");
        executor.initialize();
        return executor;
    }
}
```
