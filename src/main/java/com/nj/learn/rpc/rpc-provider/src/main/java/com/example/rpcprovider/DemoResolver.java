package com.example.rpcprovider;

import com.example.rpc.api.RpcResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


/**
 * 获取生产者的bean
 * Created by IntelliJ IDEA.
 * @author NJ
 * @create 2022/3/10 17:26
 */
@Service
public class DemoResolver implements RpcResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(String serviceClass) {
        return this.applicationContext.getBean(serviceClass);
    }

    @Override
    public <T> T resolveT(Class<T> aClass) {
        return this.applicationContext.getBean(aClass);
    }
}
