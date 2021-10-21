##1)JVM  
>①#基本指令
 java -jar 启动java程序  
 javac  编译java  
 javap  反编译class文件  
 >jps/jinfo 查看java进程  
 >jps -lmv 查看java概括  
 >jps             查看java进程
 >kill -9  3326           杀死进程
 
> jinfo 3326[进程号] 查看某个进程的 jvm参数和 system系统的参数
> jinfo -flags 3326[进程号]  只看jvm参数  
>jinfo -flag PrintGCDetails 3326  
> jstat 查看JVM内部gc相关信息  
> -----  
> jstat -class 3326    查看类加载  
> jstat -compiler 3326 查看编译情况  
> jstat -gc 3326       查看垃圾回收情况  
> jstat -gcutil 3326 1000  查看垃圾回收情况1秒一次   
> jstack -l  3326           查看死锁情况  
 jstack -l  3326  > 3326.t   保存日志  

笔记地址：https://app.yinxiang.com/fx/cf8c614b-ee10-44d6-b0c2-ac032fae8aa0  
## 2)NIO
>同步：  
     阻塞： 阻塞I/O模型（BIO），I/O复用模型  
     非阻塞： 非阻塞I/O模型(NIO)，信号驱动I/O模型  
 异步：  
        非阻塞：异步I/O模型（windows的IOCP模型，Liunx内核不支持）  

笔记地址：https://app.yinxiang.com/fx/9e8f7311-7c97-4427-b67a-6e9931eeb622  

##3)并发编程  
>1.jdk并发包  
>2.synchronized 方式的问题：  
 1.同步代码块的阻塞  无法中断 （interruptibly）  
 2.同步块的阻塞      无法控制超时  （不能自动解锁）  
 3.同步块 无法异步处理锁（不能立即知道是否可以拿到锁）  
 4.同步块 无法根据条件灵活的加锁解锁 （只能跟同步块范围一致）  
>3.更自由的锁：Lock  
>4.LockSupport -- 锁当前线程，里面有很多静态方法  
>5.Atomic  工具类（原子类工具包java.util.concurrent.atomic）    
>----并发工具类：Semaphore， CountdownLatch， CyclicBarrier

笔记地址：https://app.yinxiang.com/fx/383c8d56-c14d-4cb9-b404-8dd90b5f8b45  

## 4)Spring 和 ORM 等框架
>2.spring framework 6大模块  
 1.Core : Bean/Context/Aop  
 2.Testing : Mock/TestContext         --4个常用的模块  
 3.DataAccess: Tx/JDBC/ORM  
 4.Spring Mvc/WebFlux:web  
> -----------------------
> 5.Integration: remoting/JMS/WS  
 6.Languages:Kotlin / Groovy  
3.Spring Aop  
>5.spring bean的核心原理  
>6.spring 容器对 bean进行前置后置操作bean，自定义类实现 BeanPostProcessor 接口  
>7.spring 中对bean获取bean name 和applicationcontext，bean对象实现两个接口BeanNameAware, ApplicationContextAware  
>
笔记地址：https://app.yinxiang.com/fx/6ca369e6-5d19-4d42-a1e3-ce9cd626ba28
  
  

 
##5)MySQL 数据库和 SQL
笔记地址：https://app.yinxiang.com/fx/55d59348-68e4-4b0a-be9d-6a5be793375f

##6) 分库分表  
笔记地址：https://app.yinxiang.com/fx/55d59348-68e4-4b0a-be9d-6a5be793375f

##7)RPC 和微服务
笔记地址：https://app.yinxiang.com/fx/498933e4-b4fd-49ae-b8a2-4d2d0e3f0191  

##8) 分布式缓存
笔记地址：https://app.yinxiang.com/fx/366d38bb-2463-491e-b59d-e4882519a2d4  
##9) 分布式消息队列
笔记地址：https://app.yinxiang.com/fx/12a87bf6-914e-4c05-ad9b-2821c7fd993a 