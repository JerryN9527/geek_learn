package com.example.spring;

import com.example.spring.bean.DataSourceNames;
import com.example.spring.config.DynamicDataSource;
import com.example.spring.config.DynamicDataSourceConfig;
import com.example.spring.generate.GGoods;
import com.example.spring.generate.GGoodsDao;
import com.example.spring.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;
import java.util.List;


@Import({DynamicDataSourceConfig.class})
@MapperScan("com.example.spring.generate")
@SpringBootTest
class ApplicationTests {

    @Autowired
    private GGoodsDao gGoodsDao;

    /**
     * 测试多数据源切换
     * @throws SQLException
     */
    @Test
    void testDataSource() {
        //切换数据源
        DynamicDataSource.setDataSource(DataSourceNames.FIRST);
        List<GGoods> all = gGoodsDao.findAll();
        System.out.println(all);
        //清除
        DynamicDataSource.clearDataSource();

        //切换数据源
        DynamicDataSource.setDataSource(DataSourceNames.SECOND);
        List<GGoods> all2 = gGoodsDao.findAll();
        System.out.println(all2);
        //清除
        DynamicDataSource.clearDataSource();
    }
}
