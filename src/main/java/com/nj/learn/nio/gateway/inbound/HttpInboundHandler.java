package com.nj.learn.nio.gateway.inbound;

import com.nj.learn.nio.gateway.filter.HeaderHttpRequestFilter;
import com.nj.learn.nio.gateway.outbound.httpclient4.HttpOutboundHandler;
import com.nj.learn.nio.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 *
 * 自定义HttpHandler 继承入站适配器，添加相应的处理业务逻辑
 * 捕获请求进来（入站事件）的处理
 * 扩展：
 * 事件处理程序接口：ChannelHandler                         两个具体实现各有一个适配器：（空实现，需要继承使用）
 *       两个具体实现：ChannelOutBoundHandler 出站事件处理        ChannelInboundHandlerAdapter
 *                  ChannelInBoundHandler  入站事件处理        ChannelOutboundHandlerAdapter
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private List<String> serverProxy;
    private HeaderHttpRequestFilter headerHttpRequestFilter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(List<String> serverProxy) {
        this.serverProxy = serverProxy;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            OkhttpOutboundHandler httpOutboundHandler = new OkhttpOutboundHandler(serverProxy);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            headerHttpRequestFilter.filter(fullRequest,ctx);
            httpOutboundHandler.handle(fullRequest,ctx);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }



//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }
}