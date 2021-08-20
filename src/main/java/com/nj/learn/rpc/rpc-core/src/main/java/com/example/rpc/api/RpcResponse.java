package com.example.rpc.api;

import lombok.Data;

@Data
public class RpcResponse {
    /**
     * 响应状态
     */
    private String status;

    /**
     * 返回值
     */
    private Object result;


    /**
     * xinxi
     */
    private String message;



}
