package com.example.rpcprovider;


import com.example.rpc.demo.api.Order;
import com.example.rpc.demo.api.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "hello rpc" + System.currentTimeMillis(), 9.9f);
    }
}
