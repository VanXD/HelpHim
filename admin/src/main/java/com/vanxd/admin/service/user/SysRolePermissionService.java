package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.user.SysRolePermissionMapper;

import java.util.List;

/**
 * @author wyd on 2016/10/27.
 */
public interface SysRolePermissionService extends BaseService<SysRolePermission, SysRolePermissionMapper> {
    boolean cancelRelation(String roleId, String permissionId);

    /**
     * 获得所有权限列表，标记{roleId}已拥有的权限
     * @param roleId
     * @return
     */
    List<SysPermission> findByRoleIdAndChecked(String roleId);
}
