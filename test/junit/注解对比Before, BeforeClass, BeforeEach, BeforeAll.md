# 注解对比Before, BeforeClass, BeforeEach, BeforeAll

| **特性**                                                     | **Junit 4**  | **Junit 5** |
| ------------------------------------------------------------ | ------------ | ----------- |
| 在当前类的所有测试方法之前执行。注解在静态方法上。此方法可以包含一些初始化代码。 | @BeforeClass | @BeforeAll  |
| 在当前类中的所有测试方法之后执行。注解在静态方法上。此方法可以包含一些清理代码。 | @AfterClass  | @AfterAll   |
| 在每个测试方法之前执行。注解在非静态方法上。可以重新初始化测试方法所需要使用的类的某些属性。 | @Before      | @BeforeEach |
| 在每个测试方法之后执行。注解在非静态方法上。可以回滚测试方法引起的数据库修改。 | @After       | @AfterEach  |



![](/LearnJava/test/assets/order of exception.png)



---

参考资料

1. https://blog.csdn.net/iexploration/article/details/82023893

