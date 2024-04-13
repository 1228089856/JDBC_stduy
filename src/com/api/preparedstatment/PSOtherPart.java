package com.api.preparedstatment;

import org.junit.Test;

import java.sql.*;

/**
 * Description:联系ps的特殊使用情况
 */
public class PSOtherPart {

    /**
     * TODO:
     *  t_user插入一条数据。并且获取数据库自增长的主键
     *
     * TODO:
     *  使用总结:
     *      1.创建prepareStatement的时候，告知，需要返回自增长的主键(sql, Statement.RETURN_GENERATED_KEYS)
     *      2.获取司机装主键值的结果集对象，一行一列，获取对应的数据即可
     */
    @Test
    public void returnPrimaryKey() throws ClassNotFoundException, SQLException {
        //1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=wf20040704");

        //3. 编写SQL语句
        String sql = "insert into t_user(account,password,nickname) values(?,?,?);";

        //4. 创建statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //5. 占位符赋值
        preparedStatement.setObject(1,"test1");
        preparedStatement.setObject(2,"123456");
        preparedStatement.setObject(3,"小王");

        //6. 发送SQL语句,并且获取结果
        int i = preparedStatement.executeUpdate();

        //7. 结果解析
        if (i > 0) {
            System.out.println("插入成功");

            //可以获取回显的主键
            //获取司机装主键的结果集对象，一行 一列 id=值
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();//移动下光标
            int id = resultSet.getInt(1);
            System.out.println("自增长的主键是:"+id);
        }
        else {

            System.out.println("插入失败");
        }

        //8. 关闭资源
        preparedStatement.close();
        connection.close();

    }
}
