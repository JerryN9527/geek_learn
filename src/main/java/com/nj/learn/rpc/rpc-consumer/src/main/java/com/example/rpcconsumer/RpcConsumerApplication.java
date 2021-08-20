package com.example.rpcconsumer;

import com.example.rpc.client.Rpcfx;
import com.example.rpc.demo.api.Order;
import com.example.rpc.demo.api.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args);

        //调用
        OrderService orderService =  Rpcfx.create(OrderService.class, "http://localhost:8080/");
        Order orderById = orderService.findOrderById(1);
        System.out.println("实际调用结果：");
        System.out.println(orderById);
    }

}
