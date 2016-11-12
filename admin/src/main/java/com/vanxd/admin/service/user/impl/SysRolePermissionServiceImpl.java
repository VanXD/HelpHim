package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.service.user.SysRolePermissionService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.dict.SysPermissionTypeEnum;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wyd on 2016/10/27.
 */
@Service
@Transactional
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermission, SysRolePermissionMapper> implements SysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionService sysPermissionServiceImpl;

    @Override
    public SysRolePermissionMapper getMapper() {
        return sysRolePermissionMapper;
    }

    @Override
    public boolean cancelRelation(String roleId, String permissionId) {
        return sysRolePermissionMapper.deleteByRoleIdAndPermissionId(roleId, permissionId) > 0;
    }

    // todo 待优化
    @Override
    public List<SysPermission> findByRoleIdAndChecked(String roleId) {
        SysRolePermission rolePermCondition = new SysRolePermission();
        rolePermCondition.setRoleId(roleId);
        List<SysRolePermission> roleHasPerms = sysRolePermissionMapper.page(rolePermCondition, null, null);

        List<SysPermission> permissionTree = sysPermissionServiceImpl.getPermissionTreeAndMark(GlobalKey.MENU_MODULE_PARENT_ID, roleHasPerms);
        return permissionTree;
    }
}
