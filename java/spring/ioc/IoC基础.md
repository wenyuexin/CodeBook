# IoC基础

IoC，Inverse of Control，即控制反转，是Spring容器的内核。具体说，是某一接口具体实现类的选择控制权从调用类中移除，转交给第三方决定，即由Spring容器借由Bean配置来控制。

DI，Dependency Injection，即依赖注入，是让调用类对某一接口实现类的依赖关系由第三方（容器或者协作类）注入，以移除调用类对某一接口实现类的依赖。

**依赖注入和控制反转是同一概念吗？**

依赖注入和控制反转是对同一件事情的不同描述，从某个方面讲，就是它们描述的角度不同。依赖注入是从应用程序的角度在描述，可以把依赖注入描述完整点：应用程序依赖容器创建并注入它所需要的外部资源；而控制反转是从容器的角度在描述，描述完整点：容器控制应用程序，由容器反向的向应用程序注入应用程序所需要的外部资源。

<br>

### IoC类型

从注入方法上，主要分为3种：构造函数注入、属性注入、接口注入。Spring支持构造函数注入和属性注入。

例如，在使用支付软件支付账单的例子中，支付操作需要依赖支付软件：

**构造函数注入**

```
public class BillPayment {
	private PaymentApp app;
	
	public BillPayment(PaymentApp app) { //借助构造函数实现
		this.app = app;
	}
	
	public void pay(int num) { ... }
}

public class Consumer {
	public void payBill() {
		PaymentApp app = new Alipay();
		BillPayment billPayment = new BillPayment(app); //将app注入到账单支付中
		BillPayment.pay(100);
	}
}
```

**属性注入（setter注入）**

```
public class BillPayment {
	private PaymentApp app;
	
	public BillPayment() { 
	}
	
	public void setPaymentApp(PaymentApp app) { //通过setter方法实现
		this.app = app;
	}
	
	public void pay() { ... }
}

public class Consumer {
	public void payBill() {
		PaymentApp app = new Alipay();
		BillPayment billPayment = new BillPayment();
		billPayment.setPaymentApp(app); //将app注入到账单支付中
		BillPayment.pay(100);
	}
}
```

通过接口注入的方法和属性注入类似，但是更繁琐，不推荐使用。

### 通过容器完成依赖关系的注入

上面虽然实现了两个类之间的解耦，但其实只是将相关代码转移到Consumer类中。在有些情况下，希望将支付操作、消费者、支付软件都实现解耦，将支付软件的选择交由第三方负责。在程序上，这个第三方就是某种容器。通过容器，开发者可以从底层实现类的实例化、依赖装配等工作中解放出来，进而专注于业务逻辑的实现。Spring就是这样一个容器。

Spring可以通过配置文件或者注解描述类和类之间的依赖关系，自动完成类的初始化和依赖注入工作。具体内容涉及Java中的类加载和反射相关知识。



<br>

---

**参考资料**

1. 《精通Spring 4.x 企业应用开发实战》陈雄化，电子工业出版社，第4章
2.  [控制反转（IOC）和依赖注入（DI）的区别 - yayawcx - CSDN博客](https://blog.csdn.net/doris_crazy/article/details/18353197)