# 命令 search

### Description

Search the Docker Hub for images

### Usage

```none
docker search [OPTIONS] TERM
```

### Options

| Name, shorthand | Default | Description                                                  |
| --------------- | ------- | ------------------------------------------------------------ |
| `--automated`   |         | [**deprecated**](https://docs.docker.com/engine/deprecated/) Only show automated builds |
| `--filter , -f` |         | Filter output based on conditions provided                   |
| `--format`      |         | Pretty-print search using a Go template                      |
| `--limit`       | `25`    | Max number of search results                                 |
| `--no-trunc`    |         | Don’t truncate output<br>不截断输出结果（诸如镜像描述等很长内容，如果截断则只显示部分） |
| `--stars , -s`  |         | [**deprecated**](https://docs.docker.com/engine/deprecated/) Only displays with at least x stars |

### Extended description

Search [Docker Hub](https://hub.docker.com/) for images

See [*Find Public Images on Docker Hub*](https://docs.docker.com/engine/tutorials/dockerrepos/#searching-for-images) for more details on finding shared images from the command line.

> **Note**: Search queries return a maximum of 25 results.

### Examples

#### 1) Search images by name

This example displays images with a name containing ‘mysql’:

```none
$ docker search mysql
NAME                DESCRIPTION                                  STARS OFFICIAL AUTOMATED
mysql               MySQL is a widely used, open-source relation…  9005   [OK]
mariadb             MariaDB is a community-developed fork of MyS…  3180   [OK]
mysql/mysql-server  Optimized MySQL Server Docker images. Create…  669           [OK]
centurylink/mysql   Image containing mysql. Optimized to be link…  61            [OK]
mysql/mysql-cluster Experimental MySQL Cluster Docker images. Cr…  59
deitch/mysql-backup REPLACED! Please use http://hub.docker.com/r…  41            [OK]
bitnami/mysql       Bitnami MySQL Docker Image                     35            [OK]
tutum/mysql         Base docker image to run a MySQL database se…  34
...
```

#### 2) Display non-truncated description (--no-trunc)

This example displays images with a name containing ‘busybox’, at least 3 stars and the description isn’t truncated in the output:

```
$ docker search --stars=3 --no-trunc busybox
NAME                DESCRIPTION           STARS     OFFICIAL   AUTOMATED
busybox             Busybox base image.   325       [OK]       
progrium/busybox                          50                   [OK]
radial/busyboxplus  Full-chain, Internet enabled, busybox made from scratch. Comes in git and cURL flavors.                         8                    [OK]
```

上述例子选择了不截断输出信息，因此镜像`radial/busyboxplus`的描述被完整的显示了出来

#### 3) Limit search results (--limit)

The flag `--limit` is the maximum number of results returned by a search. This value could be in the range between 1 and 100. The default value of `--limit` is 25.

限制最大可显示的搜索结果数，范围是1~100，默认为25

#### 4) Filtering

通过`-f`或者`--filter`选线过滤搜索结果。

The filtering flag (`-f` or `--filter`) format is a `key=value` pair. If there is more than one filter, then pass multiple flags (e.g. `--filter "foo=bar" --filter "bif=baz"`)

The currently supported filters are:

- stars (int - number of stars the image has)
- is-automated (boolean - true or false) - is the image automated or not
- is-official (boolean - true or false) - is the image official or not

##### STARS

This example displays images with a name containing ‘busybox’ and **at least** 3 stars:

```
$ docker search --filter stars=3 busybox

NAME                DESCRIPTION                                STARS  OFFICIAL  AUTOMATED
busybox             Busybox base image.                          325    [OK]       
progrium/busybox                                                  50               [OK]
radial/busyboxplus  Full-chain, Internet enabled, busybox made...  8               [OK]
```

##### IS-AUTOMATED

This example displays images with a name containing ‘busybox’ and are automated builds:

```
$ docker search --filter is-automated busybox

NAME                DESCRIPTION                                STARS  OFFICIAL  AUTOMATED
progrium/busybox                                                  50            [OK]
radial/busyboxplus  Full-chain, Internet enabled, busybox made...  8            [OK]
```

##### IS-OFFICIAL

This example displays images with a name containing ‘busybox’, at least 3 stars and are official builds:

```
$ docker search --filter "is-official=true" --filter "stars=3" busybox

NAME                 DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
progrium/busybox                                                     50                   [OK]
radial/busyboxplus   Full-chain, Internet enabled, busybox made...   8                    [OK]
```

#### 5) Format the output

通过格式化输出，可以选择显示那些内容，按什么顺序显示。

The formatting option (`--format`) pretty-prints search output using a Go template.

Valid placeholders for the Go template are:

| Placeholder    | Description                       |
| :------------- | :-------------------------------- |
| `.Name`        | Image Name                        |
| `.Description` | Image description                 |
| `.StarCount`   | Number of stars for the image     |
| `.IsOfficial`  | “OK” if image is official         |
| `.IsAutomated` | “OK” if image build was automated |

When you use the `--format` option, the `search` command will output the data exactly as the template declares. If you use the `table` directive, column headers are included as well.

The following example uses a template without headers and outputs the `Name` and `StarCount` entries separated by a colon for all images:

```
{% raw %}
$ docker search --format "{{.Name}}: {{.StarCount}}" nginx

nginx: 5441
jwilder/nginx-proxy: 953
richarvey/nginx-php-fpm: 353
million12/nginx-php: 75
webdevops/php-nginx: 70
h3nrik/nginx-ldap: 35
bitnami/nginx: 23
evild/alpine-nginx: 14
million12/nginx: 9
maxexcloo/nginx: 7
{% endraw %}
```

This example outputs a table format:

```
{% raw %}
$ docker search --format "table {{.Name}}\t{{.IsAutomated}}\t{{.IsOfficial}}" nginx

NAME                                     AUTOMATED           OFFICIAL
nginx                                                        [OK]
jwilder/nginx-proxy                      [OK]                
richarvey/nginx-php-fpm                  [OK]                
jrcs/letsencrypt-nginx-proxy-companion   [OK]                
million12/nginx-php                      [OK]                
webdevops/php-nginx                      [OK]                
{% endraw %}
```