package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysRolePermissionService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author wyd on 2016/10/27.
 */
@Service
@Transactional
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermission, SysRolePermissionMapper> implements SysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysUserService sysUserServiceImpl;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public SysRolePermissionMapper getMapper() {
        return sysRolePermissionMapper;
    }

    @Override
    public boolean cancelRelation(String roleId, String permissionId) {
        return sysRolePermissionMapper.deleteByRoleIdAndPermissionId(roleId, permissionId) > 0;
    }

    @Override
    public List<SysPermission> findByRoleIdAndChecked(String userId) {
        Set<String> userHasPermissions = sysUserServiceImpl.getPermissionIdentitiesByUserId(userId);
        List<SysPermission> sysPermissions = sysPermissionMapper.page(new SysPermission(), null, null);
        for(SysPermission sysPermission : sysPermissions) {
            for(String hasPermission : userHasPermissions) {
                if(sysPermission.getPermission().equals(hasPermission)) {
                    sysPermission.setChecked(true);
                }
            }
        }
        return sysPermissions;
    }
}
