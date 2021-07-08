package com.nj.learn.demo.nio.day06_gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;


/**
 * 自定义HttpInitializer初始化机制继承 ChannelInitializer
 *      初始化的时候用来绑定我们所需要的ChannelHandler处理器到Channel管道上的流水线Pipelin
 *
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServer;

    public HttpInboundInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

    /**
     *  建立初始化的通道 SocketChannel通道
     */
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取流水线对象
        ChannelPipeline pipeline = channel.pipeline();
        //绑定事务处理机制
        pipeline.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        //创建handler聚合器
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        //添加处理机制handler
        pipeline.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
