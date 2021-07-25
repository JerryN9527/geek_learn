package com.example.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 10.（必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
 * 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。
 */
@SpringBootTest
class ApplicationTests {

    private static Connection connection;

    static {
        //获取连接
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //----------------------------------------------------1）使用 JDBC 原生接口，实现数据库的增删改查操作。
    /**
     * 新增
     * @throws SQLException
     */
    @Test
    void createJdbc() throws SQLException {
        if (connection == null){
            return;
        }
        //设置语法
        String sql = "insert into user (id,name,socre) values(?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, 9527);
        pstmt.setString(2, "ceshi9527");
        pstmt.setString(3, "100");
        //执行
        int i = pstmt.executeUpdate();
        System.out.println("影响行数："+i);
        colse(null,pstmt,connection);
    }


    /**
     * 修改
     * @throws SQLException
     */
    @Test
    void updateJdbc() throws SQLException {
        if (connection == null){
            return;
        }
        //设置语法
        String sql = "update user set socre = ?  where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, 99);
        pstmt.setInt(2, 9527);
        //执行
        int i = pstmt.executeUpdate();
        System.out.println("影响行数："+i);
        colse(null,pstmt,connection);
    }



    /**
     * 查询
     * @throws SQLException
     */
    @Test
    void queryJdbc() throws SQLException {
        if (connection == null){
            return;
        }
        //创建语法对象
        Statement statement = connection.createStatement();
        //执行sql
        ResultSet resultSet = statement.executeQuery("select * from user;");
        while (resultSet.next()){
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("id",resultSet.getInt(1));
            map.put("name",resultSet.getString(2));
            map.put("score",resultSet.getString(3));
            System.out.println(map);
        }

        System.out.println("-----------参数查询-----------");
        String sql = "select * from user where id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,9527);
        resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("id",resultSet.getInt(1));
            map.put("name",resultSet.getString(2));
            map.put("score",resultSet.getString(3));
            System.out.println(map);
        }
        colse(resultSet,statement,connection);
    }

    /**
     * 删除
     * @throws SQLException
     */
    @Test
    void deletJdbc() throws SQLException {
        if (connection == null){
            return;
        }
        //设置语法
        String sql = "delete from user  where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, 9527);
        //执行
        int i = pstmt.executeUpdate();
        System.out.println("影响行数："+i);
        colse(null,pstmt,connection);
    }

    //----------------------------------------------------2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
    /**
     * 增加事务
     * @throws SQLException
     */
    @Test
    void transactionJdbc() throws SQLException {
        //创建修改查询
        if (connection == null){
            return;
        }
        connection.setAutoCommit(false);
        //设置语法
        String sql = "insert into user (id,name,socre) values(?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setInt(1, 9527);
        pstmt.setString(2, "ceshi9527");
        pstmt.setString(3, "100");
        //执行
        int i = pstmt.executeUpdate();
        System.out.println("影响行数："+i);

        //设置语法
        sql = "update user set socre = ?  where id = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, 99);
        pstmt.setInt(2, 9527);
        int i1 = 1 / 0;
        //执行
        i = pstmt.executeUpdate();
        System.out.println("影响行数："+i);
        connection.commit();
        queryJdbc();
    }



    //----------------------------------------------------3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。
    /**
     * 增加事务
     * @throws SQLException
     */
    @Test
    void hikariJdbc() throws SQLException, IOException {
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
            String sql = "insert into user (id,name,socre) values(?,?,?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, 9527);
            pstmt.setString(2, "ceshi9527");
            pstmt.setString(3, "100");
            //执行
            int i = pstmt.executeUpdate();
            System.out.println("影响行数：" + i);

            //设置语法
            sql = "update user set socre = ?  where id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, 99);
            pstmt.setInt(2, 9527);
            //执行
            i = pstmt.executeUpdate();
            System.out.println("影响行数：" + i);
            connection.commit();


            System.out.println("-----------参数查询-----------");
            sql = "select * from user where id = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, 9527);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", resultSet.getInt(1));
                map.put("name", resultSet.getString(2));
                map.put("score", resultSet.getString(3));
                System.out.println(map);
            }
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
