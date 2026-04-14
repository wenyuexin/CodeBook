# Kafka


## 一、基础概念

### 1. 什么是 Kafka？它主要解决什么问题？

**参考答案：**

Kafka 是一个分布式、分区的、多副本的、基于发布/订阅模式的消息流平台，由 LinkedIn 开发，后捐赠给 Apache 基金会。

Kafka 主要解决以下问题：
- **高吞吐量**：支持每秒百万级消息处理，适合日志收集、流式数据处理。
- **消息解耦**：生产者和消费者无需直接交互，通过消息队列进行异步通信。
- **持久化与回溯**：消息持久化到磁盘，支持消费者按偏移量重新消费。
- **水平扩展**：通过分区和副本机制，轻松扩展集群以应对海量数据。
- **实时流处理**：结合 Kafka Streams 或流处理框架（如 Flink、Spark Streaming）构建实时管道。

### 2. Kafka 与传统消息队列（如 RabbitMQ、ActiveMQ）有哪些区别？

**参考答案：**

| 特性 | Kafka | 传统消息队列（RabbitMQ 等） |
|------|-------|----------------------------|
| 消息模型 | 拉取模型，消费者主动拉取 | 推模型，代理主动推送 |
| 吞吐量 | 极高（百万级/秒） | 一般（万级/秒） |
| 消息存储 | 持久化到磁盘，按时间或大小保留 | 内存为主，持久化可选 |
| 消息删除 | 基于时间/大小，不立即删除 | 消费后立即删除（多数） |
| 消费方式 | 消费者维护偏移量，可重复消费 | 消息确认后即删除，不支持回溯 |
| 消息顺序 | 分区内保证顺序 | 全局顺序或单队列顺序 |
| 功能丰富度 | 较简单（发布-订阅、流处理） | 丰富（死信队列、延迟队列、事务等） |
| 适用场景 | 大数据、日志、流处理、海量数据管道 | 业务消息、任务队列、低延迟场景 |

### 3. Kafka 的核心组件有哪些？

**参考答案：**

- **Producer**：生产者，负责向 Kafka 主题发送消息。
- **Consumer**：消费者，从 Kafka 主题拉取消息。
- **Consumer Group**：消费者组，组内消费者共同消费一个或多个主题，每个分区只能被组内一个消费者消费。
- **Broker**：Kafka 服务器节点，负责消息存储和处理。
- **Topic**：主题，消息的逻辑分类。
- **Partition**：分区，主题的物理分片，每个分区是有序的、不可变的消息序列。
- **Replica**：副本，分区的备份，分为 Leader 和 Follower。
- **Leader**：分区的主副本，处理所有读写请求。
- **Follower**：分区的从副本，从 Leader 同步数据，当 Leader 故障时参与选举。
- **ISR（In-Sync Replicas）**：与 Leader 保持同步的副本集合。
- **ZooKeeper / KRaft**：早期 Kafka 使用 ZooKeeper 管理集群元数据，2.8 版本后引入 KRaft 模式自管理元数据。


## 二、主题与分区

### 4. 什么是主题（Topic）和分区（Partition）？为什么需要分区？

**参考答案：**

- **Topic**：一类消息的逻辑名称，类似数据库中的表。
- **Partition**：主题的物理分片，每个分区是一个有序的、不可变的消息日志文件。

分区的作用：
1. **水平扩展**：通过增加分区数量，可以将数据分散到多个 Broker，提升吞吐量。
2. **并行处理**：一个主题的多个分区可被多个消费者并行消费。
3. **顺序保证**：分区内消息是有序的，但跨分区不保证顺序。

### 5. 分区分配策略有哪些？如何保证消息的顺序性？

**参考答案：**

生产者发送消息时，通过分区器（Partitioner）决定消息发送到哪个分区。常见策略：
- **轮询（Round-Robin）**：均匀分布到所有分区。
- **哈希（Hash）**：根据消息键（Key）的哈希值取模，相同键的消息进入同一分区，从而保证该键下的顺序。
- **自定义**：实现 `Partitioner` 接口。

**保证顺序性**：
- 全局顺序：只用一个分区（牺牲吞吐量）。
- 分区内顺序：为需要顺序的消息指定相同的键（如订单 ID），使其进入同一分区。

### 6. 什么是分区副本（Replica）？Leader 和 Follower 的作用是什么？

**参考答案：**

- **副本**：分区的冗余拷贝，用于高可用。每个分区有多个副本，其中一个是 Leader，其余是 Follower。
- **Leader**：所有读写请求都通过 Leader 处理，保证数据一致性。
- **Follower**：从 Leader 异步拉取数据，保持与 Leader 同步。当 Leader 故障时，Follower 可被选举为新的 Leader。

### 7. 什么是 ISR？如何维护 ISR？

**参考答案：**

ISR（In-Sync Replicas）是与 Leader 保持同步的副本集合。Follower 定期向 Leader 发送 Fetch 请求，如果 Follower 在一定时间（`replica.lag.time.max.ms`，默认 10 秒）内没有落后太多（无滞后条数限制或滞后条数小于 `replica.lag.max.messages`），则被加入 ISR。

当 Leader 故障时，只有 ISR 中的副本才有资格成为新 Leader，以保证数据不丢失（如果允许不完全的 ISR 选举，则可能丢失数据）。

### 8. Kafka 如何保证数据不丢失？

**参考答案：**

Kafka 通过多层次的配置保证数据可靠性：

**生产者端**：
- `acks=all`：要求所有 ISR 副本确认写入后才认为成功。
- `enable.idempotence=true`：启用幂等性，避免重复写入。
- 同步发送或回调处理失败重试。

**Broker 端**：
- `min.insync.replicas`：最小同步副本数（通常设置为 2），配合 `acks=all` 确保至少写入多个副本。
- `unclean.leader.election.enable=false`：禁止非 ISR 副本成为 Leader，避免数据丢失。
- 刷盘参数：`log.flush.interval.messages` 和 `log.flush.interval.ms` 控制持久化频率。

**消费者端**：
- `enable.auto.commit=false`：手动提交偏移量，确保处理完业务逻辑后再提交。
- 消费逻辑幂等或使用事务。

### 9. Kafka 的消息存储结构是怎样的？

**参考答案：**

Kafka 的消息存储在日志段（Log Segment）文件中。每个分区的目录包含多个 Segment：
- `.log` 文件：存储消息数据。
- `.index` 文件：偏移量索引，用于快速定位消息。
- `.timeindex` 文件：时间戳索引。

Segment 文件达到配置大小（`log.segment.bytes`，默认 1GB）或时间间隔（`log.roll.hours`）后滚动生成新文件。旧 Segment 按保留策略（时间或大小）删除或压缩。


## 三、生产者

### 10. 生产者发送消息的流程是怎样的？

**参考答案：**

1. 生产者创建 `ProducerRecord`，包含主题、分区（可选）、键、值。
2. 序列化器将键和值序列化为字节数组。
3. 分区器根据分区策略决定目标分区。
4. 消息被添加到累加器（RecordAccumulator）中，每个分区对应一个双端队列（Deque）。
5. 后台 I/O 线程（Sender）将消息批次（Batch）发送到对应的 Broker。
6. Broker 返回响应，如果成功则清除缓冲；如果失败则根据重试策略重发。

### 11. 生产者参数 `acks` 有哪几种取值？分别代表什么含义？

**参考答案：**

- `acks=0`：生产者不等待任何确认，消息立即发送。吞吐量最高，但可能丢失数据（如网络故障）。
- `acks=1`：Leader 副本写入本地日志后即确认，无需等待 Follower 同步。若 Leader 确认后未同步到 Follower 就宕机，消息会丢失。
- `acks=all` 或 `acks=-1`：Leader 等待所有 ISR 副本确认写入后才返回。这是最高可靠性级别，但延迟较高。

### 12. 什么是幂等性生产者？如何实现？

**参考答案：**

幂等性生产者确保即使重试发送，消息在分区内也只会被写入一次，不会重复。

实现机制：
- 设置 `enable.idempotence=true`。
- 每个生产者有一个 Producer ID（PID）。
- 每个消息带有序列号（Sequence Number），Broker 检查序列号，若重复则拒绝写入。
- 幂等性仅在单个分区内有效，跨分区无法保证（需要事务）。

### 13. 什么是 Kafka 事务？事务可以解决哪些问题？

**参考答案：**

Kafka 事务允许跨多个分区、多个主题的原子写入，并且可以将消费与生产结合为原子操作（读-处理-写）。

解决的问题：
- 跨多个分区的消息要么全部成功，要么全部失败。
- 精确一次处理（Exactly-Once Semantics，EOS）在流处理中：消费消息、处理后生产结果、提交偏移量作为一个事务单元。

事务需要配置 `transactional.id` 并调用 `initTransactions()`、`beginTransaction()`、`commitTransaction()` 等 API。


## 四、消费者与消费者组

### 14. 什么是消费者组？消费者组内分区分配有哪些策略？

**参考答案：**

消费者组是一组共享同一 Group ID 的消费者实例，共同消费一个或多个主题。Kafka 保证每个分区只能被组内一个消费者消费，从而实现负载均衡和水平扩展。

分区分配策略（`partition.assignment.strategy`）：
- **Range 分配**（默认）：每个主题的分区按范围分配。可能导致分配不均。
- **RoundRobin 分配**：将所有主题的分区视为整体，轮询分配给消费者。
- **Sticky 分配**：在重平衡时尽量保持原有的分配，减少分区移动。
- **Cooperative Sticky**：增量式重平衡，逐步调整分配，避免全局停止消费。

### 15. 消费者如何维护消费进度（Offset）？Kafka 如何存储 Offset？

**参考答案：**

消费者通过提交偏移量（Offset）来记录自己消费到了哪个位置。早期版本将 Offset 存储在 ZooKeeper，后来改为内部主题 `__consumer_offsets`（50 个分区）存储。

提交方式：
- **自动提交**：`enable.auto.commit=true`，每隔 `auto.commit.interval.ms` 自动提交。
- **手动提交**：调用 `commitSync()`（同步阻塞）或 `commitAsync()`（异步回调）。

### 16. 什么是再平衡（Rebalance）？它可能导致什么问题？如何避免？

**参考答案：**

再平衡是消费者组内的分区重新分配的过程，发生在消费者加入、离开或主题分区数变更时。再平衡期间，所有消费者暂停消费（STOP-THE-WORLD），直到分配完成。

问题：
- 消费停顿（Stop the World）
- 重复消费（若未正确提交 Offset）或消息丢失（若提交后未处理完）

避免策略：
- 合理设置 `session.timeout.ms` 和 `heartbeat.interval.ms`，避免因网络抖动导致误判消费者下线。
- 使用 `max.poll.interval.ms` 控制处理消息的最大时间，防止因处理过慢而被踢出组。
- 使用 Sticky 或 Cooperative Sticky 分配策略，减少重平衡影响。

### 17. 如何保证消息不重复消费？如何实现精确一次（Exactly-Once）语义？

**参考答案：**

精确一次（EOS）需要生产者和消费者配合：

**生产者端**：
- 幂等性 + 事务，确保写入不重复。

**消费者端**：
- 消费处理逻辑幂等（如下游存储支持 upsert）。
- 使用 Kafka 事务将消费和提交偏移量作为原子操作：消费者读取消息、处理、生产结果、提交偏移量在同一个事务中。

**流处理框架**（Kafka Streams / Flink）：
- 内置 EOS 支持，通过检查点（Checkpoint）和事务实现。

### 18. 消费者参数 `max.poll.records` 和 `fetch.max.bytes` 有什么区别？

**参考答案：**

- `max.poll.records`：单次 `poll()` 返回的最大消息条数，控制应用层每次处理的消息数量。
- `fetch.max.bytes`：一次拉取请求从 Broker 获取的最大字节数（默认 50MB），影响网络和磁盘 I/O。

两者可以配合使用，例如希望每次拉取 500 条消息，但若消息体较大，实际字节数可能超过 `fetch.max.bytes` 限制。


## 五、性能与可靠性

### 19. Kafka 为什么吞吐量高？

**参考答案：**

- **顺序读写磁盘**：Kafka 写消息是追加到日志文件尾部，读消息也是顺序扫描，利用磁盘顺序 IO 速度接近内存。
- **零拷贝技术**（Zero Copy）：使用 `sendfile` 系统调用，数据直接从磁盘到网卡，避免内核到用户态的数据拷贝。
- **批量发送与压缩**：生产者将消息批量发送，Broker 也批量存储，减少网络和磁盘开销。支持 gzip、snappy、lz4、zstd 压缩。
- **分区并行**：多个分区可分布在多个 Broker，读写并发处理。
- **页缓存**：充分利用操作系统页缓存，读写命中率高。

### 20. Kafka 如何实现高可用？如何进行故障转移？

**参考答案：**

- **副本机制**：每个分区有多个副本（通常 3 个），Leader 负责读写，Follower 同步数据。
- **故障检测**：Broker 通过 ZooKeeper / KRaft 发送心跳，若心跳超时则判定为故障。
- **自动 Leader 选举**：当 Leader 故障时，Controller（集群控制器）从 ISR 中选举新 Leader。
- **故障转移过程**：
  1. 检测到 Leader 失效。
  2. 从 ISR 中选出新 Leader。
  3. 更新元数据，通知所有 Broker 和客户端。
  4. 客户端重定向到新 Leader。

### 21. 什么是 Kafka 的控制器（Controller）？它的作用是什么？

**参考答案：**

Controller 是 Kafka 集群中的一个 Broker，负责管理整个集群的元数据及分区状态。主要职责：
- 监控 Broker 加入和离开，触发分区重分配。
- 负责分区 Leader 选举。
- 管理副本状态机。
- 更新集群元数据（如主题、分区、ISR 等）到所有 Broker。

Controller 通过 ZooKeeper 选举产生，当 Controller 故障时，其他 Broker 会重新选举。

### 22. 如何监控 Kafka 集群的健康状态？

**参考答案：**

关键指标：
- **Broker 指标**：请求速率、响应延迟、网络连接数、磁盘使用率。
- **主题/分区指标**：未同步副本数（`UnderReplicatedPartitions`）、ISR 缩容情况。
- **消费者滞后（Consumer Lag）**：消费进度落后于生产者的消息数量，使用 `kafka-consumer-groups` 命令或 JMX 指标 `kafka.consumer:type=consumer-fetch-manager-metrics`。
- **消息速率**：生产/消费的每秒消息数。
- **GC 和 JVM 指标**：Full GC 频率、堆内存使用。

常用工具：
- Kafka 自带的 JMX 指标，配合 Prometheus + Grafana。
- Kafka Lag Exporter、Burrow（LinkedIn 开源）。
- 商业平台：Confluent Control Center、Datadog。

### 23. Kafka 如何保证消息的顺序性？跨分区如何保证？

**参考答案：**

- 分区内顺序：Kafka 保证每个分区内的消息顺序与写入顺序一致。生产者可以指定相同的 Key，使消息进入同一分区。
- 跨分区顺序：Kafka 不保证全局顺序。如果必须全局有序，只能使用单个分区（牺牲吞吐量）。或者由应用层实现全局排序（例如通过时间戳或序列号，但会增加复杂度）。

### 24. 什么是日志压缩（Log Compaction）？什么场景下使用？

**参考答案：**

日志压缩是 Kafka 的一种清理策略（`cleanup.policy=compact`），它保留每个消息键的最新值，删除旧版本。适用于需要保留每个 Key 最新状态，而不关心历史变化的场景，例如：
- 数据库变更日志（CDC）：每个主键只保留最新记录。
- 配置信息：保存每个配置项的最新值。
- 用户状态更新：保留每个用户的最新状态。

压缩过程：后台线程扫描日志，生成哈希表记录每个 Key 的最新偏移量，然后删除旧版本，生成新的 Segment 文件。


## 六、Kafka 与流处理

### 25. 什么是 Kafka Streams？与 Flink/Spark Streaming 相比有什么特点？

**参考答案：**

Kafka Streams 是一个轻量级的 Java 库，用于构建流处理应用程序，直接在 Kafka 之上运行。

特点：
- **无需单独集群**：应用作为普通 Java 进程运行，不依赖 YARN 或 Mesos。
- **恰好一次语义（EOS）**：内置事务支持。
- **状态存储**：支持 RocksDB 作为本地状态存储，可容错。
- **事件时间处理**：支持窗口操作、连接（Join）、聚合。

与 Flink/Spark Streaming 对比：
- Kafka Streams 更轻量，适合简单到中等复杂度的流处理。
- Flink 提供更丰富的状态管理和复杂事件处理（CEP），适合大型复杂场景。
- Spark Streaming 基于微批处理，延迟较高。

### 26. Kafka 如何与 Hadoop、Spark、Flink 等大数据框架集成？

**参考答案：**

- **Spark Streaming**：使用 `spark-streaming-kafka` 消费者，支持直接流（Direct Stream）和接收器（Receiver）方式。
- **Flink**：提供 `FlinkKafkaConsumer` 和 `FlinkKafkaProducer`，支持 Exactly-Once 语义。
- **Hadoop**：使用 Kafka 的 `kafka-hadoop-loader` 或通过 Flume/Sqoop 将 Kafka 数据导入 HDFS。
- **Hive**：可通过 Hive-Kafka 集成（Storage Handler）直接查询 Kafka 主题。

通常做法：将 Kafka 作为数据管道中心，连接各种数据源和存储系统。


## 七、运维与常见问题

### 27. 如何增加一个主题的分区数？有什么注意事项？

**参考答案：**

使用命令：
```bash
./kafka-topics.sh --alter --topic my-topic --partitions <new-number> --bootstrap-server localhost:9092
```

注意事项：
- 分区数只能增加，不能减少（减少会导致现有消息无法访问）。
- 分区数变化会引发消费者组重平衡。
- 如果消息有 Key，增加分区会导致后续消息与旧消息的哈希分配不一致，影响顺序性。建议提前规划分区数。

### 28. 如何处理 Kafka 消息堆积（Consumer Lag 过高）？

**参考答案：**

排查步骤：
1. 查看消费者是否挂掉或卡住：检查消费者组状态 `kafka-consumer-groups --describe --group <group>`。
2. 检查消费者处理速度：`max.poll.records` 是否太小，业务逻辑是否有瓶颈（如数据库慢查询）。
3. 增加消费者实例：但需注意分区数限制了最大并行度（一个分区只能被一个消费者消费），若分区数不足，需先增加分区。
4. 临时增加 `fetch.max.bytes` 和 `max.poll.records` 提高单次拉取量。
5. 对于重要堆积，可考虑创建新的消费者组从最新偏移量开始消费，跳过堆积（丢弃数据），或启动临时消费者集群专门处理堆积。

### 29. 如何升级 Kafka 版本？需要注意什么？

**参考答案：**

升级步骤（以 2.x 到 3.x 为例）：
1. 阅读官方升级说明，注意不兼容变更（如 Java 版本、配置移除、协议变更）。
2. 先升级 Broker 二进制，逐个节点滚动升级：停止 Broker，替换 JAR 包，启动。
3. 升级协议版本（`inter.broker.protocol.version`）和日志格式版本（`log.message.format.version`）。
4. 升级客户端（生产者和消费者）至兼容版本。
5. 如果升级涉及 ZooKeeper 到 KRaft 迁移（3.x 支持 KRaft），需进行迁移步骤。

注意事项：
- 始终备份元数据和数据。
- 滚动升级期间保证 `min.insync.replicas` 配置合理，避免服务不可用。
- 验证集群状态和消费者滞后无异常。

### 30. Kafka 日志目录磁盘满了怎么办？

**参考答案：**

紧急处理：
1. 删除过期日志：调整保留时间 `log.retention.hours` 或大小 `log.retention.bytes`，触发清理。
2. 增加磁盘容量或迁移部分分区到其他磁盘（通过添加新日志目录并执行分区重分配）。
3. 使用 `kafka-log-dirs` 命令查看各目录使用情况。
4. 如果是因为 Leader 分布不均，可以执行优先副本选举（Leader rebalance）。

长期方案：
- 监控磁盘使用率，设置报警阈值。
- 使用多磁盘（JBOD）或 RAID。
- 合理设置保留策略，避免无限制增长。


## 八、面试实战：高频手写题

### 31. 编写一个简单的 Kafka 生产者，发送消息并处理回调。

**Java 示例：**

```java
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("retries", 3);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>("my-topic", "key1", "Hello Kafka");

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Sent to partition " + metadata.partition() +
                                   " offset " + metadata.offset());
            } else {
                exception.printStackTrace();
            }
        });
        producer.close();
    }
}
```

### 32. 编写一个简单的 Kafka 消费者，手动提交偏移量。

```java
import org.apache.kafka.clients.consumer.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "false");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n",
                                      record.offset(), record.key(), record.value());
                    // 业务处理
                }
                consumer.commitSync(); // 手动提交
            }
        } finally {
            consumer.close();
        }
    }
}
```

### 33. 查看消费者组状态和滞后（Lag）的命令。

```bash
# 列出所有消费者组
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

# 查看特定组的详细信息
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-group --describe

# 输出示例：
# GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG
# my-group        my-topic        0          1000            1500            500
# my-group        my-topic        1          2000            2000            0
```

### 34. 创建一个主题，配置 3 个分区，2 个副本。

```bash
./kafka-topics.sh --create --topic my-topic \
  --partitions 3 --replication-factor 2 \
  --bootstrap-server localhost:9092
```

### 35. 修改主题的保留时间（例如保留 7 天）。

```bash
./kafka-configs.sh --bootstrap-server localhost:9092 \
  --entity-type topics --entity-name my-topic \
  --alter --add-config retention.ms=604800000
```


## 九、总结与延伸

### 36. Kafka 在哪些大厂中被广泛使用？举例场景。

**参考答案：**

- **LinkedIn**：原始开发者，用于活动流、日志聚合、监控等。
- **Netflix**：作为数据管道核心，支撑实时推荐、监控、日志处理。
- **Uber**：用于实时数据流处理，包括位置追踪、订单匹配。
- **Twitter**：消息处理、日志收集。
- **阿里巴巴**：双十一实时计算、日志系统。
- **腾讯**：广告系统、游戏日志处理。

### 37. Kafka 的未来趋势是什么？

**参考答案：**

- **KRaft 模式成熟**：逐步替代 ZooKeeper，简化架构。
- **分层存储**：将旧数据卸载到廉价对象存储（如 S3），降低存储成本。
- **Kafka Streams 进化**：支持更复杂的状态存储、SQL 接口。
- **云原生**：Kafka on Kubernetes（如 Strimzi、Confluent for Kubernetes）成为主流。
- **与 Flink 更深度集成**：作为流计算的标准输入/输出。
