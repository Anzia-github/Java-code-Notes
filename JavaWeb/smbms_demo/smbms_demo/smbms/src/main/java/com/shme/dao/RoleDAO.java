package com.shme.dao;


import com.shme.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: RoleDAO
 * @Desc: 提供针对于smbms_role表的操作接口定义
 * @package com.shme.dao
 * @project smbms
 * @date 2020/7/10 11:12
 */
public interface RoleDAO {
    /**
     * 获取角色列表
     * @param conn Connection 数据库连接
     * @return List<Role> 角色集合
     */
    List<Role> getRoleList(Connection conn) throws SQLException;
}
