package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysUserMapper;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysUserService extends BaseService<SysUser, SysUserMapper>{

    SysUser getByUsername(String username);

    /**
     * 添加新用户
     * @param sysUser
     * @return
     */
    boolean add(SysUser sysUser);
}
