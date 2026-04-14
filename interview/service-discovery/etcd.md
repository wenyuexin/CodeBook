## etcd

### 1. etcd 是什么？它的主要使用场景有哪些？

**参考答案**：

etcd 是一个**高可用、强一致性的分布式键值存储系统**，采用 Raft 共识算法保证数据一致性。它由 CoreOS 开发，现为 CNCF 孵化项目。

**主要使用场景**：
- **服务发现与注册**：微服务注册中心（如 Kubernetes 使用 etcd 存储集群状态）。
- **配置管理**：存储动态配置，支持 watch 机制实时监听变更。
- **分布式协调**：选主（Leader 选举）、分布式锁、任务调度。
- **元数据存储**：Kubernetes 存储所有 API 对象（Pod、Service、ConfigMap 等）。
- **消息/通知**：通过 watch 实现发布-订阅模式。

---

### 2. etcd 与 ZooKeeper 相比有哪些优缺点？在什么场景下选择 etcd？

**参考答案**：

| 维度 | etcd | ZooKeeper |
|------|------|-----------|
| **一致性协议** | Raft（更易理解，实现简洁） | ZAB（类似 Raft，但历史更久） |
| **数据模型** | 层级键值（类似文件系统） | 层级 ZNode（类似文件系统） |
| **监听机制** | Watch（支持前缀、范围，长连接） | Watch（一次性，需重新注册） |
| **API** | gRPC + HTTP，功能丰富 | 基于 Jute 的定制协议，客户端需重连 |
| **性能** | 写入约 10k ops/s，读取更高 | 类似，但 ZK 有 session 开销 |
| **运维** | 部署简单，内置 metrics，集成 Prometheus | 运维复杂，需熟悉四字母命令 |
| **多语言支持** | 官方提供 Go、Java、Python、etcdctl | 官方主要 Java，其他社区实现 |
| **社区活跃度** | 高（K8s 生态驱动） | 较低，主要用于 Hadoop 等老系统 |

**选择 etcd 的场景**：新项目、云原生环境（K8s）、需要强一致性和 watch 能力、偏好 gRPC/HTTP API。ZooKeeper 适合已有 ZK 技术栈的老项目（如 Hadoop、Kafka 早期版本）。

---

### 3. 请简述 Raft 协议的核心工作原理，etcd 如何利用它实现高可用？

**参考答案**：

Raft 将分布式一致性问题分解为三个子问题：**Leader 选举、日志复制、安全性**。

- **Leader 选举**：节点有 Follower、Candidate、Leader 三种状态。Follower 若超时未收到 Leader 心跳，转为 Candidate，发起选举，获得多数派投票则成为 Leader。
- **日志复制**：Leader 接受客户端写请求，将日志条目追加到本地，并发给所有 Follower。当多数派写入成功，该日志被认为是 Committed，Leader 应用状态机并返回客户端。
- **安全性**：任何已 Committed 的日志不会被覆盖，Leader 必须包含所有已 Committed 日志。

**etcd 实现高可用**：
- 集群奇数个节点（推荐 3/5/7），容忍 (N-1)/2 节点故障。
- Leader 故障时自动选举新 Leader，期间短暂不可写，但可读。
- 使用磁盘存储 WAL（Write-Ahead Log）和快照，重启后恢复状态。

---

### 4. etcd 中的 Lease（租约）机制是什么？有什么用途？

**参考答案**：

Lease 是 etcd 提供的**时间租约**机制，客户端可以为 key 绑定一个 Lease，Lease 有生存时间（TTL）。客户端需要定期 KeepAlive 续约，否则 Lease 过期后，绑定的所有 key 会被自动删除。

**用途**：
- **服务注册与健康检查**：服务实例以临时 key 绑定 Lease，心跳续约，实例宕机后 key 自动消失。
- **分布式锁**：锁 key 绑定 Lease，防止持有锁的客户端崩溃导致死锁。
- **会话管理**：模拟 session 过期。

---

### 5. etcd 的 watch 机制是如何工作的？它支持哪些特性？

**参考答案**：

etcd 的 watch 机制允许客户端监听某个 key 或 key 前缀的变化（创建、修改、删除）。实现原理：客户端通过 gRPC 流建立长连接，服务端在有变更时推送事件。

**支持特性**：
- **单 key 监听**：监听一个具体 key。
- **前缀监听**：监听某个前缀下的所有 key（如 `/services/`）。
- **范围监听**：监听 key 在某个区间内。
- **历史事件回溯**：通过 `withRev(rev)` 从指定版本号开始监听，不会丢失中间事件。
- **事件类型**：PUT（创建/修改）、DELETE。

**与 ZooKeeper 对比**：etcd watch 是长连接持续推送，ZK 是一次性 watch（需要每次重新注册）。

---

### 6. 如何保证 etcd 中数据的强一致性？读写操作分别如何路由？

**参考答案**：

- **写操作**：必须由 Leader 处理。客户端无论请求哪个节点，若该节点不是 Leader，会返回 `etcdserver: not leader` 错误，并告知客户端当前 Leader 地址。客户端重试到 Leader，Leader 通过 Raft 复制到多数派后才提交。
- **读操作**：有两种模式：
  - **线性一致性读（默认）**：Leader 需检查自己仍是 Leader（通过心跳），保证读到最新已提交的数据，开销较大。
  - **可串行化读**：任意节点直接返回本地数据，可能读到旧数据，但性能高。

**保证**：通过 Raft 日志复制和提交索引，任何线性一致性读都能看到之前所有已提交写操作。

---

### 7. etcd 集群发生网络分区（脑裂）时，如何处理？Raft 如何保证不出现双主？

**参考答案**：

Raft 通过**多数派原则**避免脑裂。假设 5 节点集群，网络分区成 2 和 3。3 节点的分区仍能形成多数派（3 >= 3），可以选举出新 Leader；2 节点的分区无法达到多数派（2 < 3），不能选举出 Leader，因此只能读不能写，不会出现双主。

当网络恢复后，小分区节点会从大分区的 Leader 处同步缺失的日志，重新成为 Follower。

**注意**：若集群从 3 节点分裂成 1 和 2，2 节点分区也无法形成多数派（2 < 2? 实际需要 2 节点中至少 2 票，但 2 票是多数派吗？3 节点集群需要至少 2 票，2 节点分区恰好有 2 票，理论上可以选举。但 Raft 要求每次选举至少获得 (N/2)+1 票，对于 3 节点集群是 2 票。所以 2 节点分区可以达到多数派？不，注意原集群 3 节点，分裂成 1 和 2，2 节点分区有 2 个节点，但原集群总节点数 3，多数派 = 2，所以 2 节点分区确实可以选出 Leader。这就是为什么建议奇数个节点且 >=3，偶数个节点会增加双主风险。例如 2 节点集群，分裂成 1 和 1，两个分区都能获得 1 票，但多数派是 2 票？都不足，所以不会双主。实际上 Raft 不会出现双主，因为每个节点每次选举只能投一票，且必须获得超过半数。对于 2 节点分区（原集群 3 节点），分区内节点数 2，但投票需要集群总节点数的一半以上，即 2 票。这两个节点可以互相投票，正好 2 票，所以可以选出 Leader，但另一个分区只有 1 节点，只能获得 1 票，不足。所以不会双主。但 4 节点集群分裂成 2 和 2，每个分区都能获得 2 票，但多数派是 3 票，都不足，都不会选出 Leader。因此不会双主。Raft 严格保证安全性。

---

### 8. etcd 中的 MVCC（多版本并发控制）如何实现？有什么好处？

**参考答案**：

etcd 对每个 key 的每次修改都保存一个版本号（revision），全局递增。旧版本数据不会被立即覆盖，而是保留一段时间（可通过 `--compact` 压缩）。

**实现**：底层使用 BoltDB 存储 key 的多个版本，通过 revision 索引。

**好处**：
- **支持历史回溯**：可以读取指定版本的数据（`--rev` 参数）。
- **watch 机制的基础**：watch 可以指定起始版本，不丢失中间事件。
- **事务隔离**：读请求看到的是快照，不会被写阻塞。

**注意**：需要定期压缩（Compact）删除旧版本，防止存储空间无限增长。

---

### 9. 如何在 Java 应用中使用 etcd？常用的客户端有哪些？请给出简单的 put/get 示例。

**参考答案**：

常用 Java 客户端：
- **jetcd**：CoreOS 官方维护的 Java 客户端，基于 gRPC。
- **etcd4j**：社区实现，但较旧。
- **fabric8 etcd-java**：不推荐。

**示例（使用 jetcd）**：

```java
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.ByteSequence;
import static java.nio.charset.StandardCharsets.UTF_8;

public class EtcdDemo {
    public static void main(String[] args) throws Exception {
        // 1. 创建客户端
        Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        KV kvClient = client.getKVClient();

        // 2. 写入 key
        ByteSequence key = ByteSequence.from("my-key", UTF_8);
        ByteSequence value = ByteSequence.from("hello etcd", UTF_8);
        kvClient.put(key, value).get();

        // 3. 读取 key
        var getResp = kvClient.get(key).get();
        String val = getResp.getKvs().get(0).getValue().toString(UTF_8);
        System.out.println(val);

        // 4. 关闭
        client.close();
    }
}
```

---

### 10. etcd 与 Redis 的异同点？能否用 Redis 替代 etcd？

**参考答案**：

| 维度 | etcd | Redis |
|------|------|-------|
| **一致性** | 强一致（Raft） | 最终一致（主从复制），Redis Cluster 也不保证线性一致性 |
| **数据模型** | 层级键值，支持 watch | 丰富数据结构（String、Hash、List、Set、ZSet） |
| **持久化** | 默认持久化（WAL + Snapshot） | 可选 RDB/AOF，但通常用作缓存 |
| **分布式锁** | 通过 Lease + 事务实现可靠锁 | Redlock 存在争议，不够可靠 |
| **服务发现** | 原生支持（TTL + watch） | 需自己实现，缺乏标准 |
| **性能** | 写入约 10k ops/s | 写入可达 100k+ ops/s（内存） |
| **典型用途** | 配置、协调、元数据存储 | 缓存、计数器、消息队列、会话存储 |

**能否替代**：不能。Redis 不适合需要强一致性和协调的场景（如选主、服务注册）。etcd 不适合高性能缓存场景。两者互补。

---

### 11. etcd 集群备份和恢复的常用方法有哪些？

**参考答案**：

- **快照备份**：使用 `etcdctl snapshot save` 创建数据库快照（包含所有数据和版本）。
  ```bash
  etcdctl snapshot save snapshot.db
  ```
- **恢复**：使用 `etcdctl snapshot restore` 恢复到新目录，然后启动 etcd。
  ```bash
  etcdctl snapshot restore snapshot.db --data-dir /var/lib/etcd-new
  ```
- **注意事项**：恢复时需要重新配置集群成员，恢复后集群会丢失历史事件（watch 无法回溯到快照之前）。
- **增量备份**：配合 WAL 文件可做 PITR，但较复杂，常用快照 + 定期备份策略。

---

### 12. etcd 的容量限制是多少？如果数据量过大怎么办？

**参考答案**：

- **默认限制**：etcd 默认最大数据库大小为 2GB（可配置 `--quota-backend-bytes`，最大 8GB）。
- **原因**：etcd 设计用于存储元数据而非大数据，大数据库会导致性能下降（启动慢、快照大、网络传输延迟）。
- **应对策略**：
  - 定期压缩（Compact）并碎片整理（Defrag）回收空间。
  - 不使用 etcd 存储大量日志或业务数据，只存储配置和状态元数据。
  - 分拆多个 etcd 集群（如不同业务线独立）。
  - 升级到 8GB，但需要足够硬件和网络。

---

### 13. 请解释 etcd 中的 Revision、ModRevision、CreateRevision 的区别。

**参考答案**：

- **Revision**：全局递增的版本号，每次事务（可能多个 key）增加 1。
- **CreateRevision**：某个 key 首次创建时的全局 Revision 值。
- **ModRevision**：该 key 最后一次修改时的全局 Revision 值。

**示例**：
- 创建 key `/foo`，此时 Revision = 10，则 CreateRevision = 10，ModRevision = 10。
- 修改 `/foo` 为新值，此时全局 Revision = 15，则 ModRevision 变为 15，CreateRevision 仍为 10。

**用途**：可用于实现乐观锁（通过比较 ModRevision）、监听 key 从某个版本后的变化。

---

### 14. 在 Kubernetes 中，etcd 扮演什么角色？为什么 K8s 选择 etcd？

**参考答案**：

**角色**：etcd 是 Kubernetes 的**唯一状态存储**，存储所有 API 对象（Pod、Service、ConfigMap、Deployment 等）的期望状态和实际状态。kube-apiserver 无状态，所有数据持久化到 etcd。

**选择原因**：
- **强一致性**：保证集群状态不会分裂。
- **watch 机制**：kube-controller-manager、kube-scheduler 通过 watch 监听资源变化，实时响应。
- **高性能**：读写延迟低，满足控制平面需求。
- **可靠**：生产验证多年，支持集群高可用。

**注意**：K8s 1.22+ 默认使用 etcd v3 API，并优化了存储效率。

---

### 15. etcd 中事务（Txn）的使用场景？请举例。

**参考答案**：

etcd 支持小型事务（Compare-And-Swap），事务可以包含一系列条件判断和一系列操作（put/get/delete）。所有条件满足则执行成功分支，否则执行失败分支。

**典型场景**：
- **分布式锁**：`Compare(version(key) == 0) Then Put(key, owner)`。
- **原子更新**：更新前检查 modRevision，防止并发修改。
- **注册服务**：检查是否存在同名服务，不存在则创建。

**示例（etcdctl 命令）**：
```bash
etcdctl txn --interactive
compare:
mod("key") = "0"
success:
put key "value"
failure:
get key
```
