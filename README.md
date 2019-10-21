# Wecoding

#### 前言
`wecoding`致力于打造一个便捷的小型校内信息交流网站，采用当前流行的技术实现。
#### 前端
前端项目地址: [wefront](https://github.com/cangwuwuwu/wefront)
#### 项目介绍
`wecoding`基于SpringBoot + MyBatis + Vue实现。包含欢迎、首页、学生注册、学生登录、学生信息展示、学生搜索、资源分享、校园指南、资源上传、帮助中心、论坛讨论等模块。

#### 技术选型
##### 后端技术

| 技术      | 说明    |  官网  |
| ------  | -----   | ---- |
| Spring Boot        | 容器+MVC框架     |   https://spring.io/projects/spring-boot   |
| Netty     |  高可用网络服务器 |  https://netty.io/ |
| Spring Security        | 认证和授权框架     |   https://spring.io/projects/spring-security    |
| MyBatis    |   ORM框架    |   http://www.mybatis.org/mybatis-3/zh/index.html   |
| RabbitMq |  消息队列    |  	https://www.rabbitmq.com/|
| Redis | 分布式缓存 |	https://redis.io/ |
| MongoDb | 	NoSql数据库 | https://www.mongodb.com/| 
| Docker |	应用容器引擎 |	https://www.docker.com/|
| Druid |	数据库连接池 | https://github.com/alibaba/druid|
| Lombok |	简化对象封装工具 | https://github.com/rzwitserloot/lombok|
| Nginx | 高性能的HTTP和反向代理web服务器 | http://nginx.org/en/ |
| fastDFS | 高性能文件服务客户端 |  https://github.com/happyfish100/fastdfs |
| ElasticSearch | 基于Lucene的搜索服务器 |  https://www.elastic.co/products/enterprise-search |
    
##### 前端技术
见 wefront
 
##### 开发工具
   
| 工具      | 说明    |  官网  |
| ----- | ----- | ---- |
|IDEA	| 开发IDE |	https://www.jetbrains.com/idea/download|
|RedisView| redis可视化工具|https://github.com/cc20110101/RedisView|
|Navicat|数据库连接工具	|http://www.formysql.com/xiazai.html|
| Robo 3T | MongoDB数据库管理软件 | https://robomongo.org/ |
|X-shell|	Linux远程连接工具	|http://www.netsarang.com/download/software.html|
|postman| 网页调试与发送网页HTTP请求工具 |https://www.getpostman.com/ |
  
##### 开发环境
  
| 工具      | 版本号    |  下载  |
| ----- | ----- | ---- |
|JDK |	1.8	|https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html|
|SpringBoot | 2.1.6 | https://spring.io/projects/spring-boot |
|Mysql	|5.7|	https://www.mysql.com/|
|Redis |	3.2 |	https://redis.io/download|
|RabbitMQ|	3.7.14|	http://www.rabbitmq.com/download.html|
|nginx|	1.16 |http://nginx.org/en/download.html|
|ElasticSearch| 6.8.3 | https://hub.docker.com/_/elasticsearch |

#### 参考资料
* [Spring实战(第四版)](https://book.douban.com/subject/26767354/)
* [Spring Boot实战](https://book.douban.com/subject/26857423/)
* [JavaEE开发的颠覆者 Spring Boot实战](https://book.douban.com/subject/26762595/)
* [Netty实战](https://book.douban.com/subject/27038538/)

#### 更新日志
##### 2019/10/21 Add Course List Page 
* 课程列表模块，redis实现点赞系统，报名课程系统
##### 2019/10/8 New electricity reminder module
* 新增NIT宿舍电量提醒模块
##### 2019/9/27  Refactoring interface
* 重构了大部分接口，规范返回格式
* 优化了超时机制，超时后访问api提示登录
* 重做了注册/登录，使用自定义UserDetail
* 提交收支发送邮件提醒财务部
##### 2019/9/19 integration ES
* 集成了ElasticSearch完成会员全文检索
* 删除了模板语言的部分配置
##### 2019/9/6 add upload resources
* 新增资源上传功能
* 创建了统一的异常枚举类ExceptionEnum
* 控制器接口使用ResponseEntity
##### 2019/9/2 timing check resources status  
* 定时任务每天检查资源可用性并更新到数据库
* 更新了一些请求和响应的数据类型
##### 2019/8/28 return json by SpringSecurity
* 前后端分离 Spring Security 返回JSON配置
##### 2019/8/26 finish change User Password Module
* 修改信息请求改写为put请求 RESTful规范化
* 完成用户修改密码功能
##### 2019/8/25 add Upload user avatar
* 前端项目分离为vue-cli项目: [wefront](https://github.com/cangwuwuwu/wefront)
* 整合[fastDFS](https://github.com/happyfish100/fastdfs)完成头像上传展示功能
##### 2019/8/20 Perfect home/resources page
* 完善资源分享模块,新增学习路线图/在线学习资源
* 完善个人中心的修改个人信息
* 资源查询分页缓存
##### 2019/8/2 Add Transactional/message center module
* 添加事物支持
* 整合RabbitMQ, stomp消息插件
* 用户广播消息模块: 历史消息/实时接收在线消息
##### 2019/7/29 fastDFS Support
* 添加的fastDFS头像上传的后端代码
* 部分大图片配置到nginx静态资源服务器
* 添加了通过邮件方式发送反馈
##### 2019/7/21 Use Netty for websocket and iView for view
* 整合了Netty 编写了一个简单的聊天室，聊天、显示在线人数等功能
##### 2019/7/16 front-end development
* 新增通过搜索框搜索学生，查看其个人信息
##### 2019/7/14 Optimize the performance of data query and cache
* 将Druid连接池更改为性能更强大的Hikari连接池
* Spring Boot 2.x redis默认采用的lettuce存在连接超时错误,  已更换为jedis
##### 2019/7/12 Third Commit:Code refactoring & improving the experience
* 基于MVC解耦,  将业务逻辑拆分至service层
##### 2019/7/6 Second commit;
* 密码从数据库中拆分出来
##### 2019/7/3  first commit;Some basic registration and login functions
* 欢迎/首页/登录/注册/错误页面/个人中心 页面
* 登录页面  国际化功能/Remember-me功能
* 注册页面  发送邮件/学号用户名校验/随机显示已注册用户/显示用户总数/查看用户信息
* 错误页面  显示状态码/错误名称/异常信息
* 个人页面  显示个人信息

---
Thank you for your read.