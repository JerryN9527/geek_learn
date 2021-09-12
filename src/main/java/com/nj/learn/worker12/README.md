### 一、配置redis主从复制
&emsp;&emsp;我是在linux系统中操作   
  
**1.直接通过yum 安装redis**  
```bash
#下载redis  
yum install redis
#查看版本号
[root@localhost etc]# redis-server -v
Redis server v=3.2.12 sha=00000000:0 malloc=jemalloc-3.6.0 bits=64 build=7897e7d0e13773f
#先启动试一下
[root@localhost redis]# systemctl start redis
[root@localhost redis]# redis-cli 
127.0.0.1:6379> keys *
(empty list or set)
127.0.0.1:6379> exit
#正常启动后 关闭
[root@localhost redis]# systemctl stop redis
```

2.然后开始我们的主从配置  
&emsp;&emsp;有两种方式：  
&emsp;&emsp;&emsp;&emsp;直接在服务里面从节点执行:> SLAVEOF 127.0.0.1 6379  
&emsp;&emsp;&emsp;&emsp;也可以在配置文件中设置:replicaof 127.0.0.1 6380（例如在6379中conf配配置成6380的从节点）
```bash
#然后开始我们的主从配置
#查看redis 配置文件地址
[root@localhost redis]# whereis redis
redis: /etc/redis.conf
#可以直接复制这个配置文件，到我们自定义的文件目录下/home/redis
cp /etc/redis.conf /home/redis
#在复制一份,作为从机备用
cp /etc/redis.conf /home/redis/6380.conf

#进入到自定义目录下，修改从机配置
cd /home/redis
vim 6380.conf 
```
>修改其中端口号，和后台运行，这里先采用第一种方式，不在配置里配置主从
>port 6380   
>daemonize yes

主从方法一：直接在服务里面从节点执行:> SLAVEOF 127.0.0.1 6379  
```bash
#启动两个redis
[root@localhost redis]# redis-server 6379.conf 
[root@localhost redis]# redis-server 6380.conf 
#进入redis查看
#6379
[root@localhost ~]# redis-cli 
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

#6380
[root@localhost ~]# redis-cli -p 6380
127.0.0.1:6380> info replication
# Replication
role:master
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
>在6380中执行SLAVEOF 127.0.0.1 6379  
>设置6379为主机
```bash
127.0.0.1:6380> SLAVEOF 127.0.0.1 6379  
OK
127.0.0.1:6380> info replication
# Replication
role:slave  #此时角色变成了 从机节点
master_host:127.0.0.1
master_port:6379
master_link_status:up
master_last_io_seconds_ago:2
master_sync_in_progress:0
slave_repl_offset:15
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

#检测一下
#6379中：
127.0.0.1:6379> set aaa hello
OK
127.0.0.1:6379> get aaa 
"hello"

#6380中：
127.0.0.1:6380> keys *
1) "xiaobai"
2) "aaa"

127.0.0.1:6380> get aaa
"hello"
```

### 二、redis中配置sentinel 高可用
&emsp;&emsp;先配置3台redis节点，3台sentienl哨兵
```bash
#复制6380.conf,创建一个6381的节点
[root@localhost redis]# cp 6380.conf 6381.conf
#修改端口号 port=6381
[root@localhost redis]# vim 6381.conf 
[root@localhost redis]# redis-server 6381.conf 
#查看三台redis
[root@localhost redis]# ps -ef|grep redis
redis      2204      1  0 14:48 ?        00:00:03 /usr/bin/redis-server 127.0.0.1:6379
root       2315   2272  0 15:00 pts/1    00:00:00 redis-cli
root       2372      1  0 15:11 ?        00:00:01 redis-server 127.0.0.1:6380
root       2375   2316  0 15:12 pts/2    00:00:00 redis-cli -p 6380
root       2799      1  0 15:44 ?        00:00:00 redis-server 127.0.0.1:6381
root       2804   2687  0 15:44 pts/3    00:00:00 grep --color=auto redis
[root@localhost redis]# redis-cli -p 6381
#建立主从关系
127.0.0.1:6381> SLAVEOF 127.0.0.1 6379  
OK
127.0.0.1:6381> info replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6379
master_link_status:up
master_last_io_seconds_ago:2
master_sync_in_progress:0
slave_repl_offset:2451
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```
>copy sentinel.conf文件
>复制三份，并依次修改，配置修改如下：
>redis-sentinel.cof:
>sentinel monitor mymaster 127.0.0.1 6379 2 #代表选举至少需要2票
>sentinel down-after-milliseconds mymaster 60000  #监听 选举时间60秒
>sentinel failover-timeout mymaster 180000  #整个failover 超时时间 3分钟
>sentinel parallel-syncs mymaster 1  #单位时间只有1个节点可以并行的从主库拉取数据
>daemonize yes 设置后台启动

```bash
#修改哨兵配置
[root@localhost redis]# vim redis-sentinel.conf 
[root@localhost redis]# vim sentinel-26380.conf 
[root@localhost redis]# vim sentinel-26381.conf 
#启动哨兵
[root@localhost redis]# redis-sentinel redis-sentinel.conf 
[root@localhost redis]# redis-sentinel sentinel-26380.conf 
[root@localhost redis]# redis-sentinel sentinel-26381.conf 
[root@localhost redis]# ps -ef|grep redis
redis      2204      1  0 14:48 ?        00:00:04 /usr/bin/redis-server 127.0.0.1:6379
root       2315   2272  0 15:00 pts/1    00:00:00 redis-cli
root       2372      1  0 15:11 ?        00:00:02 redis-server 127.0.0.1:6380
root       2375   2316  0 15:12 pts/2    00:00:00 redis-cli -p 6380
root       2799      1  0 15:44 ?        00:00:00 redis-server 127.0.0.1:6381
root       2875      1  0 15:59 ?        00:00:00 redis-sentinel *:26379 [sentinel]
root       2893      1  0 16:00 ?        00:00:00 redis-sentinel *:26380 [sentinel]
root       2897      1  0 16:00 ?        00:00:00 redis-sentinel *:26381 [sentinel]
root       2901   2687  0 16:00 pts/3    00:00:00 grep --color=auto redis

#测试：
当我们shutdown 6379 主机，此时到了选举时间  6380 和6381会有一台机器当选主机
```

### 三、redis中配置Cluster 集群。
1.先把 刚启动的redis 全停止，命令如下：
```bash
[root@localhost redis]# redis-cli -p 6379 shutdown &
[root@localhost redis]# redis-cli -p 26379 shutdown &
```
2.准备一个文件夹cluster，在里面分别创建7000,7001,7002,7003,7004,7005一共6个，6个节点
```bash
[root@localhost ~]# cd /home/redis/cluster/
[root@localhost cluster]# ls
7000  7001  7002  7003  7004  7005
```
3.复制并修改配置文件 redis.conf 到每个节点目录并修改
```bash
[root@localhost redis]# cp redis.conf cluster/7000
[root@localhost redis]# vim cluster/7000/redis.conf 
[root@localhost redis]# vim cluster/7000/redis.conf 
[root@localhost redis]# cp cluster/7000/redis.conf cluster/7001/
[root@localhost redis]# cp cluster/7000/redis.conf cluster/7002/
[root@localhost redis]# cp cluster/7000/redis.conf cluster/7003/
[root@localhost redis]# cp cluster/7000/redis.conf cluster/7004/
[root@localhost redis]# cp cluster/7000/redis.conf cluster/7005/
[root@localhost redis]# vim cluster/7001/redis.conf 
[root@localhost redis]# vim cluster/7002/redis.conf 
[root@localhost redis]# vim cluster/7003/redis.conf 
[root@localhost redis]# vim cluster/7004/redis.conf 
[root@localhost redis]# vim cluster/7005/redis.conf 
```
>修改具体配置如下：
>*1.注释本地绑定IP地址-注释69		 -- # bind 127...1 
> 	
> 	*2.关闭保护模式-88 					protected-mode no
> 		
> 	*3.修改端口号-92						 port 7000
> 	
> 	*4.启动后台启动-136					 daemonize yes
> 	
> 	*5.修改pid文件-158						 pidfile /7000路径/redis.pid
> 	
> 	*6.修改持久化文件路径-263		 dir /7000路径
> 	
> 	*7.设定内存优化策略-597 				maxmemory-policy volatile-lru
> 	
> 	*8.关闭AOF模式-699					 appendonly no 
> 	
> 	*9.开启集群配置-838 					cluster-enabled yes
> 	
> 	*10.开启集群配置文件-846			 cluster-config-file nodes.conf
> 	
> 	*11.修改集群超时时间-852 			cluster-node-timeout 15000

4.在cluster目录下创建脚本文件start.sh 和 stop.sh
&emsp;&emsp;启动start.sh
>#!/bin/sh
>redis-server 7000/redis-7000.conf &
>redis-server 7001/redis-7001.conf &
>redis-server 7002/redis-7002.conf &
>redis-server 7003/redis-7003.conf &
>redis-server 7004/redis-7004.conf &
>redis-server 7005/redis-7005.conf &


&emsp;&emsp;停止stop.sh
>#!/bin/sh
>redis-cli -p 7000 shutdown &
>redis-cli -p 7001 shutdown &
>redis-cli -p 7002 shutdown &
>redis-cli -p 7003 shutdown &
>redis-cli -p 7004 shutdown &
>redis-cli -p 7005 shutdown &

5.创建集群主从指令：
&emsp;&emsp;3.0版本的会用到rubygems组件可参考博客：https://blog.csdn.net/wudalang_gd/article/details/52121204
```bash
#5.0版本的 1主机1从机
redis-cli --cluster create --cluster-replicas 1 192.168.65.3:7000 192.168.65.3:7001 192.168.65.3:7002 192.168.65.3:7003 192.168.65.3:7004 192.168.65.3:7005 

#我们用这个 yum上的是3.0版本的，
./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 \ 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```
 
