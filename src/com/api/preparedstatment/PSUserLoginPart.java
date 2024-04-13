package com.api.preparedstatment;

import java.sql.*;
import java.util.Scanner;

/**
 * Description:使用预编译statement完成用户登录
 *
 * TODO:防止注入攻击 | 演示 ps的使用流程
 */
public class PSUserLoginPart {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. 获取用户输入信息
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入账号:");
        String account = scanner.nextLine();

        System.out.println("请输入密码:");
        String password = scanner.nextLine();


        //2. ps的数据库流程
        // 2.1 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.2 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "wf20040704");

        /**
         * statement步骤：
         *  1.创建statement
         *  2.拼接SQL语句
         *  3.发送SQL语句，并且获取返回结果
         *
         * prepareStatement步骤：
         *   1.编写SQL语句结构 不包含动态值部分的雨具，动态值部分使用占位符？替代 注意：？只能替代动态值
         *   2.创建preparedStatement，并且传入动态值
         *   3.动态值 占位符 赋值 ？ 单独赋值即可
         *   4.发送SQL语句即可，并获取返回结果
         */

        // 2.3 编写SQL结构
        String sql = "select * from t_user where account = ? and password = ?";

        // 2.4 创建预编译statement并且设置SQL语句结构
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 2.5 单独占位符进行赋值
        /**
         * 参数1：index 占位符位置 从左向右数 从1开始
         * 参数2：object 占位符的值 可以设置任何类型的数据，避免了我们拼接的类型更加丰富
         */
        preparedStatement.setObject(1,account);
        preparedStatement.setObject(2,password);

        // 2.6 发送SQL语句，并获取返回结果
        // preparedStatement.executeUpdate | executeQuery(String sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 2.7 结果集解析
        if (resultSet.next())
            System.out.println("登录成功");
        else
            System.out.println("登录失败");

    }
}
