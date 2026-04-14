## MySql

### 多表连接与聚合

**情景**: 数据库中有三个表：

- `orders`(订单表): `order_id`, `customer_id`, `order_date`, `total_amount`

- `customers`(客户表): `customer_id`, `name`, `email`

请写出以下查询：

- 查询每个客户的总消费金额，按消费金额降序排列

- 查询2025年6月下单次数最多的前5位客户

```sql
-- 客户总消费金额
SELECT c.name, SUM(o.total_amount) AS total_spent
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name
ORDER BY total_spent DESC;

-- 2023年1月下单最多的前5客户
SELECT c.name, COUNT(o.order_id) AS order_count
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_date BETWEEN '2023-01-01' AND '2023-01-31'
GROUP BY c.customer_id, c.name
ORDER BY order_count DESC
LIMIT 5;
```