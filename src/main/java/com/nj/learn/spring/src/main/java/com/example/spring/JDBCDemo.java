package com.example.spring;

import java.sql.*;

/**
 * 10.（必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
 * 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。
 */
public class JDBCDemo {

    public static void main(String[] args) throws SQLException {
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "root");
        //创建语法对象
        Statement statement = connection.createStatement();
        //执行sql
        ResultSet resultSet = statement.executeQuery("select count(1) from country;");
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }

}
