package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(String userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}