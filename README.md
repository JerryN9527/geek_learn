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

&emsp;&emsp;作业：[ApplicationTests](src/main/java/com/nj/learn/spring/src/test/java/com/example/spring/ApplicationTests.java) 增删改查   

