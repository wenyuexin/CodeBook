# InnoDB存储结构

## InnoDB的逻辑存储结构

所有数据都按一定逻辑存放在一个空间中欧冠，称为表空间（tablespace），

表空间由段（segment）、区（extent）、页（page）组成。页有时候也称为块（block）。

**段**：表空间由段组成，常见的有数据段、索引段、回滚段等。

**区**：区是连续的页组成的空间，每个区的大小都是1MB。为保证区中页的连续性，InnoDB一次性从磁盘申请4~5个区。

**页**：InnoDB中磁盘管理的最小单位。默认情况下，页的大小为16KB（一个区有64个连续的页）。可以通过参数`innodb_page_size`修改为4KB、8KB，一旦设置将不能修改，除非通过mysqldump导入和导出产生新的库。

<br>

**InnoDB数据页结构**

File Header（文件头）38字节

Page Header（页头）56字节

Infimun Records和Supremum Records

User Record（用户记录，即行记录）38字节

Free Space（空闲空间）

Page Directory（页目录）

File Tailer（文件结尾信息）8字节

<br>

