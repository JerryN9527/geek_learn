package com.example.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.stream.IntStream;

public class MillionTest {

    private static Connection connection;
    //----------------------------------------------------提交一百万条订单数据
    /**
     * 提交一百万条订单数据
     * 影响行数：1000000
     * 用时：99112
     * @throws SQLException
     */
    @Test
    void millionInsert() throws SQLException, IOException {
        long start = System.currentTimeMillis();
        HikariDataSource dataSource = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        //获取连接池
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("hikari.properties");
            Properties props = new Properties();
            props.load(is);
            HikariConfig config = new HikariConfig(props);
            dataSource = new HikariDataSource(config);

            //获取连接
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            //设置语法
            String sql = "insert into user_order (id,user_id,user_info_name,user_info_phone,group_id,group_name,goods_id,goods_name,order_addr,order_status,count,total_pirce,pirce,creat_time,update_time)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            final PreparedStatement  finalPstmt = pstmt;
            IntStream.rangeClosed(1,1000000).boxed().forEach(i->{
                try {
                    finalPstmt.setLong(1, i);
                    finalPstmt.setLong(2, 1009527);
                    finalPstmt.setString(3, "张三");
                    finalPstmt.setString(4, "19921518990");
                    finalPstmt.setLong(5, 8001);
                    finalPstmt.setString(6, "飞行类");
                    finalPstmt.setLong(7, 1001);
                    finalPstmt.setString(8, "飞机商品");
                    finalPstmt.setString(9, "上海市浦东新区国富路1921弄34号301室");
                    finalPstmt.setInt(10, 1);
                    finalPstmt.setLong(11, 10);
                    finalPstmt.setDouble(12, 108);
                    finalPstmt.setDouble(13, 10.8);
                    finalPstmt.setDate(14, new Date(System.currentTimeMillis()));
                    finalPstmt.setDate(15, new Date(System.currentTimeMillis()));
                    finalPstmt.addBatch();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            });

            //执行
            int[] ints = finalPstmt.executeBatch();
            System.out.println("影响行数：" + ints.length);

            connection.commit();
            System.out.println("用时："+(System.currentTimeMillis()-start));
        }finally {
            //关闭连接
            colse(resultSet,pstmt,connection);
            if (dataSource != null){
                dataSource.close();
            }
        }
    }



    /**
     * 释放资源
     * @param ret
     * @param statement
     * @param conn
     */
    public void colse(ResultSet ret,Statement statement, Connection conn){
        // 8. 释放资源
        if (ret != null) {
            try {
                ret.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
