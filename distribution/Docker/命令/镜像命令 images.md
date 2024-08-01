# 命令 images

列出本地镜像。

## Usage

```
docker images [OPTIONS] [REPOSITORY[:TAG]]
```

## Options

| Name, shorthand | Default | Description                                         |
| --------------- | ------- | --------------------------------------------------- |
| `--all , -a`    |         | Show all images (default hides intermediate images) |
| `--digests`     |         | Show digests                                        |
| `--filter , -f` |         | Filter output based on conditions provided          |
| `--format`      |         | Pretty-print images using a Go template             |
| `--no-trunc`    |         | Don’t truncate output                               |
| `--quiet , -q`  |         | Only show numeric IDs                               |

## Extended description

The default `docker images` will show all top level images, their repository and tags, and their size.

Docker images have intermediate layers that increase reusability, decrease disk usage, and speed up `docker build` by allowing each step to be cached. These intermediate layers are not shown by default.

The `SIZE` is the cumulative space taken up by the image and all its parent images. This is also the disk space used by the contents of the Tar file created when you `docker save` an image.

An image will be listed more than once if it has multiple repository names or tags. This single image (identifiable by its matching `IMAGE ID`) uses up the `SIZE` listed only once.