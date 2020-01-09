# 命令 export import

需要将容器从一个系统迁移到另外一个系统，此时可以使用Docker 的导人和导出功能

## 1. docker export

### Description

Export a container’s filesystem as a tar archive

导出容器是指，导出一个已经创建的容器到一个文件，不管此时这个容器是否处于运行状态。

### Usage

```none
docker export [OPTIONS] CONTAINER
```

### Options

| Name, shorthand | Default | Description                        |
| --------------- | ------- | ---------------------------------- |
| `--output , -o` |         | Write to a file, instead of STDOUT |

### Extended description

The `docker export` command does not export the contents of volumes associated with the container. If a volume is mounted on top of an existing directory in the container, `docker export` will export the contents of the *underlying* directory, not the contents of the volume.

Refer to [Backup, restore, or migrate data volumes](https://docs.docker.com/v17.03/engine/tutorials/dockervolumes/#backup-restore-or-migrate-data-volumes) in the user guide for examples on exporting data in a volume.

<br>

## 2. docker import

### Description

Import the contents from a tarball to create a filesystem image

### Usage

```none
docker import [OPTIONS] file|URL|- [REPOSITORY[:TAG]]
```

### Options

| Name, shorthand  | Default | Description                                                  |
| ---------------- | ------- | ------------------------------------------------------------ |
| `--change , -c`  |         | Apply Dockerfile instruction to the created image            |
| `--message , -m` |         | Set commit message for imported image                        |
| `--platform`     |         | [**experimental (daemon)**](https://docs.docker.com/engine/reference/commandline/dockerd/#daemon-configuration-file)[**API 1.32+**](https://docs.docker.com/engine/api/v1.32/) Set platform if server is multi-platform capable |


### Extended description

You can specify a `URL` or `-` (dash) to take data directly from `STDIN`. The `URL` can point to an archive (.tar, .tar.gz, .tgz, .bzip, .tar.xz, or .txz) containing a filesystem or to an individual file on the Docker host. If you specify an archive, Docker untars it in the container relative to the `/` (root). If you specify an individual file, you must specify the full path within the host. To import from a remote location, specify a `URI` that begins with the `http://` or `https://` protocol.

The `--change` option will apply `Dockerfile` instructions to the image that is created. Supported `Dockerfile` instructions: `CMD`|`ENTRYPOINT`|`ENV`|`EXPOSE`|`ONBUILD`|`USER`|`VOLUME`|`WORKDIR`