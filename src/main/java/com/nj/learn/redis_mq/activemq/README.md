### 一、配置activeMQ
&emsp;&emsp;我是在linux系统中操作   
  
**1.下载activeMQ**  
>https://activemq.apache.org/components/classic/download/

2.上传到服务器中解压  
```bash
tar -zxvf apache-activemq-5.16.3-bin.tar.gz
```

3.由于我是在虚拟机安装，需要修改jetty.xml 中IP地址0.0.0.0   
```bash
[root@localhost activeMq]# vim apache-activemq-5.16.3/conf/jetty.xml 
```
> 修改成： <property name="host" value="0.0.0.0"/>   

4.启动
```bash
[root@localhost bin]#./activemq start
INFO: Loading '/home/activeMq/apache-activemq-5.16.3//bin/env'
INFO: Using java '/home/jdk1.8.0_212/bin/java'
INFO: Starting - inspect logfiles specified in logging.properties and log4j.properties to get details
INFO: pidfile created : '/home/activeMq/apache-activemq-5.16.3//data/activemq.pid' (pid '1734')
[root@localhost bin]# tail -f cd ../data/*.log
```