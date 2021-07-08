package com.nj.learn.demo.nio.day06_gateway.router;

import java.util.List;
import java.util.Random;

/**
 * 路由功能，后续可以实现负载均衡，灰度发布这些功能
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
