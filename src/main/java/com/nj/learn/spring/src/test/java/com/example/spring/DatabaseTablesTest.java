package com.example.spring;

import com.example.spring.generate.TOrderDao;
import generate.TOrder;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

/**
 * 测试分库分表
 */
@SpringBootTest
@MapperScan("com.example.spring.generate")
public class DatabaseTablesTest {

    @Autowired
    private TOrderDao tOrderDao;


    /**
     * 测试分片
     * @throws SQLException
     */
    @Test
    void testDataSource() {
        TOrder order =new TOrder();
        order.setStatus("2");
        order.setUserId(1);
        tOrderDao.insert(order);

        TOrder tOrder = tOrderDao.selectByPrimaryKey(1l);
        System.out.println(tOrder);
        List<TOrder> all = tOrderDao.findAll();
        System.out.println(all);
    }

}
