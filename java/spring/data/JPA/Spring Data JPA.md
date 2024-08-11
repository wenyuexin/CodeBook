# Spring Data JPA

JPA里最基本的概念之一是实体（Entity），把一个类标注为JPA实体需要使用映射注解进行标注：

例如：

```
package demos;
import javax.persistence.Entity;
...
import javax.persistence.Id;
import lombok.Data;

@Data    // lombok注解，
         // 为所有字段生成getters，生成toString, hashCode, equals方法（检查所有非transient字段）
         // 为所有non-final字段生成setters, 以及一个constructor
@Entity  // javax.persistence注解，用于标注某个类是一个Entity
public class Demo {
    @Id  // 标记实体的主键，主键应该是java原始类型或者原始类型的包装类，或者是以下类型之一：
         // String、java.util.Date、java.sql.Date、java.math.BigDecimal、java.math.BigInteger
    @GeneratedValue(strategy=GenerationType.AUTO) // 指定生成类型
    private Long id;
    
    ...
}
```



