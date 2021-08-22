### 第1周作业 

2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。  

&emsp;&emsp;作业：[XlassLoader](src/main/java/com/nj/learn/jvm/XlassLoader.java)   

3.（必做）画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。  

&emsp;&emsp;作业：[作业3.png](src/main/java/com/nj/learn/jvm/作业3.png)  


### 第2周作业

4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。  

&emsp;&emsp;作业：[作业4.md](src/main/java/com/nj/learn/nio/作业4.md)  

6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 GitHub   

&emsp;&emsp;作业：[TestOkHttpClient](src/main/java/com/nj/learn/nio/TestOkHttpClient.java)  

### 第3周作业

1.（必做）整合你上次作业的 httpclient/okhttp；  

&emsp;&emsp;作业：[OkhttpOutboundHandler](src/main/java/com/nj/learn/nio/gateway/outbound/okhttp/OkhttpOutboundHandler.java)  

3.（必做）实现过滤器。  

&emsp;&emsp;作业：[HeaderHttpRequestFilter](src/main/java/com/nj/learn/nio/gateway/filter/HeaderHttpRequestFilter.java)  
&emsp;&emsp;&emsp;&emsp;：[HeaderHttpResponseFilter](src/main/java/com/nj/learn/nio/gateway/filter/HeaderHttpResponseFilter.java)  

4.（选做）实现路由。  

&emsp;&emsp;作业：[RandomHttpEndpointRouter](src/main/java/com/nj/learn/nio/gateway/router/RandomHttpEndpointRouter.java)  



6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 GitHub   

&emsp;&emsp;作业：[TestOkHttpClient](src/main/java/com/nj/learn/nio/TestOkHttpClient.java)  

### 第4周作业

2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。

&emsp;&emsp;作业：[Homework](src/main/java/com/nj/learn/concurrent/Homework.java)  

6.（必做）把多线程和并发相关知识梳理一遍，画一个脑图，截图上传到 GitHub 上。 可选工具:xmind，百度脑图，wps，MindManage，或其他。 

&emsp;&emsp;作业：[多线程.jpg](src/main/java/com/nj/learn/concurrent/多线程.jpg)  
&emsp;&emsp;作业：[java并发.jpg](src/main/java/com/nj/learn/concurrent/java并发.jpg)  

### 第5周作业

2.（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。

&emsp;&emsp;作业：[SpringDemoApplication](src/main/java/com/nj/learn/school/src/main/java/io/kimmking/SpringDemoApplication.java) 自动装配  
&emsp;&emsp;作业：[Application](src/main/java/com/nj/learn/spring/src/main/java/com/example/spring/Application.java)  引入starter

10.（必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：   
1）使用 JDBC 原生接口，实现数据库的增删改查操作。   
2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。   
3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。     

&emsp;&emsp;作业：[JdbcTest](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/JdbcTest.java) 增删改查   

### 第6周作业

6.（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。  

&emsp;&emsp;作业：[pdd.sql](src/main/java/com/nj/learn/mysql/pdd.sql)   
&emsp;&emsp;作业：[pdd.pdm](src/main/java/com/nj/learn/mysql/pdd.pdm)  
 


### 第7周作业

2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率  

&emsp;&emsp;作业：[MillionTest](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/MillionTest.java)   

9.（必做）读写分离 - 动态切换数据源版本 1.0  
基于：AbstractRoutingDataSource 

&emsp;&emsp;作业：[ApplicationTests](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/ApplicationTests.java)   
&emsp;&emsp;方法：testDataSource() 测试切换数据源    

10.（必做）读写分离 - 数据库框架版本 2.0   
基于：shardingSphere-JDBC

&emsp;&emsp;作业：[ApplicationTests](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/ApplicationTests.java)  
&emsp;&emsp;方法：testReaderAndWriter()  测试写入  
&emsp;&emsp;方法：testReaderAndWriter()  测试读取 
 
 ### 第8周作业
 
2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。
 
 &emsp;&emsp;作业：[DatabaseTablesTest](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/DatabaseTablesTest.java)   
 &emsp;&emsp;sql：[database-tables.sql](src/main/java/com/nj/learn/spring/src/test/resources/database-tables.sql)   
 &emsp;&emsp;配置文件：[application-sharding-databases-tables.properties](src/main/java/com/nj/learn/spring/src/test/resources/application-sharding-databases-tables.properties)   
 
6.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。
 基于：AbstractRoutingDataSource 
 
 &emsp;&emsp;作业：[XATest](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/XATest.java)   
 
  ### 第9周作业
  
 2.3.（必做）改造自定义 RPC 的程序，提交到 GitHub：  
   
   尝试将服务端写死查找接口实现类变成泛型和反射；  
   尝试将客户端动态代理改成 AOP，添加异常处理；  
   尝试使用 Netty+HTTP 作为 client 端传输方式。  
  
  &emsp;&emsp;作业：[rpc](src/main/java/com/nj/learn/rpc)   
  
7.（必做）结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:
  
用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;  
用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;  
设计账户表，冻结资产表，实现上述两个本地事务的分布式事务
  
  &emsp;&emsp;作业：[XATest](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/XATest.java)   
  
  