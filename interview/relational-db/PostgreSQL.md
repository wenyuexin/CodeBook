PostgreSQL

## 一、基础概念与架构

### 1. PostgreSQL 与 MySQL 的核心区别有哪些？

**参考答案：**

| 特性 | PostgreSQL | MySQL（InnoDB） |
|------|------------|------------------|
| 许可证 | PostgreSQL 开源协议（类似 MIT/BSD） | GPL |
| 标准兼容性 | 高度遵循 SQL 标准 | 部分非标准扩展 |
| 数据类型 | 丰富（数组、JSONB、HSTORE、范围、几何、网络地址、枚举等） | 基础类型 + JSON（后期支持） |
| 索引类型 | B-Tree、Hash、GiST、SP-GiST、GIN、BRIN、部分索引、表达式索引 | B+Tree、Hash、全文、空间 |
| 并发模型 | MVCC（基于元组多版本，无回滚段） | MVCC（基于 Undo Log） |
| 复制与高可用 | 物理流复制、逻辑复制、内置主备、同步/异步 | 主从复制、组复制、半同步 |
| 扩展性 | 支持自定义函数、操作符、数据类型、索引方法；有 FDW（外部数据包装器） | 插件有限 |
| 适用场景 | 复杂查询、分析、地理空间、HTAP、高并发 OLTP 也可 | 轻量级 OLTP、Web 应用 |

### 2. PostgreSQL 的进程架构是怎样的？

**参考答案：**

- **Postmaster**（主进程）：监听连接请求，派生子进程。
- **每个客户端连接对应一个独立的 backend 进程**（无线程模型），该进程负责解析、优化、执行 SQL。
- **辅助进程**：
  - `walwriter`：将 WAL 缓冲区写入磁盘。
  - `autovacuum`：自动清理死元组，更新统计信息。
  - `checkpointer`：执行检查点，刷脏页。
  - `bgwriter`：后台写脏页，减少检查点压力。
  - `stats collector`：收集数据库活动统计信息。
  - `logger`：记录日志。

> 对比 MySQL：MySQL 使用线程模型，一个连接一个线程；PostgreSQL 使用进程模型，隔离性更强，但连接数很高时资源消耗更大。

### 3. 什么是 MVCC？PostgreSQL 如何实现 MVCC？

**参考答案：**

- PostgreSQL 的 MVCC 通过**在堆表元组上保留多版本**实现，不需要回滚段。
- 每个元组头包含：
  - `xmin`：插入该元组的事务 ID。
  - `xmax`：删除或更新该元组的事务 ID（更新 = 旧元组标记 xmax + 插入新元组）。
  - `ctid`：指向新版本元组的指针（更新时）。
- 事务开始时获得 `xid` 和快照（当前所有活跃事务 ID）。
- 可见性判断：元组 `xmin` < 当前快照中最小活跃 xid 且 `xmax` 无效或 `xmax` >= 当前事务或回滚 → 可见。
- 旧版本元组在 `VACUUM` 时清理。

### 4. 什么是 `VACUUM`？为什么要做 `VACUUM`？

**参考答案：**

- `VACUUM` 回收死元组占用的空间，更新可见性映射表（Visibility Map），让后续扫描跳过全死块。
- **必要性**：
  - 防止表膨胀（空间只增不减）。
  - 更新统计信息供优化器使用（`ANALYZE`）。
  - 防止事务 ID 回卷（Transaction ID Wraparound）。
- 类型：
  - 普通 `VACUUM`：不锁表，可与读并行，但不能回收所有空间。
  - `VACUUM FULL`：重写表，完全回收空间，但会锁表。
  - `AUTOVACUUM`：后台自动运行，避免手动维护。

---

## 二、数据类型与索引

### 5. PostgreSQL 有哪些高级数据类型？举例说明使用场景。

**参考答案：**

- **数组**：`text[]`，例如存储标签。
- **JSON/JSONB**：存储半结构化数据，JSONB 支持索引（GIN）。
- **HSTORE**：键值对类型。
- **范围类型**：`int4range`、`tsrange`，用于时间/区间调度。
- **几何类型**：`point`、`polygon`，地理信息系统。
- **网络地址**：`inet`、`cidr`，IP 处理。
- **枚举**：`CREATE TYPE mood AS ENUM ('sad','ok','happy')`。
- **UUID**：存储 UUID。
- **全文检索**：`tsvector`、`tsquery`。

### 6. PostgreSQL 有哪些索引类型？分别适用什么场景？

**参考答案：**

| 索引类型 | 适用场景 |
|----------|----------|
| **B-Tree**（默认） | 等值、范围、排序、`<`、`<=`、`=`、`>=`、`>`、`BETWEEN`、`IN`、`LIKE 'abc%'` |
| **Hash** | 等值查询（极少使用，一般 B-Tree 也能覆盖） |
| **GiST**（通用搜索树） | 几何数据（`point`、`circle`）、全文检索（配合 `tsvector`）、范围类型（`&&`、`@>`）、数组（`&&`） |
| **SP-GiST** | 分区树，用于点、四叉树、基数树（如 IP 地址、坐标） |
| **GIN**（广义倒排索引） | 包含多值键的数据，如数组、JSONB、全文检索（`tsvector`）、`hstore` |
| **BRIN**（块范围索引） | 超大表且数据物理顺序与逻辑顺序一致（如时序数据），索引极小 |
| **部分索引** | `CREATE INDEX ... WHERE active = true`，过滤高频访问子集 |
| **表达式索引** | `CREATE INDEX ON users (lower(name))`，加速函数查询 |

### 7. 什么是部分索引和表达式索引？举例说明。

**参考答案：**

- **部分索引**：只对表中满足条件的一部分行建立索引。
  ```sql
  CREATE INDEX idx_active_users ON users (last_login) WHERE active = true;
  ```
  适用于查询主要针对一个子集（如未删除、激活状态）。

- **表达式索引**：基于函数或表达式建立索引。
  ```sql
  CREATE INDEX idx_lower_email ON users (lower(email));
  ```
  加速 `WHERE lower(email) = 'xxx'` 查询，避免全表扫描。

### 8. 什么是 BRIN 索引？什么场景下使用？

**参考答案：**

- BRIN（Block Range Index）将表连续的数据块分组，每个组存储该块范围内数据的最小值和最大值。
- 适合**天然有序**且**超大表**的场景，如时序数据（按时间插入的日志、事件）。
- 优点：索引极小（通常几十 MB 可索引 TB 级数据）。
- 缺点：等值查询可能返回很多假阳性，需要额外扫描块内数据。
- 示例：
  ```sql
  CREATE INDEX idx_created_at_brin ON logs USING BRIN (created_at);
  ```

---

## 三、事务与并发

### 9. PostgreSQL 的事务隔离级别有哪些？默认是什么？

**参考答案：**

| 隔离级别 | 脏读 | 不可重复读 | 幻读 | 序列化异常 |
|----------|------|------------|------|------------|
| READ UNCOMMITTED（实际同 READ COMMITTED） | 不可能 | 可能 | 可能 | 可能 |
| READ COMMITTED（默认） | 不可能 | 可能 | 可能 | 可能 |
| REPEATABLE READ | 不可能 | 不可能 | 不可能（PG 实现避免） | 可能 |
| SERIALIZABLE | 不可能 | 不可能 | 不可能 | 不可能（使用 SSI 检测冲突） |

- PostgreSQL 的 `READ UNCOMMITTED` 实际等同于 `READ COMMITTED`（因为 MVCC 不支持脏读）。
- `REPEATABLE READ` 在 PostgreSQL 中通过快照隔离避免了幻读（与 MySQL 不同，MySQL RR 下仍有幻读但通过 Next-Key Lock 避免）。
- `SERIALIZABLE` 使用**序列化快照隔离（SSI）**，检测读写冲突，自动回滚可能导致序列化异常的事务。

### 10. PostgreSQL 如何实现乐观锁？

**参考答案：**

- 常用方法：使用 **`xmin` 系统列**（元组插入事务 ID）作为版本号。
  ```sql
  SELECT xmin, * FROM products WHERE id = 1;
  -- 应用计算新值
  UPDATE products SET price = 200 WHERE id = 1 AND xmin = previous_xmin;
  ```
  若影响行数为 0，说明数据已被其他事务修改。
- 也可以显式增加 `version` 列（更可控、更直观）。

### 11. 什么是行级锁？PostgreSQL 中有哪些锁模式？

**参考答案：**

- PostgreSQL 通过 **行级锁**（tuple locks）实现并发控制，不是通过存储引擎的物理锁，而是通过事务 ID 和快照。
- 行级锁模式（通过 `SELECT ... FOR` 获得）：
  - `FOR UPDATE`：排他锁，禁止其他事务加任何锁。
  - `FOR NO KEY UPDATE`：排他锁，但允许其他事务加 `FOR KEY SHARE`。
  - `FOR SHARE`：共享锁，其他事务可读但不能 `FOR UPDATE`。
  - `FOR KEY SHARE`：共享锁，只阻止其他事务修改键值。
- 表级锁（`LOCK TABLE`）：`ACCESS SHARE`、`ROW SHARE`、`ROW EXCLUSIVE`、`SHARE UPDATE EXCLUSIVE`、`SHARE`、`SHARE ROW EXCLUSIVE`、`EXCLUSIVE`、`ACCESS EXCLUSIVE`。

### 12. 什么是 SSI（Serializable Snapshot Isolation）？

**参考答案：**

- 标准 SQL 的 `SERIALIZABLE` 隔离级别通过 SSI 实现，无需传统锁。
- 原理：每个事务看到数据库的快照，同时记录读写冲突（rw-conflict）。
- 若检测到两个并发事务的依赖关系形成循环（可能导致序列化异常），则回滚其中一个事务。
- 代价：冲突较多时，回滚率增加，但避免了显式锁的性能开销。

---

## 四、SQL 优化与执行计划

### 13. 如何使用 `EXPLAIN` 分析 SQL 性能？关键字段有哪些？

**参考答案：**

- `EXPLAIN` 显示执行计划，`EXPLAIN (ANALYZE, BUFFERS)` 真实执行并显示磁盘块命中情况。
- 关键字段：
  - **Seq Scan**：全表扫描，通常需要优化。
  - **Index Scan**：索引扫描（回表）。
  - **Index Only Scan**：覆盖索引扫描，无需回表。
  - **Bitmap Heap Scan / Bitmap Index Scan**：先用索引收集匹配行的物理位置，再批量回表。
  - **Nested Loop**：嵌套循环连接，适合小表驱动大表且有索引。
  - **Hash Join**：哈希连接，适合等值连接且无索引。
  - **Merge Join**：排序合并连接，适合已排序数据。
  - **Sort**：显式排序，可能产生临时文件。
  - **Filter**：过滤条件。
  - **Buffers: shared hit/read/dirtied**：缓存命中率。

### 14. 如何优化一条慢查询？

**参考答案：**

1. 开启 `log_min_duration_statement` 捕获慢 SQL。
2. 执行 `EXPLAIN (ANALYZE, BUFFERS, VERBOSE)` 分析实际执行计划。
3. 关注：
   - 是否发生 **Seq Scan** 在大型表上 → 添加索引。
   - 是否有 **Sort** 或 **Group** 无索引 → 创建合适索引或调整查询。
   - 是否有 **Bitmap Heap Scan** 但 `rows` 估计偏差大 → 更新统计信息（`ANALYZE`）。
   - 是否有 **Nested Loop** 驱动表过大 → 改为 Hash Join 或调整查询逻辑。
4. 检查是否存在隐式类型转换导致索引失效（如 `WHERE int_col = '123'`）。
5. 改写查询：用 `EXISTS` 代替 `IN`，用 `LATERAL` 优化依赖子查询，避免 `SELECT *`。
6. 增加表分区（声明式分区）减少扫描范围。
7. 调整 `work_mem` 让排序/哈希在内存中完成。

### 15. 什么是 `pg_stat_statements`？如何利用它定位问题？

**参考答案：**

- `pg_stat_statements` 是核心统计扩展，记录 SQL 的执行次数、总耗时、平均耗时、IO 时间、共享块命中/读取等。
- 启用步骤：
  ```sql
  CREATE EXTENSION pg_stat_statements;
  ```
  并修改 `postgresql.conf` 加载库。
- 常用查询：
  ```sql
  SELECT query, calls, total_time, mean_time, rows 
  FROM pg_stat_statements 
  ORDER BY total_time DESC LIMIT 10;
  ```
- 帮助快速找出耗时最多、调用最频繁、IO 最重的 SQL。

---

## 五、高级特性

### 16. PostgreSQL 的 JSONB 与 JSON 有什么区别？如何索引 JSONB？

**参考答案：**

- **JSON**：文本存储，输入时验证合法性，但不能索引，保留格式（包括空格、键顺序）。
- **JSONB**：二进制存储，解析后按规则排序，删除重复键，支持 GIN 索引，适用于高效查询和操作。
- **索引 JSONB**：
  - 默认 GIN 索引：`CREATE INDEX idx_jsonb ON table USING gin (jsonb_col)`，支持 `?`、`?|`、`?&`、`@>` 等操作符。
  - 如果只查询特定路径，可以创建表达式索引：`CREATE INDEX idx_foo ON table ((jsonb_col->>'foo'))`。

### 17. PostgreSQL 的全文搜索如何使用？

**参考答案：**

- 核心类型：`tsvector`（文档的规范化词位）和 `tsquery`（查询条件）。
- 示例：
  ```sql
  CREATE INDEX idx_fts ON articles USING gin(to_tsvector('english', title || ' ' || body));
  SELECT * FROM articles WHERE to_tsvector('english', title || ' ' || body) @@ to_tsquery('english', 'database & index');
  ```
- 支持排名：`ts_rank` 函数。
- 支持字典（停止词、词干提取）和配置（例如 `pg_catalog.english`）。

### 18. 什么是 PostgreSQL 的声明式分区？有哪些分区方式？

**参考答案：**

- 声明式分区（10+ 版本）：通过 `PARTITION BY` 定义分区表。
- 分区方式：
  - **范围分区**（Range）：按日期、数字范围。
    ```sql
    CREATE TABLE sales (id int, sale_date date) PARTITION BY RANGE (sale_date);
    CREATE TABLE sales_2025 PARTITION OF sales FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');
    ```
  - **列表分区**（List）：按离散值（如区域）。
    ```sql
    PARTITION BY LIST (region);
    ```
  - **哈希分区**（Hash）：均匀分布数据。
- 优点：分区裁剪（查询只扫描相关分区）、批量删除（`DROP PARTITION`）、并行维护。

### 19. 什么是 FDW（Foreign Data Wrapper）？

**参考答案：**

- FDW 允许 PostgreSQL 访问外部数据源（其他 PG、MySQL、Oracle、CSV、Redis、MongoDB 等），像本地表一样查询。
- 示例（访问远程 PG）：
  ```sql
  CREATE EXTENSION postgres_fdw;
  CREATE SERVER remote_server FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'x.x.x.x', dbname 'remote_db');
  CREATE USER MAPPING FOR local_user SERVER remote_server OPTIONS (user 'remote_user', password 'xxx');
  CREATE FOREIGN TABLE remote_table (id int, name text) SERVER remote_server OPTIONS (schema_name 'public', table_name 'users');
  ```
- 适用场景：数据整合、跨库查询、数据迁移。

### 20. PostgreSQL 如何实现逻辑复制与物理复制？区别是什么？

**参考答案：**

| 特性 | 物理流复制 | 逻辑复制 |
|------|------------|----------|
| 复制内容 | 磁盘块更改（WAL 日志） | 行级别变更（发布/订阅） |
| 版本兼容 | 主备必须相同版本和操作系统 | 允许跨版本，甚至跨不同数据库（如 PG 到 PG） |
| 复制粒度 | 整个数据库实例 | 可选择性复制某些表 |
| 用途 | 高可用、只读备库、容灾 | 数据集成、升级迁移、报表库 |
| 冲突处理 | 无冲突（备库只读） | 可配置冲突策略（跳过、应用） |

- **物理复制**：备库通过 `pg_basebackup` 搭建，应用 WAL 流，无法写入。
- **逻辑复制**：发布者将表更改发布，订阅者接收并应用，支持双向（BDR 扩展）。

---

## 六、备份与高可用

### 21. PostgreSQL 的备份方式有哪些？

**参考答案：**

- **SQL 导出**（`pg_dump` / `pg_dumpall`）：逻辑备份，可跨版本，适合中小库。
  - `pg_dump -Fc`：自定义格式，支持并行恢复。
  - `pg_restore`：恢复。
- **物理备份**（文件系统级 + WAL 归档）：
  - `pg_basebackup`：制作基础备份 + 持续归档 WAL，可实现 PITR（任意时间点恢复）。
- **第三方工具**：`barman`、`pgbackrest`、`wal-g`。

### 22. 如何搭建 PostgreSQL 的高可用集群？

**参考答案：**

- **原生流复制 + 自动故障转移**：
  - 使用 `repmgr`：管理主备切换、监控、故障自动 promotion。
  - 使用 `Patroni` + `etcd/consul` + `haproxy`：提供高可用、自动切换、优雅故障处理。
- **PGPool-II**：连接池、负载均衡、自动故障转移（但较笨重）。
- **逻辑复制**：不能用于自动故障转移（延迟较大），但可用于多主（BDR）。
- **分布式方案**：Citus（分片扩展，用于分析/HTAP）。

### 23. 什么是 WAL（Write-Ahead Logging）？参数优化点有哪些？

**参考答案：**

- WAL 保证持久性和崩溃恢复：任何修改先写入 WAL 缓冲区，commit 时同步到磁盘，再修改数据文件。
- 关键参数：
  - `wal_level`：`replica` 或 `logical`（开启复制）。
  - `synchronous_commit`：`on`（同步）或 `off`（异步，性能高但可能丢事务）。
  - `wal_sync_method`：`open_datasync` / `fdatasync`（Linux 默认）。
  - `full_page_writes`：防止部分写，建议开启。
  - `checkpoint_timeout` / `max_wal_size`：控制检查点频率。

---

## 七、常见陷阱与实战

### 24. 为什么 `VACUUM` 无法回收空间？如何解决表膨胀？

**参考答案：**

- 原因：
  - 长事务（`xmin` 过旧）导致死元组无法被清理。
  - `VACUUM` 不及时或 `autovacuum` 配置不足。
  - 大量更新导致元组碎片。
- 解决：
  - 监控长事务：`SELECT * FROM pg_stat_activity WHERE state = 'idle in transaction'`。
  - 调优 `autovacuum`：`autovacuum_vacuum_scale_factor`、`autovacuum_vacuum_threshold`。
  - 对膨胀严重的表执行 `VACUUM FULL`（锁表，需停业务）或使用 `pg_repack` 在线重建。

### 25. 什么是事务 ID 回卷（Wraparound）？如何预防？

**参考答案：**

- PostgreSQL 使用 32 位事务 ID（约 42 亿），当超过最大值后回卷到 3（0 和 1 保留），导致旧元组的 `xmin` 突然变成“未来”事务，造成数据丢失。
- 预防：定期 `VACUUM` 将所有数据库的 `datfrozenxid` 推进。
- 监控：`SELECT datname, age(datfrozenxid) FROM pg_database`。
- 当 age 超过 `autovacuum_freeze_max_age`（默认 2 亿）时，autovacuum 会强制冻结（`FREEZE`）。
- 危险阈值：约 20 亿，接近时必须紧急冻结，否则数据库会拒绝写入。

### 26. 如何从 PostgreSQL 中删除大表并立即释放磁盘空间？

**参考答案：**

- 直接 `DROP TABLE` 只能标记文件为可重用，不会立即释放给操作系统（但新数据会覆盖）。
- 要立即释放磁盘空间，执行：
  ```sql
  DROP TABLE big_table;
  -- 然后对所在表空间执行（如果独立表空间）
  VACUUM FULL;
  ```
  或者删除后使用 `pg_repack`。
- 如果表空间是单独目录，可以重启数据库（非生产）或使用 `TRUNCATE` 后 `VACUUM FULL`。

### 27. `LIKE 'abc%'` 和 `LIKE '%abc'` 都能用 B-Tree 索引吗？

**参考答案：**

- `LIKE 'abc%'`（前缀匹配）可以使用 B-Tree 索引，因为字符串排序后前缀有序。
- `LIKE '%abc'`（后缀匹配）无法使用普通 B-Tree，需要使用 **pg_trgm** 扩展（GIN 索引）或反向索引。
  ```sql
  CREATE EXTENSION pg_trgm;
  CREATE INDEX idx_trgm ON table USING gin (col gin_trgm_ops);
  SELECT * FROM table WHERE col LIKE '%abc';
  ```
