# MapReduce

MapReduce是一种编程模型，用于大规模数据集（大于1TB）的并行运算。

概念"Map（映射）"和"Reduce（归约）"，是它们的主要思想，都是从函数式编程语言里借来的。它极大地方便了编程人员在不会分布式并行编程的情况下，将自己的程序运行在分布式系统上。



MR程序实际上就是将输入转为key-value格式的数据流，分别经过map函数和reduce函数处理后，最后输出key-value格式的数据。这点与函数式编程的中的高阶函数map和reduce的概念非常类似，map是将一个数据集合转换为另一个数据集合，reduce是对一个数据集合进行聚合等相应的操作。











---

参考资料

1. [MapReduce_百度百科](https://baike.baidu.com/item/MapReduce/133425?fr=aladdin)
2. [mapreduce学习笔记 - 简书](https://www.jianshu.com/p/2854e7d82c72)
3. 