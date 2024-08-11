# java.util.function.Predicate

This is a functional interface and can therefore be used as the assignment target for a lambda expression or method reference.

函数式编程接口，可用于lambda表达式或者方法引用

| Modifier and Type         | Method                             | Description                                                  |
| :------------------------ | :--------------------------------- | :----------------------------------------------------------- |
| `default Predicate<T>`    | `and(Predicate<? super T> other)`  | Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. |
| `static <T> Predicate<T>` | `isEqual(Object targetRef)`        | Returns a predicate that tests if two arguments are equal according to [`Objects.equals(Object, Object)`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Objects.html#equals(java.lang.Object,java.lang.Object)). |
| `default Predicate<T>`    | `negate()`                         | Returns a predicate that represents the logical negation of this predicate. |
| `static <T> Predicate<T>` | `not(Predicate<? super T> target)` | Returns a predicate that is the negation of the supplied predicate. |
| `default Predicate<T>`    | `or(Predicate<? super T> other)`   | Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. |
| `boolean`                 | `test(T t)`                        | Evaluates this predicate on the given argument.              |

