# 命令 attach exec

使用-d参数运行docker run命令时，容器启动后会进入后台。此时，可以使用attach或者exec命令进入容器，进而执行后续操作。

<br>

## 1. docker attach

### Description

Attach local standard input, output, and error streams to a running container

### Usage

```none
docker attach [OPTIONS] CONTAINER
```

### Options

| Name, shorthand | Default | Description                                                  |
| --------------- | ------- | ------------------------------------------------------------ |
| `--detach-keys` |         | Override the key sequence for detaching a container<br>指定退出attach模式的快捷键序列 |
| `--no-stdin`    |         | Do not attach STDIN<br>是否关闭标准输入                      |
| `--sig-proxy`   | `true`  | Proxy all received signals to the process                    |


### Extended description

Use `docker attach` to attach your terminal’s standard input, output, and error (or any combination of the three) to a running container using the container’s ID or name. This allows you to view its ongoing output or to control it interactively, as though the commands were running directly in your terminal.

> **Note:** The `attach` command will display the output of the `ENTRYPOINT/CMD` process. This can appear as if the attach command is hung when in fact the process may simply not be interacting with the terminal at that time.

You can attach to the same contained process multiple times simultaneously, from different sessions on the Docker host.

To stop a container, use `CTRL-c`. This key sequence sends `SIGKILL` to the container. If `--sig-proxy` is true (the default),`CTRL-c` sends a `SIGINT` to the container. If the container was run with `-i` and `-t`, you can detach from a container and leave it running using the `CTRL-p CTRL-q` key sequence.

> **Note:** A process running as PID 1 inside a container is treated specially by Linux: it ignores any signal with the default action. So, the process will not terminate on `SIGINT` or `SIGTERM` unless it is coded to do so.

It is forbidden to redirect the standard input of a `docker attach` command while attaching to a tty-enabled container (i.e.: launched with `-t`).

While a client is connected to container’s stdio using `docker attach`, Docker uses a ~1MB memory buffer to maximize the throughput of the application. If this buffer is filled, the speed of the API connection will start to have an effect on the process output writing speed. This is similar to other applications like SSH. Because of this, it is not recommended to run performance critical applications that generate a lot of output in the foreground over a slow client connection. Instead, users should use the `docker logs` command to get access to the logs.

### Override the detach sequence

If you want, you can configure an override the Docker key sequence for detach. This is useful if the Docker default sequence conflicts with key sequence you use for other applications. There are two ways to define your own detach key sequence, as a per-container override or as a configuration property on your entire configuration.

To override the sequence for an individual container, use the `--detach-keys=""` flag with the `docker attach` command. The format of the `<sequence>` is either a letter [a-Z], or the `ctrl-` combined with any of the following:

- `a-z` (a single lowercase alpha character )
- `@` (at sign)
- `[` (left bracket)
- `\\` (two backward slashes)
- `_` (underscore)
- `^` (caret)

These `a`, `ctrl-a`, `X`, or `ctrl-\\` values are all examples of valid key sequences. To configure a different configuration default key sequence for all containers, see [**Configuration file** section](https://docs.docker.com/engine/reference/commandline/cli/#configuration-files).

<br>

## 2. docker exec

### Description

Run a command in a running container

然而使用attach 命令有时候并不方便。当多个窗口同时attach 到同一个容器的时候，所有窗口都会同步显示；当某个窗口因命令阻塞时，其他窗口也无法执行操作了。从Docker 的1.3.0 版本起， Docker 提供了一个更加方便的工具exec 命令，可以在运行中容器内直接执行任意命令。

### Usage

```none
docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
```

### Options

| Name, shorthand      | Default | Description                                                  |
| -------------------- | ------- | ------------------------------------------------------------ |
| `--detach , -d`      |         | Detached mode: run command in the background                 |
| `--detach-keys`      |         | Override the key sequence for detaching a container          |
| `--env , -e`         |         | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) Set environment variables |
| `--interactive , -i` |         | Keep STDIN open even if not attached                         |
| `--privileged`       |         | Give extended privileges to the command                      |
| `--tty , -t`         |         | Allocate a pseudo-TTY                                        |
| `--user , -u`        |         | Username or UID (format: <name\|uid>[:<group\|gid>])         |
| `--workdir , -w`     |         | [**API 1.35+**](https://docs.docker.com/engine/api/v1.35/) Working directory inside the container |


### Extended description

The `docker exec` command runs a new command in a running container.

The command started using `docker exec` only runs while the container’s primary process (`PID 1`) is running, and it is not restarted if the container is restarted.

COMMAND will run in the default directory of the container. If the underlying image has a custom directory specified with the WORKDIR directive in its Dockerfile, this will be used instead.

COMMAND should be an executable, a chained or a quoted command will not work. Example: `docker exec -ti my_container "echo a && echo b"` will not work, but `docker exec -ti my_container sh -c "echo a && echo b"` will.