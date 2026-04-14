# Redis


## 一、基础概念

### 1. 什么是 Redis？
Redis 是一个基于内存的高性能 key-value 数据库，使用 C 语言开发。

### 2. Redis 支持哪些数据类型？
- **5 种基础数据类型**：String（字符串）、List（列表）、Set（集合）、Hash（散列）、Zset（有序集合）
- **3 种特殊数据类型**：HyperLogLog（基数统计）、Bitmap（位图）、Geospatial（地理位置）

### 3. Redis 相比 Memcached 有哪些优势？
- 支持更丰富的数据类型（Memcached 仅支持简单字符串）
- 速度更快
- 支持数据持久化

### 4. Redis 是单线程的吗？为什么？
Redis 采用单线程模型（6.0 后引入多线程处理网络 I/O，但执行命令仍为单线程）。原因：
- 基于内存操作，CPU 不是瓶颈，瓶颈通常是内存或网络
- 单线程避免上下文切换和锁竞争，实现简单

### 5. Redis 为什么这么快？
- 完全基于内存，操作时间复杂度 O(1)
- 数据结构简单且专门设计
- 单线程，无锁竞争
- 使用 I/O 多路复用模型（epoll/kqueue 等）
- 底层 VM 机制减少系统调用

### 6. I/O 多路复用原理是什么？
Redis 会优先选择时间复杂度 O(1) 的 I/O 多路复用函数，如 Linux 的 `epoll`、macOS/FreeBSD 的 `kqueue`、Solaris 的 `evport`，否则回退到 `select`。通过单线程同时监听多个 socket，实现高并发。

### 7. Redis 支持哪些编程语言？
Java、C、C#、C++、PHP、Node.js、Go 等。


## 二、数据结构与底层实现

### 1. Zset（有序集合）为什么是有序的？
Zset 内部使用**跳跃表（Skip List）** 作为底层数据结构之一（同时使用哈希表存储元素到分值的映射）。跳跃表通过多层索引实现快速查找、插入和删除，平均时间复杂度 O(log n)，从而保证元素按分值有序排列。

### 2. 跳跃表的原理是什么？
跳跃表是一种有序数据结构，通过维护多级链表索引来实现快速查找。每个节点包含键（元素）、值（分值）和多个指向其他节点的指针。查找时从顶层开始，逐层下降，跳过大量节点，达到 O(log n) 的平均复杂度。

### 3. 为什么 Redis 使用跳跃表而不是平衡树（如红黑树）？
- 内存占用更少（无需存储平衡信息如颜色）
- 实现更简单，代码可读性好
- 支持范围查找更高效（双向链表可顺序遍历）
- 并发环境下锁粒度更易控制

### 4. Redis 没有直接使用 C 字符串，而是使用 SDS（简单动态字符串），为什么？
SDS 在 C 字符串基础上增加了 `len`（已用长度）和 `free`（未用长度）字段，具有以下优点：
- 常数复杂度获取字符串长度
- 杜绝缓冲区溢出
- 减少修改字符串时的内存重分配次数
- 二进制安全（可存储任意数据，包括空字符）

### 5. Redis 对象是如何存储的？
Redis 不会直接存储数据类型，而是通过 `redisObject` 结构体封装，包含类型、编码、LRU 时间、引用计数和指向底层数据结构的指针。


## 三、持久化

### 1. 为什么需要持久化？
Redis 是内存数据库，进程退出或宕机会导致数据丢失。持久化将数据从内存保存到磁盘，实现数据恢复。

### 2. Redis 提供了哪几种持久化方式？
- **RDB（快照）**：在某个时间点生成数据副本
- **AOF（追加文件）**：记录每个写命令，追加到文件
- **混合持久化（Redis 4.0+）**：RDB + AOF 结合，提高恢复速度

### 3. RDB 持久化的触发方式有哪些？
- 手动触发：`save`（阻塞主线程）、`bgsave`（fork 子进程，不阻塞）
- 自动触发：根据 `save m n` 配置，如 `save 900 1`（900 秒内至少 1 次修改）

### 4. AOF 持久化的工作流程是怎样的？
1. 写命令追加到 AOF 缓冲区（`server.aof_buf`）
2. 根据 `appendfsync` 策略将缓冲区数据同步到磁盘：
   - `always`：每个命令同步，最安全但性能差
   - `everysec`（默认）：每秒同步一次，性能与安全平衡
   - `no`：由操作系统决定，性能最好但可能丢失数据
3. 当 AOF 文件过大时，自动触发重写（`bgrewriteaof`），压缩命令

### 5. AOF 重写期间是否会阻塞主线程？
不会。重写由子进程执行，主线程继续处理请求。重写过程中新命令会同时写入旧 AOF 文件和重写缓冲区，重写完成后将缓冲区内容追加到新文件。

### 6. RDB 和 AOF 的优缺点分别是什么？

| 持久化方式 | 优点 | 缺点 |
|-----------|------|------|
| RDB | 文件紧凑，体积小，恢复快，对性能影响小 | 非实时持久化，可能丢失最后一次快照后的数据；版本兼容性差 |
| AOF | 秒级持久化，数据更安全；兼容性好 | 文件大，恢复慢，对性能影响较大 |

### 7. 如何选择持久化策略？
- 数据可完全丢弃（如缓存）→ 可不持久化
- 单机环境，可接受十几分钟数据丢失 → RDB
- 需要秒级数据安全 → AOF
- 生产环境通常开启主从复制，并在从节点开启 AOF（`everysec`）

### 8. Redis 重启时如何加载数据？
![](assets/images/c36d3644-c8bb-4c53-b316-18543cff053f.png)
- 如果开启 AOF，优先加载 AOF 文件
- 否则加载 RDB 文件


## 四、缓存问题与解决方案

### 1. 什么是缓存穿透？如何解决？
**穿透**：请求的数据在缓存和数据库中都不存在，每次请求都打到数据库。
**解决方案**：
- 接口校验（参数合法性）
- 布隆过滤器（Bloom Filter）快速判断 key 是否存在
- 缓存空值（null），设置较短过期时间

### 2. 什么是缓存击穿？如何解决？
**击穿**：某个热点 key 突然失效，大量并发请求直接打到数据库。
**解决方案**：
- 热点数据永不过期
- 使用互斥锁（如 `SETNX`），只允许一个线程查询数据库并重建缓存，其他线程等待

### 3. 什么是缓存雪崩？如何解决？
**雪崩**：大量 key 在同一时间失效，或 Redis 宕机，导致数据库过载。
**解决方案**：
- 分散缓存失效时间（在过期时间上加随机偏移量）
- 多级缓存（本地缓存 + Redis）
- 缓存高可用（主从、集群）
- 服务降级与限流

### 4. 如何保证缓存与数据库的数据一致性？
分布式环境下无法保证强一致性，只能降低不一致概率。常见策略：
- 更新数据库后，立即更新缓存（或删除缓存）
- 使用消息队列（MQ）异步重试更新缓存
- 对于并发写，采用版本号或时间戳校验

### 5. 布隆过滤器的原理是什么？Redis 如何实现？
布隆过滤器是一个高效的概率型数据结构，用于判断一个元素**可能存在于**集合或**一定不存在**。通过多个哈希函数映射到位数组。Redis 可通过 `RedisBloom` 模块实现。


## 五、内存淘汰与过期删除

### 1. Redis 的 6 种内存淘汰策略（maxmemory-policy）
| 策略 | 说明 |
|------|------|
| `noeviction` | 不淘汰，内存达到上限时写命令返回错误 |
| `allkeys-lru` | 所有 key 中淘汰最近最少使用（LRU）的 |
| `volatile-lru` | 仅设置了过期时间的 key 中淘汰 LRU |
| `allkeys-random` | 所有 key 中随机淘汰 |
| `volatile-random` | 仅设置了过期时间的 key 中随机淘汰 |
| `volatile-ttl` | 淘汰剩余生存时间（TTL）最短的 key |

### 2. 设置 key 过期时间的四种方式
- `EXPIRE key seconds`：n 秒后过期
- `PEXPIRE key milliseconds`：n 毫秒后过期
- `EXPIREAT key timestamp`：指定时间戳（秒）过期
- `PEXPIREAT key millisecondsTimestamp`：指定时间戳（毫秒）过期

### 3. Redis 的三种过期删除策略
| 策略 | 描述 | 优点 | 缺点 |
|------|------|------|------|
| 定时删除 | 创建定时任务，到期立即删除 | 内存友好 | CPU 负担大 |
| 惰性删除 | 访问 key 时检查并删除过期 key | CPU 友好 | 内存浪费，可能泄漏 |
| 定期删除 | 每隔一段时间扫描部分过期 key | 折中方案 | 需合理配置扫描频率 |

实际 Redis 采用**惰性删除 + 定期删除**的组合。


## 六、高可用：主从复制、哨兵、集群

### 1. 主从复制的作用是什么？
- 数据冗余备份
- 读写分离，分担读压力
- 为故障恢复提供基础

### 2. 主从复制的原理简述
1. Slave 启动后发送 `SYNC`/`PSYNC` 命令
2. Master 执行 `bgsave` 生成 RDB 并发送给 Slave
3. Slave 加载 RDB 并请求 Master 后续的写命令（存储在复制积压缓冲区）
4. 之后 Master 将写命令持续同步给 Slave

### 3. 哨兵（Sentinel）的作用是什么？
在复制基础上实现**自动化故障恢复**。哨兵监控 Master 和 Slave，当 Master 下线时自动选举新 Master 并通知客户端。缺陷：写操作无法负载均衡，存储能力受单机限制。

### 4. Redis 集群（Cluster）与哨兵的区别？
- 集群支持**数据分片**（自动将 key 分布到多个节点），扩展写能力和存储能力
- 哨兵仍是单主架构，仅提供高可用

### 5. 读写分离架构有什么缺陷？
- 每个节点保存完整数据，受单机存储能力限制
- 写密集型场景不适用
- 主从延迟可能导致读到旧数据

### 6. 数据分片模型如何解决读写分离的缺陷？
将数据分散到多个独立 Master 节点（每个节点可再搭配 Slave），通过业务或代理实现分片，从而线性扩展存储和写能力。例如 Redis Cluster。

### 7. Redis 常见性能问题与解决方案
- Master 不做持久化，由 Slave 负责（AOF）
- Master 和 Slave 最好在同一局域网
- 避免在压力大的主库上增加从库


## 七、分布式锁与 Redlock

### 1. 如何使用 Redis 实现分布式锁？
利用 `SETNX`（只在 key 不存在时设置）或 `SET key value NX EX seconds` 原子命令。流程：
```java
String result = jedis.set("lockKey", "requestId", "NX", "EX", 30);
if ("OK".equals(result)) {
    // 获取锁成功，执行业务
    // 最后释放锁（使用 Lua 脚本保证原子性）
}
```

### 2. 单纯的 SETNX + EXPIRE 有什么问题？
如果 `SETNX` 后、`EXPIRE` 前进程崩溃，锁将永远无法释放。Redis 2.6.12+ 提供了 `SET` 命令的 `NX` 和 `EX` 选项，可原子地设置锁和过期时间。

### 3. 什么是 Redlock 算法？
Redlock 是 Redis 作者提出的分布式锁算法，用于多节点 Redis（N 个独立节点）。步骤：
1. 获取当前时间
2. 依次向所有节点请求锁（使用相同的 key 和随机值），每个请求设置超时（远小于锁有效时间）
3. 计算获取锁总耗时，如果从多数节点（>= N/2+1）成功且总耗时 < 锁有效时间，则认为获取成功
4. 锁的实际有效时间 = 原有效时间 - 总耗时
5. 如果失败，向所有节点发送释放锁请求


## 八、事务、Pipeline 与异步队列

### 1. Redis 支持事务吗？有什么特点？
支持事务（`MULTI`、`EXEC`、`DISCARD`、`WATCH`），但**不支持回滚**。事务中某条命令执行失败，其他命令仍会执行。

### 2. 什么是 Pipeline？与事务的区别？
Pipeline 将多个命令一次性发送到 Redis，减少网络往返时间，但命令之间**不保证原子性**（可能被其他客户端的命令穿插）。事务则保证原子执行。

### 3. 如何使用 Redis 实现异步队列？
- 使用 List 结构：`RPUSH` 生产消息，`LPOP` 消费消息。无消息时可 `BLPOP` 阻塞等待。
- 缺点：消费者下线时消息丢失。
- 实现 1:N 消息：使用发布/订阅（`PUBLISH` / `SUBSCRIBE`），但发布订阅不持久化消息。

### 4. 如何确保消息不丢失？
使用专业的消息队列（如 RabbitMQ、Kafka），或使用 Redis 的 Stream（5.0+）类型，支持消费者组、消息确认和持久化。


## 九、内存管理与优化

### 1. Redis 内存模型中的几个重要指标
- `used_memory`：分配器分配的总内存（字节）
- `used_memory_rss`：进程实际占用的操作系统内存（含碎片、自身开销）
- `mem_fragmentation_ratio` = used_memory_rss / used_memory，大于 1.5 说明碎片严重
- `mem_allocator`：内存分配器（默认 jemalloc）

### 2. Redis 内存如何划分？
- **数据**：用户存储的数据
- **进程自身**：代码、常量池等（几 MB）
- **缓冲内存**：客户端缓冲区、复制积压缓冲区、AOF 缓冲区
- **内存碎片**：分配和回收产生的不可用空间

### 3. 如何手写一个 LRU 缓存？
利用 `LinkedHashMap` 实现（Java 示例）：
```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;
    public LRUCache(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75f) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > CACHE_SIZE;
    }
}
```


## 十、运维与工具命令

### 1. 常用管理命令
```bash
dbsize                     # 当前数据库 key 数量
info                       # 服务器状态统计
monitor                    # 实时监控请求
shutdown                   # 保存并关闭
config get/set parameter   # 获取/设置配置参数
config resetstat           # 重置 info 统计
debug object key           # 获取 key 调试信息
flushdb                    # 清空当前数据库
flushall                   # 清空所有数据库
```

### 2. 常用工具命令
```bash
redis-server               # 启动服务器
redis-cli                  # 命令行客户端
redis-benchmark -n 100000 -c 50   # 性能测试（10万请求，50并发）
redis-check-aof            # AOF 日志检查
redis-check-dump           # RDB 文件检查
```

### 3. 如何在海量数据下正确迭代 key？
使用 `SCAN` 系列命令（`SCAN`、`SSCAN`、`HSCAN`、`ZSCAN`），避免 `KEYS` 阻塞。特点：
- 每次返回游标和少量数据
- 时间复杂度 O(1) 每次，整体 O(N)
- 允许在迭代过程中数据变化

### 4. 如何查看 Redis 内存使用详情？
```bash
INFO memory
```

### 5. 如何开启慢查询分析？
配置 `slowlog-log-slower-than`（微秒）和 `slowlog-max-len`，然后使用 `SLOWLOG GET` 查看。

### 6. 如何判断某个 key 是否存在？
```bash
EXISTS key
```

### 7. 如何删除 key？
```bash
DEL key1 key2 ...
```


## 十一、扩展功能（GEO、HyperLogLog、Bitmap、Bloom Filter）

### 1. Redis 如何实现“附近的人”功能？
使用 `GEO` 系列命令：
```bash
GEOADD key longitude latitude member [member...]
GEORADIUS key longitude latitude radius m/km [WITHCOORD] [WITHDIST] [ASC|DESC]
```
底层使用 `Zset` 存储，分值经过编码（Geohash）。

### 2. HyperLogLog 和 Bitmap 分别用于什么场景？
- **HyperLogLog**：基数统计（如 UV），误差约 0.81%，内存固定 12KB
- **Bitmap**：位图，适合布尔值统计（如签到、在线状态），节省空间

### 3. Redis 如何实现布隆过滤器？
通过 `RedisBloom` 模块（Redis 4.0+）。命令示例：
```bash
BF.ADD key item
BF.EXISTS key item
```


## 十二、高级话题（协议、Lua、性能）

### 1. Redis 的通信协议是什么？
RESP（REdis Serialization Protocol），特点：简单、快速解析、可读性好。

### 2. Redis 支持 Lua 脚本吗？有什么好处？
支持 `EVAL` 命令。好处：
- 原子性执行多个命令
- 减少网络往返
- 实现复杂逻辑（如分布式锁释放）

### 3. Redis 6.0 为什么要引入多线程？
仅用于网络 I/O 读写，执行命令仍是单线程。多线程可提高大包（如批量写入）的吞吐量，缓解单线程网络瓶颈。

### 4. Redis 的并发竞争问题如何解决？
单进程单线程模型下，客户端请求天然串行。但多个客户端同时修改同一 key 时，可使用分布式锁或 Lua 脚本实现原子 CAS 操作。`SETNX` 可实现简单锁。
