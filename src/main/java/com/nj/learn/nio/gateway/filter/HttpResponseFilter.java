package com.nj.learn.nio.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 响应过滤器
 */
public interface HttpResponseFilter {
    void filter(FullHttpResponse response);
}
