### 一、搭建一个 3 节点 Kafka 集群
&emsp;&emsp;我是在linux系统中操作   
  
**1.准备三个properties配置文件**  
>kafka9001.properties  kafka9002.properties  kafka9003.properties，可以从单机版的copy过来  
--分别修改其中配置：  
       -broker.id=1  
       -listeners=PLAINTEXT://:9001   
       -log.dirs=/home/kafka/logs/kafka-log1   
       -broker.list=localhost:9001,localhost:9002,localhost:9003   
   
**2.清理之前单机版zookeeper中的数据：**  
> -可以删除 zk 的本地文件或者用 ZooInspector 操作  
        -下载ZooInspector 地址：https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip  
        -下载后进入build目录， 执行java -jar zookeeper-dev-ZooInspector.jar  
        -删除zookeeper 以外的所有文件和文件夹    
>启动zookeeper：
        - nohup bin/kafka-server-start.sh config/server.properties >/dev/null 2>&1 &

<br/>  

**3.打开三个窗口依次启动：**  
```linux
./bin/kafka-server-start.sh kafka9001.properties
./bin/kafka-server-start.sh kafka9002.properties
./bin/kafka-server-start.sh kafka9003.properties
```

**4.执行操作测试**  
&emsp;        *创建带有副本的 topic：   
```linux
#创建一个topic：test32，设置3个分区partitions：3,副本因子2个replication-factor：2  
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 2   
#查看topic：test32的分区情况。  
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic testk  
#创建一个生产者  
bin/kafka-console-producer.sh --bootstrap-server localhost:9003 --topic test32   
#创建一个消费者  
bin/kafka-console-consumer.sh --bootstrap-server localhost:9001 --topic test32 --from-beginning 

```

 >执行性能测试： 

```bash
-生产者性能检测
bin/kafka-producer-perf-test.sh --topic test32 --num-records 100000 --record-size 1000 --throughput 200000 --producer-props bootstrap.servers=localhost:9002 
-消费者性能检测
bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9002 --topic test32 -- fetch-size 1048576 --messages 100000 --threads 1
```
