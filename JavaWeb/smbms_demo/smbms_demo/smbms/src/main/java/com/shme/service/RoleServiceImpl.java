package com.shme.service;

import com.shme.dao.BaseDAO;
import com.shme.dao.RoleDAO;
import com.shme.dao.RoleDAOImpl;
import com.shme.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: RoleServiceImpl
 * @Desc: 角色service层接口实现类
 * @package com.shme.service
 * @project smbms
 * @date 2020/7/10 15:52
 */
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    public RoleServiceImpl(){
        roleDAO = new RoleDAOImpl();
    }

    /**
     * 获取用户角色列表
     * @return List<Role> 用户列表
     */
    @Override
    public List<Role> getRoleList() {
        Connection conn = null;
        List<Role> roleList = null;
        try {
            conn = BaseDAO.getConnection();
            roleList = roleDAO.getRoleList(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDAO.closeResource(null,null,conn);
        }
        return roleList;
    }
}
