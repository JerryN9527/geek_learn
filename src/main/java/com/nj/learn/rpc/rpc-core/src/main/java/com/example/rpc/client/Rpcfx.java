package com.example.rpc.client;

import com.alibaba.fastjson.JSON;
import com.example.rpc.api.RpcRequest;
import com.example.rpc.api.RpcResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPC核心板块
 */
public class Rpcfx {

    
    
    public static <T>T create(final Class<T> tClass,final String url) {
        //用动态代理替换
        return (T)Proxy.newProxyInstance(Rpcfx.class.getClassLoader(),new Class[]{tClass},new RpcHandler(tClass, url));
    }


    /**
     * 自定义处理类，动态代理实际运行的类
     */
    public static class RpcHandler implements InvocationHandler{
        private static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
        private final Class<?> serviceClass;
        private final String url;

        public RpcHandler(Class<?> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        //实际调用的方法
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            //建立一个请求体
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setMethod(method.getName());
            rpcRequest.setParams(params);
            rpcRequest.setServiceClass(this.serviceClass.getName());
            //请求
            RpcResponse rpcResponse = post(rpcRequest,url);
            //返回具体响应内容
            return JSON.parse(rpcResponse.getResult().toString());
        }

        /**
         * 采用OKHttp远程调用
         * @param rpcRequest
         * @param url
         * @return
         */
        private RpcResponse post(RpcRequest rpcRequest, String url) throws IOException {
            String reqJson = JSON.toJSONString(rpcRequest);
            System.out.println("远程请求参数： "+reqJson);
            
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request =  new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE,reqJson))
                    .build();
            String respJson = okHttpClient.newCall(request).execute().body().string();
            System.out.println("远程调用返回结果："+respJson);
            return JSON.parseObject(respJson,RpcResponse.class);
        }
    }
}
