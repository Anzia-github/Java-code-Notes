package com.shme.dao;

import com.mysql.cj.util.StringUtils;
import com.shme.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: UserDAOImpl
 * @Desc: 针对user数据表操作的具体实现类
 * @package com.shme.dao
 * @project smbms
 * @date 2020/6/23 9:52
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {

    /**
     * 通过userCode获取登陆的用户信息
     *
     * @param conn     数据库连接
     * @param userCode 用户名
     * @return 查到的用户
     * @throws SQLException e
     */
    @Override
    public User getLoginUser(Connection conn, String userCode) throws SQLException {
        String sql = "select * from smbms_user where userCode=?";
        ResultSet rs = query(conn, sql, userCode);
        User user = new User();
        if (conn != null) {
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            closeResource(rs, null, null);
        }
        return user;
    }

    /**
     * 根据用户ID修改登陆密码
     *
     * @param conn        Connection 连接
     * @param newPassword String 新密码
     * @param id          int 修改密码的id
     * @return int 受影响的行数
     * @throws SQLException e
     */
    @Override
    public int updatePassword(Connection conn, String newPassword, int id) throws SQLException {
        String sql = "update `smbms_user` set `userPassword`=? where `id`=?";
        int update = update(conn, sql, newPassword, id);
        return update;
    }

    /**
     * 获取符合条件的用户个数
     *
     * @param conn     连接
     * @param userName 条件一:用户名
     * @param userRole 条件二:用户角色
     * @return int 符合条件的用户个数
     * @throws SQLException e
     */
    @Override
    public int getUserCount(Connection conn, String userName, int userRole) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) as count from smbms_user u ,smbms_role r where u.userRole = r.id");

        //如果存在用户名的条件
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" and u.userName like ?");
            userName = "%" + userName + "%";
            System.out.println("userName:" + userName);
        }

        //如果存在用户角色的条件
        if (userRole > 0) {
            sql.append(" and u.userRole = ?");
        }

        System.out.println("UserDaoImpl->getUserCount():" + sql);

        ResultSet rs;

        if ((!StringUtils.isNullOrEmpty(userName)) && (userRole > 0)) { //用户名和用户角色都存在
            rs = query(conn, sql.toString(), userName, userRole);
            System.out.println("情况一");
        } else if ((!StringUtils.isNullOrEmpty(userName)) && (userRole <= 0)) { //用户名存在,用户角色不存在
            rs = query(conn, sql.toString(), userName);
            System.out.println("情况二");
        } else if ((StringUtils.isNullOrEmpty(userName)) && (userRole > 0)) { //用户名不存在,用户角色存在
            rs = query(conn, sql.toString(), userRole);
            System.out.println("情况三");
        } else /*((StringUtils.isNullOrEmpty(userName)) && (userRole < 0))*/ { //用户名和用户角色都不存在
            rs = query(conn, sql.toString());
            System.out.println("情况四");
        }

        //获得符合条件的用户个数
        int i = 0;
        if (rs.next()) {
            i = rs.getInt("count");
            System.out.println("rs中的个数:" + i);
        }
        closeResource(rs, null, null);
        return i;
    }

    /**
     * 获取单页的用户列表
     *
     * @param conn            Connection 数据库连接
     * @param userName        String 用户名
     * @param userRole        String 用户角色
     * @param currentPageCode int 当前页码
     * @param pageSize        int 页面大小
     * @return
     */
    @Override
    public List<User> getUserList(Connection conn, String userName, int userRole, int currentPageCode, int pageSize) throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole=r.id");

        //如果有用户名的限制,追加SQL条件限制语句
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" and u.userName like ?");
            userName = "%" + userName + "%";
        }
        //如果有用户角色的限制,追加SQL条件限制语句
        if (userRole > 0) {
            sql.append(" and u.userRole=?");
        }

        int offset = (currentPageCode - 1) * pageSize;
        sql.append(" order by r.id limit ?,?");

        ResultSet rs;

        if ((!StringUtils.isNullOrEmpty(userName)) && (userRole > 0)) { //用户名和用户角色都存在
            rs = query(conn, sql.toString(), userName, userRole, offset, pageSize);
            System.out.println("情况一");
            System.out.println("sql:"+sql.toString());
            System.out.println("用户名:"+userName);
            System.out.println("用户角色:"+userRole);
            System.out.println("起始索引:"+offset);
            System.out.println("页面大小:"+pageSize);
        } else if ((!StringUtils.isNullOrEmpty(userName)) && (userRole <= 0)) { //用户名存在,用户角色不存在
            rs = query(conn, sql.toString(), userName, offset, pageSize);
            System.out.println("情况二");
            System.out.println("sql:"+sql.toString());
            System.out.println("用户名:"+userName);
            System.out.println("起始索引:"+offset);
            System.out.println("页面大小:"+pageSize);
        } else if ((StringUtils.isNullOrEmpty(userName)) && (userRole > 0)) { //用户名不存在,用户角色存在
            rs = query(conn, sql.toString(), userRole, offset, pageSize);
            System.out.println("情况三");
            System.out.println("sql:"+sql.toString());
            System.out.println("用户角色:"+userRole);
            System.out.println("起始索引:"+offset);
            System.out.println("页面大小:"+pageSize);
        } else /*((StringUtils.isNullOrEmpty(userName)) && (userRole < 0))*/ { //用户名和用户角色都不存在
            rs = query(conn, sql.toString(), offset, pageSize);
            System.out.println("情况四");
            System.out.println("sql:"+sql.toString());
            System.out.println("起始索引:"+offset);
            System.out.println("页面大小:"+pageSize);
        }

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setUserRole(rs.getInt("userRole"));
            user.setUserRoleName(rs.getString("userRoleName"));
            userList.add(user);
        }

        BaseDAO.closeResource(rs, null, null);

        return userList;
    }
}
