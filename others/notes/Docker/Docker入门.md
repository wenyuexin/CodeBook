# Docker入门

Docker基于Go语言实现，是一个能把开发的应用程序自动部署到容器的开源引擎。可以将Docker理解为轻量级的沙盒（sandbox），每个容器内运行着一个应用，不同的容器相互隔离，容器之间可以通过网络互相通信。

<br>

Docker核心组件：客户端和服务器、Docker镜像、Register（注册服务器）、Docker容器

Docker三大核心：容器（Container）、镜像（Image）、仓库（Repository）

学习Docker之前需要对Linux常用命令有一定了解，如果了解Git相关知识则更好。

<br>

学习Docker首先需要了解一些概念，例如上面说的容器、镜像、仓库，此外还包括虚拟化、容器化、LXC（Linux容器）、虚拟机、数据卷、dockerfile等。

Docker有许多命令，使用命令行界面和命令就可以完成对容器、镜像、仓库的操作。通俗的讲，Docker的命令是分层的，大部分命令包括最顶层的命令`docker`，然后是决定具体操作的二级命令，例如`docker rmi`。也有三层命令，例如`docker image`包含若干涉用于操作镜像的子命令，如`docker image rm`。由于命令数量较多，熟悉常见命令即可，部分命令可以在 [官方文档](https://docs.docker.com/reference/) 上查找。

在应用上，Docker可以运行一些典型的操作系统环境（如Debian、Ubantu、CentOS、Busybox、Alpaine），然后添加Web服务（如Apache、Tomcat、Junit、Nginx），添加数据库（如MySQL、Oracle XE、MongoDB、Redis、Cassandra）。Docker的一大优势就是构建集群，





<br>

---

**参考资料**

1. [Github - CS-Notes/Docker.md at master · CyC2018/CS-Notes]( https://github.com/CyC2018/CS-Notes/blob/master/notes/Docker.md )
2. [Docker Documentation](https://docs.docker.com/)
3. 《Docker技术入门与实战（第三版）》杨保华，机械工业出版社
4. 《第一本Docker书》 James Turnbull，人民邮电出版社 

