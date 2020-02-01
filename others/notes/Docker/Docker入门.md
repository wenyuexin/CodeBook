# Docker入门

Docker基于Go语言实现，是一个能把开发的应用程序自动部署到容器的开源引擎。可以将Docker理解为轻量级的沙盒（sandbox），每个容器内运行着一个应用，不同的容器相互隔离，容器之间可以通过网络互相通信。

<br>

Docker核心组件：客户端和服务器、Docker镜像、Register（注册服务器）、Docker容器

Docker三大核心：容器（Container）、镜像（Image）、仓库（Repository）

学习Docker之前需要对Linux常用命令有一定了解，如果了解Git相关知识则更好。

<br>

学习Docker首先需要了解一些概念，例如上面说的容器、镜像、仓库，此外还包括虚拟化、容器化、LXC（Linux容器）、虚拟机、数据卷、dockerfile等。

Docker有许多命令，使用命令行界面和命令就可以完成对容器、镜像、仓库的操作。通俗的讲，Docker的命令是分层的，大部分命令包括最顶层的命令`docker`，然后是决定具体操作的二级命令，例如`docker rmi`。也有三层命令，例如`docker image`包含若干涉用于操作镜像的子命令，如`docker image rm`。由于命令数量较多，熟悉常见命令即可，部分命令可以在 [官方文档](https://docs.docker.com/reference/) 上查找。

在应用上，Docker可以运行一些典型的操作系统环境（如Debian、Ubantu、CentOS、Busybox、Alpaine），然后添加Web服务（如Apache、Tomcat、Jetty、Nginx），添加数据库（如MySQL、Oracle XE、MongoDB、Redis、Cassandra）。Docker的一大优势就是构建集群，可以构建大数据分布式处理平台（如Hadoop、Spark、Strom、Elasticsearch）。

在编程开发方面，Docker支持C++、Java、Python、Go、JavaScript等多种语言。以Java为例，在容器中运行 Java 代码最简单的方法就是将 Java 编译指令直接写人 Dockerfile ，然后使用此 Dockerfile 构建并运行此镜像，即可启动程序。

Docker 目前已经得到了众多公有云平台的支持， 并成为除虚拟机之外的核心云业务。可以了解一下，国内外的公共云容器服务以及容器云的现状、 功能与特性。此外，还可以了解IaaS（Infrastructure as a Service）、SaaS（Software ~）、CaaS（Container ~）、MaaS（Machine  ~）等相关概念。

<br>

上述只是基础，那么，Docker 是如何实现的？它目前有何问题？它的技术生态环境是否已经成长起来了？进一步的可以了解以下内容：Docker 的核心实现技术，包括架构、命名空间、控制组联合文件系统、虚拟网络等；Docker 的安全机制；如何使用 Docker Registry 具来创建和管理私有的镜像仓库；插件化网络支持工具libnetwork等等。

扩展性的，还可以了解围绕容器技术衍生出的技术和工具。例如，高可用分布式键值数据库 Etcd ；Docker 公司推出的便于构建集群的工具 Machine Compose Swarm；Apache下的开源分布式资源管理框架Mesos；容器集群管理工具 Kubemetes（K8s）。



顺带一下，有空可以了解一下Podman等更新的容器技术。

<br>

---

**参考资料**

1. [Github - CS-Notes/Docker.md at master · CyC2018/CS-Notes]( https://github.com/CyC2018/CS-Notes/blob/master/notes/Docker.md )
2. [Docker Documentation](https://docs.docker.com/)
3. 《Docker技术入门与实战（第三版）》杨保华，机械工业出版社
4. 《第一本Docker书》 James Turnbull，人民邮电出版社 

