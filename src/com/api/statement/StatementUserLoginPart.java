package com.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * TODO:
 *  1. 明确jdbc的使用流程 和详细讲解内部设计api反复噶
 *  2. 发现问题，引出preparedStatement
 *
 * TODO：
 *  输入账号和密码
 *  进行数据库信息查询(t_user)
 *  反馈登陆成功还是登录失败
 *
 * TODO：
 *  1. 键盘输入事件，收集账号和密码信息
 *  2. 注册驱动
 *  3. 获取连接
 *  4. 创建statement
 *  5. 发送查询SQL语句，并获取返回结果
 *  6. 结果判断，显示登录成功还是失败
 *  7. 关闭资源
 */
public class StatementUserLoginPart {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1. 获取用户输入信息
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入账号:");
        String account = scanner.nextLine();

        System.out.println("请输入密码:");
        String password = scanner.nextLine();

        //2.注册驱动
        /**
         * 方案1：
         *  DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
         *  问题：注册两次驱动
         *      1.DriverManager.registerDriver() 方法本身会注册一次
         *      2.new Driver() 构造器会注册一次
         *  解决：只想注册一次驱动
         *      只触发静态代码块即可
         *  触发静态代码块：
         */
        //DriverManager.registerDriver(new Driver());

        //方案2：
        //new Driver();


        //反射 字符串 -> 提取到外部的配置文件 -> 可以在不改变代码的情况下，完成数据库驱动的切换
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. 获取数据
        /**
         * getConnection(1,2,3) 方法内部会调用静态代码块，完成驱动注册。是一个重载方法
         *
         * 核心属性：
         *  1. 数据库软件所在的主机的ip地址：
         *  2. 数据库软件所在的主机的端口号：3306
         *  3. 连接的具体的库：test
         *  4. 连接的账号： root
         *  5. 连接的密码： root
         *  6. 可选的信息： 没有
         *
         * 三个参数：
         *  String url  数据库软件所在的信息，连接的具体库，以及其他可选信息
         *      语法： jdbc:数据库管理软件名称://ip地址|主机名:port端口号/数据库名?key=value&key=value 可选信息
         *      具体： jdbc:mysql:localhost:3306/test
         *      本机的省略写法：如果你的数据库软件安装到本机，可以进行一些省略
         *            jdbc:mysql:///test
         *            强调：必须是本机，并且端口号是3306才可以省略使用 ///
         *  String user 数据库的账号
         *  String password 数据库的密码
         *
         *  两个参数：
         *  String url:
         *  Properties info:存储账号和密码。类似于Map 只不过key = value 都是字符串形式
         *
         * 一个参数：
         *  String url  ：数据库ip，端口号，具体的数据库和可选信息
         *  具体：         jdbc:mysql://localhost:3306/test?user=root&password=wf20040704
         */
        Connection connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "wf20040704");

//        Properties info = new Properties();
//        info.put("user","root");
//        info.put("password","root");
//        DriverManager.getConnection("jdbc:mysql:localhost:3306/test",info);

        //3. 创建发送SQL语句的statement对象
        // statement 可以发送SQL语句到数据库，并且获取返回结果
        /**
         * Statement只能适用于静态SQL
         * 并且会有注入攻击问题
         */
        Statement statement = connection.createStatement();

        //4. 发送SQL语句(1. 编写SQL语句 2.发送SQL语句
        String sql = "select * from t_user where account = '" + account + "' and password = '" + password + "';";

        /**
         * SQL分类：DDL（容器创建，修改，删除）DML（插入，修改，删除）DQL（查询）DCL（权限控制）TPL（事务控制语言）
         *
         * 参数： sql
         * 返回：int
         *      情况1：DML 返回影响的行数，例如：删除了三条数据 return 3
         *      情况2：非DML return 0
         * int row = executeUpdate(sql)
         *
         * 参数： sql DQL
         * 返回：resultSet 结果封装对象
         * ResultSet resultSet = executeQuery(sql)
         */
       // int i = statement.executeUpdate(sql);
        ResultSet resultSet = statement.executeQuery(sql);


        //5. 查询结果集解析 resultSet
        /**
         * Java是一种面向对象的四尾，将查询结果封装成了resultSet对象，我们应该理解，内部一定也是有行和有列的
         *
         * resultSet -> 逐行获取数据，行 ->
         *
         *
         *
         * 想要进行数据解析，我们需要进行两件事情：1. 移动游标制定获取行 2. 获取制定数据行的列数据即可
         *
         * 1. 游标移动问题
         *      resultSet内部包含一个游标，指定当前行数据
         *      默认游标制定的是第一行数据之前
         *      我们可以调用next方法向后移动一行游标
         *      如果我们有很多行数据，我们可以使用while(next){获取的数据集}
         *
         *      boolean = next() true:有很多行数据，并且向下移动一行
         *                       false:没有更多行数据，不一定
         *
         *      TODO：移动光标的方法有很多，只需要记next即可，配合while循环获取全部数据
         *
         *  2. 获取列的数据问题（获取光标指定的行的列的数据）
         *      resultSet.get类型(int columnLabel | int columnIndex);
         *
         *      columnLabel:列名 如果有别名 写别名
         *      columnIndex: 列的下角标获取 从左向右 从1开始
         */
//        while(resultSet.next()){
//            //指定当前行
//            int id = resultSet.getInt(1);
//            String account1 = resultSet.getString("account");
//            String password1 = resultSet.getString(3);
//            String nickname = resultSet.getString("nickname");
//
//            System.out.println(id + "--" + account1 + "--" + password1 + "--" + nickname);
        //移动一次光标，只要有数据，就代表登录成功
        if(resultSet.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }


        //6. 关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
