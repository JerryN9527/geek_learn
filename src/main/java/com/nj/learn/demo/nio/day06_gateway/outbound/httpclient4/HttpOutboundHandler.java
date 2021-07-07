package com.nj.learn.demo.nio.day06_gateway.outbound.httpclient4;

import com.nj.learn.demo.nio.day06_gateway.filter.HeaderHttpResponseFilter;
import com.nj.learn.demo.nio.day06_gateway.filter.HttpResponseFilter;
import com.nj.learn.demo.nio.day06_gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class HttpOutboundHandler{

    private CloseableHttpAsyncClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String backendUrl = router
        final String url = this.backendUrl + fullRequest.uri();

    }
}
