# BeanFactory和ApplicationContext

BeanFactory是一个工厂类，可以创建并管理各种类的对象（即POJO），Spring称这些对象为Bean。

### BeanFactory的类体系结构

Spring为BeanFactory提供了多种实现，最常用的是XmlBeanFactory，但在Spring 3.2中已被弃用，建议使用XmlBeanDefinitionReader、DefaultListableBeanFactory替代。

**BeanFactory是用于访问Spring Bean容器的根接口，是一个单纯的Bean工厂，也就是常说的IOC容器的顶层定义，各种IOC容器是在其基础上为了满足不同需求而扩展的，包括经常使用的ApplicationContext**。

![img](https://img-blog.csdn.net/20180808185136454?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2phdmFfeWVz/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

BeanFactory接口位于类结构树的顶端，它最主要的方法就是`getBean(String beanName)`，该方法从容器中返回特定名称的Bean，BeanFactory的功能通过其他的接口得到不断扩展。

下面对上图中设计的其他接口分别进行说明。

**ListableBeanFactory**：该接口定义了访问容器中Bean基本信息的若干方法，如查看Bean的个数、获取某一类型Bean的配置名、查看容器中是否包括某一Bean等。

**HireachicalBeanFactory**：父子级联IoC容器的接口，自容器可以通过接口方法访问父容器。

**ConfigurableBeanFactory**：这是一个重要的接口，增强了IoC容器的可定制型。它定义了设置类装载器、属性编辑器、容器初始化后置处理器等方法。

**AutowireCapableBeanFactory**：定义了将容器中的Bean按某种规则（如按名字匹配、按类型匹配等）进行自动装配的方法。

**SingletonBeanFactory**：定义了允许在运行期向容器注册单实例Bean的方法。

**BeanDefinitionRegistry**：Spring配置文件中每一个bean节点元素在Spring容器里都通过一个BeanDefinition对象表示，它描述了Bean的配置信息。而BeanDefinitionRegistry接口提供了向容器手工注册BeanDefinition对象的方法。

<br>

### 初始化BeanFactory

例如：对于下方提供的Car配置文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
            <bean id="car1" class="com.baobaotao.Car"
                p:brand="红旗CA72"
                p:color="黑色"
                p:maxSpeed="200" />
</beans>
```

可以通过XmlBeanDefinitionReader和DefaultListableBeanFactory进行装载：

```
public class BeanFactoryTest {
    public void getBean() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource res = resolver.getResource("classpath:resource/spring-config.xml"); //1
        System.out.println(res.getURL());
        
        // 废弃，不建议使用
        // BeanFactory bf = new XmlBeanFactory(res);
        
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory(); //2
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory); //3
        reader.loadBeanDefinitions(res); //4
        
        System.out.println("init BeanFactory.");
        Car car = factory.getBean("car", Car.class); //5
        System.out.println("Car bean is ready for use!");
        car.introduce();
    }

    public static void main(String[] args) throws IOException {
        BeanFactoryTest beanFactoryTest = new BeanFactoryTest();
        beanFactoryTest.getBean();
    }
}
```

XmlBeanDefinitionReader通过Resource装载Spring配置信息并启动IoC容器，然后就可以通过BeanFactory #getBean(beanName) 方法从IoC容器中获取Bean。

DefaultListableBeanFactory是整个Bean加载的核心部分，是Spring注册及加载Bean的默认实现。

<br>

## ApplicationContext

`ApplicationContext`由`BeanFactory`派生而来，提供了更多面向实际应用的功能，在`BeanFactory`中，很多功能需要以编程的方式实现，而在`ApplicationContext`中则可以通过配置的方式实现。

### ApplicationContext的体系结构
ApplicationContext的主要实现类是**ClassPathXmlApplicationContext**和**FileSystemXmlApplicationContext**，前者默认从类路径加载配置文件，后者默认从文件系统中装载配置文件。下面看一下ApplicationContext的类继承体系，如下图所示：

![img](https://img-blog.csdnimg.cn/20181210182658117.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2phdmFfeWVz,size_16,color_FFFFFF,t_70)

ApplicationContext继承了HierarchiacalBeanFactory和ListableBeanFactory接口，在此基础上，还通过多个其他的接口扩展了BeanFactory的功能。

ApplicationContext继承了HierarchiacalBeanFactory和ListableBeanFactory接口，在此基础上，还通过多个其他的接口扩展了BeanFactory的功能。如：

**ApplicationEventPublicsher**：让容器拥有发布应用上下文事件的功能，包括容器启动事件、关闭事件等。实现了ApplicationListener事件监听接口的Bean可以接收到的容器事件，并对时间进行相应处理。在ApplicationListener事件监听接口的Bean可以接收到容器事件，并对事件进行相应处理。在ApplicationContext抽象实现类AbstractApplicationContext中存在一个ApplicationEventMulticaster，他负责保存所有的监听器，以便在容器产生上下文事件时通过这些事件监听者。

**MessageSource**：为应用提供i18n国际化消息访问的功能。

**ResourcePatternResolver**：所有ApplicationContext实现类都实现了类似于PathMatchingResourcePatternResolver的功能，可以通过带前缀的Ant风格的资源文件路径装载Spring的配置文件。

**LifeCycle**：该接口提供了start()和stop()两个方法，主要用于控制一步处理过程。在具体使用时，该接口同时被ApplicationContext实现及具体Bean实现，ApplicationContext会将start/stop的信息传递给容器中所有实现了该接口的Bean，已达到管理和控制JMX、任务调度等目的。

**ConfigurableApplicationContext**扩展于ApplicationContext，它新增了两个主要的方法：refresh()和close()，让ApplicationContext具有启动、刷新和关闭应用上下问的能力。在因果那个上下文关闭的情况下调用refresh()则可清楚缓存并重新装载配置信息，而调用close()则可关闭应用上下文。这些接口方法为容器的控制管理带来了便利，但作为开发者，我们并不需要过多关系这些方法。

### ApplicationContext初始化

#### 基于XML的配置

如果配置文件放置在类路径下，则可以优先考略使用ClassPathXmlApplicationContext实现类。

```
ApplicationContext ctx = 
	new ClassPathXMLApplicationContext("resource/spring-config.xml"); 
注：此处"resource/spring-config.xml"等同于"classpath:resource/spring-config.xml"
```

如果配置文件放置在文件系统的路径下，则可以优先考虑使用FilySystemXmlApplicationContext实现类。

```
ApplicationContext ctx = 
	new FilySystemXmlApplicationContext("resource/spring-config.xml");
注：此处"resource/spring-config.xml"等同于"file:resource/spring-config.xml"
```

当然，FileSystemXmlApplicationContext和ClassPathXMLApplication都可以显式使用带资源类型前缀的路径，他们的区别在于如果不显式指定资源类型前缀，则分别将路径解析为文件系统路径和类路径。

#### 基于注解的配置方式

Spring支持基于类注解的配置方式，主要功能来自Spring的JavaConfig的子项目。JavaConfig现以升级为Spring核心框架的一部分。一个标注**@Configuration**注解的POJO即可提供Spring所需的Bean配置信息，如下所示：

```
import org.springframework.context.annotation.Bean;
import smart.Car;

@Configuration //表示这是一个配置信息提供类
public class Beans {
    @Bean(name = "car") //定义一个bean
    public Car builder(){
        Car car = new Car();
        car.setBrand("Cadillac CT6");
        car.setMaxSpeed(200);
        return car;
    }
}
```

和基于XML文件的配置相比，类注解的配置方式可以很容易地让开发者控制Bean的初始化过程，比基于XML文件的配置方式更加灵活。

Spring为基于注解类的配置提供了专门的ApplicationContext实现类：**AnnotationConfigApplicationContext**。例如：

```
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;
import smart.Car;
import static org.testng.Assert.*;

public class AnnotationApplicationContextText {
    @Test
    public void getBean(){
        //通过一个带@Configuration的POJO装载Bean配置
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Beans.class);
        Car car = ctx.getBean("car",Car.class);
        assertNotNull(car);
    }
}
```

#### 基于groovy配置文件的配置

基于Groovy的配置方式可以很容易地让开发者配置复杂Bean的初始化过程，比基于XML文件、注解的配置方式更灵活。

Spring 为基于Groovy的配置提供了专门的ApplicationContext实现类：GenericGroovyApplicationContext。

<br>

## WebApplicationContext

WebApplicationContext是专门为Web应用准备的，它**允许从相对于Web根目录的路径中装载配置文件完成初始化工作**。从WebApplicationContext中可以获得ServletContext的引用，整个Web应用上下文对象将作为属性放置到ServletContext中，以便Web应用环境可以访问Spring应用上下文。

Spring专门为此提供了一个工具类ServletContextUtils，通过该类的`getWebApplicationonContext(ServletContext sc)` 方法，可以从ServletContext中获取WebApplicationContext实例。

![img](https://img-blog.csdn.net/20180827164757956?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2phdmFfeWVz/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

非Web应用下，Bean只有singleton和prototype两种作用域，WebApplicationContext还添加了request、session和global session三个作用域。



<br>

---

**参考资料**

1. 《精通Spring 4.x 企业应用开发实战》陈雄化，电子工业出版社，第4章
2. [BeanFactory 简介以及它 和FactoryBean的区别(阿里面试) - aspirant - 博客园](https://www.cnblogs.com/aspirant/p/9082858.html)
3. [深入理解Spring系列之三：BeanFactory解析 - 张建斌 - 博客园](https://www.cnblogs.com/zhangjianbin/p/9095450.html)

