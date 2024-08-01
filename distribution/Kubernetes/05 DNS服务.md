# DNS服务

**官方文档：**

[Service](https://kubernetes.io/docs/concepts/services-networking/service/), [DNS for Services and Pods](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/), [Using CoreDNS for Service Discovery](https://kubernetes.io/docs/tasks/administer-cluster/coredns/)

[Customizing DNS Service](https://kubernetes.io/docs/tasks/administer-cluster/dns-custom-nameservers/), [CoreDNS GA for Kubernetes Cluster DNS](https://kubernetes.io/blog/2018/07/10/coredns-ga-for-kubernetes-cluster-dns/)

<br>

## DNS、A记录、SRV记录

Kubernetes DNS **schedules a DNS Pod and Service on the cluster**, and configures the **kubelets** to tell **individual containers** to use the DNS Service’s IP to resolve DNS names.

在集群中定义的每个 Service（包括 DNS 服务器自身）都会被指派一个 DNS 名称。 By default, a client Pod’s DNS search list will include the Pod’s own namespace and the cluster’s default domain. 例如：

Assume a Service named `foo` in the Kubernetes namespace `ns1`. A Pod running in namespace `bar` can look up this service by simply doing a DNS query for `foo`. A Pod running in namespace `ns2` can look up this service by doing a DNS query for `foo.ns1`.

以下各节详细介绍了受支持的记录类型和支持的布局。其中，代码部分的布局、名称或查询命令均被视为实现细节，参见 [Kubernetes DNS-Based Service Discovery](https://github.com/kubernetes/dns/blob/master/docs/specification.md).

### A记录

“正常” Service（除了 Headless Service）会以 `my-svc.my-namespace.svc.cluster-domain.example` 这种名字的形式被指派一个 DNS A 记录。 这会解析成该 Service 的 Cluster IP。

```
格式 <service>.<ns>.svc.<zone>. <ttl> IN A <cluster-ip>
示例 kubernetes.default.svc.cluster.local. 4 IN A 10.3.0.1
```

```
格式 <service>.<ns>.svc.<zone>. <ttl> IN AAAA <cluster-ip>
示例 kubernetes.default.svc.cluster.local. 4 IN AAAA 2001:db8::1
```

“Headless” Service（没有Cluster IP）也会以 `my-svc.my-namespace.svc.cluster-domain.example` 这种名字的形式被指派一个 DNS A 记录。 不像正常 Service，它会解析成该 Service 选择的一组 Pod 的 IP。 希望客户端能够使用这一组 IP，否则就使用标准的 round-robin 策略从这一组 IP 中进行选择。

【A记录，A or AAAA record。其中，A记录 (Address) 是用来指定主机名（或域名）对应的IPv4地址的DNS记录。AAAA记录(AAAA record)是用来将域名解析到IPv6地址的DNS记录。】

### SRV记录

命名端口需要创建 SRV 记录，这些端口是正常 Service或 [Headless Services](https://kubernetes.io/docs/concepts/services-networking/service/#headless-services) 的一部分。 对每个命名端口，SRV 记录具有 `_my-port-name._my-port-protocol.my-svc.my-namespace.svc.cluster-domain.example` 这种形式。 对普通 Service，这会被解析成端口号和 CNAME：`my-svc.my-namespace.svc.cluster-domain.example`。 对 Headless Service，这会被解析成多个结果，Service 对应的每个 backend Pod 各一个， 包含 `auto-generated-name.my-svc.my-namespace.svc.cluster-domain.example` 这种形式 Pod 的端口号和 CNAME。

## K8s中DNS服务的演进

作为服务发现机制的基本功能，在集群内需要能够通过服务名对服务进行访问，这就需要一个**集群范围内的DNS服务来完成从服务名到ClusterIP的解析**。

DNS服务在Kubernetes的发展过程中经历了3个阶段：

在K8s 1.2版本时，DNS服务是由**SkyDNS**提供的，它由4个容器组成：`kube2sky, skydns, etcd, healthz`。kube2sky容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址信息生成DNS记录，并将其保存到etcd中；skydns容器从etcd中读取DNS记录，并为客户端容器应用提供DNS查询服务；healthz容器提供对skydns服务的健康检查功能。

从K8s 1.4版本开始，SkyDNS组件便被**KubeDNS**替换，主要考虑是SkyDNS组件之间通信较多，整体性能不高。KubeDNS由3个容器组成：`kubedns, dnsmasq, sidecar`，去掉了SkyDNS中的etcd存储，将DNS记录直接保存在内存中，以提高查询性能。kubedns容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址生成DNS记录，并将DNS记录保存在内存中；dnsmasq容器从kubedns中获取DNS记录，提供DNS缓存，为客户端容器应用提供DNS查询服务；sidecar提供对kubedns和dnsmasq服务的健康检查功能。

从K8s 1.11版本开始，集群的DNS服务由**CoreDNS**提供。CoreDNS是CNCF基金会的一个项目，是用Go语言实现的高性能、插件式、易扩展的DNS服务端。CoreDNS解决了KubeDNS的一些问题，例如dnsmasq的安全漏洞、externalName不能使用stubDomains设置，等等。CoreDNS支持自定义DNS记录及配置upstream DNS Server，可以统一管理Kubernetes基于服务的内部DNS和数据中心的物理DNS。CoreDNS没有使用多个容器的架构，只用一个容器便实现了KubeDNS内3个容器的全部功能。

【说起来，CoreDNS和K8s其实是两个东西，前者是一个独立的工具，只不过内K8s内置，并作为默认的DNS服务组件。详细内容请点本文末尾的官网连接进行了解】

## K8s集群DNS服务的搭建过程

1）在创建**DNS**服务之前修改每个Node上 kubelet 的启动参数

修改每个Node上kubelet的启动参数，加上以下两个参数。
◎ --cluster-dns=169.169.0.100：为DNS服务的ClusterIP地址。
◎ --cluster-domain=cluster.local：为在DNS服务中设置的域名。
然后重启kubelet服务

2）创建CoreDNS应用

在部署CoreDNS应用前，需至少创建一个ConfigMap、一个Deployment和一个Service共3个资源对象。在启用了RBAC的集群中，还可以设置ServiceAccount、ClusterRole、ClusterRoleBinding对CoreDNS容器进行权限设置。

ConfigMap `coredns`主要设置CoreDNS的主配置文件Corefile的内容，其中可以定义各种域名的解析方式和使用的插件，例如

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: coredns
  namespace: kube-system
  labels:
    addonmanager.kubernetes.io/mode: EnsureExists
data:
  Corefile: |    # 主配置文件
    cluster.local {
      errors
      health
      kubernetes cluster.local in-addr.arpa ip6.arpa {
        pods insecure
        upstream
        fallthrough in-addr.arpa ip6.arpa
      }
      prometheus: 9153
      forward . /etc/resolv.conf
      cache 30
      loop
      reload 
      loadbalance
    }
    .{
      cache 30
      loadbalance
      forward . /etc/:resolv.conf
    }
```

Deployment `coredns`主要设置CoreDNS容器应用的内容

```yaml
apiVersion: apps/v1 
kind: Deployment
metadata:
  name: coredns
  namespace: kube-system
  labels:
    k8s-app: kube-dns
    kubernetes.io/cluster-service: "true"
    addonmanager.kubernetes.io/mode: Reconcile
    kubernetes.io/name: "CoreDNS"
spec:
  replicas: 1   # 这里只有1个副本
  strategy:
    type: RollingUpdate   # 目前支持两种策略：Recreate（重建）和RollingUpdate（滚动更新）
    rollingUpdate:
      maxUnavailable: 1   # 最大不可用副本数为1
  selector:
    matchLabels:
      k8s-app: kube-dns   # 有此标签的Pod才被这个Deployment管理
  template:     # 以下是Pod模板的具体内容
    metadata:
      labels:
        k8s-app: kube-dns
      annotations:        # 注解
        seccomp.security.alpha.kubernetes.io/pod: 'docker/default'
    spec:
      priorityClassName: system-cluster-critical   # 抢占式调度中优先级定义方式
      tolerations:
        key: "CriticalAddons0nly"      # 使用污点和容忍进行调度
        operator: "Exists"
      nodeSelector:
        beta.kubernetes.io/os: linux   # Pod将被定向调度到拥有此标签的Node上
      containers:
      - name: coredns    # Pod中的容器
        image: coredns/coredns:1.3.1
        imagePullPolicy: IfNotPresent
        resources:
          limits:        # 容器占用资源的上限，不能突破
            memory: 170Mi
          requests:
            cpu: 100m    # 容器申请资源的最低标准，必须满足
            memory: 70Mi
        args: [ "-conf", "/etc/coredns/Corefile" ]  # 容器运行参数
        volumeMounts:    # 把Pod的存储卷挂载到本容器中
        - name: config-volume
          mountPath: /etc/coredns
          readOnly: true
        ports:           # 容器端口
        - containerPort: 53
          name: dns
          protocol: UDP
        - containerPort: 53
          name: dns-tcp 
            protocol: TCP
        - containerPort: 9153
          name: metrics
          protocol: TCP
        livenessProbe:   # 容器探针，检查并指示容器是否准备好服务请求
          httpGet:
            path: /health
            port: 8080
            scheme: HTTP
          initialDelaySeconds: 60   # 初始化延迟时间，探针在此时间后开始运行
          timeoutSeconds: 5         # 监测的超时时间，超过后认为监测失败
          successThreshold: 1       # 探测失败后，最少连续探测成功多少次才被认定为成功
          failureThreshold: 5       # 探测成功后，最少连续探测失败多少次才被认定为失败
        securityContext:            # 安全上下文，用于定义Pod或Container的权限和访问控制设置
          allowPrivilegeEscalation: false 
          capabilities:
            add:
            - NET BIND SERVICE 
            drop:
            - all
          readOnlyRootFilesystem: true
        dnsPolicy: Default
      volumes:                       # Pod存储卷
      - name: config-volume          # 存储卷的名称
        configMap:                   # 存储卷的类型，configMap
          name: coredns
          items:
          - key: Corefile
            path: Corefile
```

Service `kube-dns`是DNS服务的配置，如下：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: kube-dns
  namespace: kube-system
  annotations:
    prometheus.io/port: "9153"
    prometheus.io/scrape: "true"
  labels:
    k8s-app: kube-dns
    kubernetes.io/cluster-service: "true"
    addonmanager.kubernetes.io/mode: Reconcile
    kubernetes.io/name: "CoreDNS"
  spec:
    selector:
      k8s-app: kube-dns
    clusterIP: 169.169.0.100 
    ports:
    - name: dns
      port: 53
      protocol: UDP
    - name: dns-tcp
      port: 53 
      protocol: TCP
    - name: metrics
      port: 9153
      protocol: TCP
```

最后通过`kubectl create`完成CoreDNS服务的创建：

```
# kubectl create -f coredns.yaml
```

然后可以通过命令行查看是否已启动。

## CoreDNS配置说明

CoreDNS的主要功能是通过**插件系统**实现的。CoreDNS实现了一种**链式插件结构**，将DNS的逻辑抽象成了一个个插件，能够灵活组合使
用。常用的插件如下：

- loadbalance：提供基于DNS的负载均衡功能。

- loop：检测在DNS解析过程中出现的简单循环问题。
- cache：提供前端缓存功能。
- health：对Endpoint进行健康检查。
- kubernetes：从Kubernetes中读取zone数据。
- etcd：从etcd读取zone数据，可以用于自定义域名记录。
- file：从RFC1035格式文件中读取zone数据。
- hosts：使用/etc/hosts文件或者其他文件读取zone数据，可以用于自定义域名记录。
- auto：从磁盘中自动加载区域文件。
- reload：定时自动重新加载Corefile配置文件的内容。
- forward：转发域名查询到上游DNS服务器。
- proxy：转发特定的域名查询到多个其他DNS服务器，同时提供到多个DNS服务器的负载均衡功能。
- prometheus：为Prometheus系统提供采集性能指标数据的URL。
- pprof：在URL路径/debug/pprof下提供运行时的性能数据。
- log：对DNS查询进行日志记录。
- errors：对错误信息进行日志记录。

例如，上文ConfigMap中的Corefile就对域名`cluster.local`设置了一系列插件。

etcd和hosts插件可以用于用户自定义域名记录。例如，以`.com`结尾的域名记录配置为从etcd中获取，并将域名记录保存在`/skydns`路径下：

```
{
  etcd com {
    path /skydns
    endpoint http://192.168.18.3:2379
    upstream /etc/resolv.conf
  }
  cache 160 com
  loadbalance
  proxy . /etc/resolv.conf
}
```

## **Pod**级别的**DNS**配置说明

**官方文档**：[DNS for Services and Pods - Pods](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/#pods)

创建 Pod 后，它的主机名是该 Pod 的 `metadata.name` 值。

PodSpec 有一个可选的 `hostname` 字段，可以用来指定 Pod 的主机名。当这个字段被设置时，它将优先于 Pod 的名字成为该 Pod 的主机名。举个例子，给定一个 `hostname` 设置为 `my-host`的 Pod，该 Pod 的主机名将被设置为 `my-host`。

PodSpec 还有一个可选的 `subdomain` 字段，可以用来指定 Pod 的子域名。例如，一个 Pod 的 `hostname` 设置为 `my-hostname`，`subdomain` 设置为 `my-subdomain`，那么在 namespace `my-namespace`中对应的完全限定域名（FQDN）为 `my-hostname.my-subdomain.my-namespace.svc.cluster-domain.example`。

例如：

```yaml
apiVersion: v1
kind: Service
metadata:
  name: default-subdomain
spec:
  selector:
    name: busybox
  clusterIP: None   # 设为None则为Headless Service，此时不会分配Cluster IP，不经过负载均衡
  ports:
  - name: foo       # Actually, no port is needed.
    port: 1234
    targetPort: 1234
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: busybox1
  labels:
    name: busybox
spec:
  hostname: busybox-1
  subdomain: default-subdomain
  containers:
  - image: busybox:1.28
    command:
      - sleep
      - "3600"
    name: busybox
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: busybox2
  labels:
    name: busybox
spec:
  hostname: busybox-2
  subdomain: default-subdomain
  containers:
  - image: busybox:1.28
    command:
      - sleep
      - "3600"
    name: busybox
```

如果 Headless Service 与 Pod 在同一个 Namespace 中，它们具有相同的子域名，集群的 KubeDNS 服务器也会为该 Pod 的完整合法主机名返回 A 记录。 例如，在同一个 Namespace 中，给定一个主机名为 `busybox-1` 的 Pod，子域名设置为 `default-subdomain`，名称为 `default-subdomain` 的 Headless Service ，Pod 将看到自己的 FQDN 为 `busybox-1.default-subdomain.my-namespace.svc.cluster.local`。 DNS 会为那个名字提供一个 A 记录，指向该 Pod 的 IP。 `busybox1` 和 `busybox2` 这两个 Pod 分别具有它们自己的 A 记录。

注意： 因为没有为 Pod 名称创建A记录，所以要创建 Pod 的 A 记录需要 `hostname` 。 没有 `hostname` 但带有 `subdomain` 的 Pod 只会为指向Pod的IP地址的 headless 服务创建 A 记录（`default-subdomain.my-namespace.svc.cluster-domain.example`）。 另外，除非在服务上设置了 `publishNotReadyAddresses=True`，否则 Pod 需要准备好 A 记录。

### dnsPolicy字段

除了使用集群范围的DNS服务（如CoreDNS），在Pod级别也能设置DNS的相关策略和配置。在Pod的配置文件中通过`spec.dnsPolicy`字段设置DNS策略，例如：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: busybox
  namespace: default
spec:
  containers:
  - image: busybox:1.28
    command:
      - sleep
      - "3600"
    imagePullPolicy: IfNotPresent
    name: busybox
  restartPolicy: Always
  hostNetwork: true
  dnsPolicy: ClusterFirstWithHostNet   # 此外还可设为Default, ClusterFirst, None
```

**字段取值**

- `Default`：Pod从运行所在的节点继承名称解析配置。 
- `ClusterFirst`：与配置的群集域后缀不匹配的任何 DNS查询(例如 `www.kubernetes.io` )都将转发到从节点继承的上游名称服务器。 群集管理员可能配置了额外的存根域和上游 DNS服务器。
- `ClusterFirstWithHostNet`：对于与 hostNetwork 一起运行的 Pod，应显式设置其DNS策略为 `ClusterFirstWithHostNet`。
- `None`：它允许 Pod 忽略 K8s 环境中的 DNS设置。 应该使用 Pod Spec 中的 `dnsConfig` 字段提供所有 DNS 设置。

**注意**：`Default` 不是默认的 DNS 策略。 如果未明确指定 `dnsPolicy`，则使用 `ClusterFirst`。

下面的例子中，Pod的 DNS策略设置为 `ClusterFirstWithHostNet`，因为它已将 `hostNetwork` 设置为 `true`。

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: busybox
  namespace: default
spec:
  containers:
  - image: busybox:1.28
    command:
      - sleep
      - "3600"
    imagePullPolicy: IfNotPresent
    name: busybox
  restartPolicy: Always
  hostNetwork: true   
  dnsPolicy: ClusterFirstWithHostNet
```

【注：`hostNetwork`设为true时，Pod中所有容器的端口号都将被直接映射到物理机上。Pod中运行的应用程序可以直接看到宿主主机的网络接口，宿主主机所在的局域网上所有网络接口都可以访问到该应用程序。这部分内容可以参考`04 K8s对象——Service.md` 第9节，或者查看官方文档】

### dnsConfig字段

自定义DNS配置可以通过`spec.dnsConfig`字段进行设置，可以设置下列内容：

- `nameservers`：一组DNS服务器的列表，最多可以设置3个。当 Pod 的 `dnsPolicy` 设置为 `None` 时，列表必须至少包含一个IP地址，否则此属性是可选的。列出的服务器将合并到从指定的 DNS 策略生成的基本名称服务器，并删除重复的地址。
- `searches`：用于在 Pod 中查找主机名的 DNS 搜索域的列表。此属性是可选的。指定后，提供的列表将合并到根据所选 DNS 策略生成的基本搜索域名中。 重复的域名将被删除。最多允许设置6个。
- `options`：配置其他可选DNS参数，例如`ndots`、`timeout`等，以`name`或`name/value`对的形式表示

`dnsConfig` 字段是可选的，它可以与任何 `dnsPolicy` 设置一起使用。 但是，当 Pod 的 `dnsPolicy` 设置为 “`None`” 时，必须指定 `dnsConfig` 字段。

例如

```yaml
# service/networking/custom-dns.yaml 

apiVersion: v1
kind: Pod
metadata:
  namespace: default
  name: dns-example
spec:
  containers:
    - name: test
      image: nginx
  dnsPolicy: "None"
  dnsConfig:
    nameservers:
      - 1.2.3.4
    searches:
      - ns1.svc.cluster-domain.example
      - my.dns.search.suffix
    options:
      - name: ndots
        value: "2"
      - name: edns0
```















---

参考资料

1. [CoreDNS: DNS and Service Discovery](https://coredns.io/)
2. [github - coredns/coredns](https://github.com/coredns/coredns)















