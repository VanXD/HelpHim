package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysRolePermissionService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.util.GlobalKey;
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
    public List<SysPermission> findByRoleIdAndChecked(String roleId) {
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setRoleId(roleId);
        List<SysRolePermission> roleHasPermissions = sysRolePermissionMapper.page(sysRolePermission, null, null);
        List<SysPermission> sysPermissions = sysPermissionMapper.page(new SysPermission(), null, null);
        Map<String, SysPermission> moduleMap = new HashMap<String, SysPermission>();
        SysPermission module = null;
        for(SysPermission sysPermission : sysPermissions) {
            for(SysRolePermission rolePermission : roleHasPermissions) {
                if(sysPermission.getPermission().equals(rolePermission.getPermission())) {
                    sysPermission.setChecked(true);
                    break;
                }
            }
            if(sysPermission.getParentId().equals(GlobalKey.MENU_MODULE_PARENT_ID)) {
                moduleMap.put(sysPermission.getId(), sysPermission);
            } else {
                module = moduleMap.get(sysPermission.getParentId());
                if(null == module) {
                    throw new BusinessException("权限菜单排序错误，模块菜单没有放在最前面！");
                }
                module.getSubPermissions().add(sysPermission);
            }

        }
        List<SysPermission> result = new ArrayList<SysPermission>();
        for(Map.Entry<String, SysPermission> entry : moduleMap.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }
}
