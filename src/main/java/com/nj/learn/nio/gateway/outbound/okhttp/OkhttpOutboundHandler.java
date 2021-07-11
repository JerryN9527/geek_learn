package com.nj.learn.nio.gateway.outbound.okhttp;

import com.nj.learn.nio.gateway.filter.HeaderHttpResponseFilter;
import com.nj.learn.nio.gateway.filter.HttpResponseFilter;
import com.nj.learn.nio.gateway.outbound.httpclient4.NamedThreadFactory;
import com.nj.learn.nio.gateway.router.HttpEndpointRouter;
import com.nj.learn.nio.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 使用okhtttpclient实现业务请求
 */
public class OkhttpOutboundHandler {

    private ExecutorService proxyService;
    private List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public OkhttpOutboundHandler(List<String> backends) {
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();


    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }

    /**
     * 处理业务请求规则
     * @param fullRequest
     * @param ctx
     */
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        //路由获取指定业务地址
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        proxyService.submit(()->fetchGet(fullRequest,ctx,url));
    }

    /**
     * 转发请求
     * @param inbound
     * @param ctx
     * @param url
     */
    private void fetchGet(final FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        //发送请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        FullHttpResponse response = null;
        try {
            Response endpointResponse = client.newCall(request).execute();
            String bodyStr=endpointResponse.body().string();


            byte [] body = bodyStr.getBytes();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            //添加Http请求头属性
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length",Integer.valueOf(endpointResponse.header("Content-Length")));

            //过滤器过滤
            filter.filter(response);

        }catch (Exception e){
            e.printStackTrace();
            new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx,e);
        }finally {
            if (fullRequest != null){
                if (!HttpUtil.isKeepAlive(fullRequest)){
                    //不是存活状态则 写出响应
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                }else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            //刷新
            ctx.flush();
            //ctx.close();
        }
    }


    /**
     * 请求错误后，关闭处理
     * @param ctx
     * @param cause
     */
    private void exceptionCaught(ChannelHandlerContext ctx, Exception cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
