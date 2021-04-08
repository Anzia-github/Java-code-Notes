package com.shme.dao;

import com.shme.pojo.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: RoleDAOImpl
 * @Desc:
 * @package com.shme.dao
 * @project smbms
 * @date 2020/7/10 11:12
 */
public class RoleDAOImpl extends BaseDAO implements RoleDAO{

    /**
     * 获取角色列表
     * @param conn Connection 数据库连接
     * @return List<Role> 角色集合
     */
    @Override
    public List<Role> getRoleList(Connection conn) throws SQLException {
        String sql = "select * from smbms_role";
        ResultSet rs = query(conn, sql);
        ArrayList<Role> roleList = new ArrayList<>();
        while (rs.next()){
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRoleCode(rs.getString("roleCode"));
            role.setRoleName(rs.getString("roleName"));
            roleList.add(role);
        }
        return roleList;
    }
}
