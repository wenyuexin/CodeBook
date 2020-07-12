# 命令 pause unpause stop

可以使用 docker [container] pause CONTAINER [CONTAINER ... ］命令来暂停一个运行中的容器。

处于paused 状态的容器，可以使用 docker [container ] unpause CONTAINER [CONTAINER ... ］ 命令来恢复到运行状态。

可以使用docker [container] stop 来终止一个运行中的容器。

<br>

## 1. docker pause

### Description

Pause all processes within one or more containers

### Usage

```none
docker pause CONTAINER [CONTAINER...]
```

### Extended description

The `docker pause` command suspends all processes in the specified containers. On Linux, this uses the cgroups freezer. Traditionally, when suspending a process the `SIGSTOP` signal is used, which is observable by the process being suspended. With the cgroups freezer the process is unaware, and unable to capture, that it is being suspended, and subsequently resumed. On Windows, only Hyper-V containers can be paused.

See the [cgroups freezer documentation](https://www.kernel.org/doc/Documentation/cgroup-v1/freezer-subsystem.txt) for further details.

**补充**：

CGroup的freezer子系统对于成批作业管理系统很有用，可以成批启动/停止任务，以达到及其资源的调度。

freezer子系统也有助于针对运行一组任务设置检查点。通过强制一组任务进入静默状态（quiescent state），freezer子系统可以获得任务的镜像。如果任务处于静默状态，其他任务就可以查看其proc或者读取内核接口来获取信息。通过收集必要信息到另一个node，然后在新node重启任务，被检查的任务可以在cluster中不同node之间迁移。

freezer是按等级划分的，冻结一个CGroup会冻结旗下的所有任务，并且包括他的所有子CGroup。每个freezer都有自己的状态和从父集成的状态。只有父子状态都为THAWED的时候，当前的CGroup才是THAWED。

**补充2**：

Hyper-V是微软第一个采用Vmware与CitrixXen一样基于hypervisor的虚拟化技术

<br>

## 2. docker unpause

### Description

Unpause all processes within one or more containers

### Usage

```none
docker unpause CONTAINER [CONTAINER...]
```


### Extended description

The `docker unpause` command un-suspends all processes in the specified containers. On Linux, it does this using the cgroups freezer.

See the [cgroups freezer documentation](https://www.kernel.org/doc/Documentation/cgroup-v1/freezer-subsystem.txt) for further details.

<br>

## 3. docker stop

### Description

Stop one or more running containers

### Usage

```none
docker stop [OPTIONS] CONTAINER [CONTAINER...]
```

### Options

| Name, shorthand | Default | Description                                |
| --------------- | ------- | ------------------------------------------ |
| `--time , -t`   | `10`    | Seconds to wait for stop before killing it |

### Extended description

The main process inside the container will receive `SIGTERM`, and after a grace period, `SIGKILL`.

该命令会先向容器发送`SIGTERM`信号，等待一段时间后（默认为10s），再发送`SIGKILL`信号。

**补充：**

`SIGTERM`信号可以被程序捕获，程序收到信号后可以立即停止、释放资源后停止、继续运行。即，收到该信号后是否终止运行还需要看进程是否支持。

`SIGKILL`不能被捕获或者忽略，并且在接收过程中不能执行任何清理在接收到该信号。当发送到程序，SIGKILL使其立即终止。

