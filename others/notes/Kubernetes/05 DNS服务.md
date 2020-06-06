# DNS服务

**官方文档：**[Service](https://kubernetes.io/docs/concepts/services-networking/service/), [DNS for Services and Pods](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/), [Using CoreDNS for Service Discovery](https://kubernetes.io/docs/tasks/administer-cluster/coredns/)

作为服务发现机制的基本功能，在集群内需要能够通过服务名对服务进行访问，这就需要一个集群范围内的DNS服务来完成从服务名到ClusterIP的解析。

DNS服务在Kubernetes的发展过程中经历了3个阶段：

在K8s 1.2版本时，DNS服务是由**SkyDNS**提供的，它由4个容器组成：kube2sky、skydns、etcd和healthz。kube2sky容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址信息生成DNS记录，并将其保存到etcd中；skydns容器从etcd中读取DNS记录，并为客户端容器应用提供DNS查询服务；healthz容器提供对skydns服务的健康检查功能。

从K8s 1.4版本开始，SkyDNS组件便被**KubeDNS**替换，主要考虑是SkyDNS组件之间通信较多，整体性能不高。KubeDNS由3个容器组成：kubedns、dnsmasq和sidecar，去掉了SkyDNS中的etcd存储，将DNS记录直接保存在内存中，以提高查询性能。kubedns容器监控Kubernetes中Service资源的变化，根据Service的名称和IP地址生成DNS记录，并将DNS记录保存在内存中；dnsmasq容器从kubedns中获取DNS记录，提供DNS缓存，为客户端容器应用提供DNS查询服务；sidecar提供对kubedns和dnsmasq服务的健康检查功能。

从K8s 1.11版本开始，集群的DNS服务由**CoreDNS**提供。CoreDNS是CNCF基金会的一个项目，是用Go语言实现的高性能、插件式、易扩展的DNS服务端。CoreDNS解决了KubeDNS的一些问题，例如dnsmasq的安全漏洞、externalName不能使用stubDomains设置，等等。