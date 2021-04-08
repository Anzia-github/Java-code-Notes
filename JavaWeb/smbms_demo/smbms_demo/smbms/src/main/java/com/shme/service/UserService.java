package com.shme.service;

import com.shme.pojo.User;

import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: UserServer
 * @Desc:  用户服务层
 * @package com.shme.service
 * @project smbms
 * @date 2020/6/25 17:16
 */
public interface UserService {

    /**
     * 用户登录
     * @param userCode 用户名(英文缩写)
     * @return User 返回此用户
     */
    User login(String userCode);

    /**
     * 根据ID修改密码
     * @param newPassword 新密码
     * @param id 用户ID
     * @return Boolean 是否修改成功
     */
    Boolean updatePassword(String newPassword,int id);

    /**
     * 获取符合条件的用户数量
     * @param userName String 用户名
     * @param userRole String 用户角色
     * @return int 符合条件的用户数量
     */
    int getUserCount(String userName,int userRole);

    /**
     * 获取指定页码的用户列表
     * @param userName String 用户名
     * @param UserRole int 用户角色
     * @param currentPageNum int 目标页码
     * @param pageSize int 页面显示的用户数量
     * @return List<User> 符合条件的用户集合
     */
    List<User> getUserList(String userName,int UserRole,int currentPageNum,int pageSize);
}
