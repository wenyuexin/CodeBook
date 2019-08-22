# BIO、NIO、AIO 区别和应用场景

转载：[BIO、NIO、AIO 区别和应用场景 - lisha006的专栏 - CSDN博客](https://blog.csdn.net/lisha006/article/details/82856906)

## 简单回顾
对于IO我们应该非常熟悉了，IO不仅仅针对文件的操作，网络编程socket的通信，就是IO操作。

输入、输出流（InputStream、OutputStream）用于读取或写入字节，如操作图片、视频等。

Reader和Writer 则用于操作字符，增加了字符编码功能。本质上计算机操作都是字节，不管是网络或者文件，Reader和Writer等于构建了应用逻辑和原始数据的另一层通道。

BufferedOutputStream、BufferedInputStream等带有缓冲区的实现，可以避免频繁的磁盘操作，通过设计缓冲区将批量数据进行一次操作。

## NIO
 能解决什么问题？为什么要有NIO，NIO是什么？

首先看一下BIO，如果有一台服务器，能承受简单的客户端请求，那么使用io和net中的同步、阻塞式API应该是可以实现了。但是为了一个用户的请求而单独启动一个线程，开销应该不小吧。java语言对线程的实现是比较重量的，启动或销毁线程，都会有明显开销，每个线程都有单独的线程棧占用明显的内存。引入线程池，就能很大程度的避免不必要的开销。

![bio](https://img-blog.csdn.net/20180927091820246?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpc2hhMDA2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

这种情况适合连接数并不多，只有最多几百个连接的普通应用，能比较好的进行工作，但如果连接数量剧增，这种实现方式就无法很好的工作了，对于并发量要求较高的企业，这种方案，肯定是不可取的。

NIO采用的是一种多路复用的机制，利用单线程轮询事件，高效定位就绪的Channel来决定做什么，只是Select阶段是阻塞式的，能有效避免大量连接数时，频繁线程的切换带来的性能或各种问题。

![nio](https://img-blog.csdn.net/20180927094928997?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpc2hhMDA2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

首先，Requester方通过Selector.open()创建了一个Selector准备好了调度角色。

创建了SocketChannel(ServerSocketChannel) 并注册到Selector中，通过设置key（SelectionKey）告诉调度者所应该关注的连接请求。

阻塞，Selector阻塞在select操作中，如果发现有Channel发生连接请求，就会唤醒处理请求。

### NIO同步非阻塞式IO

对比BIO的同步阻塞IO操作，实际上NIO是同步非阻塞IO，一个线程在同步的进行轮询检查，Selector不断轮询注册在其上的Channel，某个Channel上面发生读写连接请求，这个Channel就处于就绪状态，被Selector轮询出来，然后通过SelectionKey可以获取就绪Channel的集合，进行后续的I/O操作。
    
同步和异步说的是消息的通知机制，这个线程仍然要定时的读取stream，判断数据有没有准备好，client采用循环的方式去读取（线程自己去抓去信息），CPU被浪费。
    
非阻塞：体现在，这个线程可以去干别的，不需要一直在这等着。Selector可以同时轮询多个Channel，因为JDK使用了epoll()代替传统的select实现，没有最大连接句柄限制。所以只需要一个线程负责Selector的轮询，就可以接入成千上万的客户端。

## AIO

是在NIO的基础上引入异步通道的概念，实现异步非阻塞式的IO处理。如下图

![aio](https://img-blog.csdn.net/20180927103917548?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpc2hhMDA2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

AIO不需要通过多路复用器对注册的通道进行轮询操作即可实现异步读写。什么意思呢？NIO采用轮询的方式，一直在轮询的询问stream中数据是否准备就绪，如果准备就绪发起处理。但是AIO就不需要了，AIO框架在windows下使用windows IOCP技术，在Linux下使用epoll多路复用IO技术模拟异步IO， 即：应用程序向操作系统注册IO监听，然后继续做自己的事情。操作系统发生IO事件，并且准备好数据后，在主动通知应用程序，触发相应的函数（这就是一种以订阅者模式进行的改造）。由于应用程序不是“轮询”方式而是订阅-通知方式，所以不再需要selector轮询，由channel通道直接到操作系统注册监听。
### NIO和AIO
NIO：会等数据准备好后，再交由应用进行处理，数据的读取/写入过程依然在应用线程中完成，只是将等待的时间剥离到单独的线程中去，节省了数据准备时间，因为多路复用机制，Selector会得到复用，对于那些读写过程时间长的，NIO就不太适合。

AIO：读完（内核内存拷贝到用户内存）了系统再通知应用，使用回调函数，进行业务处理，AIO能够胜任那些重量级，读写过程长的任务。
