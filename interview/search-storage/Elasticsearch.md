## Elasticsearch 

### 1. Elasticsearch 是什么？它的核心特点有哪些？

**参考答案**：

Elasticsearch 是一个基于 **Lucene** 的分布式、RESTful 搜索和分析引擎，支持近实时（NRT）全文检索、结构化搜索、地理位置查询和数据分析。

**核心特点**：
- **分布式**：自动分片、副本、集群管理，可水平扩展。
- **高可用**：副本分片提供冗余，故障自动切换。
- **RESTful API**：基于 JSON 的 HTTP 接口，易于集成。
- **近实时**：文档索引后约 1 秒即可被搜索（可配置 refresh_interval）。
- **聚合分析**：支持指标、桶、管道聚合，实现数据统计分析。
- **Schema Free**：自动检测字段类型并创建映射，也可手动定义。

---

### 2. 请解释 Elasticsearch 中的索引（Index）、类型（Type，已废弃）、文档（Document）、分片（Shard）和副本（Replica）的概念。

**参考答案**：

- **Index**：相当于关系数据库中的“数据库”，是拥有相同结构的文档集合。
- **Type**：ES 7.x 之前，Index 下可划分多个 Type，类似“表”。从 7.0 开始废弃，8.x 彻底移除，现在一个 Index 只存储一种文档。
- **Document**：JSON 格式的数据单元，相当于数据库中的“行”。每个文档有唯一的 `_id`。
- **Shard**：索引被切分为多个分片，每个分片是一个独立的 Lucene 索引，可分散到不同节点，支持水平扩展。主分片数在索引创建时确定，不可修改。
- **Replica**：主分片的副本，提供高可用和读负载均衡。副本数可动态调整。

**示例**：一个索引有 3 个主分片和 2 个副本，则总共有 3×2=6 个分片（3 主 + 3 副本），每个主分片对应一个副本。

---

### 3. Elasticsearch 写入文档的流程是怎样的？请描述从客户端发送请求到数据可被搜索的完整过程。

**参考答案**：

1. **路由计算**：客户端向任意节点（协调节点）发送写请求，协调节点根据文档 `_id` 计算目标主分片：`shard = hash(_id) % number_of_primary_shards`。
2. **转发请求**：协调节点将请求转发给该主分片所在的节点。
3. **写入 Lucene**：主分片节点将文档写入内存缓冲区（同时写入 translog 持久化）。
4. **定时刷新（refresh）**：默认每秒，缓冲区中的文档被刷新到 Lucene 的新段（segment）中，此时文档变为可搜索（但尚未 fsync 到磁盘）。
5. **translog 与 flush**：translog 用于故障恢复。当 translog 达到一定阈值或每隔 30 分钟，会执行 flush：将内存中的段全量 fsync 到磁盘，并清空 translog。
6. **副本同步**：主分片写入成功后，并行将请求发送给所有副本分片，等待至少半数副本成功（默认 quorum = (primary + replicas)/2 + 1），然后返回客户端成功。

**注意**：`refresh` 使文档可搜索，`flush` 确保持久化。

---

### 4. Elasticsearch 的倒排索引（Inverted Index）是什么？它与关系数据库的 B+ 树索引有何区别？

**参考答案**：

**倒排索引**是一种面向单词的索引结构，由 **词项（Term）字典** 和 **倒排列表（Posting List）** 组成。倒排列表记录了包含该词项的所有文档 ID 以及词频、位置信息。

**查询过程**：分词后，根据词项找到倒排列表，合并得到匹配的文档集合，再计算相关性得分（BM25 等）。

**与 B+ 树对比**：
| 维度 | 倒排索引 | B+ 树 |
|------|----------|-------|
| 主要用途 | 全文搜索 | 精确匹配、范围查询 |
| 模糊/分词查询 | 高效 | 低效（`LIKE %abc%` 全扫描） |
| 相关性打分 | 原生支持 | 不支持 |
| 存储开销 | 较高（需维护词典和倒排表） | 相对较低 |
| 写入性能 | 需频繁合并段，写入稍慢 | 写入较快 |

---

### 5. 什么是 Elasticsearch 中的分析器（Analyzer）？它由哪三部分组成？请举例说明内置分析器。

**参考答案**：

分析器用于将文本字段转换为词项（terms），以便建立倒排索引。它由三个组件顺序组成：

1. **字符过滤器（Character Filters）**：预处理文本，如去除 HTML 标签、替换字符（`&` → `and`）。
2. **分词器（Tokenizer）**：将字符串切分为词项，如 `standard` 按单词边界切分。
3. **词项过滤器（Token Filters）**：对词项进行二次处理，如小写化、停用词移除、同义词替换、词干提取（stemming）。

**内置分析器示例**：
- `standard`：默认，按语法切分，小写化，适合大多数语言。
- `simple`：非字母切分，小写化。
- `whitespace`：按空白切分，不转小写。
- `keyword`：不切分，整个文本作为一个词项。
- `ik_smart` / `ik_max_word`（中文插件）：中文分词。

---

### 6. Elasticsearch 查询 DSL 中，`term`、`match`、`match_phrase` 和 `multi_match` 有什么区别？

**参考答案**：

| 查询类型 | 是否分词 | 说明 |
|---------|---------|------|
| `term` | 不分析查询词 | 精确匹配倒排索引中的词项，适用于 keyword 或枚举字段。 |
| `match` | 分析查询词 | 先对查询字符串分词，然后匹配包含任意词项的文档。默认 OR 操作，可通过 operator 改为 AND。 |
| `match_phrase` | 分析查询词 | 要求分词后的词项顺序相同且位置相邻（slop 参数可允许间隔）。 |
| `multi_match` | 分析查询词 | 对多个字段执行相同的 `match` 查询，可指定字段权重。 |

**示例**：
- `term`: 查询 `status` 字段为 `"active"` 的文档。
- `match`: 查询 `title` 包含 `"apple"` 或 `"phone"` 的文档。
- `match_phrase`: 查询 `content` 包含 `"data"` 紧邻 `"science"` 的文档。
- `multi_match`: 在 `title` 和 `abstract` 中搜索 `"machine learning"`，并可提高 `title` 的权重。

---

### 7. 如何实现 Elasticsearch 的复合查询（Bool Query）？`must`、`filter`、`should`、`must_not` 的区别是什么？

**参考答案**：

`bool` 查询组合多个子句，各子句类型及作用：

| 子句 | 作用 | 是否影响得分 |
|------|------|--------------|
| `must` | 必须匹配，类似 AND | 是 |
| `filter` | 必须匹配，但不计算得分，会缓存结果 | 否（得分=0） |
| `should` | 可选匹配，至少匹配一个（可配置 minimum_should_match） | 是，匹配越多得分越高 |
| `must_not` | 必须不匹配，类似 NOT | 否 |

**示例**：查询 `status` 为 `published`（filter）、`title` 包含 `elasticsearch`（must）、且 `tags` 最好包含 `search`（should）的文档：
```json
{
  "query": {
    "bool": {
      "filter": { "term": { "status": "published" } },
      "must": { "match": { "title": "elasticsearch" } },
      "should": { "term": { "tags": "search" } },
      "minimum_should_match": 1
    }
  }
}
```

---

### 8. Elasticsearch 中的聚合（Aggregation）有哪些类型？请举例说明。

**参考答案**：

聚合分为三大类：

1. **指标聚合（Metric Aggregations）**：计算数值型字段的统计值，如 `avg`、`sum`、`min`、`max`、`stats`、`cardinality`（去重计数）。
   ```json
   { "aggs": { "avg_price": { "avg": { "field": "price" } } } }
   ```

2. **桶聚合（Bucket Aggregations）**：将文档分组到桶中，如 `terms`（按字段值分组）、`date_histogram`（按时间间隔分组）、`range`（范围分组）、`geohash_grid`（地理分组）。
   ```json
   { "aggs": { "by_category": { "terms": { "field": "category" } } } }
   ```

3. **管道聚合（Pipeline Aggregations）**：对其他聚合结果进行再计算，如 `derivative`（导数）、`moving_avg`（移动平均）、`cumulative_sum`（累积和）。

**常用组合**：按月份统计销售额：
```json
{
  "aggs": {
    "sales_per_month": {
      "date_histogram": { "field": "date", "calendar_interval": "month" },
      "aggs": { "total": { "sum": { "field": "amount" } } }
    }
  }
}
```

---

### 9. Elasticsearch 集群有哪几种颜色状态？分别代表什么含义？

**参考答案**：

- **Green（绿色）**：所有主分片和副本分片都正常分配，集群健康。
- **Yellow（黄色）**：所有主分片已分配，但至少有一个副本分片未分配（如节点数不足，或正在恢复）。集群依然可提供读写服务，但存在单点故障风险。
- **Red（红色）**：至少有一个主分片未分配，导致部分数据不可读写。需要立即排查（如节点宕机、磁盘满、分配失败）。

**常见修复**：增加节点、调整副本数、重新分配分片、检查磁盘空间。

---

### 10. 如何优化 Elasticsearch 的写入性能？列举至少 5 条建议。

**参考答案**：

1. **批量写入（Bulk）**：使用 bulk API 合并多个文档请求，减少网络开销和刷新频率。
2. **增加 refresh_interval**：默认 1 秒，可调整为 `30s` 或 `-1`（禁用自动刷新），减少段合并压力。
3. **调整 translog 持久化策略**：设置 `index.translog.durability: async` 和 `index.translog.sync_interval: 30s`，牺牲少量数据可靠性换取更高写入性能。
4. **使用合适的分片数**：避免过多或过少分片。经验：单分片大小 10~50GB，节点分片数不超过节点 CPU 核数。
5. **禁用不需要的字段**：如 `_all`（已废弃）、`_source` 可禁用或压缩，减少存储和 I/O。
6. **使用 SSD 磁盘**：随机写入性能大幅提升。
7. **避免频繁更新和删除**：更新实际是重新索引，删除只是标记，会增加段合并负担。可考虑基于时间索引（rollover）。

---

### 11. 如何优化 Elasticsearch 的搜索性能？

**参考答案**：

1. **使用过滤器（filter）**：不参与得分的查询（如 `term`、`range` 在 `bool.filter` 中）可被缓存，提升速度。
2. **减少返回字段**：使用 `_source` 过滤或 `stored_fields` 只获取需要的字段。
3. **分页优化**：避免深分页（`from + size` 过大），改用 `search_after` 或 `scroll`（适合批量导出）。
4. **路由（routing）**：将相关文档路由到同一分片，避免跨分片查询。
5. **合理设置分片数和副本数**：查询时，副本可分担读负载，但过多副本会增加写入延迟。
6. **使用 profile API**：分析慢查询，优化查询语句和索引映射。
7. **硬件升级**：内存（堆内存建议不超过 32GB）、CPU、磁盘 IOPS。

---

### 12. 什么是 Elasticsearch 的 Mapping？动态映射（Dynamic Mapping）有何优缺点？

**参考答案**：

Mapping 类似于关系数据库的表结构定义，它指定了字段的数据类型（如 `text`、`keyword`、`integer`、`date`）、分析器、索引选项等。

**动态映射**：当索引一个新文档时，ES 自动检测字段类型并创建 mapping。优点是不需要预先定义，快速上手；缺点是可能推断错误（如数字 `123` 可能被映射为 `long`，而实际需要 `keyword`），或字段类型一旦创建就无法修改（需要重建索引）。

**最佳实践**：生产环境应使用显式 mapping 定义，关闭动态映射或设置 `dynamic: strict`。

---

### 13. 如何重建 Elasticsearch 索引（更改 mapping 或分片数）？请描述常用方案。

**参考答案**：

由于 ES 不支持修改已有字段的类型或主分片数，重建索引的标准方法是：

1. **创建新索引**：定义新的 mapping 和设置（如分片数、分析器）。
2. **数据迁移**：使用 `reindex` API 将旧索引数据复制到新索引。`reindex` 支持查询过滤、脚本处理数据等。
   ```json
   POST _reindex
   {
     "source": { "index": "old_index" },
     "dest": { "index": "new_index" }
   }
   ```
3. **别名切换**：原子地将别名指向新索引，应用无需修改代码。
   ```
   POST /_aliases
   {
     "actions": [
       { "remove": { "index": "old_index", "alias": "my_alias" } },
       { "add":    { "index": "new_index", "alias": "my_alias" } }
     ]
   }
   ```
4. **删除旧索引**（可选）：`DELETE old_index`。

**注意**：`reindex` 过程中，若数据量大，可使用异步任务（`wait_for_completion=false`）并监控任务进度。

---

### 14. Elasticsearch 节点有哪些角色？分别说明它们的作用。

**参考答案**：

节点通过配置 `node.roles` 可以承担以下角色（ES 7.x+）：

- **Master-eligible node**：参与集群管理，如创建/删除索引、管理节点状态。只有 master 节点可执行写元数据操作。
- **Data node**：存储数据分片，处理数据相关的读写请求。是资源消耗型节点（CPU、内存、磁盘）。
- **Ingest node**：执行预处理管道（ingest pipeline），在索引前转换数据，如解析日期、删除字段。
- **Coordinating node**：接收客户端请求，路由到其他节点并汇总结果。默认所有节点都是协调节点。
- **Machine learning node**：运行机器学习任务。
- **Transform node**：运行数据转换任务。

**生产建议**：大型集群可分离角色：专用 master 节点（不存储数据，不协调请求），专用数据节点，专用协调节点。

---

### 15. Elasticsearch 中的 `_source` 字段是什么？禁用 `_source` 会有什么影响？

**参考答案**：

`_source` 字段存储了原始 JSON 文档内容，在获取文档（get）或搜索结果中返回。默认启用。

**影响**：
- **禁用 `_source`**：无法直接返回文档内容，但可节省存储空间（约 30~50%）。无法执行 update、reindex 或高亮等依赖 `_source` 的操作。
- **部分禁用**：可以排除某些大字段（如 `_source.excludes`）。

**使用场景**：如果只依赖 ES 进行搜索，实际数据存储在其他系统（如数据库），可考虑禁用 `_source` 以节约磁盘。但大多数场景建议保留。
