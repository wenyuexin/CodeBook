# @Builder

Builder 使用创建者模式又叫建造者模式。简单来说，就是一步步创建一个对象，它对用户屏蔽了里面构建的细节，但却可以精细地控制对象的构造过程。

### @Builder内部帮我们做了什么？

1. 创建一个名为`ThisClassBuilder`的内部静态类，并具有和实体类形同的属性（称为构建器）。
2. 在构建器中：对于目标类中的所有的属性和未初始化的`final`字段，都会在构建器中创建对应属性。
3. 在构建器中：创建一个无参的`default`构造函数。
4. 在构建器中：对于实体类中的每个参数，都会对应创建类似于`setter`的方法，只不过方法名与该参数名相同。 并且返回值是构建器本身（便于链式调用），如上例所示。
5. 在构建器中：一个`build()`方法，调用此方法，就会根据设置的值进行创建实体对象。
6. 在构建器中：同时也会生成一个`toString()`方法。
7. 在实体类中：会创建一个`builder()`方法，它的目的是用来创建构建器。

例如

```java
//源码
@Builder
public class User {
    private final Integer code = 200;
    private String username;
    private String password;
}

//编译后
public class User {
    private String username;
    private String password;
    User(String username, String password) {
        this.username = username; this.password = password;
    }
  
    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public static class UserBuilder {
        private String username;
        private String password;
        UserBuilder() {}

        public User.UserBuilder username(String username) {
            this.username = username;
            return this;
        }
        public User.UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public User build() {
            return new User(this.username, this.password);
        }
        public String toString() {
            return "User.UserBuilder(username=" + this.username + ", password=" + this.password + ")";
        }
    }
}
```

<br>

---

**参考资料**

1. [详解Lombok中的@Builder用法 - 简书](https://www.jianshu.com/p/d08e255312f9)

