package com.example.rpc.api;

import lombok.Data;

@Data
public class RpcRequest {

    /**
     * 哪个接口类
     */
    private String serviceClass;
    /**
     * 哪个方法
     */
    private String method;


    /**
     * 参数
     */
    private Object [] params;

}
