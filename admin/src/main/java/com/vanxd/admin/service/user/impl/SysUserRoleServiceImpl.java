package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysUserRoleService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUserRole;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author wyd on 2016/10/20.
 */
@Service
@Transactional
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, SysUserRoleMapper> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserService sysUserServiceImpl;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUserRoleMapper getMapper() {
        return sysUserRoleMapper;
    }

    @Override
    public boolean cancelRelation(String userId, String roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return sysUserRoleMapper.deleteByUserIdAndRoleId(userId, roleId) > 0;
    }

    @Override
    public List<SysRole> findByUserIdAndChecked(String userId) {
        Set<String> userHasRoles = sysUserServiceImpl.getRoleIdentitiesByUserId(userId);
        List<SysRole> list = sysRoleMapper.page(new SysRole(), null, null);
        for(SysRole role : list) {
            for(String hadRoleIdentity : userHasRoles) {
                if(role.getRole().equals(hadRoleIdentity)) {
                    role.setChecked(true);
                }
            }
        }
        return list;
    }
}
