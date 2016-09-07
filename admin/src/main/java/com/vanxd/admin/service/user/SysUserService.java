package com.vanxd.admin.service.user;

import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.mapper.user.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wyd on 2016/6/30.
 */
@Transactional
@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户名获得用户数据
     * @param username 用户名
     * @return  用户对象
     */
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    /**
     * 获得用户的所有角色识别码
     *
     * @param sysUser 用户对象
     * @return set 角色识别码列表
     */
    public Set<String> getRoleIdentities(SysUser sysUser) {
        Set<String> rolesIdentities = new HashSet<String>();
        List<SysRole> sysRoles = sysRoleMapper.getByUserId(sysUser.getId());
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
        Set<SysRole> sysRoles = sysUser.getSysRoles();
        Set<SysPermission> sysPermissions = null;
        for(SysRole sysRole : sysRoles) {
            sysPermissions = sysRole.getSysPermissions();
            for(SysPermission sysPermission : sysPermissions) {
                permissionIdentities.add(sysPermission.getPermission());
            }
        }
        return permissionIdentities;
    }
}
