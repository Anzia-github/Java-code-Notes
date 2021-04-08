package com.kuang.lesson03;

import com.kuang.lesson02.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TestUpdate {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "update users set `name` = ? where id = ?";
            // 区别
            st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

            // 手动给参数赋值
            st.setObject(1, "狂神");
            st.setObject(2, 1);

            // 执行
            int i = st.executeUpdate();

            if (i > 0) {
                System.out.println("更新成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, null);
        }
    }
}

