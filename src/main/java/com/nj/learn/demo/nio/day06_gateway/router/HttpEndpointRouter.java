package com.nj.learn.demo.nio.day06_gateway.router;

import java.util.List;

public interface HttpEndpointRouter {
    String route(List<String> backendUrls);
}
