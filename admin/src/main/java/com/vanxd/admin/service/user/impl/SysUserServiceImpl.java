package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.shiro.authc.PasswordService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRolePermission;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.entity.user.SysUserRole;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.mapper.user.SysRolePermissionMapper;
import com.vanxd.data.mapper.user.SysUserMapper;
import com.vanxd.data.mapper.user.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wyd on 2016/6/30.
 */
@Transactional
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private PasswordService passwordService;

    @Override
    public SysUserMapper getMapper() {
        return sysUserMapper;
    }

    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public int save(SysUser entity) {
        entity.randomSalt();
        encryptPassword(entity);
        return super.save(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser entity) {
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(entity.getId());
        if(null == dbSysUser) {
            throw new BusinessException("用户不存在！");
        }
        entity.setSalt(dbSysUser.getSalt());
        entity.setUsername(dbSysUser.getUsername());
        encryptPassword(entity);
        return super.updateByPrimaryKeySelective(entity);
    }


    @Override
    public Set<String> getRoleIdentitiesByUserId(String userId) {
        Set<String> rolesIdentities = new HashSet<String>();
        SysUserRole userRoleConditions = new SysUserRole();
        userRoleConditions.setUserId(userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.page(userRoleConditions, null, null);
        for(SysUserRole userRole : sysUserRoles) {
            rolesIdentities.add(userRole.getRole());
        }
        return rolesIdentities;
    }

    @Override
    public Set<String> getPermissionIdentitiesByUserId(String userId) {
        Set<String> permissionIdentities = sysUserRoleMapper.selectPermissionsByUserId(userId);
        return permissionIdentities;
    }

    /**
     * 密码加密
     * @param entity 用户对象
     */
    private void encryptPassword(SysUser entity) {
        try {
            if(!StringUtils.isEmpty(entity.getPassword())) {
                entity.setPassword(passwordService.encryptPassword(entity.getUsername(),entity.getPassword(), entity.getSalt()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
