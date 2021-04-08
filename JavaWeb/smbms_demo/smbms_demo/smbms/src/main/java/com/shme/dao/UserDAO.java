package com.shme.dao;

import com.shme.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: UserDAO
 * @Desc: 针对于user表的数据库操作接口
 * @package com.shme.dao
 * @project smbms
 * @date 2020/6/23 9:48
 */
public interface UserDAO {

    /**
     * 获取用户登录信息
     *
     * @param conn     Connection
     * @param userCode String
     * @return User 查询到的User对象
     * @throws SQLException
     */
    User getLoginUser(Connection conn, String userCode) throws SQLException;


    /**
     * 根据用户ID修改密码
     *
     * @param conn        Connection
     * @param id          int
     * @param newPassword String
     * @return int 受影响的行数
     * @throws SQLException
     */
    int updatePassword(Connection conn, String newPassword, int id) throws SQLException;

    /**
     * 获取数据库中符合指定条件的用户数量
     *
     * @param con Connection 数据库连接
     * @param userName String 用户名
     * @param userRole String 用户角色
     * @return int 符合条件的用户个数
     * @throws SQLException
     */
    int getUserCount(Connection con, String userName, int userRole) throws SQLException;

    /**
     * 获取单页的用户列表
     * @param conn Connection 数据库连接
     * @param userName String 用户名
     * @param userRole String 用户角色
     * @param currentPageCode int 当前页码
     * @param pageSize int 页面大小
     * @return
     */
    List<User> getUserList(Connection conn, String userName, int userRole, int currentPageCode, int pageSize) throws SQLException;

}
