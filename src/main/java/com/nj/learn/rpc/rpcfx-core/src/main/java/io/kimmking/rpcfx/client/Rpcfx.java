package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.*;
import io.kimmking.rpcfx.exception.RpcfxException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static io.kimmking.rpcfx.client.Rpcfx.RpcfxInvocationHandler.postByNetty;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) {

        // 加filte之一

        // curator Provider list from zk
        List<String> invokers = new ArrayList<>(); // 监听zk的临时节点，根据事件更新这个list

        List<String> urls = router.route(invokers);

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url, filter);

    }

    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {

//        // 0. 替换动态代理 -> AOP
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader()
//                , new Class[]{serviceClass}
//                , new RpcfxInvocationHandler(serviceClass, url, filters));

        // 0. 替换动态代理 -> AOP
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new MyMethodInterceptor(serviceClass, url));
        return (T)enhancer.create();

    }

    public static class MyMethodInterceptor implements MethodInterceptor {

        public final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        public <T> MyMethodInterceptor(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable{

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(args);

//            RpcfxResponse response = post(request, url);
            RpcfxResponse response = postByNetty( request,url);
            if(!response.isStatus()){
                // 这里判断response.status，处理异常
                //failed
                throw  new RpcfxException();
            }

            // 考虑封装一个全局的RpcfxException

            return JSON.parse(response.getResult().toString());
        }


    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            // 加filter地方之二
            // mock == true, new Student("hubao");

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            if(null != filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        throw new RuntimeException(String.format("filter {} return false.", filter.toString()));
                    }
                }
            }

            //请求
//            RpcResponse rpcResponse = post(rpcRequest,url);
            RpcfxResponse rpcResponse = postByNetty( request,url);
            // 加filter地方之三
            // Student.setTeacher("cuijing");

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException

            return JSON.parse(rpcResponse.getResult().toString());
        }



        /**
         * 采用netty远程调用
         *
         * @param rpcRequest
         * @param url
         * @return
         */
        public static RpcfxResponse postByNetty(RpcfxRequest rpcRequest, String url) {
            String body = JSON.toJSONString(rpcRequest);
            RpcfxResponse result = null;
            try {
                result = NettyHttpClient.post("http://localhost:8080", body);
            } catch (Exception e) {
                e.printStackTrace();
                result =new RpcfxResponse();
                result.setException(new RpcfxException());
            }

            return result;
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
