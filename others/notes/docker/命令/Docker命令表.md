# Docker命令

以下列举的是docker部分命令，具体内容可以参考官网上的文档。

总体上，docker相关的命令可以分为两大类：和镜像相关、和容器相关。

docker的命令时分层次的，存在父命令和子命令的概念。最顶层的命令是`docker`，其下包括诸多子命令。例如`docker image`是`docker`下的一个子命令，而`docker image pull`是`docker image`下的一个子命令。

### 容器声明周期管理

|          命令          | 说明                                                         |
| :--------------------: | ------------------------------------------------------------ |
|          run           | 创建一个新的容器并运行一个命令<br>docker run [OPTIONS] IMAGE [COMMAND] [ARG...]<br><br>相当于先create后start |
| start / stop / restart | **start**：启动一个或多个已经被停止的容器<br/>docker start [OPTIONS] CONTAINER [CONTAINER...]<br><br/>**stop**：停止一个运行中的容器<br/>docker stop [OPTIONS] CONTAINER [CONTAINER...]<br><br/>**restart**：重启容器<br>docker restart [OPTIONS] CONTAINER [CONTAINER...] |
|          kill          | 杀掉一个运行中的容器<br>docker kill [OPTIONS] CONTAINER [CONTAINER...] |
|           rm           | 删除一个或多个容器<br>docker rm [OPTIONS] CONTAINER [CONTAINER...] |
|    pause / unpause     | **pause**：暂停容器中所有的进程。<br/>docker pause [OPTIONS] CONTAINER [CONTAINER...]<br/>**unpause**：恢复容器中所有的进程。<br>docker unpause [OPTIONS] CONTAINER [CONTAINER...] |
|         create         | 创建一个新的容器但不启动它<br>docker create [OPTIONS] IMAGE [COMMAND] [ARG...] |
|          exec          | 在运行的容器中执行命令<br>docker exec [OPTIONS] CONTAINER COMMAND [ARG...] |

### 容器操作

|  命令   | 说明                                                         |
| :-----: | ------------------------------------------------------------ |
|   ps    | 列出容器<br>docker ps [OPTIONS]                              |
| inspect | 获取容器/镜像的元数据<br>docker inspect [OPTIONS] NAME\|ID [NAME\|ID ...] |
|   top   | 查看容器中运行的进程信息，支持 ps 命令参数。<br>docker top [OPTIONS] CONTAINER [ps OPTIONS] |
| attach  | 连接到正在运行中的容器。<br>docker attach [OPTIONS] CONTAINER |
| events  | 从服务器获取实时事件<br/>docker events [OPTIONS]             |
|  logs   | 获取容器的日志<br>docker logs [OPTIONS] CONTAINER            |
|  wait   | 阻塞运行直到容器停止，然后打印出它的退出代码<br>docker wait [OPTIONS] CONTAINER [CONTAINER...] |
| export  | 将文件系统作为一个tar归档文件导出到STDOUT<br>docker export [OPTIONS] CONTAINER |
|  port   | 列出指定的容器的端口映射，或者查找将PRIVATE_PORT NAT到面向公众的端口<br>docker port [OPTIONS] CONTAINER [PRIVATE_PORT[/PROTO]] |

### 容器rootfs命令

|  命令  | 说明                                                         |
| :----: | ------------------------------------------------------------ |
| commit | 基于已有容器穿件新的镜像<br>docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]] |
|   cp   | 用于容器与主机之间的数据拷贝。<br>docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH\|-<br>docker cp [OPTIONS] SRC_PATH\|- CONTAINER:DEST_PATH |
|  diff  | 检查容器里文件结构的更改<br>docker diff [OPTIONS] CONTAINER  |

### 镜像仓库

|      命令      | 说明                                                         |
| :------------: | ------------------------------------------------------------ |
| login / logout | **login**：登陆到一个Docker镜像仓库，如果未指定镜像仓库地址，默认为官方仓库 Docker Hub<br/>docker login [OPTIONS] [SERVER]<br><br/>**logout** : 登出一个Docker镜像仓库，如果未指定镜像仓库地址，默认为官方仓库 Docker Hub<br>docker logout [OPTIONS] [SERVER] |
|      pull      | 从镜像仓库中拉取或者更新指定镜像<br>docker pull [OPTIONS] NAME[:TAG] |
|      push      | 将本地的镜像上传到镜像仓库,要先登陆到镜像仓库<br>docker push [OPTIONS] NAME[:TAG] |
|     search     | 从Docker Hub查找镜像<br>docker search [OPTIONS] TERM         |

### 本地镜像管理

|  命令   | 说明                                                         |
| :-----: | ------------------------------------------------------------ |
| images  | 列出本地镜像。<br>docker images [OPTIONS] [REPOSITORY[:TAG]] |
|   rmi   | 删除本地一个或多少镜像。<br/>docker rmi [OPTIONS] IMAGE [IMAGE...] |
|   tag   | 标记本地镜像，将其归入某一仓库。<br/>docker tag [OPTIONS] IMAGE[:TAG] \[REGISTRYHOST/][USERNAME/]NAME[:TAG] |
|  build  | 使用 Dockerfile 创建镜像。<br/>docker build [OPTIONS] PATH \|URL\|- |
| history | 查看指定镜像的创建历史。<br>docker history [OPTIONS] IMAGE   |
|  save   | 将指定镜像保存成 tar 归档文件<br/>docker save [OPTIONS] IMAGE [IMAGE...] |
|  load   | 导入使用 docker save 命令导出的镜像。<br>docker load [OPTIONS] |
| import  | 从归档文件中创建镜像。<br>docker import [OPTIONS] file\|URL\|- [REPOSITORY[:TAG]] |

### info|version

|  命令   | 说明                                                         |
| :-----: | ------------------------------------------------------------ |
|  info   | 显示 Docker 系统信息，包括镜像和容器数<br>docker info [OPTIONS] |
| version | 显示 Docker 版本信息。<br>docker version [OPTIONS]           |



<br>

---

**参考资料**

1. [Docker 命令大全 | 菜鸟教程](https://www.runoob.com/docker/docker-command-manual.html)
2. [Reference documentation | Docker Documentation](https://docs.docker.com/reference/)