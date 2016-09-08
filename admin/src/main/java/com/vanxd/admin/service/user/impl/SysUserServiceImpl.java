package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.shiro.authc.PasswordService;
import com.vanxd.data.dict.StatusEnum;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysUserMapper;
import com.vanxd.data.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据用户名获得用户数据
     * @param username 用户名
     * @return  用户对象
     */
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    /**
     * 添加用户
     * @param sysUser   系统用户
     * @return
     * @throws Exception
     */
    public boolean add(SysUser sysUser) {
        sysUser.setId(StringUtils.uuid());
        sysUser.randomSalt();
        try {
            sysUser.setPassword(passwordService.encryptPassword(sysUser.getUsername(),sysUser.getPassword(), sysUser.getSalt()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(StatusEnum.NEW.getCode());
        return save(sysUser) > 0;
    }

    /**
     * 获得用户的所有角色识别码
     *
     * @param sysUser 用户对象
     * @return set 角色识别码列表
     */
    public Set<String> getRoleIdentities(SysUser sysUser) {
        Set<String> rolesIdentities = new HashSet<String>();
        SysRole roleConditions = new SysRole();
        roleConditions.setUserId(sysUser.getId());
        List<SysRole> sysRoles = sysRoleMapper.page(roleConditions, null);
        for(SysRole sysRole : sysRoles) {
            rolesIdentities.add(sysRole.getRole());
        }
        return rolesIdentities;
    }

    /**
     * 获得用户的所有权限识别码
     * @param sysUser 用户对象
     * @return set 权限识别码列表
     */
    public Set<String> getPermissionIdentities(SysUser sysUser) {
        Set<String> permissionIdentities = new HashSet<String>();
        SysRole roleConditions = new SysRole();
        roleConditions.setUserId(sysUser.getId());
        List<SysRole> sysRoles = sysRoleMapper.page(roleConditions, null);
        List<SysPermission> sysPermissions = null;
        SysPermission sysPermissionCondition = null;
        for(SysRole sysRole : sysRoles) {
            sysPermissionCondition = new SysPermission();
            sysPermissionCondition.setRoleId(sysRole.getId());
            sysPermissions = sysPermissionMapper.page(sysPermissionCondition, null);
            for(SysPermission sysPermission : sysPermissions) {
                permissionIdentities.add(sysPermission.getPermission());
            }
        }
        return permissionIdentities;
    }
}
