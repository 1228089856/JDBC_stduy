package com.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class StatementQueryPart {
    /*
    * TODO:
    *    DriverManager
    *    Connection
    *    Statement
    *    ResultSet
    *
    * */
    public static void main(String[] args) throws SQLException {

        //1, 注册驱动
        /*
         * TODO:
         *      注册驱动
         *      依赖：驱动版本 8+ com.mysql.cj.jdbc.Driver
         *           驱动版本 5+ com.mysql.jdbc.Driver
         *
         */
        DriverManager.registerDriver(new Driver());
    
        //2. 获取连接
        /*
         TODO:
               java程序要和数据库创建连接
               java程序，连接数据库，肯定是要调用某个方法，方法也需要填入连接数据库的基本信息：
                数据库ip地址 127.0.0.1
                数据库端口号 3306
                账号：root
                密码：wf20040704
                连接数据库的名称：test
         */

        /**
         * 参数1：url
         *      jdbc：数据库厂商名://ip地址:port/数据库名
         * 参数2：username 数据库软件账号
         * 参数3：password 数据库软件密码
         *
         */


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","wf20040704");

        //3. 创建statement
        Statement statement = connection.createStatement();
        //4. 发送sql语句，并且获取返回结果
        String sql = "select * from t_user";
        ResultSet resultSet = statement.executeQuery(sql);


        //5. 进行结果集解析
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String account = resultSet.getString("account");
            String password = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");

            System.out.println(id + "--" + account + "--" + password + "--" + nickname);
        }
        //6. 关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
