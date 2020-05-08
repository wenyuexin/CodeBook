# K8s资源对象——Pod

Pod的基本介绍在文件`基本概念.md`有所描述。

Pod 是最小可部署的 Kubernetes 对象模型，是 Kubernetes 应用程序的基本执行单元；就像一个豌豆荚包含了多个豌豆，Pod是一组容器（例如 Docker 容器），封装了应用程序容器（某些情况下封装多个容器）、存储资源、唯一网络 IP 以及控制容器应该如何运行的选项。

一个Pod定义文件存在许多属性，例如：

|       属性名称        | 取值类型 | 是否必选 |      说明       |
| :-------------------: | :------: | :------: | :-------------: |
|      apiVersion       |  String  |    是    |     版本号      |
|         kind          |  String  |    是    |    类型，Pod    |
|       metadata        |  Object  |    是    |     元数据      |
|     metadata.name     |  String  |    是    |    Pod的名称    |
|  metadata.namespace   |  String  |    是    | Pod所属命名空间 |
|   metadata.labels[]   |   List   |          |    标签列表     |
| metadata.annotation[] |   List   |          |    注解列表     |
|         spec          |  Object  |    是    |  Pod的详细定义  |

spec包含非常多的具体项，这里就不展开了。

## 1 基本用法

使用Docker时，可以使用docker run命令创建并启动一个容器。而在Kubernetes系统中对长时间运行容器的要求是：其主程序需要一直在前台执行。

假设，容器执行了后台运行的脚本，在kubelet创建包含这个容器的Pod之后运行完该命令，即认为Pod执行结束，将立刻销毁该Pod。如果为该Pod定义了ReplicationController，则系统会监控到该Pod已经终止，之后根据RC定义中Pod的replicas副本数量生成一个新的Pod。一旦创建新的Pod，就在执行完启动命令后陷入无限循环的过程中。因此，Kubernetes需要用户自己创建的Docker镜像，并以一个前台命令作为启动命令。对于无法改造为前台执行的应用，也可以使用开源工具Supervisor辅助进行前台运行的功能。

Pod可以包含一个或者多个容器。对于多个容器的情况，例如：

```
apiVersion: v1 
kind: Pod
metadata :
  name: redis-php
  labels:
    name: redis-php 
spec:
  containers:
  - name: frontend （容器1）
    image: kubeguide/guestbook-php-frontend:localredis
    ports:
    - containerPort: 80
  - name: redis （容器2）
    image: kubeguide/ redis master
    ports :
    - containerPort: 6379
```

属于同一个Pod的多个容器应用之间相互访问时仅需要通过localhost就可以通信，例如`localhost:6379`。

## 2 静态Pod

*Static Pods* are managed directly by the kubelet daemon on a specific node, without the [API server](https://kubernetes.io/docs/reference/generated/kube-apiserver/) observing them. Unlike Pods that are managed by the control plane (for example, a [Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)); instead, the kubelet watches each static Pod (and restarts it if it crashes).

**Static Pods are always bound to one [Kubelet](https://kubernetes.io/docs/reference/generated/kubelet) on a specific node.**

The kubelet automatically tries to create a [mirror Pod](https://kubernetes.io/docs/reference/glossary/?all=true#term-mirror-pod) on the Kubernetes API server for each static Pod. This means that the Pods running on a node are visible on the API server, but cannot be controlled from there.

静态Pod是由kubelet进行管理的仅存在于特定Node上的Pod。它们不能通过API Server进行管理，无法与ReplicationController、Deployment或者DaemonSet进行关联，并且kubelet无法对它们进行健康检查。

创建：

You can configure a static Pod with either a [file system hosted configuration file](https://kubernetes.io/docs/tasks/configure-pod-container/static-pod/#configuration-files) or a [web hosted configuration file](https://kubernetes.io/docs/tasks/configure-pod-container/static-pod/#pods-created-via-http).

#### Filesystem-hosted static Pod manifest

Manifests are standard Pod definitions in JSON or YAML format in a specific directory. Use the `staticPodPath: ` field in the [kubelet configuration file](https://kubernetes.io/docs/tasks/administer-cluster/kubelet-config-file), which periodically scans the directory and creates/deletes static Pods as YAML/JSON files appear/disappear there. Note that the kubelet will ignore files starting with dots when scanning the specified directory.

For example, this is how to start a simple web server as a static Pod:

1. 先选Node：Choose a node where you want to run the static Pod. In this example, it’s `my-node1`. 

   ```shell
   ssh my-node1
   ```

2. 选择目录，防止Pod配置文件：Choose a directory, say `/etc/kubelet.d` and place a web server Pod definition there, for example `/etc/kubelet.d/static-web.yaml`:

   ```shell
   # Run this command on the node where kubelet is running
   mkdir /etc/kubelet.d/
   cat <<EOF >/etc/kubelet.d/static-web.yaml （cat命令用于查看文件内容）
   apiVersion: v1
   kind: Pod
   metadata:
     name: static-web
     labels:
       role: myrole
   spec:
     containers:
       - name: web
         image: nginx
         ports:
           - name: web
             containerPort: 80
             protocol: TCP
   EOF
   ```

3. Configure your kubelet on the node to use this directory by running it with `--pod-manifest-path=/etc/kubelet.d/` argument. On Fedora edit `/etc/kubernetes/kubelet` to include this line:

   ```
   KUBELET_ARGS="--cluster-dns=10.254.0.10 --cluster-domain=kube.local --pod-manifest-path=/etc/kubelet.d/"
   ```

   or add the `staticPodPath: ` field in the [kubelet configuration file](https://kubernetes.io/docs/tasks/administer-cluster/kubelet-config-file).

4. Restart the kubelet. On Fedora, you would run:

   ```shell
   # Run this command on the node where the kubelet is running
   systemctl restart kubelet
   ```

#### Web-hosted static pod manifest

Kubelet periodically downloads a file specified by `--manifest-url=` argument and interprets it as a JSON/YAML file that contains Pod definitions. Similar to how [filesystem-hosted manifests](https://kubernetes.io/docs/tasks/configure-pod-container/static-pod/#configuration-files) work, the kubelet refetches the manifest on a schedule. If there are changes to the list of static Pods, the kubelet applies them.

To use this approach:

1. Create a YAML file and store it on a web server so that you can pass the URL of that file to the kubelet.

   ```yaml
   apiVersion: v1
   kind: Pod
   metadata:
     name: static-web
     labels:
       role: myrole
   spec:
     containers:
       - name: web
         image: nginx
         ports:
           - name: web
             containerPort: 80
             protocol: TCP
   ```

2. Configure the kubelet on your selected node to use this web manifest by running it with `--manifest-url=`. On Fedora, edit `/etc/kubernetes/kubelet` to include this line:

   ```
   KUBELET_ARGS="--cluster-dns=10.254.0.10 --cluster-domain=kube.local --manifest-url=<manifest-url>"
   ```

3. Restart the kubelet. On Fedora, you would run:

   ```shell
   # Run this command on the node where the kubelet is running
   systemctl restart kubelet
   ```

## 3 Pod容器共享Volume

同一个Pod中的多个容器能够共享Pod级别的存储卷Volume。【可以参考`02 基本概念.md`的相关内容】

k8s中的Volume和docker中的Volume有所区别，前者属于Pod，Pod中的多个容器可以将之挂载到各自具体的目录下；k8s中Volume的生命周期和Pod相同，容器崩溃不影响数据卷中的内容。

例如：

```
apiVersion: v1
kind: Pod
metadata:
  name: volume-pod
spec:
  containers:
  - name: tomcat
    image: tomcat
    ports:
    - containerPort: 8080
    volumeMounts :
    - name: app-logs
      mountPath: /usr/1ocal/tomcat/1ogs （tomcat容器的挂载点）
  - name: busybox
    image: busybox 
    command: ["sh", "-c", "tail -f /1ogs/catalina* .1og"]
    volumeMounts :
    - name: app-logs
      mountPath: /1ogs （busybox容器的挂载点）
  volumes: (Pod的数据卷)
  - name: app-logs
    emptyDir: {}
```

## 4 ConfigMap与配置管理

A ConfigMap is an API object used to store non-confidential (非机密的, 非保密的) data in key-value pairs. [Pods](https://kubernetes.io/docs/concepts/workloads/pods/pod-overview/) can consume ConfigMaps as environment variables, command-line arguments, or as configuration files in a [volume](https://kubernetes.io/docs/concepts/storage/volumes/).

环境配置与容器镜像解耦：A ConfigMap allows you to **decouple environment-specific configuration from your [container images](https://kubernetes.io/docs/reference/glossary/?all=true#term-image)**, so that your applications are easily portable.

ConfigMap供容器使用的典型用法如下。
（1）生成为容器内的环境变量。
（2）设置容器启动命令的启动参数（需设置为环境变量）。
（3）以Volume的形式挂载为容器内部的文件或目录。

### 创建

通过YAML配置文件或者直接使用kubectl create configmap命令行的方式来创建ConfigMap。

#### 通过YAML配置文件创建

Unlike most Kubernetes objects that have a `spec`, a ConfigMap has a **`data` section to store items (keys) and their values**.

例1

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: game-demo
data:
  # property-like keys; each key maps to a simple value
  player_initial_lives: 3
  ui_properties_file_name: "user-interface.properties"
  #
  # file-like keys
  game.properties: |
    enemy.types=aliens,monsters
    player.maximum-lives=5
  user-interface.properties: |
    color.good=purple
    color.bad=yellow
    allow.textmode=true
```

例2


```
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-appvars
  data:  （有时候可将配置文件名作为key，将配置文件内容作为value，从而将配置写入ConfigMap）
    app1og1evel: info
    appdatadir: /var/data
```

然后执行命令，创建configmap，例如：

```
$kubectl create -f cm-appvars.yaml 
configmap "cm-appvars" created
```

#### 通过**kubectl**命令行方式创建：

不使用YAML文件，直接通过`kubectl create configmap`也可以创建ConfigMap，可以使用参数`--from-file`或`--from-literal`指定内容，并且可以在一行命令中指定多个参数。具体有以下方式：

- **用文件创建**：通过`--from-file`参数从文件中进行创建，可以指定key的名称，也可以在一个命令行中创建包含多个key的ConfigMap

```
语法 # kubectl create configmap NAME --from-file=[key=]source
例如 # kubectl create configmap cm-server.xm1 --from-file=server.xm1
```

- **以目录创建**：通过`--from-file`参数从目录中进行创建，该目录下的每个配置文件名都被设置为key，文件的内容被设置为value

```
语法 # kubectl create configmap NAME --from-file=config-files-dir
例如 # kubectl create configmap cm-appconf --from-file=configfiles （导入configfiles目录中的
                                                                    所有配置文件）
```

- **用文本创建**：使用`--from-literal`时会从文本中进行创建，直接将指定的`key#=value#`创建为ConfigMap的内容，语法为：

```
语法 # kubectl create configmap NAME --from-literal=key1=value1 --from-literal=key2=value2
例如 # kubectl create configmap cm-appenv --from-literal=1og1eve1=info --from-literal=appdatadir=/var/data
```

