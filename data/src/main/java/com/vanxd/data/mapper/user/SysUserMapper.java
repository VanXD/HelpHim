package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUser>{
    SysUser selectByUsername(String username);
}