## Eureka

### 1. Eureka 是什么？它属于 Spring Cloud Netflix 中的哪个组件？目前维护状态如何？

**参考答案**：

Eureka 是 Netflix 开源的服务注册与发现组件，采用 AP（可用性优先）设计。在 Spring Cloud Netflix 中，Eureka 作为注册中心，与 Ribbon（客户端负载均衡）、Feign（声明式 HTTP 客户端）等配合使用。

**维护状态**：Netflix 宣布 Eureka 2.0 停止开发，Spring Cloud Netflix 进入维护模式，不再添加新特性。官方建议迁移到 Spring Cloud Alibaba Nacos 或 Consul。但现有项目仍可稳定使用。

---

### 2. Eureka 的自我保护机制是什么？触发条件和行为是怎样的？为什么需要这个机制？

**参考答案**：

**自我保护机制**：当 Eureka Server 在短时间内（默认 15 分钟）收到的心跳续约比例低于阈值（默认 85%），Server 会认为发生了网络故障，进入自我保护模式。

**行为**：
- 不再剔除任何服务实例（即使心跳超时）。
- 在控制台显示红色警告：“EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP...”。

**触发条件**：
- 每分钟续约数 = 客户端心跳总数。
- 期望续约数 = 当前注册实例数 × 2（每个实例每分钟 2 次心跳，30 秒一次）。
- 实际续约比例 < 85%。

**为什么需要**：避免网络抖动导致大量健康实例被误剔除，优先保证可用性（AP）。虽然可能返回已下线的实例，但客户端可通过重试、熔断解决。

---

### 3. Eureka 服务实例是如何注册和续约的？参数如何配置？

**参考答案**：

- **注册**：客户端启动时向 Eureka Server 发送注册请求，包含服务名、IP、端口、元数据。
- **续约（心跳）**：客户端每 30 秒发送一次心跳，通知 Server“我还活着”。若 Server 90 秒未收到心跳，则剔除该实例。

**关键配置（Spring Cloud 中）**：
```yaml
eureka:
  instance:
    lease-renewal-interval-in-seconds: 30   # 心跳间隔（秒）
    lease-expiration-duration-in-seconds: 90 # 剔除间隔（秒）
  client:
    service-url:
      defaultZone: http://eureka-server:8761/eureka/
```

---

### 4. Eureka Server 之间如何同步注册信息？是强一致还是最终一致？

**参考答案**：

Eureka Server 通过 **Peer to Peer（对等）** 模式进行数据同步，没有主从概念。每个 Server 既是注册服务端，也是其他 Server 的客户端。

**同步机制**：
- 当服务实例注册到任意一个 Eureka Server 节点，该节点会异步将注册信息复制到其他 Peer 节点。
- 同步采用 HTTP 请求，非强一致，属于 **最终一致性**。

**特点**：
- 如果网络分区发生，每个分区内的 Server 各自独立工作，不会因数据不一致而拒绝服务。
- 网络恢复后，数据会逐步同步。

---

### 5. Eureka Client 是如何获取服务实例列表的？缓存机制是怎样的？

**参考答案**：

- **首次获取**：客户端启动时从 Eureka Server 拉取全量实例列表，并缓存到本地。
- **增量获取**：默认每 30 秒增量拉取（只获取变更的实例），更新本地缓存。
- **降级**：如果 Server 不可用，客户端仍可使用本地缓存中的实例列表（即使过期）。
- **缓存过期**：增量拉取失败 3 次后，会重新拉取全量数据。

**相关配置**：
```yaml
eureka:
  client:
    registry-fetch-interval-seconds: 30   # 拉取间隔
```

---

### 6. Eureka 与 Zookeeper 在服务发现上的最大区别是什么？分别适用什么场景？

**参考答案**：

| 维度 | Eureka | ZooKeeper |
|------|--------|-----------|
| **CAP 模型** | AP（高可用） | CP（强一致） |
| **健康检查** | 客户端心跳，超时剔除 | Session 存活（心跳），超时删除临时节点 |
| **自我保护** | 支持，网络故障时保留实例 | 无，网络分区时可能出现 Leader 选举不可用 |
| **一致性** | 最终一致，允许数据短暂不一致 | 强一致，但牺牲可用性 |

**适用场景**：
- **Eureka**：普通微服务，强调高可用，允许短暂不一致，实例变化频繁。
- **ZooKeeper**：对一致性要求极高且可用性要求不高的场景（如分布式协调、选主），不适合作为纯服务注册中心（已有 Nacos 等更好选择）。

---

### 7. 在 Spring Cloud 中，如何优雅关闭 Eureka 客户端？为什么需要优雅关闭？

**参考答案**：

优雅关闭：在应用停止时，主动向 Eureka Server 发送注销请求，而不是等待心跳超时（90 秒）自动剔除。

**实现方式**：
- 使用 `@PreDestroy` 或 Spring Boot 的 `DisposableBean`，调用 `discoveryClient.shutdown()`。
- 或者在 `application.yml` 中配置：
  ```yaml
  eureka:
    client:
      shutdown:
        enabled: true
  ```

**为什么需要**：避免消费者在 90 秒内仍调用已停止的实例，导致请求失败。优雅注销可以瞬间通知 Server 下线实例。

---

### 8. Eureka 控制台中的 “Renews threshold” 和 “Renews (last min)” 分别代表什么？

**参考答案**：

- **Renews threshold（续约阈值）**：Eureka Server 期望每分钟收到的心跳总数。计算公式 = 当前注册实例数 × 2（每个实例每分钟 2 次心跳）。
- **Renews (last min)（上一分钟实际续约数）**：上一分钟实际收到的心跳总数。

如果 `Renews (last min)` < `Renews threshold` × 85%，则触发自我保护模式（显示红色警告）。

**示例**：有 100 个实例，阈值 = 200。若实际收到 160 次心跳，比例 80% < 85%，触发自我保护。

---

### 9. Eureka 的自我保护模式可以关闭吗？关闭后有什么风险？

**参考答案**：

可以关闭，配置：
```yaml
eureka:
  server:
    enable-self-preservation: false
```

**关闭后的风险**：网络短暂抖动时，Eureka Server 会立即剔除所有未及时续约的实例，可能导致大量健康实例被误删除，造成大面积服务不可用。

**生产建议**：一般不关闭自我保护，而是配合客户端重试、熔断等机制处理。如果确实需要快速剔除故障实例，可适当调低阈值（如 50%）而非完全关闭。

---

### 10. Eureka 的 Region 和 Zone 是什么？如何实现同区域优先调用？

**参考答案**：

- **Region**：地理区域（如 us-east、cn-north）。一个 Region 包含多个 Zone。
- **Zone**：区域内的逻辑隔离单元（如同机房的实例组）。

**同区域优先调用**：配置客户端的 `prefer-same-zone-eureka` 为 true，Eureka 会优先选择与消费者相同 Zone 的服务实例，减少跨区延迟。

**配置示例**：
```yaml
eureka:
  client:
    prefer-same-zone-eureka: true
    availability-zones:
      cn-north: zone1,zone2
    service-url:
      zone1: http://eureka-zone1:8761/eureka/
      zone2: http://eureka-zone2:8761/eureka/
```

---

### 11. Eureka 实例的元数据（Metadata）有什么作用？如何自定义？

**参考答案**：

元数据是键值对，附加在服务实例上，用于提供额外信息，如版本号、环境标识、区域信息等。消费者可读取元数据实现自定义路由。

**自定义方式**：
```yaml
eureka:
  instance:
    metadata-map:
      version: v2
      env: prod
      zone: zone-a
```

**用途**：
- 灰度发布：根据元数据中的版本号，只将流量路由到新版本实例。
- 多环境隔离：不同环境（dev/test/prod）通过元数据区分。

---

### 12. 如何实现 Eureka Server 的高可用部署？最少需要几个节点？

**参考答案**：

Eureka Server 通过互相注册实现高可用（Peer to Peer）。最少需要 2 个节点（但推荐 3 个或更多）。每个节点都注册到其他节点。

**配置示例（双节点）**：
- **节点1**：`eureka.client.service-url.defaultZone=http://node2:8761/eureka/`
- **节点2**：`eureka.client.service-url.defaultZone=http://node1:8761/eureka/`

**注意**：2 节点情况下，若其中一个故障，另一个仍可提供服务，但数据同步会中断，恢复后最终一致。

**生产建议**：至少 3 个节点，部署在不同物理机/容器，避免单点。

---

### 13. 为什么 Eureka 不推荐在生产中使用？目前主流的替代方案有哪些？

**参考答案**：

**不推荐原因**：
- Netflix 已停止 Eureka 2.0 开发，Spring Cloud Netflix 进入维护模式。
- 缺乏配置中心能力，需要额外组件。
- 自我保护机制在某些场景下导致长时间返回过期实例。
- 性能不如 Nacos 等新一代注册中心。

**主流替代方案**：
- **Nacos**：Spring Cloud Alibaba 推荐，支持 AP/CP 切换，内置配置中心。
- **Consul**：HashiCorp 出品，支持多数据中心，Raft 协议。
- **ZooKeeper**：不推荐作为纯注册中心，但部分老系统仍用。

---

### 14. Eureka Client 的 `fetchRegistry` 和 `registerWithEureka` 分别有什么作用？

**参考答案**：

- **`registerWithEureka`**：是否将自身注册到 Eureka Server。默认为 true。如果应用是纯消费者（不提供服务），可设为 false。
- **`fetchRegistry`**：是否从 Eureka Server 拉取服务实例列表。默认为 true。如果应用不调用其他服务，可设为 false（极少见）。

**配置示例**：
```yaml
eureka:
  client:
    register-with-eureka: false   # 只消费，不注册
    fetch-registry: true
```

---

### 15. Eureka 与 Kubernetes 服务发现如何集成？是否有官方方案？

**参考答案**：

没有官方直接集成方案，但可以通过以下方式：

1. **Spring Cloud Kubernetes**：使用 Kubernetes 原生 Service 和 Endpoints 作为注册中心，替代 Eureka。
2. **Eureka on Kubernetes**：将 Eureka Server 部署在 K8s 上，微服务使用 K8s 的 headless service 或 StatefulSet 提供稳定网络标识，但依然依赖 Eureka。
3. **同步工具**：如 `nacos-sync` 可将 K8s Service 同步到 Eureka（或 Nacos）。

**趋势**：新项目建议直接使用 Kubernetes 服务发现 + Spring Cloud Kubernetes，或 Nacos/Consul，而不是 Eureka。
