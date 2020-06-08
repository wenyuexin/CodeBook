# K8s对象——Service

**官方文档**：[Service](https://kubernetes.io/docs/concepts/services-networking/service/)

An abstract way to expose an application running on a set of Pods as a network service.

通过创建Service，可以为一组具有相同功能的容器应用提供一个统一的入口地址，并且将请求负载分发到后端的各个容器应用上。【同一种应用可能同时运行在多个Pod上，而用户通过网络使用时并不关系具体使用的是哪个Pod上的应用，只需要一个Service提供统一的入口即可】

直接通过Pod的IP地址和端口号可以访问到容器应用内的服务，但是Pod的IP地址是不可靠的，例如当Pod所在的Node发生故障时，Pod将被Kubernetes重新调度到另一个Node，Pod的IP地址将发生变化。更重要的是，如果容器应用本身是分布式的部署方式，通过多个实例共同提供服务，就需要在这些实例的前端设置一个负载均衡器来实现请求的分发。Kubernetes中的Service就是用于解决这些问题的核心组件。

[TOC]

## 1 Service资源

Kubernetes `Service` 定义了这样一种抽象：这种抽象定义了逻辑上的一组 `Pod`，以及一种可以访问它们的策略 —— 有时候这种模式也被称为微服务。 这组被`Service` 针对的 `Pod` 通常是由 [selector](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/) 确定的。

例如，考虑一个图片处理 backend，它运行了3个副本。这些副本是可互换的 —— frontend 不需要关心它们调用了哪个 backend 副本。 然而组成这一组 backend 程序的 `Pod` 实际上可能会发生变化，frontend 客户端不应该也没必要知道，而且也不需要跟踪这一组 backend 的状态。 `Service` 定义的抽象能够解耦这种关联。

**云原生服务发现**

如果您想要在应用程序中使用 Kubernetes 接口进行服务发现，则可以查询 API server 的 endpoint 资源，只要服务中的Pod集合发生更改，端点就会更新。对于非本机应用程序，Kubernetes提供了在应用程序和后端Pod之间放置网络端口或负载均衡器的方法。

## 2 定义

一个 `Service` 在 Kubernetes 中是一个 REST 对象，和 `Pod` 类似。 像所有的 REST 对象一样， `Service` 定义可以基于 `POST` 方式，请求 API server 创建新的实例。

例如，假定有一组 `Pod`，它们对外暴露了 9376 端口，同时还被打上 `app=MyApp` 标签。如下：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: MyApp
  ports:
    - protocol: TCP     # 会将请求代理到使用TCP端口9376，并且具有标签"app=MyApp"的Pod上
      port: 80
      targetPort: 9376  # port被映射到targetPort上
```

Kubernetes 为该服务分配一个 IP 地址（ClusterIP，集群IP），该 IP 地址由服务代理使用。通过Service的IP和端口号访问时，访问将被自动分发到后端的某个Pod上。

需要注意的是， `Service` 能够将一个接收 `port` 映射到任意的 `targetPort`。 默认情况下，`targetPort` 将被设置为与 `port` 字段相同的值。

服务的默认协议是TCP。由于许多服务需要公开多个端口，因此 Kubernetes 在服务对象上支持多个端口定义。 每个端口定义可以具有相同的 `protocol`，也可以具有不同的协议。

#### 没有selector的Service

服务最常见的是抽象化对 Kubernetes Pod 的访问，但是它们也可以抽象化其他种类的后端。 实例:

- 希望在生产环境中使用外部的数据库集群，但测试环境使用自己的数据库。
- 希望服务指向另一个命名空间中或其它集群中的服务。
- 您正在将工作负载迁移到 Kubernetes。 在评估该方法时，您仅在 Kubernetes 中运行一部分后端。

上述场景都能够定义没有 selector 的 `Service`。 例:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  ports:
    - protocol: TCP
      port: 80
      targetPort: 9376
```

由于此服务没有选择器，因此 *不会* 自动创建相应的 Endpoint 对象。 您可以通过手动添加 Endpoint 对象，将服务手动映射到运行该服务的网络地址和端口：

```yaml
apiVersion: v1
kind: Endpoints
metadata:
  name: my-service
subsets:
  - addresses:
      - ip: 192.0.2.42
    ports:
      - port: 9376
```

访问没有 selector 的 `Service`，与有 selector 的 `Service` 的原理相同。 请求将被路由到用户定义的 Endpoint， 上面这个YAML中为 `192.0.2.42:9376` (TCP)。

#### Endpoint 切片

EndpointSlices 是一种 API 资源，可以为 Endpoint 提供更可扩展的替代方案。 尽管从概念上讲与 Endpoint 非常相似，但 Endpoint 切片允许跨多个资源分布网络端点。 默认情况下，一旦到达100个 Endpoint，该 Endpoint 切片将被视为“已满”，届时将创建其他 Endpoint 切片来存储任何其他 Endpoint。

Endpoint 切片提供了附加的属性和功能，这些属性和功能在 [EndpointSlices](https://kubernetes.io/docs/concepts/services-networking/endpoint-slices/) 中进行了详细描述。

## 3 Virtual IPs 和 Service 代理

在 Kubernetes 集群中，每个 Node 运行一个 `kube-proxy` 进程。`kube-proxy` 负责为 `Service` 实现了一种 **虚拟 IP** 的形式，而不是 [`ExternalName`](https://kubernetes.io/zh/docs/concepts/services-networking/service/#externalname) 的形式。

### 为什么不使用 DNS 轮询（Round-robin DNS）

为什么 Kubernetes 依赖代理将入站流量转发到后端，而不是依靠DNS 轮询，使用服务代理有以下几个原因：

- DNS 实现的历史由来已久，它不遵守记录 TTL，并且在名称查找结果到期后对其进行缓存。
- 有些应用程序仅执行一次 DNS 查找，并无限期地缓存结果。
- 即使应用和库进行了适当的重新解析，DNS 记录上的 TTL 值低或为零也可能会给 DNS 带来高负载，从而使管理变得困难。

注1：[Round-robin scheduling - wikipedia](https://en.wikipedia.org/wiki/Round-robin_scheduling)

注2：DNS，是一种分布式网络目录服务，主要用于域名与 IP 地址的相互转换，以及控制因特网的电子邮件的发送。

### 代理模式

Kubernetes v1.1添加了 iptables 模式代理，在 Kubernetes v1.2 中，kube-proxy 的 **iptables** 模式成为默认设置。 Kubernetes v1.8添加了 ipvs 代理模式。【具体内容下面就不过多展开了】

#### userspace 代理模式

这种模式，kube-proxy 会监视 Kubernetes master 对 `Service` 对象和 `Endpoints` 对象的添加和移除。 对每个 `Service`，它会在本地 Node 上打开一个端口（随机选择）。 任何连接到“代理端口”的请求，都会被代理到 `Service` 的backend `Pods` 中的某个上面（就像通过 `Endpoints` 报告的一样）。 使用哪个 backend `Pod`，是 kube-proxy 基于 `SessionAffinity` 来确定的。【其他略，见官方文档】

<img src="https://d33wubrfki0l68.cloudfront.net/e351b830334b8622a700a8da6568cb081c464a9b/13020/images/docs/services-userspace-overview.svg" alt="userspace" style="zoom:60%;" />

注：

Kubernetes API Server（kube-apiserver）：提供了HTTP Rest接口的关键服务进程，是Kubernetes里所有资源的增、删、改、查等操作的唯一入口，也是集群控制的入口进程）

#### iptables 代理模式

这种模式，kube-proxy 会监视 用于添加或移除 `Service` 对象和 `Endpoints` 对象的 Kubernetes 控制面。 对每个 `Service`，它会配置 iptables 规则，从而捕获到达该 `Service` 的 `clusterIP` 和端口 的请求，进而将请求重定向到 `Service` 的一组 backend 中的某个上面。 对于每个 `Endpoints` 对象，它也会配置 iptables 规则，这个规则会选择一个 backend 组合。

默认的策略是，kube-proxy 在 iptables 模式下随机选择一个 backend。

使用 iptables 处理流量具有较低的系统开销，因为流量由 Linux netfilter 处理，而无需在用户空间和内核空间之间切换。 这种方法也可能更可靠。【注：[Linux netfilter - 博客](https://www.cnblogs.com/liuhongru/p/11428095.html)】

如果 kube-proxy 在 iptables 模式下运行，并且所选的第一个 Pod 没有响应，则连接失败。 这与用户空间模式不同：在这种情况下，kube-proxy 将检测到与第一个 Pod 的连接已失败，并会自动使用其他后端 Pod 重试。

您可以使用 Pod [readiness 探测器](https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle/#container-probes) 验证后端 Pod 可以正常工作，以便 iptables 模式下的 kube-proxy 仅看到测试正常的后端。 这样做意味着您避免将流量通过 kube-proxy 发送到已知已失败的Pod。

<img src="https://d33wubrfki0l68.cloudfront.net/27b2978647a8d7bdc2a96b213f0c0d3242ef9ce0/e8c9b/images/docs/services-iptables-overview.svg" alt="iptables" style="zoom:60%;" />

#### IPVS 代理模式

FEATURE STATE: Kubernetes v1.11 [stable]

在 `ipvs` 模式下，kube-proxy监视Kubernetes服务和端点，调用 `netlink` 接口相应地创建 IPVS 规则， 并定期将 IPVS 规则与 Kubernetes 服务和端点同步。 该控制循环可确保 IPVS 状态与所需状态匹配。 访问服务时，IPVS 将流量定向到后端Pod之一。

IPVS代理模式基于类似于 iptables 模式的 netfilter hook函数，但是使用哈希表作为基础数据结构，并且在内核空间中工作。 这意味着，与 iptables 模式下的 kube-proxy 相比，IPVS 模式下的 kube-proxy 重定向通信的延迟要短，并且在同步代理规则时具有更好的性能。与其他代理模式相比，IPVS 模式还支持更高的网络流量吞吐量。

IPVS提供了更多选项来平衡后端Pod的流量：

- `rr`: round-robin
- `lc`: least connection (smallest number of open connections)
- `dh`: destination hashing
- `sh`: source hashing
- `sed`: shortest expected delay
- `nq`: never queue

<img src="https://d33wubrfki0l68.cloudfront.net/2d3d2b521cf7f9ff83238218dac1c019c270b1ed/9ac5c/images/docs/services-ipvs-overview.svg" alt="ipvs " style="zoom:60%;" />

## 4 多端口 Service

Kubernetes允许在Service对象上配置多个端口定义。 为服务使用多个端口时，必须提供所有端口名称，保证无歧义。 例如：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: MyApp
  ports:
    - name: http   # 端口名称只能包含小写字母、数字字符和'-'，还必须以字母数字字符开头和结尾
      protocol: TCP
      port: 80
      targetPort: 9376
    - name: https
      protocol: TCP
      port: 443
      targetPort: 9377
```

## 5 自定义的IP

在 `Service` 创建的请求中，可以通过设置 `spec.clusterIP` 字段来指定自己的**集群 IP 地址**。 比如，希望替换一个已经已存在的 DNS 条目，或者遗留系统已经配置了一个固定的 IP 且很难重新配置。

用户选择的 IP 地址必须合法的 IPv4 或者 IPv6 地址，并且这个 IP 地址在 `service-cluster-ip-range` CIDR 范围内。如果创建无效的IP，那么API Server会返回值为 422 的HTTP状态码。

【注：CIDR，无类别域间路由，Classless Inter-Domain Routing，[IP地址与CIDR - 博客](https://www.iteye.com/blog/uule-2102484)】

## 6 服务发现

Kubernetes 支持2种基本的服务发现模式 —— 环境变量和 DNS。

#### 环境变量

当 `Pod` 运行在 `Node` 上，kubelet 会为每个活跃的 `Service` 添加一组环境变量。 它同时支持 [Docker links兼容](https://docs.docker.com/userguide/dockerlinks/) 变量（查看 [makeLinkVariables](http://releases.k8s.io/master/pkg/kubelet/envvars/envvars.go#L49)）、简单的 `{SVCNAME}_SERVICE_HOST` 和 `{SVCNAME}_SERVICE_PORT` 变量，这里 `Service` 的名称需大写，横线被转换成下划线。

例如，一个名称为 `"redis-master"` 的 Service 暴露了 TCP 端口 6379，同时给它分配了 Cluster IP 地址 10.0.0.11，这个 Service 生成了如下环境变量：

```shell
REDIS_MASTER_SERVICE_HOST=10.0.0.11
REDIS_MASTER_SERVICE_PORT=6379
REDIS_MASTER_PORT=tcp://10.0.0.11:6379
REDIS_MASTER_PORT_6379_TCP=tcp://10.0.0.11:6379
REDIS_MASTER_PORT_6379_TCP_PROTO=tcp
REDIS_MASTER_PORT_6379_TCP_PORT=6379
REDIS_MASTER_PORT_6379_TCP_ADDR=10.0.0.11
```

对于需要访问服务的Pod，在使用环境变量方法将端口和群集IP发布到客户端Pod时，必须在客户端Pod出现 *之前* 创建服务。 否则，这些客户端Pod将不会设定其环境变量。 如果仅使用DNS查找服务的群集IP，则无需担心此设定问题。

#### DNS

支持群集的DNS服务器（例如CoreDNS）监视 Kubernetes API 中的新服务，并为每个服务创建一组 DNS 记录。 如果在整个群集中都启用了 DNS，则所有 Pod 都应该能够通过其 DNS 名称自动解析服务。

例如，如果在 Kubernetes 命名空间 `my-ns` 中有一个名为 `my-service` 的服务， 则控制平面和DNS服务共同为 `my-service.my-ns` 创建 DNS 记录。 `my-ns` 命名空间中的Pod应该能够通过简单地对 `my-service` 进行名称查找来找到它（ `my-service.my-ns` 也可以工作）。其他命名空间中的Pod必须将名称限定为 `my-service.my-ns` 。 这些名称将解析为为服务分配的群集IP。

Kubernetes 还支持命名端口的 DNS SRV（服务）记录。 如果 `"my-service.my-ns"` 服务具有名为 `"http"`　的端口，且协议设置为`TCP`， 则可以对 `_http._tcp.my-service.my-ns` 执行DNS SRV查询查询以发现该端口号, `"http"`以及IP地址。

Kubernetes DNS 服务器是唯一的一种能够访问 `ExternalName` 类型的 Service 的方式。 更多关于 `ExternalName` 信息可以查看[DNS Pod 和 Service](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/)。

**附：关于服务发现**

参考：[服务发现与服务发现组件的基本原理 - 博客](https://www.cnblogs.com/Yemilice/p/10923331.html)

在传统的系统部署中，服务运行在一个固定的已知的 IP 和端口上，如果一个服务需要调用另外一个服务，可以通过地址直接调用，但是，在虚拟化或容器话的环境中，服务实例的启动和销毁是很频繁的，服务地址在动态的变化，如果需要将请求发送到动态变化的服务实例上，至少需要两个步骤：

服务注册 — 存储服务的主机和端口信息

服务发现 — 允许其他用户发现服务注册阶段存储的信息

服务发现的主要优点是可以无需了解架构的部署拓扑环境，只通过服务的名字就能够使用服务，提供了一种服务发布与查找的协调机制。服务发现除了提供服务注册、目录和查找三大关键特性，还需要能够提供健康监控、多种查询、实时更新和高可用性等。

## 7 Headless Services

有时不需要或不想要负载均衡，以及单独的 Service IP。此时，可以通过指定 Cluster IP（`spec.clusterIP`）的值为 `None` 来创建 `Headless` Service。

可以使用 headless Service 与其他服务发现机制进行接口，而不必与 Kubernetes 的实现捆绑在一起。

对于 headless `Service` 并不会分配 Cluster IP，`kube-proxy` 不会处理它们，平台也不会为它们进行负载均衡和路由。 DNS 如何实现自动配置，依赖于 `Service` 是否定义了 selector。

### 配置 Selector
对定义了 selector 的 Headless Service，Endpoint 控制器在 API 中创建了 Endpoints 记录，并且修改 DNS 配置返回 A 记录（地址），通过这个地址直接到达 Service 的后端 Pod 上。

### 不配置 Selector

对没有定义 selector 的 Headless Service，Endpoint 控制器不会创建 `Endpoints` 记录。 但是，DNS 系统会查找和配置，无论是：

- `ExternalName` 类型 Service 的 CNAME 记录
- 记录：与 Service 共享一个名称的任何 `Endpoints`，以及所有其它类型

## 8 发布服务 —— 服务类型

对一些应用（如 Frontend）的某些部分，可能希望通过外部Kubernetes 集群外部IP 地址暴露 Service。

Kubernetes `ServiceTypes` 允许指定一个需要的类型的 Service，默认是 `ClusterIP` 类型。`Type` 的取值以及行为如下：

- `ClusterIP`：通过集群的内部 IP 暴露服务，选择该值，服务只能够在集群内部可以访问，这也是默认的 `ServiceType`。
- [`NodePort`](https://kubernetes.io/zh/docs/concepts/services-networking/service/#nodeport)：通过每个 Node 上的 IP 和静态端口（`NodePort`）暴露服务。`NodePort` 服务会路由到 `ClusterIP` 服务，这个 `ClusterIP` 服务会自动创建。通过请求 `<NodeIP>:<NodePort>`，可以从集群的外部访问一个 `NodePort` 服务。
- [`LoadBalancer`](https://kubernetes.io/zh/docs/concepts/services-networking/service/#loadbalancer)：使用云提供商的负载局衡器，可以向外部暴露服务。外部的负载均衡器可以路由到 `NodePort` 服务和 `ClusterIP` 服务。
- [`ExternalName`](https://kubernetes.io/zh/docs/concepts/services-networking/service/#externalname)：通过返回 `CNAME` 和它的值，可以将服务映射到 `externalName` 字段的内容（例如， `foo.bar.example.com`）。 没有任何类型代理被创建。

部分内容略过，详见：[Services - Publishing Services (ServiceTypes)](https://kubernetes.io/zh/docs/concepts/services-networking/service/#publishing-services-service-types)

## 9 从集群外部访问Pod或Service

【本节其实是承接上一节的】

由于Pod和Service都是Kubernetes集群范围内的虚拟概念，所以集群外的客户端系统无法通过Pod的IP地址或者Service的虚拟IP地址和虚拟端口号访问它们。为了让外部客户端可以访问这些服务，可以**将Pod或Service的端口号映射到宿主机**，以使客户端应用能够通过物理机访问容器应用。

### 将容器应用的端口号映射到物理机

方法1：**容器级别的hostPort**

通过设置容器级别的hostPort，将容器应用的端口号映射到物理机上：

```yaml
# pod--hostport.yaml

apiVersion: v1
kind: Pod 
metadata:
  name: webapp
  labels:
    app: webapp
spec:
  containers:
  - name: webapp
    image: tomcat
    ports:
    - containerPort: 8080  # 容器端口
      hostPort: 8081       # 宿主机端口
```

方法2：**Pod级别的hostNetwork**

通过设置Pod级别的`hostNetwork=true`，该Pod中所有容器的端口号都将被直接映射到物理机上。在设置`hostNetwork=true`时需要注意，在容器的ports定义部分如果不指定hostPort，则默认hostPort等于containerPort，如果指定了hostPort，则hostPort必须等于containerPort的值：

```yaml
# pod--hostnetwork.yaml

apiVersion: v1
kind: Pod
metadata:
  name: webapp
  labels:
    app: webapp
spec:
  hostNetwork: true
  containers :
  - name: webapp
    image: tomcat
    imagePullPolicy: Never
    ports:
    - containerPort: 8080   # 默认hostPort等于containerPort
```

### 将Service的端口号映射到物理机

方法1：**nodePort**

通过设置nodePort映射到物理机，同时设置Service的类型为NodePort：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: webapp
spec:
  type: NodePort
  ports:
  - port: 8080
    targetPort: 8080   # port被映射到targetPort上
    nodePort: 8081     # 向集群外暴露的端口
  selector:
    app: webapp
```

方法2：**LoadBalancer**

通过设置LoadBalancer映射到云服务商提供的`LoadBalancer`地址。这种用法仅用于在公有云服务提供商的云平台上设置Service的场景。在下面的例子中，`status.loadBalancer.ingress.ip`设置的`146.148.47.155`为云服务商提供的负载均衡器的IP地址。**对该Service的访问请求将会通过LoadBalancer转发到后端Pod上**，负载分发的实现方式则依赖于云服务商提供的LoadBalancer的实现机制：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: MyApp
  ports:
    - protocol: TCP
      port: 80
      targetPort: 9376
  clusterIP: 10.0.171.239
  loadBalancerIP: 78.11.24.19
  type: LoadBalancer
status:
  loadBalancer:  # 负载均衡器是异步创建的，提供给负载均衡器的信息将会通过此字段发送出去
    ingress:
      - ip: 146.148.47.155
```

某些云提供商允许个人设置 `loadBalancerIP`，此时将根据用户设置的 `loadBalancerIP` 来创建负载均衡器。如果没有设置 `loadBalancerIP`，将会给负载均衡器指派一个 临时IP。 如果设置了 `loadBalancerIP`，但云提供商并不支持这种特性，那么设置的 `loadBalancerIP` 值将会被忽略掉。

### port, nodePort, targetPort的区别

port：service暴露在cluster ip上的端口，`<clusterIP>:port` 是提供给集群内部客户访问service的入口。

nodePort：提供给集群外部客户端可访问集群内部Service的端口

targetPort：是pod上的端口

参考：[Kubernetes中的nodePort，targetPort，port的区别和意义 - 博客](https://www.cnblogs.com/devilwind/p/8881201.html)

## 10 缺点与不足

为 虚拟IP 使用 userspace 代理，将只适合小型到中型规模的集群，不能够扩展到上千 `Service` 的大型集群。 

使用 userspace 代理，隐藏了访问 `Service` 的数据包的源 IP 地址。 这使得一些类型的防火墙无法起作用。 iptables 代理不会隐藏 Kubernetes 集群内部的 IP 地址，但却要求客户端请求必须通过一个负载均衡器或 Node 端口。

`Type` 字段支持嵌套功能 —— 每一层需要添加到上一层里面。 不会严格要求所有云提供商（例如，GCE 就没必要为了使一个 `LoadBalancer` 能工作而分配一个 `NodePort`，但是 AWS 需要 ），但当前 API 是强制要求的。