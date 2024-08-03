# JSON语法入门

JSON: **J**ava**S**cript **O**bject **N**otation(JavaScript 对象表示法)

JSON 是存储和交换文本信息的语法，类似 XML。

JSON 比 XML 更小、更快，更易解析。

[toc]

## JSON 语法规则

JSON 语法是 JavaScript 对象表示语法的子集。

- 数据在名称/值对中
- 数据由逗号分隔
- 大括号 **{}** 保存对象
- 中括号 **[]** 保存数组，数组可以包含多个对象

数据以分号作为分隔符号，基本的格式是：

```
key: value
```

## JSON 值

JSON 值可以是：

- 数字（整数或浮点数，不需要添加双引号，但加上也可以解析）
- 字符串（在双引号中）
- 逻辑值（true 或 false）
- 数组（在中括号中）
- 对象（在大括号中）
- null



例如：

```json
{
    "students": { 
        "class2": [
            {
                "name": "Andy",
                "age":15,
                "male": false
            },
            {
                "name": "Bob",
                "age": null,
                "male": true
            }
        ]
    },
    "teachers": [
        {
            "name": "Charles",
            "age": 30
        }  
    ]
}
```

注：网上有很多在线校验json的工具，可用于检查语法是否错误，以及整理格式



---

**参考资料**

1. [JSON 教程 | 菜鸟教程](https://www.runoob.com/json/json-tutorial.html)