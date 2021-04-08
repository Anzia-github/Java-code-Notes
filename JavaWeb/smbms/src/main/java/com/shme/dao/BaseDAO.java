package com.shme.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: BaseDAO
 * @Desc: 数据库操作基类
 * @package com.shme.dao
 * @project smbms
 * @date 2020/6/21 20:03
 */
public class BaseDAO {

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    /**
     * 静态代码块,读取配置信息
     */
    static {
        Properties prop = new Properties();
        InputStream is = BaseDAO.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = prop.getProperty("driver");
        url = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");

    }

    /**
     * @return Connection
     * @throws ClassNotFoundException
     * @throws SQLException
     * @Desc 获取数据库连接
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * @param conn
     * @param args
     * @return ResultSet 查询结果集
     * @throws SQLException
     * @Desc 通用的查询操作
     */
    public static ResultSet query(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1, args[i]);
        }
        ResultSet rs = ps.executeQuery();
        rs.getMetaData();
        return rs;
    }

    /**
     * @param conn
     * @param sql
     * @param args
     * @return int 受影响的行数
     * @throws SQLException
     * @Desc 通用的增删改操作
     */
    public static int update(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1, args[i]);
        }
        int column = ps.executeUpdate();
        return column;
    }

    /**
     * @Desc  关闭资源操作
     * @param resultSet ResultSet
     * @param ps PreparedStatement
     * @param conn Connection
     * @return boolean 是否修改成功
     */
    public static boolean closeResource(ResultSet resultSet, PreparedStatement ps, Connection conn) {
        boolean flag = true;
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException throwables) {
            flag = false;
            throwables.printStackTrace();
        }

        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            flag = false;
            throwables.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            flag = false;
            throwables.printStackTrace();
        }

        return flag;
    }
}
