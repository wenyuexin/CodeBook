# K8s对象——Service

**官方文档**：[Service](https://kubernetes.io/docs/concepts/services-networking/service/)

An abstract way to expose an application running on a set of Pods as a network service.

通过创建Service，可以为一组具有相同功能的容器应用提供一个统一的入口地址，并且将请求负载分发到后端的各个容器应用上。【同一种应用可能同时运行在多个Pod上，而用户通过网络使用时并不关系具体使用的是哪个Pod上的应用，只需要一个Service提供统一的入口即可】

直接通过Pod的IP地址和端口号可以访问到容器应用内的服务，但是Pod的IP地址是不可靠的，例如当Pod所在的Node发生故障时，Pod将被Kubernetes重新调度到另一个Node，Pod的IP地址将发生变化。更重要的是，如果容器应用本身是分布式的部署方式，通过多个实例共同提供服务，就需要在这些实例的前端设置一个负载均衡器来实现请求的分发。Kubernetes中的Service就是用于解决这些问题的核心组件。

## Service资源

Kubernetes `Service` 定义了这样一种抽象：这种抽象定义了逻辑上的一组 `Pod`，以及一种可以访问它们的策略 —— 有时候这种模式也被称为微服务。 这组被`Service` 针对的 `Pod` 通常是由 [selector](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/) 确定的。

例如，考虑一个图片处理 backend，它运行了3个副本。这些副本是可互换的 —— frontend 不需要关心它们调用了哪个 backend 副本。 然而组成这一组 backend 程序的 `Pod` 实际上可能会发生变化，frontend 客户端不应该也没必要知道，而且也不需要跟踪这一组 backend 的状态。 `Service` 定义的抽象能够解耦这种关联。

**云原生服务发现**

如果您想要在应用程序中使用 Kubernetes 接口进行服务发现，则可以查询 API server 的 endpoint 资源，只要服务中的Pod集合发生更改，端点就会更新。对于非本机应用程序，Kubernetes提供了在应用程序和后端Pod之间放置网络端口或负载均衡器的方法。

## 定义

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

## Virtual IPs 和 Service 代理

在 Kubernetes 集群中，每个 Node 运行一个 `kube-proxy` 进程。`kube-proxy` 负责为 `Service` 实现了一种 VIP（虚拟 IP）的形式，而不是 [`ExternalName`](https://kubernetes.io/zh/docs/concepts/services-networking/service/#externalname) 的形式。

### 为什么不使用 DNS 轮询







