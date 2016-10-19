package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.mapper.user.SysRoleMapper;

import java.util.List;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleMapper>{
    /**
     * 获得所有角色，并且标记用户已关联的角色
     * @param userId
     * @return
     */
    List<SysRole> findByUserIdAndChecked(String userId);
}
