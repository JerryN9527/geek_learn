package com.nj.learn.demo.nio.day05_netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 模拟简单的Netty框架使用示例
 *         Channel            ：通道，打开一个连接，可执行读取/写入IO操作
 *         ChannelFuture      ：Java的Future接口，只能查询操作的完成情况，可以把一个回调方法添加到 ChannelFuture上，
 *                              作为操作做完后的回调通知
 *         Event & Handler    ：Netty框架是基于事件机制的，在中间各个处理的环节节点之间通信的时候，通过发送事件的方式来传递消息，
 *                              下游进行处理对应的处理器就是Handler
 *         Encoder & Decoder  ： 编码器和解码器，处理网络IO时，进行序列化和反序列化，内置很多开箱即用的编码器和解码器
 *         ChannelPipeline    ： 一个Pipeline对应一个流水线，里面对应不同的处理环节。
 */
public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {

        int port = 8808;

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInitializer());

            Channel ch = b.bind(port).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}