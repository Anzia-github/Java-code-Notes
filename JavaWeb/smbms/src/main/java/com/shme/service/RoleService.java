package com.shme.service;

import com.shme.pojo.Role;

import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: RoleService
 * @Desc: 角色service层接口
 * @package com.shme.service
 * @project smbms
 * @date 2020/7/10 15:32
 */
public interface RoleService {
    /**
     * 获取用户角色列表
     * @return List<Role> 用户列表
     */
    List<Role> getRoleList();
}
