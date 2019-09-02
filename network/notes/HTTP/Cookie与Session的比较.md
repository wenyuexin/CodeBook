# Cookie与Session的比较

本文主要讨论：Cookie 作用、安全性问题、和 Session 的比较

<br>

### 一、cookie、localStorage与sessionStorage

#### cookie

cookie英文饼干，顾名思义，cookie非常小，它的大小限制在4kb左右，是网景公司的前雇员在1993年发明。Cookie是服务器存储在本地计算机上的小块文本，并随每个请求发送到同一服务器。 它的主要用于保存登陆信息，比如你登陆某个网站市场可以看到记住密码，这就是通过在cookie中存入一段辨别用户身份的数据来实现的。

#### localStorage

localStorage是HTML5标准中新加入的技术，当然早在IE6时代就有一个userData的东西用于本地存储，而当时考虑到浏览器的兼容性，更通用的方案是使用flash。如今localStorage被大多数浏览器所支持。

#### sessionStorage

sessionStorage与localStorage的接口类似，但保存数据的生命周期与localStorage不同，做过后端的同学都知道Session这个词，翻译过来就是会话。而sessionStorage是前端的一个概念。它只是可以将一部分数据在当前会话中保存下来，刷新页面数据依旧存在。但是叶敏啊关闭后，sessionStorage中的数据就会被清空。

### 二、异同

#### 1）数据上的生命周期的不同

**Cookie** 一般由服务器生成，可设置失效时间，如果在浏览器端生成cookie，默认是关闭后失效。

**localStorage** 除非被永久清除，否则永久保存。

**sessionStorage** 仅在当前会话会有效，关闭页面或浏览器后被清除

#### 2）存放数据的大小不同

**Cookie** 一般为4kb

**localStorage 和 sessionStorage** 一般为5mb

#### 3）与服务器端通信不同

**Cookie** 每次都会携带HTTP头中，如果使用cookie保存过多数据会带来性能问题

**localStorage 和 sessionStorage** 仅在客户端（即浏览器）中保存，不参与和服务器的通信。

#### 4）易用性

**Cookie** 需要程序员自己来封装，原生的cookie接口不够友好

**localStorage 和 sessionStorage** 原生接口可以接受，可以封装来对Object和Array有更好的支持。

### 三、应用场景

因为考虑到每个HTTP请求都会带着Cookie的信息，所以Cookie当然能是精简就精简力，比较常用的一个应用场景就是判断用户是否登陆，针对登陆过的用户服务端就在它登陆时往Cookie中祝福一段加密过的唯一识别单一用户的辨识码，下次只要读取这个值就可以判断当前用户是否登陆。曾经还使用Cookie来保存用户在电商网站上的购物车信息，如今有来localStorage，这一切问题变得越来越轻松。

<br>

### 四、Session







<br>

---

**参考资料**

1. [cookie,localStorage,SessionStorage三者的区别](https://www.cnblogs.com/yaogengzhu/p/11006547.html)
2. 