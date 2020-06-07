# DNS服务

**官方文档：**

[Service](https://kubernetes.io/docs/concepts/services-networking/service/), [DNS for Services and Pods](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/), [Using CoreDNS for Service Discovery](https://kubernetes.io/docs/tasks/administer-cluster/coredns/)

[Customizing DNS Service](https://kubernetes.io/docs/tasks/administer-cluster/dns-custom-nameservers/), [CoreDNS GA for Kubernetes Cluster DNS](https://kubernetes.io/blog/2018/07/10/coredns-ga-for-kubernetes-cluster-dns/)

<br>

作为服务发现机制的基本功能，在集群内需要能够通过服务名对服务进行访问，这就需要一个**集群范围内的DNS服务来完成从服务名到ClusterIP的解析**。

DNS服务在Kubernetes的发展过程中经历了3个阶段：

在K8s 1.2版本时，DNS服务是由**SkyDNS**提供的，它由4个容器组成：`kube2sky, skydns, etcd, healthz`。kube2sky容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址信息生成DNS记录，并将其保存到etcd中；skydns容器从etcd中读取DNS记录，并为客户端容器应用提供DNS查询服务；healthz容器提供对skydns服务的健康检查功能。

从K8s 1.4版本开始，SkyDNS组件便被**KubeDNS**替换，主要考虑是SkyDNS组件之间通信较多，整体性能不高。KubeDNS由3个容器组成：`kubedns, dnsmasq, sidecar`，去掉了SkyDNS中的etcd存储，将DNS记录直接保存在内存中，以提高查询性能。kubedns容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址生成DNS记录，并将DNS记录保存在内存中；dnsmasq容器从kubedns中获取DNS记录，提供DNS缓存，为客户端容器应用提供DNS查询服务；sidecar提供对kubedns和dnsmasq服务的健康检查功能。

从K8s 1.11版本开始，集群的DNS服务由**CoreDNS**提供。CoreDNS是CNCF基金会的一个项目，是用Go语言实现的高性能、插件式、易扩展的DNS服务端。CoreDNS解决了KubeDNS的一些问题，例如dnsmasq的安全漏洞、externalName不能使用stubDomains设置，等等。CoreDNS支持自定义DNS记录及配置upstream DNS Server，可以统一管理Kubernetes基于服务的内部DNS和数据中心的物理DNS。CoreDNS没有使用多个容器的架构，只用一个容器便实现了KubeDNS内3个容器的全部功能。

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
        volumeMounts:    # 数据卷挂载
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
        securityContext:
          allowPrivilegeEscalation: false 
          capabilities:
            add:
            - NET BIND SERVICE 
            drop:
            - all
          readOnlyRootFilesystem: true
        dnsPolicy: Default
      volumes :
      - name: config-volume
        configMap:
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







---

参考资料

1. [CoreDNS: DNS and Service Discovery](https://coredns.io/)
2. [github - coredns/coredns](https://github.com/coredns/coredns)















