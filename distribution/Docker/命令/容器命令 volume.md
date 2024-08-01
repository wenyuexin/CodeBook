# 容器命令 volume

`docker volume`包含了若干关于数据卷操作的子命令：

|         命令          | 说明                             |
| :-------------------: | -------------------------------- |
| docker volume create  | 创建数据卷                       |
| docker volume inspect | 查看一个或者多个数据卷的具体信息 |
|   docker volume ls    | 列举数据卷                       |
|  docker volume prune  | 移除所有未使用的本地数据卷       |
|   docker volume rm    | 移除一个或者多个数据卷           |

<br>

## 1. docker volume create

### Description

Create a volume

[**API 1.21+**](https://docs.docker.com/engine/api/v1.21/) The client and daemon API must both be at least [1.21](https://docs.docker.com/engine/api/v1.21/) to use this command. Use the `docker version` command on the client to check your client and daemon API versions.

### Usage

```none
docker volume create [OPTIONS] [VOLUME]
```

### Options

| Name, shorthand | Default | Description                 |
| --------------- | ------- | --------------------------- |
| `--driver , -d` | `local` | Specify volume driver name  |
| `--label`       |         | Set metadata for a volume   |
| `--name`        |         | Specify volume name         |
| `--opt , -o`    |         | Set driver specific options |


### Extended description

Creates a new volume that containers can consume and store data in. If a name is not specified, Docker generates a random name.

### Examples

Create a volume and then configure the container to use it:

```
$ docker volume create hello

hello

$ docker run -d -v hello:/world busybox ls /world
```

The mount is created inside the container’s `/world` directory. Docker does not support relative paths for mount points inside the container.

Multiple containers can use the same volume in the same time period. This is useful if two containers need access to shared data. For example, if one container writes and the other reads the data.

Volume names must be unique among drivers. This means you cannot use the same volume name with two different drivers. If you attempt this `docker` returns an error:

```none
A volume named  "hello"  already exists with the "some-other" driver. Choose a different volume name.
```

If you specify a volume name already in use on the current driver, Docker assumes you want to re-use the existing volume and does not return an error.

#### Driver-specific options

Some volume drivers may take options to customize the volume creation. Use the `-o` or `--opt` flags to pass driver options:

```
$ docker volume create --driver fake \
    --opt tardis=blue \
    --opt timey=wimey \
    foo
```

These options are passed directly to the volume driver. Options for different volume drivers may do different things (or nothing at all).

The built-in `local` driver on Windows does not support any options.

The built-in `local` driver on Linux accepts options similar to the linux `mount` command. You can provide multiple options by passing the `--opt` flag multiple times. Some `mount` options (such as the `o` option) can take a comma-separated list of options. Complete list of available mount options can be found [here](http://man7.org/linux/man-pages/man8/mount.8.html).

For example, the following creates a `tmpfs` volume called `foo` with a size of 100 megabyte and `uid` of 1000.

```
$ docker volume create --driver local \
    --opt type=tmpfs \
    --opt device=tmpfs \
    --opt o=size=100m,uid=1000 \
    foo
```

Another example that uses `btrfs`:

```
$ docker volume create --driver local \
    --opt type=btrfs \
    --opt device=/dev/sda2 \
    foo
```

Another example that uses `nfs` to mount the `/path/to/dir` in `rw` mode from `192.168.1.1`:

```
$ docker volume create --driver local \
    --opt type=nfs \
    --opt o=addr=192.168.1.1,rw \
    --opt device=:/path/to/dir \
    foo
```

<br>

## 2. docker volume inspect

### Description

Display detailed information on one or more volumes

[**API 1.21+**](https://docs.docker.com/engine/api/v1.21/) The client and daemon API must both be at least [1.21](https://docs.docker.com/engine/api/v1.21/) to use this command. Use the `docker version` command on the client to check your client and daemon API versions.

### Usage

```none
docker volume inspect [OPTIONS] VOLUME [VOLUME...]
```

### Options

| Name, shorthand | Default | Description                                   |
| --------------- | ------- | --------------------------------------------- |
| `--format , -f` |         | Format the output using the given Go template |


### Examples

```
$ docker volume create
85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d
$ docker volume inspect 85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d
[
  {
      "Name": "85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d",
      "Driver": "local",
      "Mountpoint": "/var/lib/docker/volumes/85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d/_data",
      "Status": null
  }
]

$ docker volume inspect --format '{{ .Mountpoint }}' 85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d
/var/lib/docker/volumes/85bffb0677236974f93955d8ecc4df55ef5070117b0e53333cc1b443777be24d/_data
```

<br>

## 3. docker volume ls

### Description

List volumes

[**API 1.21+**](https://docs.docker.com/engine/api/v1.21/) The client and daemon API must both be at least [1.21](https://docs.docker.com/engine/api/v1.21/) to use this command. Use the `docker version` command on the client to check your client and daemon API versions.

### Usage

```none
docker volume ls [OPTIONS]
```

### Options

| Name, shorthand | Default | Description                                  |
| --------------- | ------- | -------------------------------------------- |
| `--filter , -f` |         | Provide filter values (e.g. ‘dangling=true’) |
| `--format`      |         | Pretty-print volumes using a Go template     |
| `--quiet , -q`  |         | Only display volume names                    |

### Extended description

List all the volumes known to Docker. You can filter using the `-f` or `--filter` flag. Refer to the [filtering](https://docs.docker.com/engine/reference/commandline/volume_ls/#filtering) section for more information about available filter options.

### Examples

#### 1) Create a volume

```
$ docker volume create rosemary

rosemary

$ docker volume create tyler

tyler

$ docker volume ls

DRIVER              VOLUME NAME
local               rosemary
local               tyler
```

#### 2) Filtering

The filtering flag (`-f` or `--filter`) format is of “key=value”. If there is more than one filter, then pass multiple flags (e.g., `--filter "foo=bar" --filter "bif=baz"`)

The currently supported filters are:

- dangling (boolean - true or false, 0 or 1)
- driver (a volume driver’s name)
- label (`label=` or `label==`)
- name (a volume’s name)

##### DANGLING

The `dangling` filter matches on all volumes not referenced by any containers

```
$ docker run -d  -v tyler:/tmpwork  busybox

f86a7dd02898067079c99ceacd810149060a70528eff3754d0b0f1a93bd0af18
$ docker volume ls -f dangling=true
DRIVER              VOLUME NAME
local               rosemary
```

##### DRIVER

The `driver` filter matches volumes based on their driver.

The following example matches volumes that are created with the `local` driver:

```
$ docker volume ls -f driver=local

DRIVER              VOLUME NAME
local               rosemary
local               tyler
```

##### LABEL

The `label` filter matches volumes based on the presence of a `label` alone or a `label` and a value.

First, let’s create some volumes to illustrate this;

```
$ docker volume create the-doctor --label is-timelord=yes

the-doctor
$ docker volume create daleks --label is-timelord=no

daleks
```

The following example filter matches volumes with the `is-timelord` label regardless of its value.

```
$ docker volume ls --filter label=is-timelord

DRIVER              VOLUME NAME
local               daleks
local               the-doctor
```

As the above example demonstrates, both volumes with `is-timelord=yes`, and `is-timelord=no` are returned.

Filtering on both `key` *and* `value` of the label, produces the expected result:

```
$ docker volume ls --filter label=is-timelord=yes

DRIVER              VOLUME NAME
local               the-doctor
```

Specifying multiple label filter produces an “and” search; all conditions should be met;

```
$ docker volume ls --filter label=is-timelord=yes --filter label=is-timelord=no

DRIVER              VOLUME NAME
```

##### NAME

The `name` filter matches on all or part of a volume’s name.

The following filter matches all volumes with a name containing the `rose` string.

```
$ docker volume ls -f name=rose

DRIVER              VOLUME NAME
local               rosemary
```

#### 3) Formatting

The formatting options (`--format`) pretty-prints volumes output using a Go template.

Valid placeholders for the Go template are listed below:

| Placeholder   | Description                                                  |
| :------------ | :----------------------------------------------------------- |
| `.Name`       | Volume name                                                  |
| `.Driver`     | Volume driver                                                |
| `.Scope`      | Volume scope (local, global)                                 |
| `.Mountpoint` | The mount point of the volume on the host                    |
| `.Labels`     | All labels assigned to the volume                            |
| `.Label`      | Value of a specific label for this volume. For example `{{.Label "project.version"}}` |

When using the `--format` option, the `volume ls` command will either output the data exactly as the template declares or, when using the `table` directive, includes column headers as well.

The following example uses a template without headers and outputs the `Name` and `Driver` entries separated by a colon for all volumes:

```
$ docker volume ls --format "{{.Name}}: {{.Driver}}"

vol1: local
vol2: local
vol3: local
```

<br>

## 4. docker volume prune

### Description

Remove all unused local volumes

[**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) The client and daemon API must both be at least [1.25](https://docs.docker.com/engine/api/v1.25/) to use this command. Use the `docker version` command on the client to check your client and daemon API versions.

### Usage

```none
docker volume prune [OPTIONS]
```

### Options

| Name, shorthand | Default | Description                           |
| --------------- | ------- | ------------------------------------- |
| `--filter`      |         | Provide filter values (e.g. ‘label=’) |
| `--force , -f`  |         | Do not prompt for confirmation        |

### Extended description

Remove all unused local volumes. Unused local volumes are those which are not referenced by any containers

### Examples

```
$ docker volume prune

WARNING! This will remove all local volumes not used by at least one container.
Are you sure you want to continue? [y/N] y
Deleted Volumes:
07c7bdf3e34ab76d921894c2b834f073721fccfbbcba792aa7648e3a7a664c2e
my-named-vol

Total reclaimed space: 36 B
```

<br>

## 5. docker volume rm

### Description

Remove one or more volumes

[**API 1.21+**](https://docs.docker.com/engine/api/v1.21/) The client and daemon API must both be at least [1.21](https://docs.docker.com/engine/api/v1.21/) to use this command. Use the `docker version` command on the client to check your client and daemon API versions.

### Usage

```none
docker volume rm [OPTIONS] VOLUME [VOLUME...]
```

### Options

| Name, shorthand | Default | Description                                                  |
| --------------- | ------- | ------------------------------------------------------------ |
| `--force , -f`  |         | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) Force the removal of one or more volumes |

### Extended description

Remove one or more volumes. You cannot remove a volume that is in use by a container.

### Examples

```
  $ docker volume rm hello
  hello
```

