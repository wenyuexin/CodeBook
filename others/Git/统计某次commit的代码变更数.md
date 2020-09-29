# 统计某次commit的代码变更数

使用一下命令：

```
commit diff --numstat <commitId1> <commitId2>
```

把本次的commitId和上一次commit的id带入，将返回以下形式的内容：

```
delNum addNum xxx/xxx/xxx/FileName.java
```

其中，

delNum：表示commit1相对于commit2删除的代码数

addNum：表示commit1相对于commit2新增的代码数

<br>

---

参考资料：

1. https://www.downzz.com/gitjiaocheng/34815.html

