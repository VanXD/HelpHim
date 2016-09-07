package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysRolePermission;

public interface SysRolePermissionMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
}