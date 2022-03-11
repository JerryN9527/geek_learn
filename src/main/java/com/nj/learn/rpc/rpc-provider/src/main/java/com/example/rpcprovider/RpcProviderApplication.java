package com.example.rpcprovider;

import com.example.rpc.api.RpcRequest;
import com.example.rpc.server.RpcInvoker;
import com.example.rpc.api.RpcResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApplication.class, args);
    }

    @Autowired
    RpcInvoker invoker;

    @PostMapping("/")
    private Object ppp(@RequestBody RpcRequest request){
        return invoker.invoke(request);
    }

    @Bean
    public RpcInvoker creatBean(@Autowired RpcResolver resolver){
        return new RpcInvoker(resolver);
    }
}
