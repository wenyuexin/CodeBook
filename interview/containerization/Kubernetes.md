# Kubernetes 面试题

## 一、基础概念

### 1. 什么是 Kubernetes？
Kubernetes（简称 K8s）是一个开源的容器编排平台，用于自动化部署、扩展和管理容器化应用程序。它提供了容器编排、服务发现、负载均衡、存储编排、自动部署和回滚、自动扩缩容等功能。

### 2. Kubernetes 的核心概念有哪些？
- **Pod**：最小的部署单元，包含一个或多个容器
- **Node**：集群中的工作节点，运行 Pod
- **Cluster**：一组 Node 和 Master 的集合
- **Namespace**：用于隔离资源的虚拟集群
- **Label**：用于标识和选择对象的键值对
- **Annotation**：用于存储任意非标识性数据
- **Service**：定义一组 Pod 的访问策略
- **Deployment**：管理 Pod 的副本和更新策略
- **StatefulSet**：管理有状态应用
- **DaemonSet**：确保所有或某些 Node 上运行一个 Pod 副本
- **ConfigMap**：存储配置数据
- **Secret**：存储敏感信息
- **PV（Persistent Volume）**：存储资源
- **PVC（Persistent Volume Claim）**：存储资源请求

### 3. Kubernetes 和 Docker 的区别是什么？
- Docker 是容器运行时，负责创建和管理容器
- Kubernetes 是容器编排平台，可以管理多个 Docker 节点
- Kubernetes 支持多种容器运行时（Docker、containerd、CRI-O 等）
- Docker 提供单机容器管理，Kubernetes 提供集群级容器管理

### 4. 什么是容器编排？为什么需要容器编排？
容器编排是指自动管理容器的部署、扩展、联网和可用性。需要容器编排的原因：
- 管理大量容器
- 自动化部署和扩展
- 服务发现和负载均衡
- 故障自愈
- 滚动更新和回滚
- 资源利用率优化

## 二、架构和组件

### 1. Kubernetes 的架构是什么？
Kubernetes 采用主从架构，主要分为：
- **Control Plane（控制平面）**：负责集群的决策
- **Worker Node（工作节点）**：负责运行容器

### 2. Control Plane 包含哪些组件？

#### API Server
- 集群的统一入口
- 处理 REST 请求
- 认证、授权、准入控制
- 数据验证和持久化

#### etcd
- 分布式键值存储
- 存储集群所有配置和状态信息
- 提供 watch 机制
- 作为集群的唯一数据源

#### Scheduler
- 负责将 Pod 调度到合适的 Node
- 根据资源需求、策略、约束进行调度
- 支持多种调度算法和优先级

#### Controller Manager
- 维护集群状态
- 包含多个控制器：
  - Node Controller
  - Replica Controller
  - Endpoint Controller
  - Service Account Controller
  - Namespace Controller

#### Cloud Controller Manager
- 云平台特定的控制器
- 管理云平台资源（如负载均衡器、存储卷）

### 3. Worker Node 包含哪些组件？

#### Kubelet
- Node 上的代理
- 与 API Server 通信
- 管理 Pod 生命周期
- 上报 Node 和 Pod 状态
- 执行容器运行时接口（CRI）调用

#### Kube-proxy
- 维护网络规则
- 实现 Service 负载均衡
- 支持 iptables、IPVS 模式

#### Container Runtime
- 运行容器的软件
- 支持 Docker、containerd、CRI-O 等

### 4. Kubernetes 的通信流程是怎样的？
1. 用户通过 kubectl 向 API Server 发送请求
2. API Server 进行认证、授权和准入控制
3. 将请求持久化到 etcd
4. Controller 监听变化并执行相应操作
5. Scheduler 调度 Pod 到合适的 Node
6. Kubelet 在 Node 上创建和管理 Pod
7. Kube-proxy 配置网络规则

### 5. etcd 在 Kubernetes 中的作用是什么？
- 存储集群配置数据
- 存储集群状态信息
- 提供 watch 机制
- 实现分布式一致性
- 支持高可用部署

## 三、Pod 相关

### 1. 什么是 Pod？为什么需要 Pod？
Pod 是 Kubernetes 中最小的可部署单元，包含：
- 一个或多个共享存储和网络的容器
- 共享的 IPC/UTS 命名空间
- 共享的存储卷

需要 Pod 的原因：
- 容器间需要紧密协作（sidecar 模式）
- 共享网络和存储命名空间
- 原子性调度和管理
- 资源隔离和管理

### 2. Pod 的生命周期有哪些状态？
- **Pending**：Pod 已创建，但容器还未启动
- **Running**：Pod 已绑定到 Node，所有容器已创建，至少一个容器在运行
- **Succeeded**：所有容器成功终止，不会重启
- **Failed**：所有容器已终止，至少一个容器异常终止
- **Unknown**：无法获取 Pod 状态

### 3. Pod 的重启策略有哪些？
- **Always**：总是重启
- **OnFailure**：失败时重启
- **Never**：从不重启

### 4. Pod 的探针类型有哪些？

#### Liveness Probe（存活探针）
- 检测容器是否还在运行
- 失败时重启容器
- 三种类型：
  - exec：执行命令
  - httpGet：发送 HTTP 请求
  - tcpSocket：检查 TCP 端口

#### Readiness Probe（就绪探针）
- 检测容器是否准备好接收流量
- 失败时从 Service 的 Endpoints 中移除
- 不重启容器

#### Startup Probe（启动探针）
- 检测容器是否启动
- 用于启动时间较长的容器
- 成功后禁用，失败时重启容器

### 5. Pod 的调度过程是怎样的？
1. 用户创建 Pod
2. Scheduler 监听到未调度的 Pod
3. 过滤阶段：筛选出符合条件的 Node
4. 打分阶段：对 Node 进行打分排序
5. 选择最高分的 Node
6. 绑定 Pod 到 Node
7. Kubelet 在 Node 上创建 Pod

### 6. Pod 的亲和性和反亲和性是什么？
- **Node Affinity**：Pod 调度到特定的 Node
- **Pod Affinity**：Pod 调度到已有特定 Pod 的 Node
- **Pod Anti-Affinity**：Pod 不调度到已有特定 Pod 的 Node

### 7. 什么是 Init Container？
Init Container 是在应用容器启动前运行的容器：
- 按顺序执行
- 必须成功完成才能启动主容器
- 用于初始化配置、等待依赖服务等

### 8. Pod 中的容器如何通信？
- 同一 Pod 内的容器通过 localhost 通信
- 共享相同的网络命名空间
- 共享存储卷
- 使用 IPC 机制（信号量、共享内存等）

## 四、Service 和网络

### 1. 什么是 Service？
Service 是定义一组 Pod 的访问策略的抽象：
- 提供稳定的网络端点
- 实现负载均衡
- 支持服务发现
- 通过 Label Selector 选择 Pod

### 2. Service 的类型有哪些？
- **ClusterIP**：集群内部访问（默认）
- **NodePort**：通过 Node 的 IP 和端口访问
- **LoadBalancer**：通过云平台的负载均衡器访问
- **ExternalName**：映射到外部 DNS 名称

### 3. Service 如何发现后端 Pod？
- 通过 Label Selector 选择 Pod
- 监听 Pod 的变化
- 自动更新 Endpoints
- Kube-proxy 维护网络规则

### 4. 什么是 Endpoint？
Endpoint 是 Service 后端的实际 Pod IP 和端口列表：
- 由 Endpoint Controller 自动维护
- 当 Pod 就绪时添加到 Endpoint
- 当 Pod 不可用时从 Endpoint 中移除

### 5. Headless Service 是什么？
Headless Service 是没有 ClusterIP 的 Service：
- 直接返回 Pod IP 列表
- 用于 StatefulSet
- 需要自己实现负载均衡
- 配合 DNS 实现服务发现

### 6. Ingress 是什么？
Ingress 是管理外部访问集群内服务的规则：
- 基于 HTTP/HTTPS 路由
- 支持虚拟主机
- 支持 TLS 终止
- 需要 Ingress Controller 实现

### 7. 常见的 Ingress Controller 有哪些？
- Nginx Ingress Controller
- Traefik
- HAProxy
- Kong
- Istio Gateway

### 8. Kubernetes 的网络模型是什么？
- 所有 Pod 在扁平网络空间中
- 每个 Pod 都有独立的 IP
- Pod 之间可以直接通信
- Node 可以与所有 Pod 通信
- Network Policy 控制流量

### 9. CNI 是什么？
CNI（Container Network Interface）是容器网络接口标准：
- 定义容器运行时和网络插件之间的接口
- 支持多种网络插件
- 实现 Pod 网络

### 10. 常见的 CNI 插件有哪些？
- **Flannel**：简单的 VXLAN 网络
- **Calico**：基于 BGP 的网络，支持 Network Policy
- **Weave Net**：简单的加密网络
- **Cilium**：基于 eBPF 的高性能网络
- **Canal**：Flannel + Calico 的组合

### 11. Kube-proxy 的工作模式有哪些？
- **iptables**：使用 iptables 规则实现负载均衡
- **IPVS**：使用 IPVS 实现更高性能的负载均衡
- **userspace**：用户空间代理（已废弃）

## 五、存储和卷

### 1. Kubernetes 支持的存储卷类型有哪些？

#### 临时卷
- **emptyDir**：Pod 生命周期内有效的临时存储
- **hostPath**：Node 主机文件系统路径

#### 网络存储
- **NFS**：网络文件系统
- **CephFS**：Ceph 文件系统
- **GlusterFS**：分布式文件系统
- **iSCSI**：块存储

#### 云存储
- **AWS EBS**：Elastic Block Store
- **Azure Disk**：Azure 磁盘
- **GCE Persistent Disk**：Google Cloud 磁盘

#### 特殊类型
- **ConfigMap**：配置数据
- **Secret**：敏感信息
- **DownwardAPI**：Pod 信息
- **Projected**：多种卷的投影

### 2. PV 和 PVC 的区别是什么？
- **PV（Persistent Volume）**：集群级别的存储资源，由管理员创建
- **PVC（Persistent Volume Claim）**：命名空间级别的存储请求，由用户创建
- PVC 绑定到 PV，实现存储的动态分配

### 3. PV 的访问模式有哪些？
- **ReadWriteOnce**：单节点读写
- **ReadOnlyMany**：多节点只读
- **ReadWriteMany**：多节点读写
- **ReadWriteOncePod**：单 Pod 读写

### 4. PV 的回收策略有哪些？
- **Retain**：保留数据，需要手动清理
- **Delete**：删除 PVC 时同时删除 PV
- **Recycle**：已废弃，改为 Delete

### 5. 什么是 StorageClass？
StorageClass 定义存储的类型和参数：
- 定义存储类型（如 SSD、HDD）
- 定义存储提供者
- 支持动态创建 PV
- 包含回收策略、绑定模式等参数

### 6. 动态存储供给是什么？
动态存储供给是指根据 PVC 自动创建 PV：
- 用户创建 PVC
- StorageClass 定义存储类型
- Provisioner 自动创建 PV
- PV 自动绑定到 PVC

## 六、部署和调度

### 1. Deployment 的功能是什么？
- 管理 Pod 的副本数量
- 滚动更新和回滚
- 声明式更新
- 暂停和恢复更新
- 自动扩缩容

### 2. Deployment 的更新策略有哪些？
- **RollingUpdate**：滚动更新，逐步替换 Pod（默认）
- **Recreate**：先删除所有 Pod，再创建新 Pod

### 3. Deployment 的回滚机制是怎样的？
- Deployment 保存历史版本
- 使用 kubectl rollout undo 回滚
- 可以回滚到指定版本
- 保留的历史版本数量可配置

### 4. StatefulSet 的特点是什么？
- 稳定的网络标识
- 稳定的持久化存储
- 有序部署和扩展
- 有序删除和终止
- 有序的滚动更新

### 5. DaemonSet 的使用场景有哪些？
- 运行集群存储守护进程
- 运行日志收集守护进程
- 运行监控守护进程
- 在每个 Node 上运行网络插件

### 6. ReplicaSet 和 Deployment 的区别？
- ReplicaSet 管理 Pod 副本
- Deployment 管理 ReplicaSet
- Deployment 提供更新和回滚功能
- 通常直接使用 Deployment，不直接使用 ReplicaSet

### 7. HPA（Horizontal Pod Autoscaler）是什么？
HPA 是水平 Pod 自动扩缩器：
- 根据 CPU、内存等指标自动调整副本数
- 支持自定义指标
- 支持多指标
- 配合 Metrics Server 使用

### 8. VPA（Vertical Pod Autoscaler）是什么？
VPA 是垂直 Pod 自动扩缩器：
- 自动调整 Pod 的 CPU 和内存请求和限制
- 支持自动和推荐模式
- 需要重启 Pod 应用变更
- 与 HPA 互斥

### 9. Scheduler 的调度算法有哪些？
- **Predicates**：过滤不符合条件的 Node
- **Priorities**：对符合条件的 Node 打分
- 支持自定义调度器
- 支持调度策略配置

### 10. 什么是 Taint 和 Tolerations？
- **Taint**：污点，标记 Node 不接受普通 Pod
- **Tolerations**：容忍，允许 Pod 调度到有污点的 Node
- 常用于 Master Node 或专用 Node

### 11. 常见的 Taint 效果有哪些？
- **NoSchedule**：不调度新 Pod，不影响已运行的 Pod
- **PreferNoSchedule**：尽量不调度
- **NoExecute**：不调度且驱逐已有 Pod

## 七、配置和密钥

### 1. ConfigMap 的作用是什么？
- 存储配置数据
- 环境变量注入
- 命令行参数注入
- 配置文件挂载
- 支持热更新（部分情况）

### 2. Secret 的作用是什么？
- 存储敏感信息（密码、密钥、证书）
- Base64 编码存储
- 支持多种类型：
  - Opaque：通用密钥
  - kubernetes.io/tls：TLS 证书
  - kubernetes.io/dockerconfigjson：Docker 凭证
  - kubernetes.io/service-account-token：Service Account Token

### 3. Secret 和 ConfigMap 的区别是什么？
- Secret 用于敏感信息，ConfigMap 用于非敏感配置
- Secret 数据是 Base64 编码，ConfigMap 是明文
- Secret 有额外的安全保护
- Secret 通常不记录到日志

### 4. 如何在 Pod 中使用 ConfigMap？
- 环境变量
- 命令行参数
- 配置文件挂载
- 使用 subPath 挂载单个文件

### 5. 什么是 Downward API？
Downward API 将 Pod 信息暴露给容器：
- Pod 的 IP、名称、命名空间
- Pod 的标签和注解
- Pod 的资源请求和限制
- Pod 的 Node 信息

## 八、安全和认证

### 1. Kubernetes 的安全机制有哪些？
- **认证**：验证用户身份
- **授权**：验证操作权限
- **准入控制**：拦截和修改请求
- **网络策略**：控制网络流量
- **Pod 安全标准**：限制 Pod 权限
- **Secret 管理**：保护敏感信息

### 2. Kubernetes 的认证方式有哪些？
- **X.509 客户端证书**：最常用的方式
- **Bearer Token**：基于令牌的认证
- **OpenID Connect**：与外部身份提供商集成
- **Webhook**：自定义认证

### 3. Kubernetes 的授权方式有哪些？
- **RBAC（Role-Based Access Control）**：基于角色的访问控制
- **ABAC（Attribute-Based Access Control）**：基于属性的访问控制（已废弃）
- **Webhook**：自定义授权
- **Node Authorizer**：节点授权

### 4. RBAC 的核心概念有哪些？
- **Role**：命名空间级别的权限
- **ClusterRole**：集群级别的权限
- **RoleBinding**：将 Role 绑定到主体
- **ClusterRoleBinding**：将 ClusterRole 绑定到主体

### 5. 什么是 Service Account？
Service Account 是 Pod 运行时的身份标识：
- 每个 Pod 自动关联 Service Account
- 用于 API Server 认证
- 挂载到 Pod 的 /var/run/secrets/kubernetes.io/serviceaccount
- 包含 Token 和 CA 证书

### 6. 什么是 Admission Controller？
Admission Controller 是拦截 API 请求的插件：
- 在认证和授权之后执行
- 可以验证和修改请求
- 内置多个 Controller
- 支持自定义 Webhook

### 7. 常见的 Admission Controller 有哪些？
- **NamespaceLifecycle**：防止删除系统命名空间
- **ResourceQuota**：限制资源使用
- **LimitRanger**：设置默认资源限制
- **PodSecurityPolicy**：Pod 安全策略（已废弃，使用 Pod Security Standards）
- **ValidatingAdmissionWebhook**：验证 Webhook
- **MutatingAdmissionWebhook**：修改 Webhook

### 8. 什么是 Network Policy？
Network Policy 控制网络流量：
- 基于 Pod 的标签选择
- 规则类型：
  - ingress：入站规则
  - egress：出站规则
- 支持白名单和黑名单
- 需要 CNI 插件支持

### 9. Pod Security Standards 是什么？
Pod Security Standards 定义 Pod 安全级别：
- **Privileged**：无限制
- **Baseline**：基本安全限制
- **Restricted**：严格安全限制
- 通过 Namespace 级别配置

## 九、监控和日志

### 1. 如何监控 Kubernetes 集群？
- **Metrics Server**：收集资源使用指标
- **Prometheus**：存储和查询指标
- **Grafana**：可视化监控数据
- **Alertmanager**：告警管理

### 2. Metrics Server 的作用是什么？
- 收集 Node 和 Pod 的资源使用情况
- 提供 CPU 和内存指标
- 为 HPA 提供数据
- 通过 Metrics API 暴露数据

### 3. 如何收集 Pod 日志？
- **kubectl logs**：查看日志
- **日志文件**：/var/log/containers/
- **Logging Agent**：Fluentd、Fluent Bit
- **Sidecar 模式**：日志容器
- **Direct API**：直接从 API Server 获取

### 4. 常见的日志收集方案有哪些？
- **ELK Stack**：Elasticsearch + Logstash + Kibana
- **EFK Stack**：Elasticsearch + Fluentd + Kibana
- **Loki**：轻量级日志系统
- **Fluentd + Elasticsearch + Kibana**

## 十、故障排查

### 1. 如何排查 Pod 启动失败？
- 使用 kubectl describe pod 查看事件
- 使用 kubectl logs 查看容器日志
- 检查资源限制和请求
- 检查镜像拉取问题
- 检查配置错误

### 2. 常见的 Pod 故障有哪些？
- **ImagePullBackOff**：镜像拉取失败
- **CrashLoopBackOff**：容器反复崩溃
- **Pending**：Pod 无法调度
- **RunContainerError**：容器运行错误
- **OOMKilled**：内存不足被杀死

### 3. 如何排查 Service 无法访问的问题？
- 检查 Service 的 Endpoints
- 检查 Pod 的标签选择器
- 检查网络策略
- 检查 Kube-proxy 状态
- 使用 kubectl port-forward 测试

### 4. 如何排查网络问题？
- 检查 Pod IP 分配
- 检查 DNS 解析
- 检查网络策略
- 检查 CNI 插件状态
- 使用 ping、curl 等工具测试

### 5. 如何排查存储问题？
- 检查 PV 和 PVC 状态
- 检查存储类配置
- 检查存储提供者
- 检查 Pod 的挂载路径
- 检查存储权限

## 十一、高级特性

### 1. 什么是 Operator？
Operator 是使用自定义资源扩展 Kubernetes 的模式：
- 使用 CRD（Custom Resource Definition）定义自定义资源
- 使用控制器管理自定义资源
- 实现特定应用的运维逻辑
- 常见框架：Kubebuilder、Operator SDK

### 2. 什么是 CRD？
CRD（Custom Resource Definition）是自定义资源定义：
- 扩展 Kubernetes API
- 定义新的资源类型
- 可以像内置资源一样使用
- 与 Controller 配合实现自定义逻辑

### 3. 什么是 Helm？
Helm 是 Kubernetes 的包管理器：
- 使用 Chart 打包应用
- 支持模板化配置
- 支持版本管理
- 支持依赖管理
- 简化应用部署

### 4. Chart 的结构是什么？
- Chart.yaml：Chart 元数据
- values.yaml：默认配置值
- templates/：模板文件
- charts/：依赖的 Charts
- README.md：说明文档

### 5. 什么是 Kustomize？
Kustomize 是 Kubernetes 原生的配置管理工具：
- 声明式配置
- 支持基础和覆盖
- 内置到 kubectl
- 不需要模板语言

### 6. 什么是 Service Mesh？
Service Mesh 是微服务通信的基础设施层：
- 服务间通信管理
- 流量管理
- 安全通信（mTLS）
- 可观测性（指标、日志、追踪）
- 常见实现：Istio、Linkerd

### 7. 什么是 Istio？
Istio 是开源的 Service Mesh 实现：
- 数据平面：Envoy 代理
- 控制平面：Pilot、Citadel、Galley
- 功能：
  - 流量管理
  - 安全
  - 可观测性
  - 策略执行

### 8. 什么是 Custom Resource？
Custom Resource 是 Kubernetes API 的扩展：
- 由 CRD 定义
- 像内置资源一样使用
- 可以被 kubectl 管理
- 通常配合 Controller 使用

### 9. 什么是 Controller？
Controller 是控制循环：
- 监听资源变化
- 比较期望状态和实际状态
- 执行协调操作
- 保持系统一致性

### 10. 什么是 Operator Pattern？
Operator Pattern 是一种设计模式：
- 将特定领域的知识编码到软件中
- 自动化复杂任务
- 基于声明式 API
- 示例：数据库 Operator、消息队列 Operator

## 十二、实战场景

### 1. 如何实现应用的零停机更新？
- 使用 Deployment 的 RollingUpdate 策略
- 设置合适的 readinessProbe
- 设置适当的 maxSurge 和 maxUnavailable
- 使用多副本确保可用性
- 使用蓝绿部署或金丝雀发布

### 2. 如何实现蓝绿部署？
- 创建两个 Deployment：blue 和 green
- 使用 Service 指向当前版本
- 部署新版本到另一个 Deployment
- 验证新版本后切换 Service
- 逐步删除旧版本

### 3. 如何实现金丝雀发布？
- 使用两个 Deployment：stable 和 canary
- 设置不同的副本数比例
- 使用 Service 同时指向两个 Deployment
- 观察新版本指标
- 逐步调整流量比例

### 4. 如何实现应用的自动扩缩容？
- 配置 HPA
- 安装 Metrics Server
- 设置 CPU/内存目标
- 设置最小和最大副本数
- 配置自定义指标（可选）

### 5. 如何实现持久化存储？
- 创建 StorageClass
- 创建 PVC
- 在 Pod 中挂载 PVC
- 配置合适的访问模式
- 备份和恢复策略

### 6. 如何实现配置的热更新？
- 使用 ConfigMap
- 挂载为配置文件
- 应用监听配置文件变化
- 使用 Reloader 工具自动重启 Pod
- 或者使用 sidecar 监听变化

### 7. 如何实现多环境部署？
- 使用 Namespace 隔离环境
- 使用 Helm 的 values 文件
- 使用 Kustomize 的 overlay
- 配置不同的 ConfigMap 和 Secret
- 使用不同的域名和证书

### 8. 如何实现高可用部署？
- 多副本部署
- 使用反亲和性分散 Pod
- 多可用区部署
- 健康检查和自愈
- 备份和灾难恢复

### 9. 如何实现资源限制和配额？
- 设置 Pod 的资源请求和限制
- 使用 LimitRange 设置默认值
- 使用 ResourceQuota 限制命名空间资源
- 监控资源使用情况
- 优化资源配置

### 10. 如何实现日志集中管理？
- 使用 Fluentd 或 Fluent Bit 收集日志
- 发送到 Elasticsearch 或 Loki
- 使用 Kibana 或 Grafana 查看日志
- 配置日志轮转和清理
- 实现日志查询和分析

## 十三、性能优化

### 1. 如何优化 Kubernetes 集群性能？
- 合理配置资源请求和限制
- 使用 HPA 自动扩缩容
- 优化镜像大小（多阶段构建）
- 使用本地缓存
- 优化网络配置
- 选择合适的存储类型

### 2. 如何减少镜像拉取时间？
- 使用本地镜像仓库
- 预拉取镜像到 Node
- 使用镜像缓存
- 优化镜像层
- 使用更小的基础镜像

### 3. 如何提高 DNS 解析性能？
- 使用 CoreDNS 的缓存
- 调整 CoreDNS 副本数
- 使用 NodeLocal DNSCache
- 减少 DNS 查询
- 使用 IP 直接访问

### 4. 如何优化网络性能？
- 选择高性能的 CNI 插件
- 使用 HostNetwork（谨慎使用）
- 优化 Service 数量
- 使用 Network Policy 限制流量
- 配置合适的 MTU

### 5. 如何优化存储性能？
- 选择合适的存储类型（SSD）
- 使用本地存储（适用场景）
- 优化 IOPS 和吞吐量
- 使用存储缓存
- 合理配置 PV 大小

## 十四、最佳实践

### 1. 资源配置最佳实践
- 设置合适的资源请求和限制
- 监控资源使用情况
- 使用 HPA 自动扩缩容
- 避免过度配置或配置不足
- 定期 review 和调整

### 2. 安全最佳实践
- 使用非 root 用户运行容器
- 最小化容器权限
- 使用 Network Policy 限制网络访问
- 定期更新镜像和组件
- 使用 Secret 管理敏感信息
- 启用审计日志

### 3. 镜像管理最佳实践
- 使用官方或可信的基础镜像
- 定期更新镜像
- 扫描镜像漏洞
- 使用最小化镜像（Alpine、distroless）
- 使用多阶段构建减小镜像大小
- 使用镜像签名和验证

### 4. 部署最佳实践
- 使用声明式配置
- 使用版本控制管理配置
- 使用 GitOps 工作流
- 实现自动化部署
- 进行充分的测试

### 5. 监控和日志最佳实践
- 建立完整的监控体系
- 设置合理的告警
- 集中收集和分析日志
- 使用追踪工具（Jaeger、Zipkin）
- 定期 review 监控和日志

### 6. 灾难恢复最佳实践
- 定期备份 etcd
- 备份应用数据
- 文档化恢复流程
- 定期演练恢复流程
- 使用多集群部署

## 十五、常见问题

### 1. Kubernetes 的学习曲线为什么比较陡峭？
- 概念众多且相互关联
- 分布式系统复杂性
- 需要了解网络、存储等知识
- 调试和排查困难

### 2. 如何快速上手 Kubernetes？
- 从简单场景开始
- 使用 Minikube 或 Kind 本地练习
- 理解核心概念
- 多实践和实验
- 参考官方文档和社区资源

### 3. Kubernetes 和 Docker Swarm 的区别？
- Kubernetes 功能更丰富
- Kubernetes 社区更活跃
- Kubernetes 学习曲线更陡峭
- Docker Swarm 更简单易用

### 4. Kubernetes 和 Mesos 的区别？
- Kubernetes 专注于容器编排
- Mesos 是通用的资源调度器
- Kubernetes 生态更完善
- Mesos 支持多种工作负载

### 5. Kubernetes 的版本发布节奏？
- 每 4 个月发布一个主版本
- 每个主版本维护约 1 年
- 每个版本维护约 3 个补丁版本
- 建议使用最新稳定版本

## 十六、常用命令

### 1. Pod 相关命令
```bash
# 查看 Pod
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs <pod-name>

# 创建 Pod
kubectl run <name> --image=<image>

# 删除 Pod
kubectl delete pod <pod-name>

# 进入 Pod
kubectl exec -it <pod-name> -- /bin/bash
```

### 2. Deployment 相关命令
```bash
# 创建 Deployment
kubectl create deployment <name> --image=<image>

# 扩缩容
kubectl scale deployment <name> --replicas=3

# 更新镜像
kubectl set image deployment/<name> <container>=<image>

# 查看更新状态
kubectl rollout status deployment/<name>

# 回滚
kubectl rollout undo deployment/<name>
kubectl rollout undo deployment/<name> --to-revision=<revision>

# 查看历史
kubectl rollout history deployment/<name>
```

### 3. Service 相关命令
```bash
# 创建 Service
kubectl expose deployment <name> --port=80 --target-port=8080

# 查看 Service
kubectl get svc
kubectl describe svc <service-name>
```

### 4. 配置相关命令
```bash
# 创建 ConfigMap
kubectl create configmap <name> --from-file=<path>

# 创建 Secret
kubectl create secret generic <name> --from-literal=key=value

# 查看 ConfigMap/Secret
kubectl get configmap
kubectl get secret
```

### 5. 常用调试命令
```bash
# 查看事件
kubectl get events --sort-by='.lastTimestamp'

# 查看 Node 状态
kubectl get nodes
kubectl describe node <node-name>

# 查看资源使用
kubectl top nodes
kubectl top pods

# 端口转发
kubectl port-forward <pod-name> <local-port>:<pod-port>
```

---

## 总结

Kubernetes 是一个功能强大的容器编排平台，掌握这些面试题能够帮助你：

1. 理解 Kubernetes 的核心概念和架构
2. 掌握常用的功能和特性
3. 具备故障排查能力
4. 了解最佳实践
5. 解决实际工作中的问题

建议：
- 深入理解官方文档
- 多动手实践
- 关注社区动态
- 学习高级特性
- 积累实战经验
