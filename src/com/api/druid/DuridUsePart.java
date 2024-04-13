package com.api.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description:druid连接池使用类
 */
public class DuridUsePart {

    /**
     * 直接使用代码设置连接池连接参数方法
     *
     * 1. 创建一个druid连接池对象
     *
     * 2. 设置连接池参数【必须|非必须】
     *
     * 3. 获取连接【通用方法，所有连接池都一样】
     *
     * 4. 回收连接
     */
    public void testHard() throws SQLException {
        //连接池对象
        DruidDataSource dataSource = new DruidDataSource();

        //设置参数
        //必须 连接数据库驱动类的全限定符【注册驱动】|url|user|password
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("wf20040704");
        //非必须 初始化连接数量，最大的连接数量
        dataSource.setInitialSize(5);//初始化连接数量
        dataSource.setMaxActive(10);//最大连接数量

        //获取连接
        DruidPooledConnection connection = dataSource.getConnection();

        //数据库curd


        //回收连接
        connection.close();

    }

    /**
     * 通过读取外部配置文件的方式，实例化druid连接池对象
     */
    @Test
    public void testSoft() throws Exception {
        //1.读取外部配置文件 Properties
        Properties properties = new Properties();

        //src下的文件，可以使用类加载器提供的方法
        InputStream ips = DuridUsePart.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(ips);
        //2.使用连接池的工具类的工厂模式，创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();

        //数据库curd

        connection.close();

    }
}
