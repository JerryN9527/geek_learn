package com.nj.learn.nio.gateway;

import com.nj.learn.nio.gateway.server.GatewayNettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 网关启动器
 */

public class DemoGatewayApplication {

    private static Logger log = LoggerFactory.getLogger(DemoGatewayApplication.class);

    static final String GAETEWAY_NAME = "netty-gateway";
    static final String GAETEWAY_VERSION = "3.0";
    static final int port = 8111;


    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args) {
        //已注册的服务
        log.info("==={}:{} 启 动 中===",GAETEWAY_NAME,GAETEWAY_VERSION);
        String proxyServers =System.getProperty("proxyServers","http://localhost:8001,http://localhost:8002");
        GatewayNettyServer gatewayNettyConfig = new GatewayNettyServer(port,Arrays.asList(proxyServers.split(",")));
        gatewayNettyConfig.run();
        log.info("==={}:{} 启 动 成 功，请 访 问 http://localhost:{}/",GAETEWAY_NAME,GAETEWAY_VERSION,port);
    }
}
