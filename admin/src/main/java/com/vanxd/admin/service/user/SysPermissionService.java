package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;

import java.util.List;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysPermissionService extends BaseService<SysPermission, SysPermissionMapper>{
    List<SysPermission> getPermissionTreeAndMark(String menuModuleParentId, List<SysRolePermission> roleHasPerms);
}
