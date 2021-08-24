package com.example.spring;

import com.example.spring.bean.DataSourceNames;
import com.example.spring.config.DynamicDataSource;
import com.example.spring.generate.GGoods;
import com.example.spring.generate.GGoodsDao;
import com.example.spring.generate.TOrderDao;
import generate.TOrder;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * 测试分库分表
 */
@SpringBootTest
@MapperScan("com.example.spring.generate")
public class XATest {

    @Autowired
    private GGoodsDao gGoodsDao;

    @Autowired
    private TOrderDao tOrderDao;


    /**
     * 测试XA事务
     * @throws SQLException
     */
    @Test
    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    void testDataSource() {
//        tOrderDao.deleteAll();

        TOrder a = new TOrder();
        a.setUserId(1);
        a.setOrderId(16l);
        a.setStatus("222222");
        tOrderDao.insertSelective(a);

        TOrder b = new TOrder();
        b.setUserId(2);
        b.setOrderId(16l);
        b.setStatus("222222");
        tOrderDao.insertSelective(b);
        int i = 1 / 0;

        List<TOrder> all = tOrderDao.findAll();
        System.out.println(all.size());
    }

    /**
     * 查看XA事务是否回滚
     */
    @Test
    void testSelect() {
        List<TOrder> all = tOrderDao.findAll();
        System.out.println(all.size());
    }
}
