# flybbs

#### 介绍
开源论坛项目、目前正在配合cpdogbbs前端准备改造
数据库设计.
后端是基于springboot2.X搭建.
集成免费的SSL认证.集成spring security安全框架.
集成oauth2-client (QQ第三方oauth用户登录认证).
数据库MYSQL8.X版本.
集成mybatis框架.实现了前端入参动态表查询.
mybatis-generator插件一键生成基本CURD代码
手动实现查询分页算法不集成分页插件.效率高很多.
前端使用nginx转发.完整的用户注册,发贴,回贴.签到.权限认证.
配合spring security rememberMe实现认证cookie缓存
一次认证可默认保存一个月时间.不删除浏览器cookie情况下.
完整的登录认证系统.前后端分离的架构.

#### 微服务化改造
1. 模块化拆分
2. 加入nacos 注册中心
3. 加入admin 监控
4. 加入gateway 模块
5. 加入slueth-zipkin 
6. 加入kafka spring-cloud-stream
7. 加入sentinel 
8. 加入seata 

#### 安装教程

1.  下载此项目源代码.
2.  太重了、