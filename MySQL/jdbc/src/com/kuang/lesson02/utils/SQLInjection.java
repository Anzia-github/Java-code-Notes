package com.kuang.lesson02.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInjection {
    public static void main(String[] args) {

        //login("kuangshen", "123456");
        //SQL注入
        login("' or '1=1","' or '1=1");

    }

    //登陆业务
    public static void login(String username, String password) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            st = conn.createStatement();

            // SQL
            String sql = "select * from users where `NAME` = '" + username + "' AND `password` = '" + password + "'";

            rs = st.executeQuery(sql);// 查询完毕会返回一个结果集

            while (rs.next()) {
                System.out.println(rs.getString("NAME"));
                System.out.println(rs.getString("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }
}
