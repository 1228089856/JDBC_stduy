package com.api.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 基于工具类的curd
 */
public class JDBCCrudPart {

    public void testInsert() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        //数据库curd动作
        JDBCUtils.freeConnection(connection);
    }

}
