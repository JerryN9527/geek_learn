package com.nj.learn.demo.nio.day06_gateway.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 网关入站服务
 */
public class HttpInboundServer {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private int port;

    private List<String> proxyServer;

    public HttpInboundServer(int port, List<String> proxyServer) {
        this.port = port;
        this.proxyServer = proxyServer;
    }

    /**
     * 入站方法
     *      利用Netty框架创建网关服务端
     */
    public void run() throws InterruptedException {
        //创建主从Reactor模式
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap server = new ServerBootstrap();
            //设置参数
            //SO_BACKLOG,TCP连接中的连接数128
            server.option(ChannelOption.SO_BACKLOG, 128)
                    //禁用TCP缓冲区Nagle算法 Disable the Nagle algorithm
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //复用TIME-WAIT 的连接还没完全死掉；
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    //复用TIME-WAIT 的连接还没完全死掉；
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            //绑定Reactor
            server.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //绑定自定义的初始化Channel对象
                    .childHandler(new HttpInboundInitializer(proxyServer));

            Channel channel = server.bind(port).sync().channel();
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            //优雅的监听关闭
            channel.closeFuture().sync();
        }finally {
            //关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
