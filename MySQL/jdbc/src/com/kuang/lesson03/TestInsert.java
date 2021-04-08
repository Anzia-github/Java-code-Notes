package com.kuang.lesson03;

import com.kuang.lesson02.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TestInsert {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "INSERT INTO users(id,`NAME`,`PASSWORD`,`email`,`birthday`)" +
                    "VALUES(?,?,?,?,?)";
            // 区别
            st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

            // 手动给参数赋值
            st.setObject(1,5); //id
            st.setObject(2,"qinjiang"); //名字
            st.setObject(3,"123456"); //密码
            st.setObject(4,"123456@qq.com"); //邮箱
            // 注意点，sql.Date 数据库     java.sql.Date()
            //       util.Date Java     new Date().getTime() 获得时间戳
            st.setObject(5,new java.sql.Date(new Date().getTime())); //生日

            // 执行
            int i = st.executeUpdate();

            if (i > 0) {
                System.out.println("插入成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, null);
        }
    }
}

