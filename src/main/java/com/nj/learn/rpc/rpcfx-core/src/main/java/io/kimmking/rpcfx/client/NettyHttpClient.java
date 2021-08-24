package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;
import org.springframework.util.StringUtils;

import java.net.URI;


/**
 * 使用Netty 做http请求
 */
public class NettyHttpClient {

    public final static String RPC_CLIENT_SERVER_DATA = "SERVER_DATA";


    /**
     * 发送post请求
     * @param url
     * @param body
     * @return
     */
    public static RpcfxResponse post(String url, String body) throws Exception {
        String result = connectPost(url, body);
        return  JSON.parseObject(result,RpcfxResponse.class);
    }

    public static String connectPost(String url, String body) throws Exception {
        URI uri = new URI(url);

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//                    ch.pipeline().addLast(new HttpResponseDecoder());
////                     客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                    ch.pipeline().addLast(new HttpRequestEncoder());
////                    ch.pipeline().addLast(new HttpClientOutboundHandler());
//                }
//            });
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel channel)
                        throws Exception {
                    //channel.pipeline().addLast(new HttpRequestEncoder());
                    //channel.pipeline().addLast(new HttpResponseDecoder());
                    channel.pipeline().addLast(new HttpClientCodec());
                    channel.pipeline().addLast(new HttpObjectAggregator(65536));
                    channel.pipeline().addLast(new HttpContentDecompressor());
                    channel.pipeline().addLast(new HttpClientHandler(body,uri));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(uri.getHost(), uri.getPort()).sync();

//            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();

            Object result;
            do{
                AttributeKey<String> key = AttributeKey.valueOf(RPC_CLIENT_SERVER_DATA);
                 result = f.channel().attr(key).get();
            }while (result == null);
            System.out.println("netty返回结果值："+result.toString());
            return result.toString();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyHttpClient client = new NettyHttpClient();
        RpcfxRequest rpcRequest = new RpcfxRequest();
        rpcRequest.setServiceClass("OrderService");
        String body = JSON.toJSONString(rpcRequest);
        String post = client.connectPost("http://localhost:8080", body);
        System.out.println(post);
    }
}
