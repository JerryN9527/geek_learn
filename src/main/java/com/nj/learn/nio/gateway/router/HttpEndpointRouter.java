package com.nj.learn.nio.gateway.router;

import java.util.List;

public interface HttpEndpointRouter {
    String route(List<String> backendUrls);
}
