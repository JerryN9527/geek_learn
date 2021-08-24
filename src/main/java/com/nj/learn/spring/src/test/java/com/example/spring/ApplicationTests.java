package com.example.spring;

import com.example.spring.bean.DataSourceNames;
import com.example.spring.config.DynamicDataSource;
//import com.example.spring.config.DynamicDataSourceConfig;
import com.example.spring.generate.GGoods;
import com.example.spring.generate.GGoodsDao;
import com.example.spring.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


//@Import({DynamicDataSourceConfig.class})
@MapperScan("com.example.spring.generate")
@SpringBootTest
class ApplicationTests {

    @Autowired
    private GGoodsDao gGoodsDao;

    /**
     * 测试多数据源切换
     * 需要引入DynamicDataSourceConfig自定义配置，和启用properties文件
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

    /**
     *
     * 测试时，需要注掉properties文件，和spring-druid,
     *          切换称shardingSphere配置的yml文件，和非spring的druid
     * 依靠shardingSphere-JDBC
     * 测试读写分离
     * @throws SQLException
     */
    @Test
    void testReaderAndWriter() {
        //插入5条数据
        for (int i = 1; i <= 5; i++) {
            GGoods gGoods = new GGoods();
            gGoods.setId(1001+i)
                .setCreatTime(new Date())
                .setUpdateTime(new Date())
                .setDelSign(false)
                .setGAbout("简介：测试商品"+i)
                .setGName("测试商品"+i)
                .setGCommit("热销")
                .setGImageUrl("www.baidu.com")
                .setGUnitPrice(1.1);
            gGoodsDao.insertSelective(gGoods);
        }
    }

    /**
     * 读取数据
     */
    @Test
    public void testMSQuery(){
        for (int i = 0; i < 10; i++) {
            GGoods gGoods = gGoodsDao.selectByPrimaryKey(1001 + i);
            System.out.println(gGoods);
        }
    }

}
