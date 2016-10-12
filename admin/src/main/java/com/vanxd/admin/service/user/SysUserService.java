package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysUserMapper;

import java.util.Set;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysUserService extends BaseService<SysUser, SysUserMapper>{

    /**
     * 根据用户名获得用户数据
     * @param username 用户名
     * @return  用户对象
     */
    SysUser getByUsername(String username);

    /**
     * todo 改为一条SQL
     * 获得用户的所有权限识别码
     * @param sysUser 用户对象
     * @return set 权限识别码列表
     */
    Set<String> getPermissionIdentities(SysUser sysUser);

    /**
     * todo 改为一条sql
     *
     * 获得用户的所有角色识别码
     *
     * @param sysUser 用户对象
     * @return set 角色识别码列表
     */
    Set<String> getRoleIdentities(SysUser sysUser);
}
