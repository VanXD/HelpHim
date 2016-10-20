package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUserRole;
import com.vanxd.data.mapper.user.SysUserRoleMapper;

import java.util.List;

/**
 * @author wyd on 2016/10/20.
 */
public interface SysUserRoleService extends BaseService<SysUserRole, SysUserRoleMapper>{
    /**
     * 根据用户ID和角色ID，解除关联
     * @param userId 必须 用户ID
     * @param roleId 必须 角色ID
     * @return
     */
    boolean cancelRelation(String userId, String roleId);

    /**
     * 获得所有角色，并且标记用户已关联的角色
     * @param userId
     * @return
     */
    List<SysRole> findByUserIdAndChecked(String userId);
}
