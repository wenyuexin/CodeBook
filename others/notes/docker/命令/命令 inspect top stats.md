# 命令 inspect top stats

inspect用于查看容器的具体信息，top查看容器内进程的信息，stats查看容器的统计信息

<br>

## 1. docker inspect

### Description

Return low-level information on Docker objects

以json 格式返回包括容器Id 、创建时间、路径、状态、镜像、配置等容器信息

### Usage

```none
docker inspect [OPTIONS] NAME|ID [NAME|ID...]
```

### Options

| Name, shorthand | Default | Description                                       |
| --------------- | ------- | ------------------------------------------------- |
| `--format , -f` |         | Format the output using the given Go template     |
| `--size , -s`   |         | Display total file sizes if the type is container |
| `--type`        |         | Return JSON for specified type                    |


### Extended description

Docker inspect provides detailed information on constructs controlled by Docker.

By default, `docker inspect` will render results in a JSON array.

<br>

## 2. docker top

### Description

Display the running processes of a container

该子命令类似于Linux 系统中的 top 命令，会打印出容器内的进程信息，包括PID（进程ID） 、用户、时间、命令等。

### Usage

```none
docker top CONTAINER [ps OPTIONS]
```

<br>

## 3. docker stats

### Description

Display a live stream of container(s) resource usage statistics

显示CPU 、内存、存储、网络等使用情况的统计信息。

### Usage

```none
docker stats [OPTIONS] [CONTAINER...]
```

### Options

| Name, shorthand | Default | Description                                                  |
| --------------- | ------- | ------------------------------------------------------------ |
| `--all , -a`    |         | Show all containers (default shows just running)             |
| `--format`      |         | Pretty-print images using a Go template<br>格式化输出信息    |
| `--no-stream`   |         | Disable streaming stats and only pull the first result<br>不持续输出，默认会自动更新持续实时结果； |
| `--no-trunc`    |         | Do not truncate output                                       |


### Extended description

**1)**

The `docker stats` command returns a live data stream for running containers. To limit data to one or more specific containers, specify a list of container names or ids separated by a space. You can specify a stopped container but stopped containers do not return any data.

If you want more detailed information about a container’s resource usage, use the `/containers/(id)/stats` API endpoint.

> **Note**: On Linux, the Docker CLI reports memory usage by subtracting page cache usage from the total memory usage. The API does not perform such a calculation but rather provides the total memory usage and the amount from the page cache so that clients can use the data as needed.

> **Note**: The `PIDS` column contains the number of processes and kernel threads created by that container. Threads is the term used by Linux kernel. Other equivalent terms are “lightweight process” or “kernel task”, etc. A large number in the `PIDS` column combined with a small number of processes (as reported by `ps` or `top`) may indicate that something in the container is creating many threads.

**2)**

如果不使用`--format`指定输出格式，那么将展示以下内容：

| Column name               | Description                                                  |
| :------------------------ | :----------------------------------------------------------- |
| `CONTAINER ID` and `Name` | the ID and name of the container                             |
| `CPU %` and `MEM %`       | the percentage of the host’s CPU and memory the container is using |
| `MEM USAGE / LIMIT`       | the total memory the container is using, and the total amount of memory it is allowed to use<br>上面显示的是百分比，这是显示的是实际大小 |
| `NET I/O`                 | The amount of data the container has sent and received over its network interface |
| `BLOCK I/O`               | The amount of data the container has read to and written from block devices on the host |
| `PIDs`                    | the number of processes or threads the container has created |

例如

```
$ docker stats awesome_brattain 67b2525d8ad1

CONTAINER ID   NAME    CPU %   MEM USAGE / LIMIT     MEM %   NET I/O     BLOCK I/O   PIDS
b95a83497c91   test    0.28%   5.629MiB / 1.952GiB   0.28%   916B / 0B   147kB / 0B   9
67b2525d8ad1   foobar  0.00%   1.727MiB / 1.952GiB   0.09%   2.48kB / 0B 4.11MB / 0B  2
```

**3)**

**Formatting**

The formatting option (`--format`) pretty prints container output using a Go template.

Valid placeholders for the Go template are listed below:

| Placeholder  | Description                                  |
| :----------- | :------------------------------------------- |
| `.Container` | Container name or ID (user input)            |
| `.Name`      | Container name                               |
| `.ID`        | Container ID                                 |
| `.CPUPerc`   | CPU percentage                               |
| `.MemUsage`  | Memory usage                                 |
| `.NetIO`     | Network IO                                   |
| `.BlockIO`   | Block IO                                     |
| `.MemPerc`   | Memory percentage (Not available on Windows) |
| `.PIDs`      | Number of PIDs (Not available on Windows)    |

When using the `--format` option, the `stats` command either outputs the data exactly as the template declares or, when using the `table` directive, includes column headers as well.

The following example uses a template without headers and outputs the `Container` and `CPUPerc` entries separated by a colon for all images:

例如

```
$ docker stats --format "{{.Container}}: {{.CPUPerc}}"

09d3bb5b1604: 6.61%
9db7aa4d986d: 9.19%
3f214c61ad1d: 0.00%
```

例如

```
$ docker stats --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}"

CONTAINER           CPU %               PRIV WORKING SET
1285939c1fd3        0.07%               796 KiB / 64 MiB
9c76f7834ae2        0.07%               2.746 MiB / 64 MiB
d1ea048f04e4        0.03%               4.583 MiB / 64 MiB
```