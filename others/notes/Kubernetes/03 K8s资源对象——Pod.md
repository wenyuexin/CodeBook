# K8s资源对象——Pod

Pod的基本介绍在文件`基本概念.md`有所描述。

Pod 是最小可部署的 Kubernetes 对象模型，是 Kubernetes 应用程序的基本执行单元；就像一个豌豆荚包含了多个豌豆，Pod是一组容器（例如 Docker 容器），封装了应用程序容器（某些情况下封装多个容器）、存储资源、唯一网络 IP 以及控制容器应该如何运行的选项。

一个Pod定义文件存在许多属性，例如：

|       属性名称        | 取值类型 | 是否必选 |      说明       |
| :-------------------: | :------: | :------: | :-------------: |
|      apiVersion       |  String  |    是    |     版本号      |
|         kind          |  String  |    是    |    类型，Pod    |
|       metadata        |  Object  |    是    |     元数据      |
|     metadata.name     |  String  |    是    |    Pod的名称    |
|  metadata.namespace   |  String  |    是    | Pod所属命名空间 |
|   metadata.labels[]   |   List   |          |    标签列表     |
| metadata.annotation[] |   List   |          |    注解列表     |
|         spec          |  Object  |    是    |  Pod的详细定义  |

spec包含非常多的具体项，这里就不展开了。

## 基本用法

使用Docker时，可以使用docker run命令创建并启动一个容器。而在Kubernetes系统中对长时间运行容器的要求是：其主程序需要一直在前台执行。

假设，容器执行了后台运行的脚本，在kubelet创建包含这个容器的Pod之后运行完该命令，即认为Pod执行结束，将立刻销毁该Pod。如果为该Pod定义了ReplicationController，则系统会监控到该Pod已经终止，之后根据RC定义中Pod的replicas副本数量生成一个新的Pod。一旦创建新的Pod，就在执行完启动命令后陷入无限循环的过程中。因此，Kubernetes需要用户自己创建的Docker镜像，并以一个前台命令作为启动命令。对于无法改造为前台执行的应用，也可以使用开源工具Supervisor辅助进行前台运行的功能。

Pod可以包含一个或者多个容器。对于多个容器的情况，例如：

```
apiVersion: v1 
kind: Pod
metadata :
  name: redis-php
  labels:
    name: redis-php 
spec:
  containers:
  - name: frontend （容器1）
    image: kubeguide/guestbook-php-frontend:localredis
    ports:
    - containerPort: 80
  - name: redis （容器2）
    image: kubeguide/ redis master
    ports :
    - containerPort: 6379
```

属于同一个Pod的多个容器应用之间相互访问时仅需要通过localhost就可以通信，例如`localhost:6379`。

## 静态Pod

