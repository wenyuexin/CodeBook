# 命令 rm

### Description

Remove one or more containers

删除一个或多个容器。

注：如果想删除镜像可以使用`docker rmi`或者`docker image rm`

### Usage

```none
docker rm [OPTIONS] CONTAINER [CONTAINER...]
```

### Options

| Name, shorthand  | Default | Description                                                  |
| ---------------- | ------- | ------------------------------------------------------------ |
| `--force , -f`   |         | Force the removal of a running container (uses `SIGKILL`)<br>强行终止并删除一个运行中的容器 |
| `--link , -l`    |         | Remove the specified link<br>删除容器的连接，但保留容器      |
| `--volumes , -v` |         | Remove the volumes associated with the container<br>删除容器挂载的数据卷 |


### Examples

#### 1) Remove a container

This will remove the container referenced under the link `/redis`.

```
$ docker rm /redis

/redis
```

#### 2) Remove a link specified with `--link` on the default bridge network

This will remove the underlying link between `/webapp` and the `/redis` containers on the default bridge network, removing all network communication between the two containers. This does not apply when `--link` is used with user-specified networks.

```
$ docker rm --link /webapp/redis

/webapp/redis
```

#### 3) Force-remove a running container

This command will force-remove a running container.

```
$ docker rm --force redis

redis
```

The main process inside the container referenced under the link `redis` will receive `SIGKILL`, then the container will be removed.

#### 4) Remove all stopped containers

```
$ docker rm $(docker ps -a -q)
```

This command will delete all stopped containers. The command `docker ps -a -q` will return all existing container IDs and pass them to the `rm` command which will delete them. Any running containers will not be deleted.

#### 5) Remove a container and its volumes

```
$ docker rm -v redis
redis
```

This command will remove the container and any volumes associated with it. Note that if a volume was specified with a name, it will not be removed.

#### 6) Remove a container and selectively remove volumes

```
$ docker create -v awesome:/foo -v /bar --name hello redis
hello
$ docker rm -v hello
```

In this example, the volume for `/foo` will remain intact, but the volume for `/bar` will be removed. The same behavior holds for volumes inherited with `--volumes-from`.