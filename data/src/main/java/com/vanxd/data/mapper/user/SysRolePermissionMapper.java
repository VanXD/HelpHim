package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
    int deleteByRoleIdAndPermissionId(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}