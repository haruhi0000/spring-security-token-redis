# spring-security-token-redis
用spring security实现token登录和保存session到redis

token不是jwt这种类型的，只是一个UUID。
Spring Security默认的配置下会拦截post login请求，返回给浏览器一个登录页面，没有认证的请求也会重定向到login页面。
但现在大部分都是前后端分离的项目，所以需要自己实现登录的代码。

禁用Spring Security的登录模块需要实现AuthenticationManager接口
或者在@SpringBootApplication上添加exclude = {UserDetailsServiceAutoConfiguration.class}。


# 需求
1. 用账号名和密码登录，然后获取到token，之后的请求需要在请求头中加上这个token。
2. token和用户会话保存到redis里，实现多实例服务端的场景。
3. 每次请求都会重置token的失效时间，保证用户可以持续的请求。

# 依赖
- java 11
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-data-redis
- h2database
- springfox-swagger

# 数据库表
用户表和角色表是多对多关系
## account 用户信息表
```
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '佚名',
  `password` varchar(100) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);
```
## role 角色表
```
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '无',
  PRIMARY KEY (`id`)
);
```
## account_role 用户和角色中间表

```
CREATE TABLE IF NOT EXISTS `account_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);
```
