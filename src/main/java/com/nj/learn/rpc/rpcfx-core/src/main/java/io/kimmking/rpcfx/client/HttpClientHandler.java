package io.kimmking.rpcfx.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 入站操作
 */
public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    private String body;
    private URI uri;


    public HttpClientHandler(String body, URI uri) {
        this.body=body;
        this.uri=uri;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0
                , HttpMethod.POST
                ,uri.toASCIIString()
                , Unpooled.wrappedBuffer(body.getBytes()));

        // 构建http请求
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
        request.headers().add(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");


        ctx.write(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("msg -> "+msg);
        if(msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse)msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> "+result);
            buf.release();
            AttributeKey<String> key = AttributeKey.valueOf(NettyHttpClient.RPC_CLIENT_SERVER_DATA);
            ctx.channel().attr(key).set(result);
            ctx.channel().close();
        }
    }

}