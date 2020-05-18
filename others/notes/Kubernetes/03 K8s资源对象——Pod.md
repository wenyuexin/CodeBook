# K8s资源对象——Pod

Pod的基本介绍在文件`基本概念.md`有所描述。本文中，除了介绍Pod的创建、使用、生命周期等内容外，主要精力在于说明Pod的调度（也就是什么Pod放到什么Node上）。

<br>

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

```yaml
apiVersion: v1 
kind: Pod
metadata :
  name: redis-php    # Pod的名称
  labels:
    name: redis-php  # 定义标签
spec:
  containers:
  - name: frontend   # 容器1
    image: kubeguide/guestbook-php-frontend:localredis
    ports:
    - containerPort: 80
  - name: redis      # 容器2
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

### 创建

You can configure a static Pod with either a [file system hosted configuration file](https://kubernetes.io/docs/tasks/configure-pod-container/static-pod/#configuration-files) or a [web hosted configuration file](https://kubernetes.io/docs/tasks/configure-pod-container/static-pod/#pods-created-via-http).

#### Filesystem-hosted static Pod manifest

Manifests are standard Pod definitions in JSON or YAML format in a specific directory. Use the `staticPodPath: ` field in the [kubelet configuration file](https://kubernetes.io/docs/tasks/administer-cluster/kubelet-config-file), which periodically scans the directory and creates/deletes static Pods as YAML/JSON files appear/disappear there. Note that the kubelet will ignore files starting with dots when scanning the specified directory.

For example, this is how to start a simple web server as a static Pod:

1. 先选Node：

   Choose a node where you want to run the static Pod. In this example, it’s `my-node1`. 

   ```shell
   ssh my-node1
   ```

2. 选择目录，放置Pod配置文件：

   Choose a directory, say `/etc/kubelet.d` and place a web server Pod definition there, for example `/etc/kubelet.d/static-web.yaml`:

   ```shell
   # Run this command on the node where kubelet is running
   mkdir /etc/kubelet.d/
   cat <<EOF >/etc/kubelet.d/static-web.yaml  # cat命令用于查看文件内容
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

```yaml
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
      mountPath: /usr/1ocal/tomcat/1ogs  # tomcat容器的挂载点
  - name: busybox
    image: busybox 
    command: ["sh", "-c", "tail -f /1ogs/catalina* .1og"]
    volumeMounts :
    - name: app-logs
      mountPath: /1ogs    # busybox容器的挂载点
  volumes:                # Pod的数据卷
  - name: app-logs
    emptyDir: {}
```

## 4 ConfigMap及其在Pod中的配置管理

A ConfigMap is an API object used to store non-confidential (非机密的, 非保密的) data in key-value pairs. [Pods](https://kubernetes.io/docs/concepts/workloads/pods/pod-overview/) can consume ConfigMaps as environment variables, command-line arguments, or as configuration files in a [volume](https://kubernetes.io/docs/concepts/storage/volumes/).

环境配置与容器镜像解耦：A ConfigMap allows you to **decouple environment-specific configuration from your [container images](https://kubernetes.io/docs/reference/glossary/?all=true#term-image)**, so that your applications are easily portable.

ConfigMap供容器使用的典型用法如下。
（1）生成为容器内的环境变量。
（2）设置容器启动命令的启动参数（需设置为环境变量）。
（3）以Volume的形式挂载为容器内部的文件或目录。

### 创建ConfigMap

通过YAML配置文件或者直接使用kubectl create configmap命令行的方式来创建ConfigMap。

#### 通过YAML配置文件创建

Unlike most Kubernetes objects that have a `spec`, a ConfigMap has a **`data` section to store items (keys) and their values**.

例1

```yaml
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


```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-appvars
   # 有时候可将配置文件名作为key，将配置文件内容作为value，从而将配置写ConfigMap
  data:
    app1og1evel: info
    appdatadir: /var/data
```

然后执行命令，创建configmap，例如：

```
$kubectl create -f cm-appvars.yaml 
configmap "cm-appvars" created
```

#### 通过**kubectl**命令行方式创建

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

### Pod中使用ConfigMap

可以通过环境变量方式使用，或者通过`volumeMount`使用ConfigMap.

- 通过环境变量使用

以下面这个ConfigMap为例

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-appvars
  data:  
    app1og1evel: info
    appdatadir: /var/data
```

然后，使用**valueFrom**将ConfigMap中的键值对导入Pod中：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: cm-test-pod 
spec:
  containers:
  - name: cm-test
    image: busybox 
    # env命令：查询环境变量
    # grep命令：查找文件里符合条件的字符串
    command: [ "/bin/sh", "-c", "env | grep APP" ] 
    env:
    - name: APPLOGLEVEL
      valueFrom:               # key “apploglevel"对应的值
        configMapKeyRef:
          name: cm-appvars     # 环境变量的值取自cm-appvars:
          key: apploglevel     # key为apploglevel
    - name: APPDATADIR
      valueFrom:               # key "appdatadir"对应的值
        configMapKeyRef:
          name: cm-appvars     # 环境变量的值取自cm-appvars
          key: appdatadir      # key为appdatadir
   restartPolicy: Never        # Pod在执行完启动命令后将会退出，并且不会被系统自动重启
```

Kubernetes从1.6版本开始，引入了一个新的字段**envFrom**，实现了在Pod环境中将ConfigMap（也可用于Secret资源对象）中所有定义的`key=value`自动生成为环境变量：

```yaml
apiVersion: v1
kind: Pod
metadata :
  name: cm-test-pod
spec:
  containers:
  - name: cm-test
    image: busybox
    command: [ "/bin/sh", "-c"， "env" ]
    envFrom:
    - configMapRef
      name: cm-appvars   # 根据cm-appvars中的key=value自动生成环境变量
  restartPolicy: Never
```

- 通过**volumeMount**使用ConfigMap

假定文件`cm-appconfigfiles.yaml`在data中写入了两个配置文件，如下：

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-serverxml
data:
  key-serverxml: |
    <?xml version='1.0' encoding='utf-8'?>
    # 后续内容略过
  key-loggingproperties: |
    # 文件内容略过
```

然后将`cm-appconfigfiles`中的内容以文件的形式mount到容器内的`/configfiles`目录下。即

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: cm-test-app
spec:
  containers:
  - name: cm-test-app
    image: kubeguide/tomcat-app:v1
    ports:
    - containerPort: 8080
    volumeMounts: 
    - name: serverxml               # 引用volume的名称
      mountPath: /configfiles       # 挂载到容器内的目录
  volumes:
  - name: serverxml                 # 定义Volume的名称
    configMap:
      name: cm-appconfigfiles       # 使用ConfigMap cm-appconfigfiles
      items:
      - key: key-serverxml          # key=key-serverxml
        path: server.xml            # value将server.xml文件名进行挂载
      - key: key-loggingproperties  # key=key-loggingproperties
        path: logging.properties    # value将logging.properties文件名进行挂载
```

另一个例子：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
  containers:
    - name: demo
      image: game.example/demo-game
      env:
        # Define the environment variable
        - name: PLAYER_INITIAL_LIVES    # Notice that the case is different here
                                        # from the key name in the ConfigMap.
          valueFrom:
            configMapKeyRef:
              name: game-demo           # The ConfigMap this value comes from.
              key: player_initial_lives # The key to fetch.
        - name: UI_PROPERTIES_FILE_NAME
          valueFrom:
            configMapKeyRef:
              name: game-demo
              key: ui_properties_file_name
      volumeMounts:           # 将volume中的config挂载到本容器中的"/config"上
      - name: config
        mountPath: "/config"
        readOnly: true
  volumes:
    # Set volumes at the Pod level, then mount them into containers inside that Pod
    - name: config
      configMap:
        # Provide the name of the ConfigMap you want to mount.
        name: game-demo       # ConfigMap的具体内容这里就不贴了
```

### 使用ConfigMap的限制条件

- ConfigMap必须在Pod之前创建。

- ConfigMap受Namespace限制，只有处于相同Namespace中的Pod才可以引用它。

- ConfigMap中的配额管理还未能实现。

- kubelet只支持可以被API Server管理的Pod使用ConfigMap。

  kubelet在本Node上通过`--manifest-url`或`--config`自动创建的静态Pod将无法引用ConfigMap。

- 在Pod对ConfigMap进行挂载（volumeMount）操作时，在容器内部只能挂载为“目录”，无法挂载为“文件”。在挂载到容器内部后，在目录下将包含ConfigMap定义的每个item.

  如果在该目录原来还有其他文件，则容器内的该目录将被挂载的ConfigMap覆盖。如果应用程序需要保留原来的其他文件，则需要进行额外的处理：可以将ConfigMap挂载到容器内部的临时目录，再通过启动脚本将配置文件复制或者链接到（cp或link命令）应用所用的实际配置目录下。

## 5 在容器内获取Pod的信息

每个Pod在被成功创建出来之后，都会被系统分配唯一的名字、IP地址，并且处于某个Namespace中，这些与Pod相关的信息可以通过`Downward API`获取。

具体有以下两种方式将Pod信息注入容器内部：

- 环境变量：用于单个变量，可以将Pod信息和Container信息注入容器内部。
- Volume挂载：将数组类信息生成为文件并挂载到容器内部。

可以参考：

[通过环境变量将Pod信息呈现给容器 - Kubernetes](https://kubernetes.io/zh/docs/tasks/inject-data-application/environment-variable-expose-pod-information/)

[通过文件将Pod信息呈现给容器 - Kubernetes](https://kubernetes.io/zh/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/)

例子：

- 通过环境变量获取

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: dapi-envars-fieldref
spec:
  containers:
    - name: test-container
      image: k8s.gcr.io/busybox
      command: [ "sh", "-c"]
      args:
      - while true; do
          echo -en '\n';
          printenv MY_NODE_NAME MY_POD_NAME MY_POD_NAMESPACE;
          printenv MY_POD_IP MY_POD_SERVICE_ACCOUNT;
          sleep 10;
        done;
      env:
        - name: MY_NODE_NAME
          valueFrom:
            fieldRef:
              fieldPath: spec.nodeName
        - name: MY_POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: MY_POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        - name: MY_POD_IP
          valueFrom:
            fieldRef:
              fieldPath: status.podIP
        - name: MY_POD_SERVICE_ACCOUNT
          valueFrom:
            fieldRef:
              fieldPath: spec.serviceAccountName
  restartPolicy: Never
```

在上面这个配置文件中，可以看到五个环境变量。`env`字段是一个EnvVars类型的数组。 数组中第一个元素指定`MY_NODE_NAME`这个环境变量从Pod的`spec.nodeName`字段获取变量值。同样，其它环境变量也是从Pod的字段获取它们的变量值。

- 通过Volume获取

在文件`02 基本概念.md`中有提过，K8s的Volume支持多种类型的卷，其中就包括`downwardAPI`


```yaml
apiVersion: v1
kind: Pod
metadata:
  name: kubernetes-downwardapi-volume-example
  labels:
    zone: us-est-coast
    cluster: test-cluster1
    rack: rack-22
  annotations:
    build: two
    builder: john-doe
spec:
  containers:
    - name: client-container
      image: k8s.gcr.io/busybox
      command: ["sh", "-c"]
      args:
      - while true; do
          if [[ -e /etc/podinfo/labels ]]; then
            echo -en '\n\n'; cat /etc/podinfo/labels; fi;
          if [[ -e /etc/podinfo/annotations ]]; then
            echo -en '\n\n'; cat /etc/podinfo/annotations; fi;
          sleep 5;
        done;
      volumeMounts:     # 容器将Pod的Volume挂载到自身某个目录下
        - name: podinfo
          mountPath: /etc/podinfo
  volumes:              # 挂载downwardAPI类型的卷，其中记录了Pod的信息
    - name: podinfo
      downwardAPI:
        items:
          - path: "labels"
            fieldRef:
              fieldPath: metadata.labels       # Pod的标签
          - path: "annotations"
            fieldRef:
              fieldPath: metadata.annotations  # Pod的注解
```

在配置文件中，你可以看到Pod有一个`downwardAPI`类型的Volume，并且挂载到容器中的`/etc`。

查看`downwardAPI`下面的`items`数组。每个数组元素都是一个DownwardAPIVolumeFile。 第一个元素指示Pod的`metadata.labels`字段的值保存在名为`labels`的文件中。 第二个元素指示Pod的`annotations`字段的值保存在名为`annotations`的文件中。

## 6 Pod的生命周期

### Pod phase

A Pod’s `status` field is a [PodStatus](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#podstatus-v1-core) object, which has a `phase` field.

The phase (运行阶段) of a Pod is a simple, **high-level summary of where the Pod is in its lifecycle**. The phase is not intended to be a comprehensive rollup of observations of Container or Pod state, nor is it intended to be a comprehensive state machine. 

阶段并不是对容器或Pod的综合汇总，也不是为了做为综合状态机。

The number and meanings of Pod phase values are tightly guarded. Other than what is documented here, nothing should be assumed about Pods that have a given `phase` value.

Here are the possible values for `phase`:

|      Value      | Description                                                  |
| :-------------: | :----------------------------------------------------------- |
| 挂起 `Pending`  | The Pod has been accepted by the Kubernetes system, but one or more of the Container images has not been created. This includes time before being scheduled as well as time spent downloading images over the network, which could take a while.<br>Pod 已被 Kubernetes 系统接受，但有一个或者多个容器镜像尚未创建 |
| 运行中`Running` | The Pod has been bound to a node, and all of the Containers have been created. At least one Container is still running, or is in the process of starting or restarting.<br>该 Pod 已经绑定到了一个节点上，Pod 中所有的容器都已被创建。至少有一个容器正在运行，或者正处于启动或重启状态。 |
| 成功`Succeeded` | All Containers in the Pod have terminated in success, and will not be restarted.<br>Pod 中的所有容器都被成功终止，并且不会再重启。 |
|  失败 `Failed`  | All Containers in the Pod have terminated, and at least one Container has terminated in failure. That is, the Container either exited with non-zero status or was terminated by the system.<br>Pod 中的所有容器都已终止了，并且至少有一个容器是因为失败终止 |
| 未知 `Unknown`  | For some reason the state of the Pod could not be obtained, typically due to an error in communicating with the host of the Pod.<br>因为某些原因无法取得 Pod 的状态，通常是因为与 Pod 所在主机通信失败 |

### Pod conditions —— Pod状态

Pod 有一个 PodStatus 对象，其中包含一个[PodConditions](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#podcondition-v1-core)数组。

PodCondition数组每个元素是以下6种字段之一：

- The `lastProbeTime` field provides a timestamp for when the Pod condition was last probed.
- The `lastTransitionTime` field provides a timestamp for when the Pod last transitioned from one status to another.
- The `message` field is a human-readable message indicating details about the transition.
- The `reason` field is a unique, one-word, CamelCase reason for the condition’s last transition.
- The `status` field is a string, with possible values “`True`”, “`False`”, and “`Unknown`”.
- The `type` field is a string with the following possible values:
  - `PodScheduled`: the Pod has been scheduled to a node;
  - `Ready`: the Pod is able to serve requests and should be added to the load balancing pools of all matching Services;
  - `Initialized`: all [init containers](https://kubernetes.io/docs/concepts/workloads/pods/init-containers) have started successfully;
  - `ContainersReady`: all containers in the Pod are ready.

### Pod probes —— Pod探针

探针是由 kubelet 对容器执行的定期诊断。要执行诊断，kubelet 需要调用由容器实现的 Handler。

有三种类型的处理程序（Handler）：

- [ExecAction](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#execaction-v1-core): Executes a specified command inside the Container. The diagnostic is considered successful if the command exits with a status code of 0.  在容器内执行指定命令。
- [TCPSocketAction](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#tcpsocketaction-v1-core): Performs a TCP check against the Container’s IP address on a specified port. The diagnostic is considered successful if the port is open. 对指定端口上的容器IP地址进行 TCP 检查。
- [HTTPGetAction](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#httpgetaction-v1-core): Performs an HTTP Get request against the Container’s IP address on a specified port and path. The diagnostic is considered successful if the response has a status code greater than or equal to 200 and less than 400.  对指定的端口和路径上的容器 IP 地址执行 HTTP Get 请求。

每次探测都将获得以下三种结果之一：

- Success: The Container passed the diagnostic. 通过诊断
- Failure: The Container failed the diagnostic. 未通过诊断
- Unknown: The diagnostic failed, so no action should be taken. 诊断失败

The kubelet can optionally perform and react to three kinds of probes on running Containers:

- `livenessProbe`: Indicates whether the Container is running. If the liveness probe fails, the kubelet kills the Container, and the Container is subjected to its `restart policy`. If a Container does not provide a liveness probe, the default state is `Success`. 指示容器是否正在运行。
- `readinessProbe`: Indicates whether the Container is ready to service requests. If the readiness probe fails, the endpoints controller removes the Pod’s IP address from the endpoints of all Services that match the Pod. The default state of readiness before the initial delay is `Failure`. If a Container does not provide a readiness probe, the default state is `Success`. 指示容器是否准备好服务请求。
- `startupProbe`: Indicates whether the application within the Container is started. All other probes are disabled if a startup probe is provided, until it succeeds. If the startup probe fails, the kubelet kills the Container, and the Container is subjected to its restart policy. If a Container does not provide a startup probe, the default state is `Success`. 指示容器中的应用是否已经启动。

### 重启策略

PodSpec 中有一个 `restartPolicy` 字段，可能的值为 **Always**（默认值）、**OnFailure** 和 **Never**。 `restartPolicy` 适用于 Pod 中的所有容器。`restartPolicy` 仅指通过同一节点上的 kubelet 重新启动容器。失败的容器由 kubelet 以五分钟为上限的指数退避延迟（10秒，20秒，40秒…）重新启动，并在成功执行十分钟后重置。如 [Pod 文档](https://kubernetes.io/docs/user-guide/pods/#durability-of-pods-or-lack-thereof) 中所述，一旦绑定到一个节点，Pod 将永远不会重新绑定到另一个节点。

### Pod 的生命

一般来说，Pod 不会消失，直到个人或控制器人为的销毁Pod。这个规则的唯一例外是成功或失败的 `phase` 超过一段时间（由 master 确定）的Pod将过期并被自动销毁。

有三种可用的控制器（换句话说，Job RC Deployment等资源对象对应着不同生命周期的Pod）：

- 使用 [Job](https://kubernetes.io/docs/concepts/jobs/run-to-completion-finite-workloads/) 运行预期会终止的 Pod，例如批量计算。Job 仅适用于重启策略为 `OnFailure` 或 `Never` 的 Pod。
- 对预期不会终止的 Pod 使用 [ReplicationController](https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller/)、[ReplicaSet](https://kubernetes.io/docs/concepts/workloads/controllers/replicaset/) 和 [Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) ，例如 Web 服务器。 ReplicationController 仅适用于具有 `restartPolicy` 为 Always 的 Pod。
- 提供特定于机器的系统服务，使用 [DaemonSet](https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/) 为每台机器运行一个 Pod 。

所有这三种类型的控制器都包含一个 PodTemplate。建议创建适当的控制器，让它们来创建 Pod，而不是直接自己创建 Pod。这是因为单独的 Pod 在机器故障的情况下没有办法自动复原，而控制器却可以。

如果节点死亡或与集群的其余部分断开连接，则 Kubernetes 将应用一个策略将丢失节点上的所有 Pod 的 `phase` 设置为 Failed。

### 使用Probe的例子

```yaml
apiVersion: v1
kind: Pod
metadata:
  labels:
    test: liveness
  name: liveness-http
spec:
  containers:
  - args:
    - /server
    image: k8s.gcr.io/liveness
    livenessProbe:
      httpGet:
        # 当没有定义 "host" 时，使用 "PodIP"
        # host: my-host
        # 当没有定义 "scheme" 时，使用 "HTTP" scheme 只允许 "HTTP" 和 "HTTPS"
        # scheme: HTTPS
        path: /healthz
        port: 8080
        httpHeaders:
        - name: X-Custom-Header
          value: Awesome
      initialDelaySeconds: 15
      timeoutSeconds: 1
    name: liveness
```

- Pod 中只有一个容器并且正在运行。容器成功退出。

  - 记录完成事件。
  - 如果 `restartPolicy` 为：
  - Always：重启容器；Pod `phase` 仍为 Running。
  - OnFailure：Pod `phase` 变成 Succeeded。
  - Never：Pod `phase` 变成 Succeeded。

- Pod 中只有一个容器并且正在运行。容器退出失败。

  - 记录失败事件。
  - 如果 `restartPolicy` 为：
  - Always：重启容器；Pod `phase` 仍为 Running。
  - OnFailure：重启容器；Pod `phase` 仍为 Running。
  - Never：Pod `phase` 变成 Failed。

- Pod 中有两个容器并且正在运行。有一个容器退出失败。

  - 记录失败事件。

  - 如果 restartPolicy 为：

  - Always：重启容器；Pod `phase` 仍为 Running。

  - OnFailure：重启容器；Pod `phase` 仍为 Running。

  - Never：不重启容器；Pod `phase` 仍为 Running。

  - 如果有一个容器没有处于运行状态，并且两个容器退出：

  - 记录失败事件。

  - 如果`restartPolicy`为：

    - Always：重启容器；Pod `phase` 仍为 Running。
    - OnFailure：重启容器；Pod `phase` 仍为 Running。
    - Never：Pod `phase` 变成 Failed。

- Pod 中只有一个容器并处于运行状态。容器运行时内存超出限制：

  - 容器以失败状态终止。
  - 记录 OOM 事件。
  - 如果 `restartPolicy` 为：
  - Always：重启容器；Pod `phase` 仍为 Running。
  - OnFailure：重启容器；Pod `phase` 仍为 Running。
  - Never: 记录失败事件；Pod `phase` 仍为 Failed。

- Pod 正在运行，磁盘故障：

  - 杀掉所有容器。
  - 记录适当事件。
  - Pod `phase` 变成 Failed。
  - 如果使用控制器来运行，Pod 将在别处重建。

- Pod 正在运行，其节点被分段。

  - 节点控制器等待直到超时。
  - 节点控制器将 Pod `phase` 设置为 Failed。
  - 如果是用控制器来运行，Pod 将在别处重建。

## 7 Pod调度

**滚动升级**

早期版本中，只有一个Pod副本控制器RC，该控制器的实现方式是：RC独立于所控制的Pod，并通过Label标签这个松耦合关联关系控制目标Pod实例的创建和销毁。后来，RC升级为ReplicaSet，后者支持集合选择标签。另外，RC的作用实际正被Deployment替代，用于更加自动地完成Pod副本的部署、版本更新、回滚等功能。

与RC不同，ReplicaSet被设计成能控制多个不同标签的Pod副本。K8s借此可以实现滚动升级。例如：

```
selector:
  matchLabels:
    version: v2
  matchExpressions:
  - {key: version, operator: In, values:[v1,v2]}   # 同时包含v1和v2版本的Pod
```

Deployment的滚动升级也是借助ReplicaSet实现的。实际应用中，应该使用Deployment。

**在特定Node上运行**

通常，只关心Pod副本被成功调度到集群中的任何一个可用节点，而不关心具体会调度到哪个节点。但有时候会希望某种Pod的副本全部在指定的一个或者一些节点上运行，例如将MySQL数据库调度到一个具有SSD磁盘的目标节点上。此时可以使用Pod模板中的NodeSelector属性。

为此，需要先将对应的Node打上标签，然后再Pod模板中使用NodeSelector进行筛选。当然，在实际场景中需要面对非常多的问题。 

相关内容点可以参考官方文档：[将 Pod 分配给节点 - Kubernetes](https://kubernetes.io/docs/concepts/configuration/assign-pod-node/)

### Deployment 自动调度

例如

```yaml
# nginx-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata :
  name: nginx-deployment
spec:
  replicas: 3   # 由Deployment自动创建3个副本
  template:
    metadata:
    labels:
      app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
```

此时，Pod由系统全自动完成调度。它们各自最终运行在哪个节点上，完全由Master的Scheduler经过一系列算法计算得出，用户无法干预调度过程和结果。

### **NodeSelector**：定向调度

将Pod调度到指定的一些Node上，可以通过Node的标签（Label）和Pod的nodeSelector属性相匹配来实现。

- 首先通过kubectl label命令给目标Node打上一些标签：

```
语法 kubectl label nodes <node-name> <1abel-key>=<label-value>
例子 $ kubectl label nodes k8s-node-1 disktype=ssd
```

集群的节点名称可以执行 `kubectl get nodes` 命令获取。运行 `kubectl get nodes --show-labels` 并且查看节点当前具有了一个标签来验证它是否有效。也可以使用 `kubectl describe node "nodename"` 命令查看指定节点的标签完整列表。

- 然后，在Pod的定义中加上nodeSelector的设置

```yaml
# pods/pod-nginx.yaml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  nodeSelector:     # Node筛选
    disktype: ssd
```

如果我们给多个Node都定义了相同的标签，则scheduler会根据调度算法从这组Node中挑选一个可用的Node进行Pod调度。可以通过运行 `kubectl get pods -o wide` 并查看分配给 pod 的 “NODE” 来验证其是否有效。

除了个人附加的标签外，K8s还给节点预设了一组标准标签。这些标签是

- [kubernetes.io/arch](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#kubernetes-io-arch)
- [kubernetes.io/os](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#kubernetes-io-os)
- [beta.kubernetes.io/arch](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#beta-kubernetes-io-arch-deprecated) (deprecated)
- [beta.kubernetes.io/os](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#beta-kubernetes-io-os-deprecated) (deprecated)
- [kubernetes.io/hostname](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#kubernetes-io-hostname)
- [beta.kubernetes.io/instance-type](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#beta-kubernetes-io-instance-type-deprecated) (deprecated)
- [node.kubernetes.io/instance-type](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#nodekubernetesioinstance-type)
- [failure-domain.beta.kubernetes.io/region](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#failure-domainbetakubernetesioregion) (deprecated)
- [failure-domain.beta.kubernetes.io/zone](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#failure-domainbetakubernetesiozone) (deprecated)
- [topology.kubernetes.io/region](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#topologykubernetesioregion)
- [topology.kubernetes.io/zone](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/#topologykubernetesiozone)

 **注意：**这些标签的值是特定于云供应商的，因此不能保证可靠。例如，`kubernetes.io/hostname` 的值在某些环境中可能与节点名称相同，但在其他环境中可能是一个不同的值。

### NodeAffinity：Node亲和调度

NodeAffinity意为Node亲和性的调度策略，是用于替换NodeSelector的全新调度策略。目前有两种节点亲和性表达。

- **RequiredDuringSchedulingIgnoredDuringExecution**：必须满足指定的规则才可以调度Pod到Node上（*功能与nodeSelector很像，但是使用的是不同的语法*），相当于硬限制。
-  **PreferredDuringSchedulingIgnoredDuringExecution**：强调优先满足指定规则，调度器会尝试调度Pod到Node上，但并不强求，相当于软限制。多个优先级规则还可以设置权重（weight）值，以定义执行的先后顺序。

`IgnoredDuringExecution`的意思是：如果一个Pod所在的节点在Pod运行期间标签发生了变更，不再符合该Pod的节点亲和性需求，则系统将忽略Node上Label的变化，该Pod能继续在该节点运行。

```yaml
pods/pod-with-node-affinity.yaml 

apiVersion: v1
kind: Pod
metadata:
  name: with-node-affinity
spec:
  affinity:
    nodeAffinity:            # 使用nodeAffinity筛选节点
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:   # 此处只有一个nodeSelectorTerms与nodeAffinity关联
        - matchExpressions:  # 此处只有一个matchExpressions与nodeSelectorTerms关联
          - key: kubernetes.io/e2e-az-name
            operator: In
            values:
            - e2e-az1
            - e2e-az2
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 1             # 用于设定优先级的权重值
        preference:
          matchExpressions:
          - key: another-node-label-key
            operator: In
            values:
            - another-node-label-value
  containers:
  - name: with-node-affinity
    image: k8s.gcr.io/pause:2.0
```

如果同时指定了 `nodeSelector` 和 `nodeAffinity`，两者必须都要满足，才能将 pod 调度到候选节点上。

如果你指定了多个与 `nodeAffinity` 类型关联的 `nodeSelectorTerms`，则**其中一个** `nodeSelectorTerms` 满足，就可以将pod调度到节点上。

如果你指定了多个与 `nodeSelectorTerms` 关联的 `matchExpressions`，则**只有当所有** `matchExpressions` 满足，才会将pod调度到节点上。

### **PodAffinity**：**Pod**亲和与互斥调度

Node只有亲和的概念，但是Pod存在亲和与互斥（反亲和, anti-affinity）。

这种规则可以描述为：如果在具有标签A的Node上运行了一个或者多个符合条件B的Pod，那么Pod应该（如果是互斥，那么就拒绝）在这个Node上运行。

B 表示一个具有可选的关联命令空间列表的 LabelSelector；与节点不同，因为 pod 是命名空间限定的（因此 pod 上的标签也是命名空间限定的），所以作用于 pod 标签的标签选择器必须指定选择器应用在哪个命名空间。从概念上讲，A 是一个拓扑域，如节点，机架，云供应商地区，云供应商区域等。你可以使用 `topologyKey` 来表示它，`topologyKey` 是节点标签的键以便系统用来表示这样的拓扑域。

可以参考上面`NodeSelector：定向调度`中列举的标签。

**注意：**

- Pod 间亲和与反亲和需要大量的处理，这可能会显著减慢大规模集群中的调度。不建议在超过数百个节点的集群中使用它们。
- Pod 反亲和需要对节点进行一致的标记，即集群中的每个节点必须具有适当的标签能够匹配 `topologyKey`。如果某些或所有节点缺少指定的 `topologyKey` 标签，可能会导致意外行为。

例子

```yaml
# pods/pod-with-pod-affinity.yaml 
apiVersion: v1
kind: Pod
metadata:
  name: with-pod-affinity
spec:
  affinity:
    podAffinity:         # Pod亲和
      requiredDuringSchedulingIgnoredDuringExecution:
      - labelSelector:   # 使用labelSelector筛选节点
          matchExpressions:
          - key: security
            operator: In
            values:
            - S1
        # zone是地理区域意义上的，这里要求新的Pod与security=S1的Pod为同一个zone
        topologyKey: failure-domain.beta.kubernetes.io/zone
    podAntiAffinity:      # Pod互斥
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 100
        podAffinityTerm:
          labelSelector:
            matchExpressions:
            - key: security
              operator: In
              values:
              - S2
          # 这里希望新的Pod与security=S2的Pod处在不同的zone
          topologyKey: failure-domain.beta.kubernetes.io/zone
  containers:
  - name: with-pod-affinity
    image: k8s.gcr.io/pause:2.0
```

### Taints（污点）和 Tolerations（容忍）

Taints [teɪnt] v. / n.

节点亲和性（NodeAffinity），是pod的一种属性（偏好或硬性要求），它使pod被吸引到一类特定的节点。Taint 则相反，它**使节点能够拒绝特定的pod**。

**Taint 和 toleration 相互配合，可以用来避免 pod 被分配到不合适的节点上**。每个节点上都可以应用一个或多个 taint，这表示对于那些不能容忍这些 taint 的 pod，是不会被该节点接受的。如果将 toleration 应用于 pod 上，则表示这些 pod 可以（但不要求）被调度到具有匹配 taint 的节点上。即，Taints应用于节点，Tolerations应用于Pod，只要Pod可以容忍节点上的Taints，就可以调度过去。

个人认为，Taints、Tolerations的作用与NodeSelector、NodeAffinity类似，但是后两者是给节点定义了某种“正面”的标签，Pod根据标签定义的“亲近程度”进行判断，如果匹配则将Pod调度到相关Node上。而Taints、Tolerations相反，是给Node定义“反面”标签作为污点，Pod先确认是否可以容忍，只有Pod与Node相互匹配才可以完成Pod调度。

#### 在具体使用

可以使用命令 [kubectl taint](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#taint) 给节点增加一个 taint。例如：

```
kubectl taint nodes node1 key1=value1:NoSchedule
```

给节点 `node1` 增加一个 taint，它的 key 是 `key1`，value 是 `value1`，effect 是 `NoSchedule`。这表示只有拥有和这个 taint 相匹配的 toleration 的 pod 才能够被分配到 `node1` 这个节点。可以在 PodSpec 中定义 pod 的 toleration。下面两个 toleration 均与上面例子中使用 `kubectl taint` 命令创建的 taint 相匹配，因此如果一个 pod 拥有其中的任何一个 toleration 都能够被分配到 `node1` ：

想删除上述命令添加的 taint ，可以运行：

```
kubectl taint nodes node1 key1:NoSchedule-
```

可以在 PodSpec 中为容器设定容忍标签。以下两个容忍标签都与上面的 `kubectl taint` 创建的污点“匹配”， 因此具有任一容忍标签的Pod都可以将其调度到 `node1` 上：

```
tolerations:
- key: "key1"
  operator: "Equal"  # 这里要求key value和effect都相同
  value: "value1"
  effect: "NoSchedule"
```

```
tolerations:
- key: "key1"
  operator: "Exists"  # 这里要求key和effect相同即可
  effect: "NoSchedule"
```

一个 **toleration 和一个 taint 相匹配**是指它们有一样的 key 和 effect ，并且：

- 如果 `operator` 是 `Exists` （此时 toleration 不能指定 `value`），或者
- 如果 `operator` 是 `Equal` ，则它们的 `value` 应该相等

注意：存在两种特殊情况：

- 如果一个 toleration 的 `key` 为空且 operator 为 `Exists`，那么这个 toleration 与任意的 key 、value 和 effect 都匹配，即这个 toleration 能容忍任意 taint。

  ```yaml
  tolerations:
  - operator: "Exists"
  ```

- An empty `effect` matches all effects with key `key`.

  ```yaml
  tolerations:
  - key: "key"
  operator: "Exists"
  ```

#### 关于effect

`effect` 的值可以是 `NoSchedule, PreferNoSchedule, NoExecute`。

其中， `PreferNoSchedule`是“优化”或“软”版本的 `NoSchedule` ——系统会 *尽量* 避免将 pod 调度到存在其不能容忍 taint 的节点上，但这不是强制的。

`effect` 的值还可以设置为 `NoExecute`，此时的判断方式如下：

**一个节点可以添加多个 taint ，一个 pod 也可以添加多个 toleration**。Kubernetes 处理多个 taint 和 toleration 的过程就像一个过滤器：先遍历Node上的所有 taint ，忽略那些与 pod的toleration 相匹配的的 taint。余下未被忽略的 taint 的 effect 值决定了 pod 是否会被分配到该节点。特别是以下情况：

- 如果未被过滤的 taint 中存在**一个以上** effect 值为 `NoSchedule` 的 taint，则 Kubernetes 不会将 pod 分配到该节点。
- 如果未被过滤的 taint 中不存在 effect 值为 `NoSchedule` 的 taint，但是存在 effect 值为 `PreferNoSchedule` 的 taint，则 Kubernetes 会 *尝试* 将 pod 分配到该节点。
- 如果未被过滤的 taint 中存在**一个以上** effect 值为 `NoExecute` 的 taint，则 Kubernetes 不会将 pod 分配到该节点（如果 pod 还未在节点上运行），或者将 pod 从该节点驱逐（如果 pod 已经在节点上运行）。

总之，忽略调与toleration相匹配的taint ，对于剩下的taint ，取决于effect的值。

例如，假设您给一个节点添加了如下的 taint

```shell
kubectl taint nodes node1 key1=value1:NoSchedule
kubectl taint nodes node1 key1=value1:NoExecute
kubectl taint nodes node1 key2=value2:NoSchedule
```

然后存在一个 pod，它有两个 toleration：

```yaml
tolerations:
- key: "key1"           # 与key1=value1:NoSchedule匹配
  operator: "Equal"
  value: "value1"
  effect: "NoSchedule"
- key: "key1"           # 与key1=value1:NoExecute匹配
  operator: "Equal"
  value: "value1"
  effect: "NoExecute"
```

在这个例子中，上述 pod 不会被分配到上述节点，因为其没有 toleration 和第三个 taint 相匹配。但是如果在给节点添加上述 taint 之前，该 pod 已经在上述节点运行，那么它还可以继续运行在该节点上，因为第三个 taint 是三个 taint 中唯一不能被这个 pod 容忍的。

#### 应用

- **专用节点**：如果您想将某些节点专门分配给特定的一组用户使用，您可以给这些节点添加一个 taint（即， `kubectl taint nodes nodename dedicated=groupName:NoSchedule`），然后给这组用户的 pod 添加一个相对应的 toleration（通过编写一个自定义的 [admission controller](https://kubernetes.io/docs/admin/admission-controllers/)，很容易就能做到）。

- **配备了特殊硬件的节点**：在部分节点配备了特殊硬件（比如 GPU）的集群中，我们希望不需要这类硬件的 pod 不要被分配到这些特殊节点，以便为后继需要这类硬件的 pod 保留资源。要达到这个目的，可以先给配备了特殊硬件的节点添加 taint（例如 `kubectl taint nodes nodename special=true:NoSchedule` or `kubectl taint nodes nodename special=true:PreferNoSchedule`)，然后给使用了这类特殊硬件的 pod 添加一个相匹配的 toleration。

- **基于 taint 的驱逐**：这是在每个 pod 中配置的在节点出现问题时的驱逐行为，可以参考以下链接

  [基于 taint 的驱逐](https://kubernetes.io/docs/concepts/configuration/taint-and-toleration/#%E5%9F%BA%E4%BA%8E-taint-%E7%9A%84%E9%A9%B1%E9%80%90) 

- **基于节点状态添加 taint**：Node 生命周期控制器会自动创建与 Node 条件相对应的带有 `NoSchedule` 效应的污点。 同样，调度器不检查节点条件，而是检查节点污点。这确保了节点条件不会影响调度到节点上的内容。用户可以通过添加适当的 Pod 容忍度来选择忽略某些 Node 的问题(表示为 Node 的调度条件)。

  自 K8s 1.8 起， DaemonSet 控制器自动为所有守护进程添加如下 `NoSchedule` toleration 以防 DaemonSet 崩溃：

  - `node.kubernetes.io/memory-pressure`
  - `node.kubernetes.io/disk-pressure`
  - `node.kubernetes.io/out-of-disk` (*只适合 critical pod*)
  - `node.kubernetes.io/unschedulable` (1.10 或更高版本)
  - `node.kubernetes.io/network-unavailable` (*只适合 host network*)

### Pod Priority Preemption：优先级抢占

preemption [ˌpriːˈempʃn] 抢占; 先发制人; 先占; 先买权; 优先

更多相关内容参见：[Pod Priority and Preemption - Kubernetes](https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/)

[Pods](https://kubernetes.io/docs/user-guide/pods) can have *priority*. **Priority indicates the importance of a Pod relative to other Pods**. If a Pod cannot be scheduled, the scheduler tries to preempt (evict) lower priority Pods to make scheduling of the pending Pod possible.

注意: In a cluster where not all users are trusted, a malicious user could create Pods at the highest possible priorities, causing other Pods to be evicted/not get scheduled. An administrator can use `ResourceQuota` to prevent users from creating pods at high priorities. 【防止恶意用户创建高优先级的Pod】

#### How to use priority and preemption

1. Add one or more [PriorityClasses](https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/#priorityclass).
2. Create Pods with[`priorityClassName`](https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/#pod-priority) set to one of the added PriorityClasses. Of course you do not need to create the Pods directly; normally you would add `priorityClassName` to the Pod template of a collection object like a Deployment. 在Pod模板中添加`priorityClassName` 即可

例子

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
  env: test
spec:
  containers:
  - name: nginx
    image: nginx 
    imagePullPolicy: IfNotPresent
  priorityClassName: high-priority
```

如果发生了需要抢占的调度，高优先级Pod就可能抢占节点N，并将其低优先级Pod驱逐出节点N，高优先级Pod的status信息中的nominatedNodeName字段会记录目标节点N的名称。

其次，高优先级Pod仍然无法保证最终被调度到节点N上，在节点N上低优先级Pod被驱逐的过程中，如果有新的节点满足高优先级Pod的需求，就会把它调度到新的Node上。而如果在等待低优先级的Pod退出的过程中，又出现了优先级更高的Pod，调度器将会调度这个更高优先级的Pod到节点N上，并重新调度之前等待的高优先级Pod。【抢占过程中出现了更高级的Pod，则优先调度这个Pod】

最后，使用优先级抢占的调度策略可能会导致某些Pod永远无法被成功调度。因此优先级调度不但增加了系统的复杂性，还可能带来额外不稳定的因素。因此，**一旦发生资源紧张的局面，应优先要考虑的是集群扩容，如果无法扩容，则再考虑有监管的优先级调度特性**，比如结合基于Namespace的资源配额限制来约束任意优先级抢占行为。

#### How to disable preemption

Preemption is controlled by a kube-scheduler flag `disablePreemption`, which is set to `false` by default. If you want to disable preemption despite the above note, you can set `disablePreemption` to `true`.

This option is available in component configs only and is not available in old-style command line options. Below is a sample component config to disable preemption:

```yaml
apiVersion: kubescheduler.config.k8s.io/v1alpha1
kind: KubeSchedulerConfiguration
algorithmSource:
  provider: DefaultProvider
...
disablePreemption: true  # 屏蔽preemption
```

#### PriorityClass

A PriorityClass is a non-namespaced object that **defines a mapping from a priority class name to the integer value of the priority**【定义了priority class name到优先级整数之间的映射】. The name is specified in the `name` field of the PriorityClass object’s metadata. The value is specified in the required `value` field. The higher the value, the higher the priority. The name of a PriorityClass object must be a valid [DNS subdomain name](https://kubernetes.io/docs/concepts/overview/working-with-objects/names#dns-subdomain-names), and it cannot be prefixed with `system-`.

A PriorityClass object can have any 32-bit integer value smaller than or equal to 1 billion. Larger numbers are reserved for critical system Pods that should not normally be preempted or evicted. A cluster admin should create one PriorityClass object for each such mapping that they want.

PriorityClass also has two optional fields: `globalDefault` and `description`. The `globalDefault` field indicates that the value of this PriorityClass should be used for Pods without a `priorityClassName`. **Only one PriorityClass with `globalDefault` set to true can exist in the system**. If there is no PriorityClass with `globalDefault` set, the priority of Pods with no `priorityClassName` is zero.

The `description` field is an arbitrary string. It is meant to tell users of the cluster when they should use this PriorityClass. 【可选字段`description` 说明何时使用PriorityClass】

例子

```yaml
apiVersion: scheduling.k8s.io/v1
kind: PriorityClass
metadata:
  name: high-priority
value: 1000000
globalDefault: false 
description: "This priority class should be used for XYZ service pods only."
```

#### Non-preempting PriorityClass

Pods with `PreemptionPolicy: Never` will be placed in the scheduling queue ahead of lower-priority pods, but they cannot preempt other pods. A non-preempting pod waiting to be scheduled will stay in the scheduling queue, until sufficient resources are free, and it can be scheduled. Non-preempting pods, like other pods, are subject to scheduler back-off. This means that if the scheduler tries these pods and they cannot be scheduled, they will be retried with lower frequency, allowing other pods with lower priority to be scheduled before them. 【`PreemptionPolicy: Never`的Pod会在低优先级的Pod前，但不会抢占，而是在队列中等待资源充足时被调度】

Non-preempting pods may still be preempted by other, high-priority pods. 【但仍然会被其他Pod抢占】

`PreemptionPolicy` defaults to `PreemptLowerPriority`, which will allow pods of that PriorityClass to preempt lower-priority pods (as is existing default behavior). If `PreemptionPolicy` is set to `Never`, pods in that PriorityClass will be non-preempting. 【字段的默认值为`PreemptLowerPriority`】

The use of the `PreemptionPolicy` field requires the `NonPreemptingPriority` [feature gate](https://kubernetes.io/docs/reference/command-line-tools-reference/feature-gates/) to be enabled.

An example use case is for data science workloads. A user may submit a job that they want to be prioritized above other workloads, but do not wish to discard existing work by preempting running pods. The high priority job with `PreemptionPolicy: Never` will be scheduled ahead of other queued pods, as soon as sufficient cluster resources “naturally” become free.

例子

```yaml
apiVersion: scheduling.k8s.io/v1
kind: PriorityClass
metadata:
  name: high-priority-nonpreempting
value: 1000000
preemptionPolicy: Never  # 此时Pod是Non-preempting
globalDefault: false
description: "This priority class will not cause other pods to be preempted."
```

### DaemonSet：在每个Node上都调度一个Pod

官方文档：[DaemonSet - Kubernetes](https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/)

DaemonSet是K8s 1.2版本新增的一种资源对象，用于管理在集群中每个Node上仅运行一份Pod的副本实例。

*DaemonSet* 确保全部（或者某些）节点上运行一个 Pod 的副本。当有节点加入集群时， 也会为他们新增一个 Pod 。当有节点从集群移除时，这些 Pod 也会被回收。删除 DaemonSet 将会删除它创建的所有 Pod。

DaemonSet 的一些典型用法：

- 在每个节点上运行**集群存储** DaemonSet，例如 `glusterd`、`ceph`。
- 在每个节点上运行**日志收集** DaemonSet，例如 `fluentd`、`logstash`。
- 在每个节点上运行**监控** DaemonSet，例如 [Prometheus Node Exporter](https://github.com/prometheus/node_exporter)、[Flowmill](https://github.com/Flowmill/flowmill-k8s/)、[Sysdig 代理](https://docs.sysdig.com/)、`collectd`、[Dynatrace OneAgent](https://www.dynatrace.com/technologies/kubernetes-monitoring/)、[AppDynamics 代理](https://docs.appdynamics.com/display/CLOUD/Container+Visibility+with+Kubernetes)、[Datadog 代理](https://docs.datadoghq.com/agent/kubernetes/daemonset_setup/)、[New Relic 代理](https://docs.newrelic.com/docs/integrations/kubernetes-integration/installation/kubernetes-installation-configuration)、Ganglia `gmond` 或者 [Instana 代理](https://www.instana.com/supported-integrations/kubernetes-monitoring/)。

一个简单的用法是在所有的节点上都启动一个 DaemonSet，将被作为每种类型的 daemon 使用。

一个稍微复杂的用法是单独对每种 daemon 类型使用多个 DaemonSet，但具有不同的标志， 并且对不同硬件类型具有不同的内存、CPU 要求。

DaemonSet的Pod调度策略与RC类似，除了使用系统内置的算法在每个Node上进行调度，也可以在Pod的定义中使用NodeSelector或NodeAffinity来指定满足条件的Node范围进行调度。

#### 创建DaemonSet

先在编写yaml文件，和其它所有 Kubernetes 配置一样，DaemonSet 需要 `apiVersion`、`kind` 和 `metadata` 字段。有关配置文件的基本信息，详见文档 [部署应用](https://kubernetes.io/docs/user-guide/deploying-applications/)、[配置容器](https://kubernetes.io/docs/tasks/) 和 [使用 kubectl 进行对象管理](https://kubernetes.io/docs/concepts/overview/object-management-kubectl/overview/)。

例子：

```yaml
# controllers/daemonset.yaml 
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: fluentd-elasticsearch
  namespace: kube-system
  labels:
    k8s-app: fluentd-logging
spec:
  selector:
    matchLabels:
      name: fluentd-elasticsearch
  template:
    metadata:
      labels:
        name: fluentd-elasticsearch
    spec:
      tolerations:
      # this toleration is to have the daemonset runnable on master nodes
      # remove it if your masters can't run pods
      - key: node-role.kubernetes.io/master
        effect: NoSchedule
      containers:
      - name: fluentd-elasticsearch
        image: quay.io/fluentd_elasticsearch/fluentd:v2.5.2
        resources:
          limits:           # 该资源的最小申请量，系统必须满足要求
            memory: 200Mi
          requests:         # 该资源最大允许使用的量，不能被突破
            cpu: 100m
            memory: 200Mi
        volumeMounts:       # 将DaemonSet的数据卷挂载到容器中
        - name: varlog
          mountPath: /var/log
        - name: varlibdockercontainers
          mountPath: /var/lib/docker/containers
          readOnly: true
      terminationGracePeriodSeconds: 30
      volumes:               # 属于DaemonSet的数据卷
      - name: varlog
        hostPath:
          path: /var/log
      - name: varlibdockercontainers
        hostPath:
          path: /var/lib/docker/containers
```

然后，基于 YAML 文件创建 DaemonSet:

```
kubectl apply -f https://k8s.io/examples/controllers/daemonset.yaml
```

**Pod 模板**

`.spec` 中唯一必需的字段是 `.spec.template`。

`.spec.template` 是一个 [Pod 模板](https://kubernetes.io/docs/concepts/workloads/pods/pod-overview/#pod-templates)。除了它是嵌套的，并且不具有 `apiVersion` 或 `kind` 字段，它与 [Pod](https://kubernetes.io/docs/concepts/workloads/pods/pod/) 具有相同的 schema。

除了 Pod 必需字段外，在 DaemonSet 中的 Pod 模板必须指定合理的标签（查看 [Pod Selector](https://kubernetes.io/zh/docs/concepts/workloads/controllers/daemonset/#pod-selector)）。

在 DaemonSet 中的 Pod 模板必须具有一个值为 `Always` 的 [`RestartPolicy`](https://kubernetes.io/docs/user-guide/pod-states)，或者未指定它的值，默认是 `Always`。

**Pod Selector**

`.spec.selector` 字段表示 Pod Selector，它与 [Job](https://kubernetes.io/docs/concepts/jobs/run-to-completion-finite-workloads/) 的 `.spec.selector` 的作用是相同的。

从 K8s 1.8 开始，必须指定与 `.spec.template` 的标签匹配的 pod selector。当不配置时，pod selector 将不再有默认值。selector 默认与 `kubectl apply` 不兼容。 此外，一旦创建了 DaemonSet，它的 `.spec.selector` 就不能修改。修改 pod selector 可能导致 Pod 意外悬浮，并且这对用户来说是困惑的。

`spec.selector` 表示一个对象，它由如下两个字段组成：

- `matchLabels` - 与 [ReplicationController](https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller/) 的 `.spec.selector` 的作用相同。
- `matchExpressions` - 允许构建更加复杂的 Selector，可以通过指定 key、value 列表 ，以及与 key 和 value 列表相关的操作符。

当上述两个字段都指定时，结果表示的是 AND 关系。

如果指定了 `.spec.selector`，必须与 `.spec.template.metadata.labels` 相匹配。如果与它们配置的不匹配，则会被 API 拒绝。

另外，通常不应直接通过另一个 DaemonSet 或另一个工作负载资源（例如 ReplicaSet）来创建其标签与该选择器匹配的任何 Pod。否则，DaemonSet [控制器](https://kubernetes.io/docs/admin/kube-controller-manager/)会认为这些 Pod 是由它创建的。Kubernetes 不会阻止你这样做。您可能要执行此操作的一种情况是，手动在节点上创建具有不同值的 Pod 进行测试。

#### 如何调度 Daemon Pods

DaemonSet 确保所有符合条件的节点都运行该 Pod 的一个副本。通常，运行 Pod 的节点由 Kubernetes 调度器抉择。不过，DaemonSet pods 由 DaemonSet 控制器创建和调度。这将引入以下问题：

- Pod 行为的不一致性：等待调度的正常 Pod 已被创建并处于 `Pending` 状态，但 DaemonSet pods 未在 `Pending` 状态下创建。 这使用户感到困惑。
- [Pod preemption](https://kubernetes.io/docs/concepts/configuration/pod-priority-preemption/)由默认 scheduler 处理。 启用抢占后，DaemonSet 控制器将在不考虑 pod 优先级和抢占的情况下制定调度决策。

`ScheduleDaemonSetPods` 允许个人使用默认调度器，而不是 DaemonSet 控制器来调度 DaemonSets。方法是将 `NodeAffinity` 添加到 DaemonSet pods，而不是 `.spec.nodeName`。 然后使用默认调度器将 pod 绑定到目标主机。 如果 DaemonSet pod 的亲和节点已存在，则替换它。 DaemonSet 控制器仅在创建或修改 DaemonSet pods 时执行这些操作，并且不对 DaemonSet 的 `spec.template` 进行任何更改。例如：

```yaml
nodeAffinity:
  requiredDuringSchedulingIgnoredDuringExecution:
    nodeSelectorTerms:
    - matchFields:
      - key: metadata.name
        operator: In
        values:
        - target-host-name
```

**污点和容忍度**

尽管Daemon Pods遵循 [污点和容忍度](https://kubernetes.io/docs/concepts/configuration/taint-and-toleration) 规则，根据相关特性，会自动将以下容忍度添加到 DaemonSet Pods 中。

| 容忍度关键词                             | 影响       | 版本  | 描述                                                         |
| :--------------------------------------- | :--------- | :---- | :----------------------------------------------------------- |
| `node.kubernetes.io/not-ready`           | NoExecute  | 1.13+ | DaemonSet pods will not be evicted when there are node problems such as a network partition. |
| `node.kubernetes.io/unreachable`         | NoExecute  | 1.13+ | DaemonSet pods will not be evicted when there are node problems such as a network partition. |
| `node.kubernetes.io/disk-pressure`       | NoSchedule | 1.8+  |                                                              |
| `node.kubernetes.io/memory-pressure`     | NoSchedule | 1.8+  |                                                              |
| `node.kubernetes.io/unschedulable`       | NoSchedule | 1.12+ | DaemonSet pods tolerate unschedulable attributes by default scheduler. |
| `node.kubernetes.io/network-unavailable` | NoSchedule | 1.12+ | DaemonSet pods, who uses host network, tolerate network-unavailable attributes by default scheduler. |

#### 与 Daemon Pods 通信

与 DaemonSet 中的 Pod 进行通信的几种可能模式如下：

- **Push**：将 DaemonSet 中的 Pod 配置为将更新发送到另一个 Service，例如统计数据库。
- **NodeIP 和已知端口**：DaemonSet 中的 Pod 可以使用 `hostPort`，从而可以通过节点 IP 访问到 Pod。客户端能通过某种方法获取节点 IP 列表，并且基于此也可以获取到相应的端口。
- **DNS**：创建具有相同 Pod Selector 的 [Headless Service](https://kubernetes.io/docs/concepts/services-networking/service/#headless-services)，然后通过使用 `endpoints` 资源或从 DNS 中检索到多个 A 记录来发现 DaemonSet。
- **Service**：创建具有相同 Pod Selector 的 Service，并使用该 Service 随机访问到某个节点上的 daemon（没有办法访问到特定节点）。

#### 更新 DaemonSet

如果修改了节点标签，DaemonSet 将立刻向新匹配上的节点添加 Pod，同时删除不能够匹配的节点上的 Pod。

个人可以修改 DaemonSet 创建的 Pod，但是不允许对 Pod 的所有字段进行更新。并且当下次 节点（即使具有相同的名称）被创建时，DaemonSet Controller 将继续使用最初的模板。

可以删除一个 DaemonSet。如果使用 `kubectl` 并指定 `--cascade=false` 选项，则 Pod 将被保留在节点上。然后可以创建具有不同模板的新 DaemonSet。具有不同模板的新 DaemonSet 将能够通过标签匹配并识别所有已经存在的 Pod。 如果有任何 Pod 需要替换，则 DaemonSet 根据它的 `updateStrategy` 来替换。

#### DaemonSet 的可替代选择

init 脚本、裸Pod、静态Pod、Deployments

### Job：批处理调度

K8s从1.2版本开始支持批处理类型的应用。可以通过Kubernetes Job资源对象来定义并启动一个批处理任务。

批处理任务通常并行（或者串行）启动多个计算进程去处理一批工作项（work item），处理完成后，整个批处理任务结束。按照批处理任务实现方式的不同，批处理任务可以分为以下几种模式（建议用后两种）：

- `Job Template Expansion`模式：**一个Job对象对应一个待处理的Work item，有几个Work item就产生几个独立的Job**，通常适合Work item数量少、每个Work item要处理的数据量比较大的场景，比如有一个100GB的文件作为一个Work item，总共有10个文件需要处理。

  由于在这种模式下每个Work item对应一个Job实例，所以这种模式首先定义一个Job模板，模板里的主要参数是Work item的标识，因为每个Job都处理不同的Work item。

  例：

  先定义模板

  ```yaml
  # application/job/job-tmpl.yaml 
  
  apiVersion: batch/v1
  kind: Job
  metadata:
    name: process-item-$ITEM   # The $ITEM syntax is not meaningful to K8s
    labels:
      jobgroup: jobexample
  spec:
    template:
      metadata:
        name: jobexample
        labels:
          jobgroup: jobexample
      spec:
        containers:
        - name: c
          image: busybox
          command: ["sh", "-c", "echo Processing item $ITEM && sleep 5"]
        restartPolicy: Never
  ```

  通过下面的操作，生成了3个对应的Job定义文件并创建Job

  ```
  # Expand the template into multiple files, one for each item to be processed.
  mkdir ./jobs
  for i in apple banana cherry
  do
    cat job-tmpl.yaml | sed "s/\$ITEM/$i/" > ./jobs/job-$i.yaml
  done
  ```

  

- `Queue with Pod Per Work Item`模式：**用一个任务队列存放Work item，一个Job对象作为消费者去完成这些Work item**，在这种模式下，**Job会启动N个Pod**，每个Pod都对应一个Work item。

  在这种模式下需要一个任务队列存放Work item，比如RabbitMQ，客户端程序先把要处理的任务变成Work item放入任务队列，然后编写Worker程序、打包镜像并定义成为Job中的Work Pod。Worker程序的实现逻辑是从任务队列中拉取一个Work item并处理，在处理完成后即结束进程。

- `Queue with Variable Pod Count`模式：**也是用一个任务队列存放Work item**，一个**Job对象作为消费者**去完成这些Work item，但与上面的模式不同，**Job启动的Pod数量是可变的**。

  这种模式下，Worker程序需要知道队列中是否还有等待处理的Work item，如果有就取出来处理，否则就认为所有工作完成并结束进程，所以任务队列通常要采用Redis或者数据库来实现。

还有一种被称为`Single Job with Static Work Assignment`的模式，也是一个Job产生多个Pod，但它采用程序静态方式分配任务项，而不是采用队列模式进行动态分配。

|                           Pattern                            | Single Job object<br>是否是一个Job | Fewer pods than work items?<br>Pod数量是否小于Work items数量 | Use app unmodified?<br>用户程序是否需要修改 | Works in Kube 1.1? |
| :----------------------------------------------------------: | :--------------------------------: | :----------------------------------------------------------: | :-----------------------------------------: | :----------------: |
| [Job Template Expansion](https://kubernetes.io/docs/tasks/job/parallel-processing-expansion/) |                                    |                                                              |                      ✓                      |         ✓          |
| [Queue with Pod Per Work Item](https://kubernetes.io/docs/tasks/job/coarse-parallel-processing-work-queue/) |                 ✓                  |                                                              |                  sometimes                  |         ✓          |
| [Queue with Variable Pod Count](https://kubernetes.io/docs/tasks/job/fine-parallel-processing-work-queue/) |                 ✓                  |                              ✓                               |                                             |         ✓          |
|            Single Job with Static Work Assignment            |                 ✓                  |                                                              |                      ✓                      |                    |

考虑到批处理的并行问题，Kubernetes将Job分以下几种类型：

**a) Non-parallel Jobs**
通常一个Job只启动一个Pod，除非Pod异常，才会重启该Pod，一旦此Pod正常结束，Job将结束。

**b) Parallel Jobs with a fixed completion count**
并行Job会启动多个Pod，此时需要设定Job的`.spec.completions`参数为一个正数，当正常结束的Pod数量达至此参数设定的值后，Job结束。此外，Job的`.spec.parallelism`参数用来控制并行度，即同时启动几个Job来处理Work Item。

**c) Parallel Jobs with a work queue**
任务队列方式的并行Job需要一个独立的Queue，Work item都在一个Queue中存放，不能设置Job的`.spec.completions`参数，此时Job有以下特性。
◎ 每个Pod都能独立判断和决定是否还有任务项需要处理。
◎ 如果某个Pod正常结束，则Job不会再启动新的Pod。
◎ 如果一个Pod成功结束，则此时应该不存在其他Pod还在工作的情况，它们应该都处于即将结束、退出的状态。
◎ 如果所有Pod都结束了，且至少有一个Pod成功结束，则整个Job成功结束

### Cronjob：定时任务

**FEATURE STATE:** `Kubernetes v1.8 [beta]`

*Cron Job* 创建基于时间调度的 [Jobs](https://kubernetes.io/docs/concepts/workloads/controllers/jobs-run-to-completion/)。

定时任务Cron Job类似于Linux Cron（cron是一个linux下的定时执行工具，相当于windows下的scheduled task，可以在无需人工干预的情况下定时地运行任务task）

一个 CronJob 对象就像 *crontab* (cron table) 文件中的一行。它用 [Cron](https://en.wikipedia.org/wiki/Cron) 格式进行编写，并周期性地在给定的调度时间执行 Job。

**注意：** 所有 **CronJob** 的 `schedule:` 时间都使用 UTC 时间表示。

When creating the manifest for a CronJob resource, make sure the name you provide is a valid [DNS subdomain name](https://kubernetes.io/docs/concepts/overview/working-with-objects/names#dns-subdomain-names). **The name must be no longer than 52 characters**. This is because the CronJob controller will automatically append 11 characters to the job name provided and there is a constraint that the maximum length of a Job name is no more than 63 characters. 【名称程度不能超过52个字符】

CronJobs are useful for creating periodic and recurring tasks, like running backups or sending emails. CronJobs can also schedule individual tasks for a specific time, such as scheduling a Job for when your cluster is likely to be idle (闲置的).

例子

```yaml
# application/job/cronjob.yaml 
# prints the current time and a hello message every minute

apiVersion: batch/v1beta1
kind: CronJob
metadata:
  name: hello
spec:
  schedule: "*/1 * * * *"
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: busybox
            args:
            - /bin/sh
            - -c
            - date; echo Hello from the Kubernetes cluster
          restartPolicy: OnFailure
```







### 自定义调度器





## 8 **Init Container** —— 初始化容器





## 9 Pod的升级与回滚





## 10 Pod的扩容与缩容







