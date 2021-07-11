package com.nj.learn.demo.nio.day06_gateway.outbound.httpclient4;

import com.nj.learn.demo.nio.day06_gateway.filter.HeaderHttpResponseFilter;
import com.nj.learn.demo.nio.day06_gateway.filter.HttpRequestFilter;
import com.nj.learn.demo.nio.day06_gateway.filter.HttpResponseFilter;
import com.nj.learn.demo.nio.day06_gateway.router.HttpEndpointRouter;
import com.nj.learn.demo.nio.day06_gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 使用htttpclient4实现业务请求
 */
public class HttpOutboundHandler{
    /**
     * 使用htttpclient4实现业务请求
     */
    private CloseableHttpAsyncClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(List<String> backends) {
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

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response,context) -> 6000)
                .build();
        httpclient.start();
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }

    /**
     * 处理业务请求规则
     * @param fullRequest
     * @param ctx
     */
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilter filter) {
        //路由获取指定业务地址
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        //过滤器进行请求头过滤
        filter.filter(fullRequest, ctx);
        proxyService.submit(()->fetchGet(fullRequest,ctx,url));
    }

    /**
     * 转发请求
     * @param inbound
     * @param ctx
     * @param url
     */
    private void fetchGet(final FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE,HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("mao",inbound.headers().get("mao"));
        //发送请求
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse endpointResponse) {
                handleResponse(inbound,ctx,endpointResponse);
            }

            @Override
            public void failed(final Exception e) {
                //终止请求
                httpGet.abort();
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                //终止请求
                httpGet.abort();
            }
        });


    }

    /**
     * 处理响应数据
     * @param fullRequest
     * @param ctx
     * @param endpointResponse
     */
    private void handleResponse(final FullHttpRequest fullRequest,final ChannelHandlerContext ctx,final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());

            byte [] body = EntityUtils.toByteArray(endpointResponse.getEntity());
//            System.out.println(new String(body));
//            System.out.println(body.length);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            //添加Http请求头属性
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

            //过滤器过滤
            filter.filter(response);

//            for (Header e : endpointResponse.getAllHeaders()) {
//                //response.headers().set(e.getName(),e.getValue());
//                System.out.println(e.getName() + " => " + e.getValue());
//            }
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
