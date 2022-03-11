package com.example.rpc.api;

import lombok.Data;

/**
 * 响应实体
 */
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

    private Exception exception;

}
