# 命令 save load

## 1 存出镜像

可以使用`docker image save`或者`docker save`实现。

Save one or more images to a tar archive (streamed to STDOUT by default)

### Usage

```
docker save [OPTIONS] IMAGE [IMAGE...]
```

### Extended description

Produces a tarred repository to the standard output stream. Contains all parent layers, and all tags + versions, or specified `repo:tag`, for each argument provided.

For example uses of this command, refer to the [examples section](https://docs.docker.com/engine/reference/commandline/save/#examples) below.

### Options

| Name, shorthand | Default | Description                        |
| --------------- | ------- | ---------------------------------- |
| `--output , -o` |         | Write to a file, instead of STDOUT |

### Examples

#### Create a backup that can then be used with `docker load`.

```
$ docker save busybox > busybox.tar

$ ls -sh busybox.tar （ls -sh命令用于显示Linux下的文件大小）

2.7M busybox.tar

$ docker save --output busybox.tar busybox 

$ ls -sh busybox.tar

2.7M busybox.tar

$ docker save -o fedora-all.tar fedora

$ docker save -o fedora-latest.tar fedora:latest
```

#### Save an image to a tar.gz file using gzip

You can use gzip to save the image file and make the backup smaller.

```
docker save myimage:latest | gzip > myimage_latest.tar.gz
```

注：“|”是管道命令操作符，简称管道符。利用Linux所提供的管道符“|”将两个命令隔开，管道符左边命令的输出就会作为管道符右边命令的输入。连续使用管道意味着第一个命令的输出会作为 第二个命令的输入，第二个命令的输出又会作为第三个命令的输入，依此类推

#### Cherry-pick particular tags

You can even cherry-pick(筛选, 精选) particular tags of an image repository.

```
$ docker save -o ubuntu.tar ubuntu:lucid ubuntu:saucy
```

<br>

## 2 载入镜像

可以使用`docker image load`或者`docker load`实现。

Load an image from a tar archive or STDIN

### Usage

```
docker load [OPTIONS]
```

### Extended description

Load an image or repository from a tar archive (even if compressed with gzip, bzip2, or xz) from a file or STDIN. It restores both images and tags.

For example uses of this command, refer to the [examples section](https://docs.docker.com/engine/reference/commandline/load/#examples) below.

### Options

| Name, shorthand | Default | Description                                  |
| --------------- | ------- | -------------------------------------------- |
| `--input , -i`  |         | Read from tar archive file, instead of STDIN |
| `--quiet , -q`  |         | Suppress the load output                     |

### Examples

```
$ docker image ls （列出本地镜像）

REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE

$ docker load < busybox.tar.gz

Loaded image: busybox:latest
$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
busybox             latest              769b9341d937        7 weeks ago         2.489 MB

$ docker load --input fedora.tar (说明：这个tar包里有两个镜像)

Loaded image: fedora:rawhide

Loaded image: fedora:20

$ docker images （列出本地镜像）

REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
busybox             latest              769b9341d937        7 weeks ago         2.489 MB
fedora              rawhide             0d20aec6529d        7 weeks ago         387 MB
fedora              20                  58394af37342        7 weeks ago         385.5 MB
fedora              heisenbug           58394af37342        7 weeks ago         385.5 MB
fedora              latest              58394af37342        7 weeks ago         385.5 MB
```

