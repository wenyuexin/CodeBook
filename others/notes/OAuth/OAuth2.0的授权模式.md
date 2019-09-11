# OAuth2.0的授权模式

OAuth2.0提供了以下5种授权模式：**授权码授权模式**（Authorization code Grant）、**隐式授权模式**（Implicit Grant）、**密码模式**（Resource Owner Password Credentials Grant）、**客户端凭证模式**（Client Credentials Grant）、Refresh Token Grant。

<br>

## 1 授权码授权模式（Authorization code Grant）

### The Flow (Part One)

The client will redirect the user to the authorization server with the following parameters in the query string:

- `response_type` with the value `code`
- `client_id` with the client identifier
- `redirect_uri` with the client redirect URI. This parameter is optional, but if not send the user will be redirected to a pre-registered redirect URI.
- `scope` a space delimited list of scopes
- `state` with a CSRF token. This parameter is optional but highly recommended. You should store the value of the CSRF token in the user’s session to be validated when they return.

All of these parameters will be validated by the authorization server.

The user will then be asked to login to the authorization server and approve the client.

If the user approves the client they will be redirected from the authorisation server back to the client (specifically to the redirect URI) with the following parameters in the query string:

- `code` with the authorization code
- `state` with the state parameter sent in the original request. You should compare this value with the value stored in the user’s session to ensure the authorization code obtained is in response to requests made by this client rather than another client application.

### The Flow (Part Two)

The client will now send a POST request to the authorization server with the following parameters:

- `grant_type` with the value of `authorization_code`
- `client_id` with the client identifier
- `client_secret` with the client secret
- `redirect_uri` with the same redirect URI the user was redirect back to
- `code` with the authorization code from the query string

The authorization server will respond with a JSON object containing the following properties:

- `token_type` this will usually be the word “Bearer” (to indicate a bearer token)
- `expires_in` with an integer representing the TTL of the access token (i.e. when the token will expire)
- `access_token` the access token itself
- `refresh_token` a refresh token that can be used to acquire a new access token when the original expires

![img](https://img2018.cnblogs.com/blog/1332033/201906/1332033-20190612141610870-762104708.png)

**基本流程**：

-  第一步：用户访问页面
-  第二步：访问的页面将请求重定向到认证服务器
-  第三步：认证服务器向用户展示授权页面，等待用户授权
-  第四步：用户授权，认证服务器生成一个code和带上client_id发送给应用服务器然后，应用服务器拿到code，并用client_id去后台查询对应的client_secret。之后重新跳转到redirect_uri。
-  第五步：将code、client_id、client_secret传给认证服务器换取access_token和refresh_token
-  第六步：将access_token和refresh_token传给应用服务器
-  第七步：验证token，访问真正的资源页面

**说明**：

1. client_id和client_secret是事先向认证服务器注册或者其他方式得到的。应用服务器在请求授权时需要携带client_id和用于重定向的链接redirect_uri等参数。不同的用户需要授权时，认证服务器会生成不同的code，然后应用服务器通过发送code、client_id、client_secret三者同时获取access_token和refresh_token。

2. access_token的作用就是取代账号和密码，在expires_in时间内直接通过access_token就可以获取数据。当access_token过期后可以refresh_token获取新的access_token。

**优点**：

安全性：token有过期时间，refresh_token可以换取新的token；通过code、client_id、client_secret换取token的过程是服务器之间的通信，难以被截获；client_secret始终存储在认证服务器中

**缺点**：

需要多次请求

**应用场景**：

主流的第三方验证都使用这种模式

<br>

## 2 隐式授权模式（Implicit Grant）

该模式和授权码授权模式类似，区别在于：

- It is intended to be used for user-agent-based clients (e.g. single page web apps) that can’t keep a client secret because all of the application code and storage is easily accessible.

  主要针对应用程序不能存储`client_id` 和`client_secret` 的情况，例如单页面的web应用。

- Secondly instead of the authorization server returning an authorization code which is exchanged for an access token, the authorization server returns an access token. 

  认证服务器不返回code，而是直接返回access_token

**基本流程**：

- 第一步：用户访问页面时，重定向到认证服务器。
-  第二步：认证服务器给用户一个认证页面，等待用户授权。
-  第三步：用户授权，认证服务器想应用页面返回Token
-  第四步：验证Token，访问真正的资源页面

**使用场景**：

应用只有页面，没有后台管理，只能使用第三方认证后直接访问。例如，调查问卷

<br>

## 3 密码模式（Resource Owner Password Credentials Grant）

This grant is a great user experience for **trusted first party clients** both on the web and in native device applications.

### The Flow

The client will ask the user for their authorization credentials (**ususally a username and password**).

The client then sends a POST request with following body parameters to the authorization server:

- `grant_type` with the value `password`
- `client_id` with the the client’s ID
- `client_secret` with the client’s secret
- `scope` with a space-delimited list of requested scope permissions.
- `username` with the user’s username
- `password` with the user’s password

The authorization server will respond with a JSON object containing the following properties:

- `token_type` with the value `Bearer`
- `expires_in` with an integer representing the TTL of the access token
- `access_token` the access token itself
- `refresh_token` a refresh token that can be used to acquire a new access token when the original expires

<br>

**基本流程**：

-  第一步：用户访问用页面时，输入第三方认证所需要的信息(通常是账号和密码)
-  第二步：应用页面那种这个信息去认证服务器授权
-  第三步：认证服务器授权通过，拿到token，访问真正的资源页面

**优点**：不需要多次请求转发，额外开销，同时可以获取更多的用户信息。(都拿到账号密码了)

**缺点**：局限性，认证服务器和应用方必须有超高的信赖。

**应用场景**：自家公司搭建的认证服务器

<br>

## 4 客户端凭证模式（Client Credentials Grant）

The simplest of all of the OAuth 2.0 grants, this grant is suitable for **machine-to-machine authentication** where a specific user’s permission to access data is not required.

### The Flow

The client sends a POST request with following body parameters to the authorization server:

- `grant_type` with the value `client_credentials`
- `client_id` with the the client’s ID
- `client_secret` with the client’s secret
- `scope` with a space-delimited list of requested scope permissions.

The authorization server will respond with a JSON object containing the following properties:

- `token_type` with the value `Bearer`
- `expires_in` with an integer representing the TTL of the access token
- `access_token` the access token itself



**基本流程**：

-  第一步：用户访问应用客户端
-  第二步：通过客户端定义的验证方法，拿到token，无需授权
-  第三步：访问资源服务器A
-  第四步：拿到一次token就可以畅通无阻的访问其他的资源页面。

这是一种最简单的模式，只要client请求，我们就将AccessToken发送给它。这种模式是最方便但最不安全的模式。因此这就要求我们对client完全的信任，而client本身也是安全的。因此这种模式一般用来提供给我们完全信任的服务器端服务。在这个过程中不需要用户的参与。

<br>

## 5 Refresh Token Grant

Access tokens eventually expire; however some grants respond with a refresh token which enables the client to get a new access token without requiring the user to be redirected.

### The Flow

The client sends a POST request with following body parameters to the authorization server:

- `grant_type` with the value `refresh_token`
- `refresh_token` with the refresh token
- `client_id` with the the client’s ID
- `client_secret` with the client’s secret
- `scope` with a space-delimited list of requested scope permissions. This is optional; if not sent the original scopes will be used, otherwise you can request a reduced set of scopes.

The authorization server will respond with a JSON object containing the following properties:

- `token_type` with the value `Bearer`
- `expires_in` with an integer representing the TTL of the access token
- `access_token` the access token itself
- `refresh_token` a refresh token that can be used to acquire a new access token when the original expires

<br>

## 6 授权模式的选择

![img](https://alexbilbie.com/images/oauth-grants.svg)



<br>

---

**参考资料**

2. [OAuth Community Site](https://oauth.net/)
2. [OAuth 2.0 Grant Types](https://oauth.net/2/grant-types/)
3. [A Guide To OAuth 2.0 Grants - Alex Bilbie](https://alexbilbie.com/guide-to-oauth-2-grants/)
4. [OAuth2.0的四种授权模式 - 懵懂的半壶 - 博客园](https://www.cnblogs.com/Innocent-of-Dabber/p/11009811.html)