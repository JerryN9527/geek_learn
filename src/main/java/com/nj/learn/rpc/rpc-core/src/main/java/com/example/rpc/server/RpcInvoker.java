package com.example.rpc.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.rpc.api.RpcRequest;
import com.example.rpc.api.RpcResolver;
import com.example.rpc.api.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 生产者端进行反射调用
 */
public class RpcInvoker {
    private RpcResolver resolver;

    public RpcInvoker(RpcResolver resolver){
        this.resolver = resolver;
    }

    public RpcResponse invoke(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        String serviceClass = request.getServiceClass();
        try {
            // 作业1：改成泛型和反射
            //直接用反射
//            Object result = invokeOne(serviceClass,request);
            Object result = invokeTwo(serviceClass,request);
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
//            response.setResult(JSON.toJSONString(result));
            response.setStatus("200");
            return response;
        } catch ( Exception e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setStatus("710");
            return response;
        }

    }

    /**
     * 反射加泛型
     * @param serviceClass
     * @param request
     * @return
     * @throws ClassNotFoundException
     */
    private Object invokeTwo(String serviceClass, RpcRequest request) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = Class.forName(serviceClass);
        Object obj = resolver.resolveT(aClass);
        Method method = resolveMethodFromClass(aClass, request.getMethod());
        return method.invoke(obj,request.getParams());
    }

    private Object invokeOne(String serviceClass, RpcRequest request) throws InvocationTargetException, IllegalAccessException {
        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
        Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
        return method.invoke(service, request.getParams()); // dubbo, fastjson,
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
