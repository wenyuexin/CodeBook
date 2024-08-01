# 命令 image

管理镜像

### Usage

```
docker image COMMAND
```

### Child commands

| Command                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [docker image build](https://docs.docker.com/engine/reference/commandline/image_build/) | Build an image from a Dockerfile                             |
| [docker image history](https://docs.docker.com/engine/reference/commandline/image_history/) | Show the history of an image                                 |
| [docker image import](https://docs.docker.com/engine/reference/commandline/image_import/) | Import the contents from a tarball to create a filesystem image |
| [docker image inspect](https://docs.docker.com/engine/reference/commandline/image_inspect/) | Display detailed information on one or more images           |
| [docker image load](https://docs.docker.com/engine/reference/commandline/image_load/) | Load an image from a tar archive or STDIN                    |
| [docker image ls](https://docs.docker.com/engine/reference/commandline/image_ls/) | List images                                                  |
| [docker image prune](https://docs.docker.com/engine/reference/commandline/image_prune/) | Remove unused images                                         |
| [docker image pull](https://docs.docker.com/engine/reference/commandline/image_pull/) | Pull an image or a repository from a registry                |
| [docker image push](https://docs.docker.com/engine/reference/commandline/image_push/) | Push an image or a repository to a registry                  |
| [docker image rm](https://docs.docker.com/engine/reference/commandline/image_rm/) | Remove one or more images                                    |
| [docker image save](https://docs.docker.com/engine/reference/commandline/image_save/) | Save one or more images to a tar archive (streamed to STDOUT by default) |
| [docker image tag](https://docs.docker.com/engine/reference/commandline/image_tag/) | Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE        |

<br>

**补充：**

查询官网文档可知，上述命令都可以省了`image`，因为去除后的命令本身仍然是合法的docker命令。例如，`docker build`也可以使用，并且作用和`docker image build`相同。

不过存在例外：

`docker image ls`并不能使用`docker ls`替代，而要使用`docker images`。

`docker image rm`的作用是删除镜像，`docker rm`则用于删除容器，若要删除镜像还可以用`docker rmi`。