package com.nj.learn.nio.gateway.server;

import com.nj.learn.nio.gateway.inbound.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 启动Netty并配置
 */
public class GatewayNettyServer {

    private int port;
    private List<String> serverProxy;

    public GatewayNettyServer(int port, List<String> serverProxy) {
        this.port = port;
        this.serverProxy = serverProxy;
    }

    /**
     * 启动
     */
    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup worGroup = new NioEventLoopGroup(16);


        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            //设置参数
            //SO_BACKLOG,TCP连接中的连接数128
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
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

            bootstrap.group(bossGroup,worGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInboundInitializer(serverProxy));
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
