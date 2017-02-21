package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.admin.util.ShiroUtil;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by wyd on 2016/8/25.
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionMapper> implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public SysPermissionMapper getMapper() {
        return sysPermissionMapper;
    }

    @Override
    public int save(SysPermission entity) {
        entity.setCreatorUserId(ShiroUtil.getSessionSysUser().getId());
        if(null == entity.getIsShow()) {
            entity.setIsShow(false);
        }
        if(StringUtils.isEmpty(entity.getParentId())) {
            entity.setParentId(GlobalKey.MENU_MODULE_PARENT_ID);
        }
        return super.save(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(SysPermission entity) {
        if(null == entity.getIsShow()) {
            entity.setIsShow(false);
        }
        return super.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<SysPermission> getPermissionTreeAndMark(String parentId, List<SysRolePermission> roleHasPerms) {
        SysPermission condition = new SysPermission();
        condition.setParentId(parentId);
        List<SysPermission> list = list(condition, null, null);
        if( !CollectionUtils.isEmpty(list) ) {
            for(SysPermission permission : list) {
                markeHasPerm(permission, roleHasPerms);
                getPermissionTreeAndMark(permission.getId(), roleHasPerms);
                permission.setSubPermissions(getPermissionTreeAndMark(permission.getId(), roleHasPerms));
            }
        }
        return list;
    }

    @Override
    public SysPermission findByPermission(String permission) {
        return sysPermissionMapper.selectByPermission(permission);
    }

    /**
     * 标记角色已有的权限
     * @param permission
     * @param roleHasPerms
     */
    private void markeHasPerm(SysPermission permission, List<SysRolePermission> roleHasPerms) {
        for(SysRolePermission rolePermission : roleHasPerms) {
            if(permission.getPermission().equals(rolePermission.getPermission())) {
                permission.setChecked(true);
            }
        }
    }
}
