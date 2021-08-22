package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        try {
            // 作业1：改成泛型和反射
            //直接用反射
//            Object result = invokeOne(serviceClass,request);
            Object result = invokeTwo(serviceClass,request);
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( Exception e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
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
    private Object invokeTwo(String serviceClass, RpcfxRequest request) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = Class.forName(serviceClass);
        Object obj = resolver.resolveT(aClass);
        Method method = resolveMethodFromClass(aClass, request.getMethod());
        return method.invoke(obj,request.getParams());
    }

    private Object invokeOne(String serviceClass, RpcfxRequest request) throws InvocationTargetException, IllegalAccessException {
        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
        Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
        return method.invoke(service, request.getParams()); // dubbo, fastjson,
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
