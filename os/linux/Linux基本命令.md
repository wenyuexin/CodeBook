# Linux基本命令

[toc]

### 1 文件管理

|  命令   | 说明                                                         |
| :-----: | ------------------------------------------------------------ |
|   cat   | 用于连接文件并打印到标准输出设备上                           |
|  more   | more 命令类似 cat ，不过会以一页一页的形式显示，更方便使用者逐页阅读，而最基本的指令就是按空白键（space）就往下一页显示，按 b 键就会往回（back）一页显示，而且还有搜寻字串的功能（与 vi 相似），使用中的说明文件，请按 h 。 |
|  less   | less 与 more 类似，但使用 less 可以随意浏览文件，而 more 仅能向前移动，却不能向后移动，而且 less 在查看之前不会加载整个文件。 |
|   ls    | 用于显示指定工作目录下之内容（列出目前工作目录所含之文件及子目录) |
|  find   | 在指定目录下查找文件。<br>任何位于参数之前的字符串都将被视为欲查找的目录名。如果使用该命令时，不设置任何参数，则find命令将在当前目录下查找子目录与文件。并且将查找到的子目录和文件全部进行显示 |
|   cp    | 用于复制文件或目录                                           |
|   rm    | 删除一个文件或者目录                                         |
|   mv    | 为文件或目录改名、或将文件或目录移入其它位置                 |
|  chmod  | Linux/Unix 的文件调用权限分为三级 : 文件拥有者、群组、其他。<br>利用 chmod 可以藉以控制文件如何被他人所调用。<br/>使用权限 : 所有使用者 |
|  chown  | Linux/Unix 是多人多工操作系统，所有的文件皆有拥有者。<br>利用 chown 将指定文件的拥有者改为指定的用户或组，用户可以是用户名或者用户ID；组可以是组名或者组ID；文件是以空格分开的要改变权限的文件列表，支持通配符 。<br/>一般来说，这个指令只有是由系统管理者(root)所使用，一般使用者没有权限可以改变别人的文件拥有者，也没有权限把自己的文件拥有者改设为别人。只有系统管理者(root)才有这样的权限。<br/>使用权限 : root |
|  chgrp  | 用于变更文件或目录的所属群组。<br/>在UNIX系统家族里，文件或目录权限的掌控交由拥有者及所属群组来管理。您可以使用chgrp指令去变更文件与目录的所属群组，设置方式采用群组名称或群组识别码皆可。 |
|  diff   | 用于比较文件的差异。<br/>diff以逐行的方式，比较文本文件的异同处。如果指定要比较目录，则diff会比较目录中相同文件名的文件，但不会比较其中子目录。 |
|   scp   | 用于 Linux 之间复制文件和目录。<br/>scp 是 secure copy 的缩写, scp 是 linux 系统下基于 ssh 登陆进行安全的远程文件拷贝命令。scp 是加密的，rcp 是不加密的，scp 是 rcp 的加强版 |
| whereis | 用于查找文件。<br/>该指令会在特定目录中查找符合条件的文件。这些文件应属于原始代码、二进制文件，或是帮助文件。<br/>该指令只能用于查找二进制文件、源代码文件和man手册页，一般文件的定位需使用locate命令。 |
| locate  | 用于查找符合条件的文档，他会去保存文档和目录名称的数据库内，查找合乎范本样式条件的文档或目录。<br/>一般情况我们只需要输入 `locate file_name` 即可查找指定文件。 |
|  which  | 用于查找文件。<br/>which指令会在环境变量$PATH设置的目录里查找符合条件的文件。 |
|   ln    | ln命令非常重要，功能是为某一个文件在另外一个位置建立一个同步的链接。<br>当我们需要在不同的目录，用到相同的文件时，我们不需要在每一个需要的目录下都放一个必须相同的文件，我们只要在某个固定的目录，放上该文件，然后在 其它的目录下用ln命令链接（link）它就可以，不必重复的占用磁盘空间。 |

注：ch - change,  mod - module,  ln - link

### 2 文档编辑

| 命令 | 说明                                                         |
| ---- | ------------------------------------------------------------ |
| grep | 用于查找文件里符合条件的字符串。<br/>grep 指令用于查找内容包含指定的范本样式的文件，如果发现某文件的内容符合所指定的范本样式，预设 grep 指令会把含有范本样式的那一列显示出来。若不指定任何文件名称，或是所给予的文件名为 -，则 grep 指令会从标准输入设备读取数据。 |
|      |                                                              |
|      |                                                              |
|      |                                                              |

### 3 文件传输

|      |      |
| ---- | ---- |
|      |      |
|      |      |


### 4 磁盘管理

| 命令  | 说明                                                         |
| :---: | ------------------------------------------------------------ |
|  cd   | 用于切换当前工作目录至 dirName(目录参数)。<br>其中 dirName 表示法可为绝对路径或相对路径。若目录名称省略，则变换至使用者的 home 目录 (也就是刚 login 时所在的目录)。另外，"~" 也表示为 home目录 的意思，"." 则是表示目前所在的目录，".." 则表示目前目录位置的上一层目录。 |
| tree  | 用于以树状图列出目录的内容。<br>执行tree指令，它会列出指定目录下的所有文件，包括子目录里的文件。 |
|  pwd  | 显示工作目录<br>执行pwd指令可立刻得知目前所在的工作目录的绝对路径名称 |
| mkdir | 建立名称为 dirName 的子目录                                  |
| rmdir | 删除空的目录                                                 |
|       |                                                              |
|       |                                                              |


### 5 磁盘维护



### 6 网络通讯

|   命令   | 说明                                                         |
| :------: | ------------------------------------------------------------ |
|   ping   | 用于检测主机。<br/>执行ping指令会使用ICMP传输协议，发出要求回应的信息，若远端主机的网络功能没有问题，就会回应该信息，因而得知该主机运作正常。 |
|  telnet  | 用于远端登入。<br/>执行telnet指令开启终端机阶段作业，并登入远端主机。 |
| ifconfig | 用于显示或设置网络设备。<br/>ifconfig可设置网络设备的状态，或是显示目前的设置。 |
|  router  | 用于显示和操作IP路由表                                       |
|          |                                                              |
|          |                                                              |




### 7 系统管理

|   命令   | 说明                                                         |
| :------: | ------------------------------------------------------------ |
|    ps    | 显示当前进程 (process) 的状态                                |
|   kill   | 用于删除执行中的程序或工作。<br>kill可将指定的信息送至程序。预设的信息为SIGTERM(15)，可将指定程序终止。若仍无法终止该程序，可使用SIGKILL(9)信息尝试强制删除程序。程序或工作的编号可利用ps指令或jobs指令查看。 |
| killall  |                                                              |
|   free   | 用于显示内存状态。<br/>free指令会显示内存的使用情况，包括实体内存，虚拟的交换文件内存，共享内存区段，以及系统核心使用的缓冲区等。 |
|   top    | 用于实时显示 process 的动态。<br/>使用权限：所有使用者。     |
| useradd  | 用于建立用户帐号。<br/>useradd 可用来建立用户帐号。帐号建好之后，再用 passwd 设定帐号的密码。可用 userdel 删除帐号。使用 useradd 指令所建立的帐号，实际上是保存在 /etc/passwd 文本文件中。 |
| usermod  | usermod命令用于修改用户帐号。<br/>usermod可用来修改用户帐号的各项设定。 |
| userdel  | 用于删除用户帐号。<br/>userdel可删除用户帐号与相关的文件。若不加参数，则仅删除用户帐号，而不删除相关文件。 |
| groupadd | groupadd 命令用于创建一个新的工作组，新工作组的信息将被添加到系统文件中。<br/>相关文件:<br/>1) /etc/group 组账户信息。<br/>2) /etc/gshadow 安全组账户信息。<br/>3) /etc/login.defs Shadow密码套件配置 |
| groupmod | 用于更改群组识别码或名称。<br/>需要更改群组的识别码或名称时，可用groupmod指令来完成这项工作。 |
| groupdel | 用于删除群组。<br/>需要从系统上删除群组时，可用groupdel (group delete)指令来完成这项工作。倘若该群组中仍包括某些用户，则必须先删除这些用户后，方能删除群组。 |
|   sudo   | sudo命令以系统管理者的身份执行指令，也就是说，经由 sudo 所执行的指令就好像是 root 亲自执行。<br/>使用权限：在 /etc/sudoers 中有出现的使用者。 |
|  groups  | 显示指定用户帐户的组群成员身份                               |
|   date   | date命令可以用来显示或设定系统的日期与时间，在显示方面，使用者可以设定欲显示的格式，格式设定为一个加号后接数个标记 |
|          |                                                              |
|          |                                                              |



### 8 系统设置

|  命令   | 说明                                                         |
| :-----: | ------------------------------------------------------------ |
| crontab | crontab是用来定期执行程序<br/>当安装完成操作系统之后，默认便会启动此任务调度命令。crond 命令每分钟会定期检查是否有要执行的工作，如果有要执行的工作便会自动执行该工作。而 linux 任务调度的工作主要分为以下两类：<br/>1、系统执行的工作：系统周期性所要执行的工作，如备份系统数据、清理缓存<br/>2、个人执行的工作：某个用户定期要做的工作，例如每隔10分钟检查邮件服务器是否有新信，这些工作可由每个用户自行设置 |
| passwd  | 用于更改使用者的密码                                         |
|  clear  | 用于清除屏幕                                                 |
|         |                                                              |
|         |                                                              |

### 9 备份压缩

| 命令 | 说明                                                         |
| :--: | ------------------------------------------------------------ |
| tar  | 用于备份文件。<br/>tar是用来建立，还原备份文件的工具程序，它可以加入，解开备份文件内的文件。 |
| gzip | 用于压缩文件。<br/>gzip是个使用广泛的压缩程序，文件经它压缩过后，其名称后面会多出".gz"的扩展名 |
|      |                                                              |
|      |                                                              |

### 10 设备管理



### 11 其他

vi/vim  tail  head  wget






<br>

### 附录

Linux命令的很多采用缩写，并且是比较神奇的缩写。不过还是有点规律：

**目录缩写**

| 缩写  | 全称                  | 说明                       |
| :---: | --------------------- | -------------------------- |
| /bin  | BINaries              | 二进制文件                 |
| /dev  | DEVices               | 特殊设备文件               |
| /lib  | LIBraries             | 通用库文件                 |
| /mnt  | MouNT                 | 让用户临时挂载其他文件系统 |
| /opt  | OPTion                | 第三方软件                 |
| /proc | PROCess               | 系统内存的映射             |
| /sbin | Superuser BINaries    | 系统管理命令程序           |
| /srv  | SeRVices              | 系统服务数据               |
| /sys  | SYStem                | 虚拟文件系统               |
| /tmp  | TeMPorary             | 公共临时文件               |
| /usr  | Unix System Resources | Unix操作系统软件资源       |
| /var  | VARiable              | 变量文件                   |

**命令缩写**

|   缩写   | 全称                         | 说明                                                         |
| :------: | ---------------------------- | ------------------------------------------------------------ |
|   apt    | Advanced Packaging Tool      | Debian Linux 发行版中的软件包管理器                          |
|   bash   | Bourne Again SHell           | 一种流行的shell                                              |
|    bg    | BackGround                   | 后台，用于将作业放到后台运行，让前台可以执行其他任务，不被阻塞，与命令后加 &效果相同 |
|   cal    | CALendar                     | 日历                                                         |
|   cat    | CATenate                     | 连接，连接文件并打印到标准输出设备上                         |
|  chgrp   | CHange GRouP                 | 改变文件所属用户组                                           |
|    cd    | Change Directory             | 切换工作目录                                                 |
|    cp    | CoPy                         | 复制                                                         |
|   diff   | DIFFerent                    | 不同                                                         |
|    df    | Disk Free                    | 查看磁盘剩余空间                                             |
|    du    | Disk Usage                   | 查看磁盘使用情况                                             |
|   dpkg   | Debian PacKaGe               | Debian Linux包命令                                           |
|   env    | ENVironment                  | 查看环境变量                                                 |
|   exec   | EXECute                      | 执行                                                         |
|   grep   | Gnu Regular Expression Press | Gnu正则表达式                                                |
|   grub   | GRand Unified BootLoader     | 多重程序引导器                                               |
| ifconfig | InterFace CONFIGuration      | 网络接口配置                                                 |
|   init   | INITialization               | 初始化                                                       |
|  insmod  | INStall MODule               | 加载模块到内核中                                             |
|    ln    | LiNk                         | 连接                                                         |
|    ls    | LiSt                         | 列出                                                         |
|  lsmod   | LiSt Mode                    | 罗列出已经加载到内核的模块                                   |
|   man    | MANual                       | 手册，查看指令相关手册                                       |
|  mkdir   | MaKe DIRectory               | 创建目录                                                     |
|    mv    | MoVe                         | 移动，移动文件或文件夹，可用来重命名                         |
|  parted  | PARTition EDitor             | GNU组织开发的磁盘分区大小调整工具                            |
|  passwd  | PASSWorD                     | 密码命令                                                     |
|   ping   | Packet InterNet Groper       | 网络包搜索器                                                 |
|    Ps    | Processes Status             | 进程状态                                                     |
|   pwd    | Print Working Directory      | 打印工作目录                                                 |
|    rm    | ReMove                       | 移除，删除                                                   |
|  rmdir   | ReMove DIRectory             | 移除空目录                                                   |
|  rmmod   | ReMove MODule                | 移除模块，从内核中                                           |
|   rmp    | RPM/Redhat Package Manager   | Redhat/RPM 软件包管理器                                      |
|    su    | Switch User                  | 切换用户                                                     |
|   sudo   | Switch User DO               | 切换用户执行命令                                             |
|   tar    | TApe Archive                 | 磁带档案卷                                                   |

**命令选项**

| 选项 | 说明                                                         |
| :--: | ------------------------------------------------------------ |
|  a   | all : 全部，所有 (ls , lsattr , uname)<br/>archive : 存档 (cp , rsync)<br/>append : 附加 (tar -A , 7z) |
|  b   | blocksize : 块大小，带参数 (du , df)<br/>batch : 批处理模式 (交互模式的程序通常拥有此选项，如 top -b) |
|  c   | commands : 执行命令，带参数 (bash , ksh , python)<br/>create : 创建 (tar) |
|  d   | debug : 调试<br/>delete : 删除<br/>directory : 目录 (ls)     |
|  e   | execute : 执行，带参数 (xterm , perl)<br/>edit : 编辑<br/>exclude : 排除 |
|  f   | force : 强制，不经确认(cp , rm ,mv)<br/>file : 文件，带参数 (tar)<br/>configuration file : 指定配置文件(有些守护进程拥有此选项，如 ssh ,<br/>lighttpd) |
|  g   |                                                              |
|  h   | help : 帮助<br/>human readable : 人性化显示(ls , du , df)<br/>headers : 头部 |
|  i   | interactive : 交互模式，提示(rm , mv)<br>include : 包含      |
|  k   | keep : 保留<br/>kill                                         |
|  l   | long listing format : 长格式(ls)<br/>list : 列表<br/>load : 读取 (gcc , emacs) |
|  m   | message : 消息 (cvs)<br/>manual : 手册 (whereis)<br/>create home : 创建 home 目录 (usermod , useradd) |
|  n   | number : 行号、编号 (cat , head , tail , pstree , lspci)<br/>no : (useradd , make) |
|  o   | output : 输出 (cc , sort)<br/>options : 选项 (mount)         |
|  p   | port : 端口，带参数 (很多网络工具拥有此选项，如 ssh , lftp )<br/>protocol : 协议，带参数<br/>passwd : 密码，带参数 |
|  q   | quiet : 静默                                                 |
|  r   | reverse : 反转<br/>recursive : 递归 (cp , rm , chmod -R)     |
|  s   | silent : 安静<br/>size : 大小，带参数<br/>subject            |
|  t   | tag<br/>type : 类型 (mount)                                  |
|  u   | user : 用户名、UID，带参数                                   |
|  v   | verbose : 冗长<br/>version : 版本                            |
|  w   | width : 宽度<br/>warning : 警告                              |
|  x   | exclude : 排除 (tar , zip)                                   |
|  y   | yes                                                          |
|  z   | zip : 启用压缩 (bzip , tar , zcat , zip , cvs)               |



<br>

---

**参考资料**

1. [linux常用命令（50个） - 后知、后觉 - 博客园](https://www.cnblogs.com/xuxinstyle/p/9609551.html)
2. [Linux 命令大全 | 菜鸟教程](https://www.runoob.com/linux/linux-command-manual.html)
3. [linux 基本命令 - c_G-17 - 博客园](https://www.cnblogs.com/shiqi17/p/9344570.html)
4. [Linux常见缩写 - CH_DHY - 简书](https://www.jianshu.com/p/1b176d4fbeb6)
5. [linux缩写 - 虎骑常侍 - 简书](https://www.jianshu.com/p/96850f191d33)