# MySQL

## 一、MySQL 基础

### 1. MySQL 体系结构（一条 SQL 的执行流程）

1. **连接层**：管理连接、权限验证。
2. **服务层**：
   - 查询缓存（8.0 已移除）
   - 解析器（词法/语法分析，生成解析树）
   - 预处理器（检查表/列存在、权限）
   - 优化器（生成执行计划，选择索引）
   - 执行引擎（调用存储引擎接口）
3. **存储引擎层**：InnoDB、MyISAM 等，负责数据读写。
4. **文件层**：数据文件（.ibd）、日志文件（binlog、redo log）。

### 2. varchar(50) 和 varchar(500) 的区别

存储实际内容时，varchar 占用 **实际字符长度 + 1~2 字节长度标识**。区别在于：
- 内存临时表可能分配定义长度，过长的定义会消耗更多内存
- 索引长度限制（如 767 字节），过大的 varchar 无法完全索引
- 行大小限制（65535 字节），定义过长会导致行溢出

---

## 二、存储引擎

### 1. InnoDB、MyISAM、Memory 对比

| 特性 | InnoDB | MyISAM | Memory |
|------|--------|--------|--------|
| 存储限制 | 64TB | 有 | 有 |
| 事务安全 | 支持 | - | - |
| 锁机制 | 行锁 | 表锁 | 表锁 |
| B+Tree 索引 | 支持 | 支持 | 支持 |
| Hash 索引 | - | - | 支持 |
| 全文索引 | 支持（5.6+） | 支持 | - |
| 空间使用 | 高 | 低 | N/A |
| 批量插入速度 | 低 | 高 | 高 |
| 支持外键 | 支持 | - | - |

### 2. InnoDB 原理

- **页大小**：默认 16KB，与磁盘扇区对齐，能容纳足够多的索引行。
- **自适应哈希索引（AHI）**：InnoDB 会监控对 B+Tree 索引的访问，如果发现某个页被频繁等值查询，就会在内存中自动建立哈希索引加速访问。只存在于内存，只对热点数据生效。
- **事务支持数**：128*1023（mysql5.6 innodb1.2），通过回滚段存储事务。

---

## 三、索引

### 1. 索引类型

1. **普通索引**：最基本的索引类型，没有唯一性限制。
2. **唯一索引**：列不允许有重复值。
3. **主键索引**：特殊的唯一索引，不允许 NULL 值，一个表只能有一个主键。
4. **组合索引**：由多个列组合而成，遵循最左前缀原则。
5. **全文索引**：全文搜索，仅 MyISAM 和 InnoDB 支持。
6. **空间索引**：MySQL 5.7+ 支持 GIS 数据类型。

### 2. B+Tree 与 B-Tree 区别

- **关键字数量**：B+树分支结点有 m 个关键字，叶子结点也有 m 个；B 树有 m 个子结点但只有 m-1 个关键字。
- **存储位置**：B+树非叶子节点关键字只起索引作用，实际数据在叶子节点；B 树非叶子节点也存储关键字。
- **查询稳定性**：B 树找到具体数值就结束，B+树必须走到叶子节点才结束，查询更稳定。

### 3. 聚簇索引与辅助索引

**聚簇索引**：每个 InnoDB 表都拥有聚簇索引，按以下规则创建：
- 有主键 → 利用主键创建聚簇索引
- 无主键 → 选择非空唯一索引创建聚簇索引
- 也没有 → 隐式创建自增列作为聚簇索引

**辅助索引**：叶子节点存放主键的键值。查询需要两步：先通过辅助索引找主键，再通过主键在聚簇索引中找行记录（两次 B+树搜索）。

### 4. 主键索引与唯一索引的区别

1. 主键是一种约束，唯一索引是一种索引，本质不同。
2. 主键创建后一定包含唯一性索引，唯一性索引并不一定就是主键。
3. 唯一性索引列允许空值，主键列不允许为空值。
4. 一个表最多只能创建一个主键，但可以创建多个唯一索引。
5. 主键更适合不易更改的唯一标识，如自动递增列、身份证号。

### 5. 联合索引最左前缀原则

```sql
-- 联合索引 (a, b, c)
WHERE a=1 AND b=2 AND c=3  -- 用到 a,b,c
WHERE a=1 AND b=2          -- 用到 a,b
WHERE a=1                  -- 用到 a
WHERE b=2 AND c=3          -- 用不到（缺少 a）
WHERE a=1 AND c=3          -- 只用到 a（跳过 b）
```

### 6. 什么是回表？如何避免？

**回表**：使用二级索引查询时，先找到主键值，再根据主键到聚簇索引中获取完整行数据的过程。

**避免方法**：
- 使用覆盖索引（索引包含查询所需所有字段）
- 尽量减少 `SELECT *`
- 使用索引下推（ICP）减少回表次数

### 7. 什么情况下索引会失效？

1. 在 WHERE 子句中对索引列使用函数或计算
2. 使用 NOT、!=、<> 操作符
3. 使用 OR 连接条件（除非所有 OR 条件都有索引）
4. 列类型不匹配（如字符串列与数字比较）
5. 使用 LIKE 以通配符开头（如 '%abc'）
6. 复合索引不遵循最左前缀原则
7. 数据量很少时，优化器可能选择全表扫描

---

## 四、事务与锁

### 1. ACID 特性

- **原子性（Atomicity）**：事务是不可分割的工作单位，要么全部执行，要么全部不执行。
- **一致性（Consistency）**：事务执行前后，数据库从一个一致状态变到另一个一致状态。
- **隔离性（Isolation）**：多个事务并发执行时，一个事务的执行不应影响其他事务。
- **持久性（Durability）**：一旦事务提交，其结果就是永久性的。

### 2. 事务隔离级别

| 隔离级别 | 脏读 | 不可重复读 | 幻读 |
|----------|------|------------|------|
| READ UNCOMMITTED | 可能 | 可能 | 可能 |
| READ COMMITTED | 不可能 | 可能 | 可能 |
| REPEATABLE READ（默认） | 不可能 | 不可能 | 可能（InnoDB 通过 Next-Key Lock 可解决） |
| SERIALIZABLE | 不可能 | 不可能 | 不可能 |

### 3. MVCC（多版本并发控制）

MVCC 通过维护数据的多个版本来实现并发控制：
- **版本保留**：数据被修改时，不直接覆盖原有数据，而是创建新版本
- **读不阻塞写，写不阻塞读**：读操作访问旧版本数据，写操作创建新版本
- **事务隔离**：不同事务看到的数据版本可能不同

**InnoDB 实现**：
- 每行记录隐藏列：`DB_TRX_ID`（最近修改事务ID）、`DB_ROLL_PTR`（回滚指针指向 undo log）
- **Read View**：事务开始时确定哪些活跃事务可见
- RR 级别下，事务第一个 SELECT 生成 Read View，整个事务期间复用
- RC 级别下，每次 SELECT 都重新生成 Read View

### 4. InnoDB 锁类型

- **共享锁（S）**：读锁，`SELECT ... LOCK IN SHARE MODE`
- **排他锁（X）**：写锁，`SELECT ... FOR UPDATE`
- **意向锁（IS/IX）**：表级锁，表示事务准备加行锁
- **行锁（Record Lock）**：锁住索引记录
- **间隙锁（Gap Lock）**：锁住记录之间的间隙，防止幻读
- **Next-Key Lock** = 行锁 + 间隙锁，锁定一个区间及记录本身

### 5. 死锁

**定义**：两个或多个事务相互持有对方需要的锁，无法继续执行。

**避免方法**：
- 固定顺序访问表和行
- 尽量使用较短的隔离级别（如 RC）
- 为 SQL 添加合适的索引，减少行锁升级为表锁
- 事务中避免用户交互，快速提交

---

## 五、SQL 优化

### 1. 执行计划（EXPLAIN）

- **type**：const/eq_ref/ref/range/index/ALL（从好到差）
- **possible_keys / key**：实际使用的索引
- **Extra**：
  - Using index：覆盖索引
  - Using where：需要过滤
  - Using filesort：需要额外排序（需优化）
  - Using temporary：需要临时表（需优化）

### 2. 慢查询优化

1. 开启慢查询日志，定位具体 SQL
2. 使用 `EXPLAIN` 查看执行计划
3. 查看 `SHOW PROCESSLIST`，检查是否有锁等待
4. 分析索引使用情况，必要时强制索引
5. 查看服务器状态（`SHOW STATUS`）如 Handler_read 等

### 3. 超大分页优化

传统 `LIMIT offset, size` 会先扫描 offset+size 行，效率极低。

**优化方法**：
1. **延迟关联**：先查主键，再关联原表
   ```sql
   SELECT * FROM t
   JOIN (SELECT id FROM t ORDER BY id LIMIT 1000000,10) tmp ON t.id = tmp.id;
   ```
2. **游标分页**：记录上一次的 ID
   ```sql
   SELECT * FROM t WHERE id > last_id ORDER BY id LIMIT 10;
   ```
3. **子查询**（配合覆盖索引）
   ```sql
   SELECT * FROM t WHERE id >= (SELECT id FROM t ORDER BY id LIMIT 1000000,1) LIMIT 10;
   ```

### 4. 索引下推（ICP）

Index Condition Pushdown 是 MySQL 5.6 引入的优化，在**索引遍历过程中**就应用 WHERE 条件中的部分过滤，减少回表次数。

例如：联合索引 `(name, age)`，查询 `WHERE name LIKE '张%' AND age=20`。没有 ICP 时，先回表再过滤 age；有 ICP 时，在索引内部过滤 age，不满足的直接跳过。

### 5. 其他优化技巧

- 避免 `SELECT *`，只查询需要的列
- 优化 JOIN 操作，确保 JOIN 字段有索引
- 避免在 WHERE 子句中对字段使用函数或计算
- 考虑分表或分区处理大数据量表
- 适当使用子查询或临时表
- 定期更新统计信息

---

## 六、主从复制与高可用

### 1. 主从复制原理

1. 主库将变更写入 binlog 日志
2. 从库 IO 线程连接主库，拉取 binlog 并写入 relay log
3. 从库 SQL 线程读取 relay log 并重放

**复制方式**：
- **异步复制**：主库不关心从库是否接收（默认）
- **半同步复制**：至少一个从库接收 binlog 后才提交事务
- **GTID 复制**：基于全局事务 ID，简化故障切换

**binlog 格式**：STATEMENT、ROW、MIXED

### 2. 读写分离

基于主从复制架构：写主库，数据自动同步到从库。

**延时问题解决方法**：
- 分库：将一个主库拆分为多个主库
- 打开 MySQL 并行复制
- 写代码时注意，插入后立马查询可能查不到
- 对必须立即查询的场景设置直连主库

### 3. 高可用方案

- **主从复制 + 自动切换**：MHA、Orchestrator、MySQL Router
- **MGR（MySQL Group Replication）**：原生组复制，基于 Paxos，支持多主
- **InnoDB Cluster**：MySQL Shell + MGR + Router
- **ProxySQL / MaxScale**：读写分离、负载均衡

---

## 七、日志系统

### 1. Redo Log（重做日志）

- InnoDB 保证持久性，崩溃恢复用
- 物理日志，循环写
- 记录物理修改，用于恢复已提交的事务

### 2. Undo Log（回滚日志）

- 实现 MVCC 和事务回滚
- 逻辑日志
- 记录行的旧版本，用于回滚和一致性读

### 3. Binlog（二进制日志）

- Server 层，用于主从复制、数据恢复
- 逻辑日志，记录 SQL 或行变更
- 三种格式：STATEMENT、ROW、MIXED

### 4. 其他日志

- **Error Log**：错误日志
- **Slow Query Log**：慢查询日志
- **General Query Log**：通用查询日志（一般不开启）

---

## 八、实战场景

### 1. 乐观锁与悲观锁

**悲观锁**：`SELECT ... FOR UPDATE` 或 `LOCK IN SHARE MODE`，事务结束释放。

**乐观锁**：通过版本号（version）实现，不加锁。
```sql
UPDATE table SET value = new_value, version = version+1
WHERE id = 1 AND version = old_version;
```
影响行数为 0 则重试或报错。

### 2. 在线修改表结构（避免锁表）

MySQL 5.6+ InnoDB 支持 Online DDL：
- `ALGORITHM=INPLACE`：原地修改，允许并发 DML
- `LOCK=NONE`：完全允许读写

常用工具：**pt-online-schema-change**（Percona Toolkit），通过创建影子表、触发器、交换表名实现无锁变更。

### 3. 超大表添加索引

- 使用 `pt-online-schema-change` 在线添加，不阻塞业务
- 或创建新表，导入旧表数据（分批），再 rename 交换
- 注意磁盘空间、IO 压力和主从延迟

### 4. Redis 与 MySQL 数据一致性

**方案：先写数据库，再删缓存**

- 操作顺序问题、并发操作可能导致不一致
- 缓存加过期时间兜底
- 或使用 mysql 同步到 redis 的方案

### 5. 常见陷阱

**为什么 `WHERE a+1 = 5` 无法使用索引？**
- 对索引列进行运算或函数会导致索引失效
- 应改写为：`WHERE a = 4`

**COUNT(*) / COUNT(1) / COUNT(列) 区别：**
- `COUNT(*)`：统计所有行，包括 NULL
- `COUNT(1)`：与 `COUNT(*)` 效果相同
- `COUNT(列)`：统计该列非 NULL 值的行数

---

## 九、数据库设计基础

### 1. JOIN 类型

- **INNER JOIN**：返回两表中匹配的行
- **LEFT JOIN**：返回左表所有行，右表不匹配则为 NULL
- **RIGHT JOIN**：返回右表所有行，左表不匹配则为 NULL
- **FULL JOIN**：返回两表所有行，不匹配则为 NULL
- **CROSS JOIN**：返回两表的笛卡尔积

### 2. SQL 注入

**防止方法**：
1. 使用参数化查询/预处理语句（PreparedStatement）
2. 输入验证和过滤
3. 使用 ORM 框架
4. 最小权限原则
5. 避免动态拼接 SQL 语句

### 3. 三大范式

- **1NF**：每个列都是不可分割的原子值
- **2NF**：满足 1NF，且非主键列完全依赖于整个主键
- **3NF**：满足 2NF，且非主键列之间没有传递依赖

### 4. 窗口函数

在不减少行数的情况下对数据进行计算，常用于排名、移动平均等。

```sql
-- 计算每个部门的工资排名
SELECT employee_id, department_id, salary,
       RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) as dept_rank
FROM employees;

-- 计算移动平均
SELECT date, sales,
       AVG(sales) OVER (ORDER BY date ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) as moving_avg
FROM sales_data;
```

### 5. 存储过程

预编译的 SQL 语句集合，存储在数据库中。

**优点**：提高性能、代码复用、增强安全性、减少 SQL 注入风险

**缺点**：调试困难、可移植性差、增加数据库服务器负担、版本管理困难