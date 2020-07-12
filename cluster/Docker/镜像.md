# Docker镜像

Docker 运行容器前需要本地存在对应的镜像， 如果镜像不存在， Docker 会尝试先从默认镜像仓库下载（默认使用 Docker Hub 公共注册服务器中的仓库）。

镜像类似于`GitHub`仓库中的代码。镜像的操作主要有：获取、查看、搜索、删除、创建、存出、导入等，相关操作可以通过`docker image`的子命令完成，当然部分操作也有对应的`docker`子命令，例如`docker image pull`和`docker pull`。

