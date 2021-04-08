package com.shme.service;

import com.shme.dao.BaseDAO;
import com.shme.dao.UserDAO;
import com.shme.dao.UserDAOImpl;
import com.shme.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: UserServiceImpl
 * @Desc:  用户服务层实现
 * @package com.shme.service
 * @project smbms
 * @date 2020/6/25 17:18
 */
public class UserServiceImpl implements UserService{

    //业务层都会调用DAO层,所以要引入DAO层;
    private UserDAO userDAO;
    public UserServiceImpl(){
        userDAO = new UserDAOImpl();
    }

    /**
     * @Desc 通过传入的用户名查询数据库中的信息
     * @param userCode 用户名(英文缩写)
     * @return User 数据库中查到的用户
     */
    @Override
    public User login(String userCode) {
        User loginUser = null;
        Connection conn = null;
        try {
            conn = BaseDAO.getConnection();
            loginUser = userDAO.getLoginUser(conn, userCode);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeResource(null, null, conn);
            return loginUser;
        }
    }

    /**
     * 根据用户ID修改登录密码
     * @param newPassword 新密码
     * @param id 用户ID
     * @return Boolean 是否修改成功
     */
    @Override
    public Boolean updatePassword(String newPassword, int id) {
        Boolean flag = false;
        Connection conn = null;
        int update = 0;
        try {
            conn = BaseDAO.getConnection();
            update = userDAO.updatePassword(conn, newPassword, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (update>0){
                flag= true;
            }
            BaseDAO.closeResource(null,null,conn);
        }
        return flag;
    }

    /**
     * 获取符合条件的用户数量
     * @param userName 用户名
     * @param userRole 用户角色
     * @return int 符合条件的用户个数
     */
    @Override
    public int getUserCount(String userName, int userRole) {
        Connection conn = null;
        int count = 0;
        try {
            conn = BaseDAO.getConnection();
            count = userDAO.getUserCount(conn, userName, userRole);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDAO.closeResource(null,null,conn);
        }
        return count;
    }

    /**
     * 获取指定页码的用户列表
     * @param userName       String 用户名
     * @param userRole       int 用户角色
     * @param currentPageCode int 目标页码
     * @param pageSize       int 页面显示的用户数量
     * @return List<User> 符合条件的用户集合
     */
    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageCode, int pageSize) {
        Connection conn = null;
        List<User> userList = null;
        try {
            conn = BaseDAO.getConnection();
            if (currentPageCode>0 && pageSize >0){
                userList = userDAO.getUserList(conn, userName, userRole, currentPageCode, pageSize);
                System.out.println(userList);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDAO.closeResource(null,null,conn);
        }
        return userList;
    }
}
