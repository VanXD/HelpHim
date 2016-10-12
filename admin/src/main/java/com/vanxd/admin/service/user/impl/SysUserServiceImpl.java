package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.shiro.authc.PasswordService;
import com.vanxd.data.dict.StatusEnum;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysUserMapper;
import com.vanxd.data.util.VanStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
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
        entity.setId(VanStringUtils.uuid());
        entity.randomSalt();
        encryptPassword(entity);
        entity.setCreateTime(new Date());
        entity.setStatus(StatusEnum.NEW.getCode());
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
    public Set<String> getRoleIdentities(SysUser sysUser) {
        Set<String> rolesIdentities = new HashSet<String>();
        SysRole roleConditions = new SysRole();
        roleConditions.setUserId(sysUser.getId());
        List<SysRole> sysRoles = sysRoleMapper.page(roleConditions, null, null);
        for(SysRole sysRole : sysRoles) {
            rolesIdentities.add(sysRole.getRole());
        }
        return rolesIdentities;
    }

    @Override
    public Set<String> getPermissionIdentities(SysUser sysUser) {
        Set<String> permissionIdentities = new HashSet<String>();
        SysRole roleConditions = new SysRole();
        roleConditions.setUserId(sysUser.getId());
        List<SysRole> sysRoles = sysRoleMapper.page(roleConditions, null, null);
        List<SysPermission> sysPermissions = null;
        SysPermission sysPermissionCondition = null;
        for(SysRole sysRole : sysRoles) {
            sysPermissionCondition = new SysPermission();
            sysPermissionCondition.setRoleId(sysRole.getId());
            sysPermissions = sysPermissionMapper.page(sysPermissionCondition, null, null);
            for(SysPermission sysPermission : sysPermissions) {
                permissionIdentities.add(sysPermission.getPermission());
            }
        }
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
