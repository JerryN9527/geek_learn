package com.example.rpc.api;

/**
 * 用与bean获取
 */
public interface RpcResolver {

    /**
     * 获取bean
     * @param serviceClass
     * @return
     */
    Object resolve(String serviceClass);

    /**
     * 获取bean+泛型
     * @param aClass
     * @param <T>
     * @return
     */
    <T>T resolveT(Class<T> aClass);
}
